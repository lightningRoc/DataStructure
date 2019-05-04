import java.util.Scanner;


public class DijkstraSP {
	//利用Dijkstra算法来计算单源最短路径
	//该方法保存所有的与已经遍历过的图相连的点的结构进入优先队列且不删除它，也可以改进优先队列，令其只保存源点到各个点的当前最小的距离,需要可以变更某一点的值的优先队列
	
	private WeightedDigraph.Edge[] edgeTo;//保存到某点
	private double[] distTo;//保存源点到某一点的当前最短路径
	private boolean[] visited;//标识某一点是否计算完成
	private BinaryHeap<Point> pq;
	private int count;//计算放松次数，以便快速结束
	
	private static class Point implements Comparable<Point>
	{
		//保存源点到某点的距离以及该点的标识，方便利用优先队列取到最小的那一个点
		
		private int v;//对应的点
		private double dist;
		
		public Point(int v,double dist)
		{
			this.v=v;
			this.dist=dist;
		}
		
		public int compareTo(Point another)//权重比较函数
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
	
	public DijkstraSP(WeightedDigraph g,int s)//给定图以及源点s，计算s到各点的最短路径
	{
		//计算最短路
		edgeTo=new WeightedDigraph.Edge[g.getV()];
		distTo=new double[g.getV()];
		visited=new boolean[g.getV()];
		count=0;
		pq=new BinaryHeap<>();
		
		for(int v=0;v<g.getV();v++)
		{
			distTo[v]=Double.POSITIVE_INFINITY;//对到各点的距离进行初始化
		}
		
		distTo[s]=0.0;
		visited[s]=true;
		//对点s进行放松操作
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
			//取出下一个要进行放松的点
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
	
	private void relax(WeightedDigraph g,int v)//对顶点v进行放松操作
	{
		for(WeightedDigraph.Edge e:g.adj(v))
		{
			int w=e.to();
			if(visited[w])continue;
			if(distTo[w]>distTo[v]+e.getWeight())
			{
				//System.out.println(distTo[v]+"+"+e.getWeight()+"="+(distTo[v]+e.getWeight())+"->"+distTo[w]);
				//distTo[w]=distTo[v]+e.getWeight();
				//简单修复一下精度问题
				distTo[w]=(distTo[v]*1000+e.getWeight()*1000)/1000;
				edgeTo[w]=e;
				pq.insert(new Point(w,distTo[w]));
			}
		}
	}
	
	public double distTo(int v)
	{
		//返回到某点的最短路径
		return distTo[v];
	}
	
	public WeightedDigraph.Edge edgeTo(int v)
	{
		//返回到某点对应的边
		return edgeTo[v];
	}
	
	public void PathTo(int v)
	{
		//打印到某点的最短路径
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
		
		
		System.out.println("各点的最短路:");
		for(int v=0;v<g.getV();v++)
		{
			WeightedDigraph.Edge e=sp.edgeTo(v);
			if(!(e==null))System.out.println("edgeTo:"+e.from()+"->"+e.to()+":"+e.getWeight()+"   "+v+":"+sp.distTo(v));
			System.out.println("整个路径:");
			sp.PathTo(v);
		}
	}

}
