import java.util.Scanner;

public class Digraph {
	//使用邻接表表示的有向图

	private int V;//顶点数
	private int E;//边的数目
	private MyLinkedList<Integer>[] adj;//邻接表
	private DigraphCycle aux;//判断是否有环的辅助类
	
	public Digraph(int V)//根据给定顶点数初始化图
	{
		this.V=V;
		this.E=0;
		adj=(MyLinkedList<Integer>[])new MyLinkedList[V];
		for(int v=0;v<V;v++)
			adj[v]=new MyLinkedList<Integer>();
	}
	
	public Digraph(Scanner in)//从输入流构造需要的图
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
	
	public void addEdge(int v,int w)//添加一条边v为出点，w为入点
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
	
	public Iterable<Integer> adj(int v)//返回顶点v的迭代器
	{
		return adj[v];
	}
	
	private class DigraphCycle//判断有向图是否有环
	{
		private boolean[] visited;//记录遍历过的顶点
		private int[] path;//记录路径
		private MyStack<Integer> cycle;//保存环本身
		private boolean[] onStack;//记录程序栈中的顶点
		
		public DigraphCycle(Digraph g)
		{
			onStack=new boolean[g.getV()];
			path=new int[g.getV()];
			cycle=null;
			visited=new boolean[g.getV()];
			for(int v=0;v<g.getV();v++)
			{
				//深度优先方式遍历图
				if(!visited[v])dfs(g,v);
			}
		}
		
		private void dfs(Digraph g,int v)//对图g中的顶点进行深度优先遍历
		{
			onStack[v]=true;//v进入程序栈中
			visited[v]=true;//v遍历过
			
			for(int w:g.adj(v))
			{
				//遍历v的邻接点
				if(this.hasCycle())return;
				else if(!visited[w])
				{
					//继续遍历没有遍历过的邻接点
					path[w]=v;//通过记录点的上一个点来记录一条路径
					dfs(g,w);
				}
				else if(onStack[w])//当正在遍历的顶点与还在程序栈中的顶点邻接时，则发现环
				{
					cycle=new MyStack<Integer>();
					for(int i=v;i!=w;i=path[i])
					{
						cycle.push(i);//记录环
					}
					cycle.push(w);
					cycle.push(v);
				}
			}
			
			onStack[v]=false;//当方法结束时，v不在程序栈中
		}
		
		public boolean hasCycle()//图是否有环
		{
			return cycle!=null;
		}
		
		public Iterable<Integer> cycle()
		{
			return cycle;//环的迭代器
		}
	}
	
	public void findCycle()//判断是否有环
	{
		aux=new DigraphCycle(this);
	}
	
	public boolean hasCycle()//是否有环
	{
		return aux.hasCycle();
	}
	
	public Iterable<Integer> cycle()//环的迭代器
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
		System.out.println("是否有环:"+g.hasCycle());
		if(g.hasCycle())
		{
			for(int i:g.cycle())
			{
				System.out.print(i+"->");
			}
		}
	}

}
