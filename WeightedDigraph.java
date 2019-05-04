import java.util.Scanner;

public class WeightedDigraph {
	//ʹ���ڽӱ�ʵ�ֵļ�Ȩ����ͼ
	
	public static class Edge implements Comparable<Edge>
	{
		//��ʾ����ͼ�ı�
		private int v;//�ߵ����
		private int w;//�ߵĳ���
		private double weight;//�ߵ�Ȩ��
		
		public Edge(int v,int w,double weight)
		{
			this.v=v;
			this.w=w;
			this.weight=weight;
		}
		
		public int compareTo(Edge another)//Ȩ�رȽϺ���
		{
			if(this.getWeight()<another.getWeight())return -1;
			else if(this.getWeight()>another.getWeight())return 1;
			else return 0;
		}
		
		public double getWeight()
		{
			return weight;
		}
		
		public int from()//��ȡ���
		{
			return v;
		}
		
		public int to()//��ȡ����
		{
			return w;
		}
	}
	
	private int V;//������
	private int E;//����
	private MyLinkedList<Edge>[] adj;//�ڽӱ�
	
	public WeightedDigraph(int V)
	{
		this.V=V;
		this.E=0;
		adj=(MyLinkedList<Edge>[])new MyLinkedList[V];
		for(int v=0;v<V;v++)
		{
			adj[v]=new MyLinkedList<Edge>();
		}
	}
	
	public WeightedDigraph(Scanner in)
	{
		//����������������Ȩ����ͼ
		this(in.nextInt());
		int n=in.nextInt();
		for(int i=0;i<n;i++)
		{
			//��ȡ����������Ӧ��Ȩ��
			int v=in.nextInt();
			int w=in.nextInt();
			double weight=in.nextDouble();
			Edge e=new Edge(v,w,weight);
			addEdge(e);
		}
	}
	
	public int getV()
	{
		return V;
	}
	
	public int getE()
	{
		return E;
	}
	
	public void addEdge(Edge e)
	{
		adj[e.from()].add(e);
		E++;
	}
	
	public Iterable<Edge> adj(int v)
	{
		return adj[v];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		WeightedDigraph g=new WeightedDigraph(in);
		for(int i=0;i<g.getV();i++)
		{
			System.out.print("point "+i+":");
			for(Edge e:g.adj(i))
			{
				int v=e.from();
				int w=e.to();
				System.out.print(v+"->"+w+":"+e.getWeight()+"  ");
			}
			System.out.println();
		}
	}

}
