import java.util.Scanner;


public class DijkstraSP {
	//����Dijkstra�㷨�����㵥Դ���·��
	//�÷����������е����Ѿ���������ͼ�����ĵ�Ľṹ�������ȶ����Ҳ�ɾ������Ҳ���ԸĽ����ȶ��У�����ֻ����Դ�㵽������ĵ�ǰ��С�ľ���,��Ҫ���Ա��ĳһ���ֵ�����ȶ���
	
	private WeightedDigraph.Edge[] edgeTo;//���浽ĳ��
	private double[] distTo;//����Դ�㵽ĳһ��ĵ�ǰ���·��
	private boolean[] visited;//��ʶĳһ���Ƿ�������
	private BinaryHeap<Point> pq;
	private int count;//������ɴ������Ա���ٽ���
	
	private static class Point implements Comparable<Point>
	{
		//����Դ�㵽ĳ��ľ����Լ��õ�ı�ʶ�������������ȶ���ȡ����С����һ����
		
		private int v;//��Ӧ�ĵ�
		private double dist;
		
		public Point(int v,double dist)
		{
			this.v=v;
			this.dist=dist;
		}
		
		public int compareTo(Point another)//Ȩ�رȽϺ���
		{
			if(this.getDist()<another.getDist())return -1;
			else if(this.getDist()>another.getDist())return 1;
			else return 0;
		}
		
		public int getV()
		{
			return v;
		}
		
		public double getDist()
		{
			return dist;
		}
	}
	
	public DijkstraSP(WeightedDigraph g,int s)//����ͼ�Լ�Դ��s������s����������·��
	{
		//�������·
		edgeTo=new WeightedDigraph.Edge[g.getV()];
		distTo=new double[g.getV()];
		visited=new boolean[g.getV()];
		count=0;
		pq=new BinaryHeap<>();
		
		for(int v=0;v<g.getV();v++)
		{
			distTo[v]=Double.POSITIVE_INFINITY;//�Ե�����ľ�����г�ʼ��
		}
		
		distTo[s]=0.0;
		visited[s]=true;
		//�Ե�s���з��ɲ���
		for(WeightedDigraph.Edge e:g.adj(s))
		{
			int w=e.to();
			distTo[w]=e.getWeight();
			edgeTo[w]=e;
			pq.insert(new Point(w,distTo[w]));
		}
		
		while((!pq.isEmpty())&&(count<g.getV()))
		{
			WeightedDigraph.Edge e=edgeTo[pq.deleteMin().getV()];
			//ȡ����һ��Ҫ���з��ɵĵ�
			int next=e.to();
			if(visited[next])continue;
			else
			{
				visited[next]=true;
				relax(g,next);
				count++;
				System.out.println(next);
			}
		}
	}
	
	private void relax(WeightedDigraph g,int v)//�Զ���v���з��ɲ���
	{
		for(WeightedDigraph.Edge e:g.adj(v))
		{
			int w=e.to();
			if(visited[w])continue;
			if(distTo[w]>distTo[v]+e.getWeight())
			{
				//System.out.println(distTo[v]+"+"+e.getWeight()+"="+(distTo[v]+e.getWeight())+"->"+distTo[w]);
				//distTo[w]=distTo[v]+e.getWeight();
				//���޸�һ�¾�������
				distTo[w]=(distTo[v]*1000+e.getWeight()*1000)/1000;
				edgeTo[w]=e;
				pq.insert(new Point(w,distTo[w]));
			}
		}
	}
	
	public double distTo(int v)
	{
		//���ص�ĳ������·��
		return distTo[v];
	}
	
	public WeightedDigraph.Edge edgeTo(int v)
	{
		//���ص�ĳ���Ӧ�ı�
		return edgeTo[v];
	}
	
	public void PathTo(int v)
	{
		//��ӡ��ĳ������·��
		while(true)
		{
			System.out.print(v+"<-");
			if(edgeTo[v]==null)break;
			v=(edgeTo[v].from());
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		WeightedDigraph g=new WeightedDigraph(in);
		DijkstraSP sp=new DijkstraSP(g,0);
		
		
		System.out.println("��������·:");
		for(int v=0;v<g.getV();v++)
		{
			WeightedDigraph.Edge e=sp.edgeTo(v);
			if(!(e==null))System.out.println("edgeTo:"+e.from()+"->"+e.to()+":"+e.getWeight()+"   "+v+":"+sp.distTo(v));
			System.out.println("����·��:");
			sp.PathTo(v);
		}
	}

}
