import java.util.Scanner;

public class WeightedGraph {
	//ʹ���ڽӱ�ʵ�ֵļ�Ȩ����ͼ
	
	public static class Edge implements Comparable<Edge>//��ʾһ����
	{
		private int v;//��ʾ�ߵĶ���
		private int w;//��ʾ��һ������
		private double weight;//�ߵ�Ȩ��
		
		public Edge(int v,int w,double weight)
		{
			this.v=v;
			this.w=w;
			this.weight=weight;
		}
		
		public double getWeight()
		{
			return weight;
		}
		
		public int either()//��ȡ����һ������
		{
			return v;
		}
		
		public int other(int v)//���ظ����ñߵĶ���Ķ�Ӧ����һ����
		{
			if(v==this.v)return this.w;
			else return this.v;
		}
		
		public int compareTo(Edge another)//Ȩ�رȽϺ���
		{
			if(this.getWeight()<another.getWeight())return -1;
			else if(this.getWeight()>another.getWeight())return 1;
			else return 0;
		}
	}
	
	private int V;//������
	private int E;//����
	private MyLinkedList<Edge>[] adj;//�ڽӱ�
	
	public WeightedGraph(int V)
	{
		//��������������һ��Ȩ����ͼ
		this.V=V;
		this.E=0;
		adj=(MyLinkedList<Edge>[])new MyLinkedList[V];
		for(int v=0;v<V;v++)
		{
			adj[v]=new MyLinkedList<Edge>();
		}
	}
	
	public WeightedGraph(Scanner in)
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
	
	public void addEdge(Edge e)//���һ����
	{
		int v=e.either();
		int w=e.other(v);
		
		adj[v].add(e);
		adj[w].add(e);
		E++;
	}
	
	public Iterable<Edge> adj(int v)//�����붥��v���������б�
	{
		return adj[v];
	}
	
	public int getV()
	{
		return V;
	}
	
	public int getE()
	{
		return E;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		WeightedGraph g=new WeightedGraph(in);
		for(int i=0;i<g.getV();i++)
		{
			System.out.print("point "+i+":");
			for(Edge e:g.adj(i))
			{
				int v=e.either();
				int w=e.other(v);
				System.out.print(w+":"+e.getWeight()+"  ");
			}
			System.out.println();
		}
	}

}
