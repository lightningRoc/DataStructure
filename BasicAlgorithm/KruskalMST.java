import java.util.Scanner;

public class KruskalMST {
	//����kruskal�㷨��������С������
	//�������ȶ����������С�ıߣ�����union-find�㷨���ж��������Ƿ���ͬһ����ͨ����(ͬһ����������)
	
	private MyLinkedList<WeightedGraph.Edge> mst;//��С������
	
	public KruskalMST(WeightedGraph g)
	{
		mst=new MyLinkedList<WeightedGraph.Edge>();
		BinaryHeap<WeightedGraph.Edge> pq=new BinaryHeap<>();
		//���б߼������ȶ���
		for(int v=0;v<g.getV();v++)
		{
			for(WeightedGraph.Edge e:g.adj(v))
			{
				if(e.other(v)<v)continue;//v��Ӧ�ı��Ѿ���ӹ�
				pq.insert(e);
			}
		}
		UnionFindWeightedCompression uf=new UnionFindWeightedCompression(g.getV());
		
		while((!pq.isEmpty())&&mst.size()<g.getV()-1)
		{
			//Ѱ����С�ı�,�������Ч��������С������(��������)
			WeightedGraph.Edge e=pq.deleteMin();
			int v=e.either();
			int w=e.other(v);
			if(uf.connected(v, w))continue;//����v,w�ı��Ѿ���ͬһ����������,��ʧЧ
			uf.union(v, w);
			mst.add(e);
		}
	}
	
	public Iterable<WeightedGraph.Edge> edges()
	{
		//������С�������߶�Ӧ�ĵ�����
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
		
		System.out.println("��Ȩ��:"+total);
	}

}
