import java.util.Scanner;

public class Huffman {
	//ʵ��huffman�㷨
	
	private static int R=256;//��ĸ����
	private static int resSize;
	private static int sourceSize;
	
	//��ʾhuffman���Ľ��
	private static class Node implements Comparable<Node>
	{
		private char c;//��ʾ�Ķ�Ӧ���ַ�
		private int f;//����Ӧ��Ƶ��(Ȩ��)
		private Node left,right;//���Һ���
		
		Node(char c,int f,Node left,Node right)
		{
			this.c=c;
			this.f=f;
			this.left=left;
			this.right=right;
		}
		
		public boolean isLeaf()//����Ƿ���Ҷ�ӽ��
		{
			return left==null&&right==null;
		}
		
		public int compareTo(Node another)
		{
			return this.f-another.f;
		}
	}
	
	public static void compress()
	{
		//��������
		int[] f=getInput();
		System.out.println("input complete");
		//����Huffman��
		Node root=buildTree(f);
		System.out.println("tree complete");
		//������Ӧ�ı���
		String[] codes=buildCode(root);
		System.out.println("codes complete");
		
		//�������ֵ
		for(int i=0;i<codes.length;i++)
		{
			if(codes[i]!=null)
			{
				System.out.println((char)i+":"+codes[i]);
				resSize+=codes[i].length()*f[i];
			}
		}
		System.out.println("ѹ���ʣ�"+(1.0*resSize)/sourceSize);
	}
	
	//��ȡ���벢���������ĸ���ֵ�Ƶ��
	private static int[] getInput()
	{
		sourceSize=0;
		resSize=0;
		
		int[] f=new int[R];
		Scanner scan=new Scanner(System.in);
		while(scan.hasNextLine())
		{
			String s=scan.nextLine();
			for(int i=0;i<s.length();i++)
			{
				f[s.charAt(i)%256]++;
			}
			sourceSize+=s.length()*8;
		}
		return f;
	}
	
	//���ݸ�����ĸ��Ƶ��������Huffman��
	private static Node buildTree(int[] f)
	{
		BinaryHeap<Node> pq=new BinaryHeap<>();
		
		for(char c=0;c<R;c++)
		{
			//���ڵ����ʱ���������ȶ���
			if(f[c]>0)pq.insert(new Node(c, f[c], null, null));
		}
		
		while(pq.size()>1)
		{
			//ѡ��������С�Ľ��ϲ�
			Node a=pq.deleteMin();
			Node b=pq.deleteMin();
			Node parent=new Node('\0', a.f+b.f, a, b);
			
			pq.insert(parent);
		}
		
		return pq.deleteMin();
	}
	
	//���ڸ�����Huffman��������������Huffman����
	private static String[] buildCode(Node root)
	{
		String[] codes=new String[R];
		buildCode(codes,root,"");
		return codes;
	}
	
	//�����ĵݹ鷽����������������Ӧ��Huffman����
	private static void buildCode(String[] codes,Node root,String current)
	{
		if(root.isLeaf())
		{
			//�������һ��
			codes[root.c]=current;
			return;
		}
		//���µݽ�
		buildCode(codes,root.left,current+'0');
		buildCode(codes,root.right,current+'1');
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Huffman huf=new Huffman();
		huf.compress();
	}

}
