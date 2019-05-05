import java.math.BigDecimal;
import java.util.Scanner;

public class AcyclicSP {
	//�����Ȩ�޻�ͼ�ĵ�Դ���·��
	//����ͼ����������е�ķ��ɼ��ɵõ���������֮������·
	
	private WeightedDigraph.Edge[] edgeTo;//��ĳ�����̾���Ӧ����������
	private double[] distTo;//������ĵ�ǰ��̾���
	
	public AcyclicSP(WeightedDigraph g,Digraph noWeight,int s)
	{
		edgeTo=new WeightedDigraph.Edge[g.getV()];
		distTo=new double[g.getV()];
		
		for(int v=0;v<g.getV();v++)
		{
			distTo[v]=Double.POSITIVE_INFINITY;
		}
		distTo[s]=0;
		
		Topological aux=new Topological(noWeight);
		
		for(int v:aux.order())
		{
			//����������Ե���з��ɲ���,���ڶԵ�v���з��ɺ󣬲����ٴ����κ�ָ��v�ıߣ����ÿ��ѭ�����Ƕ��ܹ��ҳ���ǰ������·��
			relax(g, v);
		}
	}
	
	private void relax(WeightedDigraph g,int v)
	{
		//��һ����з��ɲ���
		for(WeightedDigraph.Edge e:g.adj(v))
		{
			int w=e.to();
			if(distTo[w]>distTo[v]+e.getWeight())
			{
				distTo[w]=distTo[v]+e.getWeight();
				edgeTo[w]=e;
			}
		}
	}
	
	public double distTo(int v)
	{
		return distTo[v];
	}
	
	public WeightedDigraph.Edge edgeTo(int v)
	{
		return edgeTo[v];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		
		//Ϊ���ٴ������ǽ�ͨ���ٴ�����ͬ����ͬ��������Ȩ������ͼ
		WeightedDigraph g=new WeightedDigraph(in);
		Digraph aux=new Digraph(in);
		
		AcyclicSP sp=new AcyclicSP(g,aux,5);
		
		
		System.out.println("��������·:");
		for(int v=0;v<g.getV();v++)
		{
			WeightedDigraph.Edge e=sp.edgeTo(v);
			if(!(e==null))System.out.println("edgeTo:"+e.from()+"->"+e.to()+":"+e.getWeight()+"   "+v+":"+sp.distTo(v));
		}
	}

}
