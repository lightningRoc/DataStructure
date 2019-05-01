import java.util.Scanner;

public class Topological {
	
	private Iterable<Integer> order;//顶点拓扑序
	
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
	
	public Topological(Digraph g)
	{
		g.findCycle();//判断是否有环,无则可以进行拓扑排序
		if(!g.hasCycle())
		{
			DFSOrder dfs=new DFSOrder(g);
			order=dfs.reversePost();//可证明，图的深度优先搜索的顶点的逆后序是图的拓扑序
		}
	}
	
	public Iterable<Integer> order()//图拓扑序
	{
		return order;
	}
	
	public boolean isDAG()//图是否为有向无环图
	{
		return order!=null;
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
		
		Topological getTopo=new Topological(g);
		if(getTopo.isDAG())
		{
			System.out.println("图无环,拓扑序:");
			for(int i:getTopo.order())
			{
				System.out.print(i+"->");
			}
		}
		else
		{
			System.out.println("图有环");
		}
	}

}
