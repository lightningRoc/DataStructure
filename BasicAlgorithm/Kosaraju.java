import java.util.Scanner;

public class Kosaraju {
	//����kosaraju�㷨����������ͼ��ǿ��ͨ����
	
	private boolean[] visited;
	private int[] id;//����ĳ������v��Ӧ��ǿ��ͨ����
	private int count;//ǿ��ͨ��������
	
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
	
	public Kosaraju(Digraph g)
	{
		visited=new boolean[g.getV()];
		id=new int[g.getV()];		
		
		DFSOrder aux=new DFSOrder(getReverse(g));//��ȡ����ͼ��������������������
		for(int s:aux.reversePost())
		{
			//��֤��������s����������û�б���ǹ��ĵ�v����v�ڷ���ͼ�������ĵ���λ��s֮��ʱ������v��s��һ��·��(��������������)
			//���s��v��ͬһ��ǿ��ͨ������
			if(!visited[s])
			{
				dfs(g,s);
				count++;
			}
		}
	}
	
	private void dfs(Digraph g,int v)
	{
		visited[v]=true;
		id[v]=count;
		for(int w:g.adj(v))
		{
			if(!visited[w])
			{
				dfs(g,w);
			}
		}
	}
	
	public int id(int v)//��ȡ��v��Ӧ��ǿ��ͨ����id
	{
		return id[v];
	}
	
	public int count()//��ȡǿ��ͨ��������
	{
		return count;
	}
	
	public Digraph getReverse(Digraph g)//���ͼg��Ӧ�ķ���ͼ
	{
		Digraph reverseG=new Digraph(g.getV());
		for(int v=0;v<g.getV();v++)
		{
			for(int w:g.adj(v))
			{
				reverseG.addEdge(w, v);
			}
		}
		
		return reverseG;
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
		
		Kosaraju res=new Kosaraju(g);
		System.out.println("ǿ��ͨ��������:"+res.count());
	}

}
