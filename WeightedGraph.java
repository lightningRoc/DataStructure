import java.util.Scanner;

public class WeightedGraph {
	//使用邻接表实现的加权无向图
	
	public static class Edge implements Comparable<Edge>//表示一条边
	{
		private int v;//表示边的顶点
		private int w;//表示另一个顶点
		private double weight;//边的权重
		
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
		
		public int either()//获取其中一个顶点
		{
			return v;
		}
		
		public int other(int v)//返回给定该边的顶点的对应的另一顶点
		{
			if(v==this.v)return this.w;
			else return this.v;
		}
		
		public int compareTo(Edge another)//权重比较函数
		{
			if(this.getWeight()<another.getWeight())return -1;
			else if(this.getWeight()>another.getWeight())return 1;
			else return 0;
		}
	}
	
	private int V;//顶点数
	private int E;//边数
	private MyLinkedList<Edge>[] adj;//邻接表
	
	public WeightedGraph(int V)
	{
		//给定顶点数创建一加权无向图
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
	
	public void addEdge(Edge e)//添加一条边
	{
		int v=e.either();
		int w=e.other(v);
		
		adj[v].add(e);
		adj[w].add(e);
		E++;
	}
	
	public Iterable<Edge> adj(int v)//返回与顶点v相连的所有边
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
