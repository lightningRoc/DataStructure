import java.util.Scanner;

public class Graph {
	//ʹ���ڽӱ��ʾ������ͼ
	
	private int V;//������
	private int E;//�ߵ���Ŀ
	private MyLinkedList<Integer>[] adj;//�ڽӱ�
	
	public Graph(int V)//���ݸ�����������ʼ��ͼ
	{
		this.V=V;
		this.E=0;
		adj=(MyLinkedList<Integer>[])new MyLinkedList[V];
		for(int v=0;v<V;v++)
			adj[v]=new MyLinkedList<Integer>();
	}
	
	public Graph(Scanner in)//��������������Ҫ��ͼ
	{
		this(in.nextInt());
		int n=in.nextInt();
		for(int i=0;i<n;i++)
		{
			//��ȡ������
			int v=in.nextInt();
			int w=in.nextInt();
			addEdge(v,w);
		}
	}
	
	public void addEdge(int v,int w)//���һ����v,w�ֱ�Ϊ��������
	{
		adj[v].add(w);
		adj[w].add(v);
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
	
	public Iterable<Integer> adj(int v)//���ض���v�ĵ�����
	{
		return adj[v];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		Graph g=new Graph(in);
		for(int i=0;i<g.getV();i++)
		{
			System.out.print("point "+i+":");
			for(int n:g.adj(i))
			{
				System.out.print(n+" ");
			}
			System.out.println();
		}
	}

}
