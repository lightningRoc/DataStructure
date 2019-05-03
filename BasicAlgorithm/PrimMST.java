import java.util.Scanner;

public class PrimMST {
	//使用Prim算法生成给定图的最小生成树
	//该方法保存所有的与生成树相连的边进入优先队列且不删除它，也可以改进优先队列，令其只保存各个点到生成树的当前最小的距离
	//(需要额外的数据结构来快速获取某点对应的最小距离)
	
	private MyLinkedList<WeightedGraph.Edge> mst;//保存最小生成树
	private boolean[] visited;//标记点是否已经加入最小生成树
	private BinaryHeap<WeightedGraph.Edge> pq;//辅助优先队列

	public PrimMST(WeightedGraph g)
	{
		pq=new BinaryHeap<WeightedGraph.Edge>();
		visited=new boolean[g.getV()];
		mst=new MyLinkedList<WeightedGraph.Edge>();
		
		join(g,0);//从点0开始
		while(!pq.isEmpty())
		{
			WeightedGraph.Edge e=pq.deleteMin();//下一条边
			
			int v=e.either();
			int w=e.other(v);
			if(visited[v]&&visited[w])
			{
				//边已经在生成树中
				continue;
			}
			
			mst.add(e);
			//由于无法知道加入的边的哪一个点已经在树中,因此判断两点并且加入接下来应该判断的边
			if(!visited[v])join(g,v);
			if(!visited[w])join(g,w);
		}
	}
	
	private void join(WeightedGraph g,int v)//将v加入最小生成树中
	{
		visited[v]=true;
		for(WeightedGraph.Edge e:g.adj(v))
		{
			//将所有与生成树相邻的非生成树的边加入优先队列
			if(!visited[e.other(v)])
			{
				pq.insert(e);
			}
		}
	}
	
	public Iterable<WeightedGraph.Edge> edges()
	{
		//返回最小生成树边对应的迭代器
		return mst;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		WeightedGraph g=new WeightedGraph(in);
		PrimMST mst=new PrimMST(g);
		double total=0;
		for(WeightedGraph.Edge e:mst.edges())
		{
			int v=e.either();
			int w=e.other(v);
			System.out.println(v+"-"+w+":"+e.getWeight());
			total+=e.getWeight();
		}
		System.out.println("总权重:"+total);
	}

}
