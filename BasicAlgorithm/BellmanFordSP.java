import java.util.Scanner;

public class BellmanFordSP {
	//使用基于队列的Bellman-Ford算法计算一般的无负权重环的图的单源最短路径
	
	private double[] distTo;//从起点都某点的当前路径长度
	private WeightedDigraph.Edge edgeTo[];//到某点的最短路径应该经过的相邻的边
	private boolean[] onQ;//判断顶点是否在队列中(即上次是否处理过，处理过的点才有可能在下一次判断中进行放松操作,从而省去无意义的判断)
	private MyQueue<Integer> queue;//利用队列来搜索点
	private int cost;//relax()的调用次数
	private Iterable<Integer> cycle;//保存图的负权重环(如果有的话)
	
	public BellmanFordSP(WeightedDigraph g,int s)
	{
		distTo=new double[g.getV()];
		edgeTo=new WeightedDigraph.Edge[g.getV()];
		onQ=new boolean[g.getV()];
		queue=new MyQueue<>();
		
		//一般步骤:1、起点距离置为0,2、其他点置为无穷，3、任意顺序放松所有边，重复V次
		for(int v=0;v<g.getV();v++)
		{
			distTo[v]=Double.POSITIVE_INFINITY;
		}
		distTo[s]=0;
		queue.enqueue(s);
		onQ[s]=true;
		
		while(!queue.isEmpty()&&!hasNegativeCycly())
		{
			int v=queue.dequeue();
			onQ[v]=false;
			relax(g, v);
		}
		
	}
	
	private void relax(WeightedDigraph g,int v)
	{
		//对点v进行放松
		for(WeightedDigraph.Edge e:g.adj(v))
		{
			int w=e.to();
			if(distTo[w]>distTo[v]+e.getWeight())
			{
				distTo[w]=distTo[v]+e.getWeight();
				edgeTo[w]=e;
				if(!onQ[w])
				{
					//放松成功的点加入下一次应该放松的行列
					queue.enqueue(w);
					onQ[w]=true;
				}
			}
			
			//对所有边的放松是否完成了V轮,是则可以寻找是否有负权重边,若含有负权重边当放松了V轮之后edgeTo数组中必定含有一个环切为负权重环
			if(cost++%g.getV()==0)
			{
				findNegativeCycle();
			}
		}
	}
	
	private void findNegativeCycle()//寻找当前是否有负权重环
	{
		int V=edgeTo.length;
		WeightedDigraph auxG;
		auxG=new WeightedDigraph(V);
		//创建放松的V轮后的图的子图(到各点的当前最小路径过的所有边)
		for(int v=0;v<V;v++)
		{
			if(edgeTo[v]!=null)
			{
				auxG.addEdge(edgeTo[v]);
			}
		}
		
		WeightedDigraphCycle finder=new WeightedDigraphCycle(auxG);
		
		cycle=finder.cycle();
	}
	
	public boolean hasNegativeCycly()//图是否有负权重环
	{
		return cycle!=null;
	}
	
	public Iterable<Integer> negativeCycle()//负权重环的迭代器
	{
		return cycle;
	}
	
	private class WeightedDigraphCycle//判断有向图是否有环
	{
		private boolean[] visited;//记录遍历过的顶点
		private int[] path;//记录路径
		private MyStack<Integer> cycle;//保存环本身
		private boolean[] onStack;//记录程序栈中的顶点
		
		public WeightedDigraphCycle(WeightedDigraph g)
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
		
		private void dfs(WeightedDigraph g,int v)//对图g中的顶点进行深度优先遍历
		{
			onStack[v]=true;//v进入程序栈中
			visited[v]=true;//v遍历过
			
			for(WeightedDigraph.Edge e:g.adj(v))
			{
				//遍历v的邻接点
				int w=e.to();
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
	
	public WeightedDigraph.Edge edgeTo(int v)
	{
		return edgeTo[v];
	}
	
	public double distTo(int v)
	{
		return distTo[v];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		WeightedDigraph g=new WeightedDigraph(in);
		BellmanFordSP sp=new BellmanFordSP(g,0);
		
		
		if(sp.hasNegativeCycly())
		{
			System.out.println("图含有负权重环");
			for(int v:sp.negativeCycle())
			{
				System.out.print(v+"->");
			}
		}
		else
		{
			System.out.println("各点的最短路:");
			for(int v=0;v<g.getV();v++)
			{
				WeightedDigraph.Edge e=sp.edgeTo(v);
				if(!(e==null))System.out.println("edgeTo:"+e.from()+"->"+e.to()+":"+e.getWeight()+"   "+v+":"+sp.distTo(v));
			}
		}
	}

}
