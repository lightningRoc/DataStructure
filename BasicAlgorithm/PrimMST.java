import java.util.Scanner;

public class PrimMST {
	//ʹ��Prim�㷨���ɸ���ͼ����С������
	//�÷����������е��������������ı߽������ȶ����Ҳ�ɾ������Ҳ���ԸĽ����ȶ��У�����ֻ��������㵽�������ĵ�ǰ��С�ľ���
	//(��Ҫ��������ݽṹ�����ٻ�ȡĳ���Ӧ����С����)
	
	private MyLinkedList<WeightedGraph.Edge> mst;//������С������
	private boolean[] visited;//��ǵ��Ƿ��Ѿ�������С������
	private BinaryHeap<WeightedGraph.Edge> pq;//�������ȶ���

	public PrimMST(WeightedGraph g)
	{
		pq=new BinaryHeap<WeightedGraph.Edge>();
		visited=new boolean[g.getV()];
		mst=new MyLinkedList<WeightedGraph.Edge>();
		
		join(g,0);//�ӵ�0��ʼ
		while(!pq.isEmpty())
		{
			WeightedGraph.Edge e=pq.deleteMin();//��һ����
			
			int v=e.either();
			int w=e.other(v);
			if(visited[v]&&visited[w])
			{
				//���Ѿ�����������
				continue;
			}
			
			mst.add(e);
			//�����޷�֪������ıߵ���һ�����Ѿ�������,����ж����㲢�Ҽ��������Ӧ���жϵı�
			if(!visited[v])join(g,v);
			if(!visited[w])join(g,w);
		}
	}
	
	private void join(WeightedGraph g,int v)//��v������С��������
	{
		visited[v]=true;
		for(WeightedGraph.Edge e:g.adj(v))
		{
			//�����������������ڵķ��������ı߼������ȶ���
			if(!visited[e.other(v)])
			{
				pq.insert(e);
			}
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
		PrimMST mst=new PrimMST(g);
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
