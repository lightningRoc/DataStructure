import java.util.Scanner;

public class KruskalMST {
	//利用kruskal算法来生成最小生成树
	//利用优先队列来获得最小的边，利用union-find算法来判断来个点是否在同一个连通分量(同一个生成树中)
	
	private MyLinkedList<WeightedGraph.Edge> mst;//最小生成树
	
	public KruskalMST(WeightedGraph g)
	{
		mst=new MyLinkedList<WeightedGraph.Edge>();
		BinaryHeap<WeightedGraph.Edge> pq=new BinaryHeap<>();
		//所有边加入优先队列
		for(int v=0;v<g.getV();v++)
		{
			for(WeightedGraph.Edge e:g.adj(v))
			{
				if(e.other(v)<v)continue;//v对应的边已经添加过
				pq.insert(e);
			}
		}
		UnionFindWeightedCompression uf=new UnionFindWeightedCompression(g.getV());
		
		while((!pq.isEmpty())&&mst.size()<g.getV()-1)
		{
			//寻找最小的边,如果其有效，加入最小生成树(各个子树)
			WeightedGraph.Edge e=pq.deleteMin();
			int v=e.either();
			int w=e.other(v);
			if(uf.connected(v, w))continue;//连接v,w的边已经在同一个生成树中,边失效
			uf.union(v, w);
			mst.add(e);
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
		KruskalMST mst=new KruskalMST(g);
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
