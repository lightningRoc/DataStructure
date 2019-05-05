import java.math.BigDecimal;
import java.util.Scanner;

public class AcyclicSP {
	//计算加权无环图的单源最短路径
	//按照图的拓扑序进行点的放松即可得到到各个点之间的最短路
	
	private WeightedDigraph.Edge[] edgeTo;//到某点的最短距离应经过哪条边
	private double[] distTo;//到各点的当前最短距离
	
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
			//按照拓扑序对点进行放松操作,由于对点v进行放松后，不会再处理任何指向v的边，因此每次循环我们都能够找出当前点的最短路径
			relax(g, v);
		}
	}
	
	private void relax(WeightedDigraph g,int v)
	{
		//都一点进行放松操作
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
		
		//为快速处理，我们仅通过再次输入同样的同来建立无权的有向图
		WeightedDigraph g=new WeightedDigraph(in);
		Digraph aux=new Digraph(in);
		
		AcyclicSP sp=new AcyclicSP(g,aux,5);
		
		
		System.out.println("各点的最短路:");
		for(int v=0;v<g.getV();v++)
		{
			WeightedDigraph.Edge e=sp.edgeTo(v);
			if(!(e==null))System.out.println("edgeTo:"+e.from()+"->"+e.to()+":"+e.getWeight()+"   "+v+":"+sp.distTo(v));
		}
	}

}
