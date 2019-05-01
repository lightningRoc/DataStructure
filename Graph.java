import java.util.Scanner;

public class Graph {
	//使用邻接表表示的无向图
	
	private int V;//顶点数
	private int E;//边的数目
	private MyLinkedList<Integer>[] adj;//邻接表
	
	public Graph(int V)//根据给定顶点数初始化图
	{
		this.V=V;
		this.E=0;
		adj=(MyLinkedList<Integer>[])new MyLinkedList[V];
		for(int v=0;v<V;v++)
			adj[v]=new MyLinkedList<Integer>();
	}
	
	public Graph(Scanner in)//从输入流构造需要的图
	{
		this(in.nextInt());
		int n=in.nextInt();
		for(int i=0;i<n;i++)
		{
			//读取两个点
			int v=in.nextInt();
			int w=in.nextInt();
			addEdge(v,w);
		}
	}
	
	public void addEdge(int v,int w)//添加一条边v,w分别为两个顶点
	{
		adj[v].add(w);
		adj[w].add(v);
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
	
	public Iterable<Integer> adj(int v)//返回顶点v的迭代器
	{
		return adj[v];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		Graph g=new Graph(in);
		for(int i=0;i<g.getV();i++)
		{
			System.out.print("point "+i+":");
			for(int n:g.adj(i))
			{
				System.out.print(n+" ");
			}
			System.out.println();
		}
	}

}
