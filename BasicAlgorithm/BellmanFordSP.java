import java.util.Scanner;

public class BellmanFordSP {
	//ʹ�û��ڶ��е�Bellman-Ford�㷨����һ����޸�Ȩ�ػ���ͼ�ĵ�Դ���·��
	
	private double[] distTo;//����㶼ĳ��ĵ�ǰ·������
	private WeightedDigraph.Edge edgeTo[];//��ĳ������·��Ӧ�þ��������ڵı�
	private boolean[] onQ;//�ж϶����Ƿ��ڶ�����(���ϴ��Ƿ������������ĵ���п�������һ���ж��н��з��ɲ���,�Ӷ�ʡȥ��������ж�)
	private MyQueue<Integer> queue;//���ö�����������
	private int cost;//relax()�ĵ��ô���
	private Iterable<Integer> cycle;//����ͼ�ĸ�Ȩ�ػ�(����еĻ�)
	
	public BellmanFordSP(WeightedDigraph g,int s)
	{
		distTo=new double[g.getV()];
		edgeTo=new WeightedDigraph.Edge[g.getV()];
		onQ=new boolean[g.getV()];
		queue=new MyQueue<>();
		
		//һ�㲽��:1����������Ϊ0,2����������Ϊ���3������˳��������бߣ��ظ�V��
		for(int v=0;v<g.getV();v++)
		{
			distTo[v]=Double.POSITIVE_INFINITY;
		}
		distTo[s]=0;
		queue.enqueue(s);
		onQ[s]=true;
		
		while(!queue.isEmpty()&&!hasNegativeCycly())
		{
			int v=queue.dequeue();
			onQ[v]=false;
			relax(g, v);
		}
		
	}
	
	private void relax(WeightedDigraph g,int v)
	{
		//�Ե�v���з���
		for(WeightedDigraph.Edge e:g.adj(v))
		{
			int w=e.to();
			if(distTo[w]>distTo[v]+e.getWeight())
			{
				distTo[w]=distTo[v]+e.getWeight();
				edgeTo[w]=e;
				if(!onQ[w])
				{
					//���ɳɹ��ĵ������һ��Ӧ�÷��ɵ�����
					queue.enqueue(w);
					onQ[w]=true;
				}
			}
			
			//�����бߵķ����Ƿ������V��,�������Ѱ���Ƿ��и�Ȩ�ر�,�����и�Ȩ�رߵ�������V��֮��edgeTo�����бض�����һ������Ϊ��Ȩ�ػ�
			if(cost++%g.getV()==0)
			{
				findNegativeCycle();
			}
		}
	}
	
	private void findNegativeCycle()//Ѱ�ҵ�ǰ�Ƿ��и�Ȩ�ػ�
	{
		int V=edgeTo.length;
		WeightedDigraph auxG;
		auxG=new WeightedDigraph(V);
		//�������ɵ�V�ֺ��ͼ����ͼ(������ĵ�ǰ��С·���������б�)
		for(int v=0;v<V;v++)
		{
			if(edgeTo[v]!=null)
			{
				auxG.addEdge(edgeTo[v]);
			}
		}
		
		WeightedDigraphCycle finder=new WeightedDigraphCycle(auxG);
		
		cycle=finder.cycle();
	}
	
	public boolean hasNegativeCycly()//ͼ�Ƿ��и�Ȩ�ػ�
	{
		return cycle!=null;
	}
	
	public Iterable<Integer> negativeCycle()//��Ȩ�ػ��ĵ�����
	{
		return cycle;
	}
	
	private class WeightedDigraphCycle//�ж�����ͼ�Ƿ��л�
	{
		private boolean[] visited;//��¼�������Ķ���
		private int[] path;//��¼·��
		private MyStack<Integer> cycle;//���滷����
		private boolean[] onStack;//��¼����ջ�еĶ���
		
		public WeightedDigraphCycle(WeightedDigraph g)
		{
			onStack=new boolean[g.getV()];
			path=new int[g.getV()];
			cycle=null;
			visited=new boolean[g.getV()];
			for(int v=0;v<g.getV();v++)
			{
				//������ȷ�ʽ����ͼ
				if(!visited[v])dfs(g,v);
			}
		}
		
		private void dfs(WeightedDigraph g,int v)//��ͼg�еĶ������������ȱ���
		{
			onStack[v]=true;//v�������ջ��
			visited[v]=true;//v������
			
			for(WeightedDigraph.Edge e:g.adj(v))
			{
				//����v���ڽӵ�
				int w=e.to();
				if(this.hasCycle())return;
				else if(!visited[w])
				{
					//��������û�б��������ڽӵ�
					path[w]=v;//ͨ����¼�����һ��������¼һ��·��
					dfs(g,w);
				}
				else if(onStack[w])//�����ڱ����Ķ����뻹�ڳ���ջ�еĶ����ڽ�ʱ�����ֻ�
				{
					cycle=new MyStack<Integer>();
					for(int i=v;i!=w;i=path[i])
					{
						cycle.push(i);//��¼��
					}
					cycle.push(w);
					cycle.push(v);
				}
			}
			
			onStack[v]=false;//����������ʱ��v���ڳ���ջ��
		}
		
		public boolean hasCycle()//ͼ�Ƿ��л�
		{
			return cycle!=null;
		}
		
		public Iterable<Integer> cycle()
		{
			return cycle;//���ĵ�����
		}
	}
	
	public WeightedDigraph.Edge edgeTo(int v)
	{
		return edgeTo[v];
	}
	
	public double distTo(int v)
	{
		return distTo[v];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		WeightedDigraph g=new WeightedDigraph(in);
		BellmanFordSP sp=new BellmanFordSP(g,0);
		
		
		if(sp.hasNegativeCycly())
		{
			System.out.println("ͼ���и�Ȩ�ػ�");
			for(int v:sp.negativeCycle())
			{
				System.out.print(v+"->");
			}
		}
		else
		{
			System.out.println("��������·:");
			for(int v=0;v<g.getV();v++)
			{
				WeightedDigraph.Edge e=sp.edgeTo(v);
				if(!(e==null))System.out.println("edgeTo:"+e.from()+"->"+e.to()+":"+e.getWeight()+"   "+v+":"+sp.distTo(v));
			}
		}
	}

}
