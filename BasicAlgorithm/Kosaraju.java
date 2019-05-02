import java.util.Scanner;

public class Kosaraju {
	//利用kosaraju算法来计算有向图的强连通分量
	
	private boolean[] visited;
	private int[] id;//保存某个顶点v对应的强连通分量
	private int count;//强连通分量数量
	
	private class DFSOrder//计算出对图进行深度优先搜索的顶点顺序
	{
		private boolean[] visited;
		private MyStack<Integer> reversePost;//搜索的逆后序
		
		public DFSOrder(Digraph g)
		{
			reversePost=new MyStack<Integer>();
			visited=new boolean[g.getV()];
			for(int v=0;v<g.getV();v++)
			{
				if(!visited[v])
				{
					dfs(g,v);
				}
			}
		}
		
		private void dfs(Digraph g,int v)
		{
			visited[v]=true;
			for(int w:g.adj(v))
			{
				if(!visited[w])
				{
					dfs(g,w);
				}
			}
			
			reversePost.push(v);
		}
		
		public Iterable<Integer> reversePost()//逆后序迭代器
		{
			return reversePost;
		}
	}
	
	public Kosaraju(Digraph g)
	{
		visited=new boolean[g.getV()];
		id=new int[g.getV()];		
		
		DFSOrder aux=new DFSOrder(getReverse(g));//获取反向图顶点深度优先搜索逆后序
		for(int s:aux.reversePost())
		{
			//可证明，对于s可以遇到的没有被标记过的点v，当v在反向图的逆后序的点中位于s之后时，存在v到s的一条路径(类似拓扑序的情况)
			//因此s与v在同一个强连通分量中
			if(!visited[s])
			{
				dfs(g,s);
				count++;
			}
		}
	}
	
	private void dfs(Digraph g,int v)
	{
		visited[v]=true;
		id[v]=count;
		for(int w:g.adj(v))
		{
			if(!visited[w])
			{
				dfs(g,w);
			}
		}
	}
	
	public int id(int v)//获取点v对应的强连通分量id
	{
		return id[v];
	}
	
	public int count()//获取强连通分量数量
	{
		return count;
	}
	
	public Digraph getReverse(Digraph g)//获得图g对应的方向图
	{
		Digraph reverseG=new Digraph(g.getV());
		for(int v=0;v<g.getV();v++)
		{
			for(int w:g.adj(v))
			{
				reverseG.addEdge(w, v);
			}
		}
		
		return reverseG;
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
		
		Kosaraju res=new Kosaraju(g);
		System.out.println("强连通分量数量:"+res.count());
	}

}
