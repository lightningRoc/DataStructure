import java.util.Scanner;

public class Digraph {
	//ʹ���ڽӱ��ʾ������ͼ

	private int V;//������
	private int E;//�ߵ���Ŀ
	private MyLinkedList<Integer>[] adj;//�ڽӱ�
	private DigraphCycle aux;//�ж��Ƿ��л��ĸ�����
	
	public Digraph(int V)//���ݸ�����������ʼ��ͼ
	{
		this.V=V;
		this.E=0;
		adj=(MyLinkedList<Integer>[])new MyLinkedList[V];
		for(int v=0;v<V;v++)
			adj[v]=new MyLinkedList<Integer>();
	}
	
	public Digraph(Scanner in)//��������������Ҫ��ͼ
	{
		this(in.nextInt());
		int n=in.nextInt();
		for(int i=0;i<n;i++)
		{
			//��ȡ������
			int v=in.nextInt();
			int w=in.nextInt();
			addEdge(v,w);
		}
	}
	
	public void addEdge(int v,int w)//���һ����vΪ���㣬wΪ���
	{
		adj[v].add(w);
		E++;
	}
	
	public int getV()
	{
		return V;
	}
	
	public int getE()
	{
		return E;
	}
	
	public Iterable<Integer> adj(int v)//���ض���v�ĵ�����
	{
		return adj[v];
	}
	
	private class DigraphCycle//�ж�����ͼ�Ƿ��л�
	{
		private boolean[] visited;//��¼�������Ķ���
		private int[] path;//��¼·��
		private MyStack<Integer> cycle;//���滷����
		private boolean[] onStack;//��¼����ջ�еĶ���
		
		public DigraphCycle(Digraph g)
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
		
		private void dfs(Digraph g,int v)//��ͼg�еĶ������������ȱ���
		{
			onStack[v]=true;//v�������ջ��
			visited[v]=true;//v������
			
			for(int w:g.adj(v))
			{
				//����v���ڽӵ�
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
	
	public void findCycle()//�ж��Ƿ��л�
	{
		aux=new DigraphCycle(this);
	}
	
	public boolean hasCycle()//�Ƿ��л�
	{
		return aux.hasCycle();
	}
	
	public Iterable<Integer> cycle()//���ĵ�����
	{
		return aux.cycle();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		Digraph g=new Digraph(in);
		for(int i=0;i<g.getV();i++)
		{
			System.out.print("point "+i+":");
			for(int n:g.adj(i))
			{
				System.out.print(n+" ");
			}
			System.out.println();
		}
		
		g.findCycle();
		System.out.println("�Ƿ��л�:"+g.hasCycle());
		if(g.hasCycle())
		{
			for(int i:g.cycle())
			{
				System.out.print(i+"->");
			}
		}
	}

}
