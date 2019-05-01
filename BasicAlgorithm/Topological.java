import java.util.Scanner;

public class Topological {
	
	private Iterable<Integer> order;//����������
	
	private class DFSOrder//�������ͼ����������������Ķ���˳��
	{
		private boolean[] visited;
		private MyStack<Integer> reversePost;//�����������
		
		public DFSOrder(Digraph g)
		{
			reversePost=new MyStack<Integer>();
			visited=new boolean[g.getV()];
			for(int v=0;v<g.getV();v++)
			{
				if(!visited[v])
				{
					dfs(g,v);
				}
			}
		}
		
		private void dfs(Digraph g,int v)
		{
			visited[v]=true;
			for(int w:g.adj(v))
			{
				if(!visited[w])
				{
					dfs(g,w);
				}
			}
			
			reversePost.push(v);
		}
		
		public Iterable<Integer> reversePost()//����������
		{
			return reversePost;
		}
	}
	
	public Topological(Digraph g)
	{
		g.findCycle();//�ж��Ƿ��л�,������Խ�����������
		if(!g.hasCycle())
		{
			DFSOrder dfs=new DFSOrder(g);
			order=dfs.reversePost();//��֤����ͼ��������������Ķ�����������ͼ��������
		}
	}
	
	public Iterable<Integer> order()//ͼ������
	{
		return order;
	}
	
	public boolean isDAG()//ͼ�Ƿ�Ϊ�����޻�ͼ
	{
		return order!=null;
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
		
		Topological getTopo=new Topological(g);
		if(getTopo.isDAG())
		{
			System.out.println("ͼ�޻�,������:");
			for(int i:getTopo.order())
			{
				System.out.print(i+"->");
			}
		}
		else
		{
			System.out.println("ͼ�л�");
		}
	}

}
