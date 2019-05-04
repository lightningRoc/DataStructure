import java.util.Scanner;

public class WeightedDigraph {
	//使用邻接表实现的加权有向图
	
	public static class Edge implements Comparable<Edge>
	{
		//表示有向图的边
		private int v;//边的入点
		private int w;//边的出点
		private double weight;//边的权重
		
		public Edge(int v,int w,double weight)
		{
			this.v=v;
			this.w=w;
			this.weight=weight;
		}
		
		public int compareTo(Edge another)//权重比较函数
		{
			if(this.getWeight()<another.getWeight())return -1;
			else if(this.getWeight()>another.getWeight())return 1;
			else return 0;
		}
		
		public double getWeight()
		{
			return weight;
		}
		
		public int from()//获取入点
		{
			return v;
		}
		
		public int to()//获取出点
		{
			return w;
		}
	}
	
	private int V;//顶点数
	private int E;//边数
	private MyLinkedList<Edge>[] adj;//邻接表
	
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
		//根据输入流构建加权无向图
		this(in.nextInt());
		int n=in.nextInt();
		for(int i=0;i<n;i++)
		{
			//读取两个点与相应的权重
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
