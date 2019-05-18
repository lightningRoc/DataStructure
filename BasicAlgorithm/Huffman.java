import java.util.Scanner;

public class Huffman {
	//实现huffman算法
	
	private static int R=256;//字母基数
	private static int resSize;
	private static int sourceSize;
	
	//表示huffman数的结点
	private static class Node implements Comparable<Node>
	{
		private char c;//表示的对应的字符
		private int f;//结点对应的频率(权重)
		private Node left,right;//左右孩子
		
		Node(char c,int f,Node left,Node right)
		{
			this.c=c;
			this.f=f;
			this.left=left;
			this.right=right;
		}
		
		public boolean isLeaf()//结点是否是叶子结点
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
		//处理输入
		int[] f=getInput();
		System.out.println("input complete");
		//构造Huffman树
		Node root=buildTree(f);
		System.out.println("tree complete");
		//构造相应的编码
		String[] codes=buildCode(root);
		System.out.println("codes complete");
		
		//输出编码值
		for(int i=0;i<codes.length;i++)
		{
			if(codes[i]!=null)
			{
				System.out.println((char)i+":"+codes[i]);
				resSize+=codes[i].length()*f[i];
			}
		}
		System.out.println("压缩率："+(1.0*resSize)/sourceSize);
	}
	
	//获取输入并计算各个字母出现的频率
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
	
	//根据各个字母的频率来建立Huffman树
	private static Node buildTree(int[] f)
	{
		BinaryHeap<Node> pq=new BinaryHeap<>();
		
		for(char c=0;c<R;c++)
		{
			//当节点存在时，加入优先队列
			if(f[c]>0)pq.insert(new Node(c, f[c], null, null));
		}
		
		while(pq.size()>1)
		{
			//选择两个最小的结点合并
			Node a=pq.deleteMin();
			Node b=pq.deleteMin();
			Node parent=new Node('\0', a.f+b.f, a, b);
			
			pq.insert(parent);
		}
		
		return pq.deleteMin();
	}
	
	//对于给定的Huffman树建立各个结点的Huffman编码
	private static String[] buildCode(Node root)
	{
		String[] codes=new String[R];
		buildCode(codes,root,"");
		return codes;
	}
	
	//辅助的递归方法来建立各个结点对应的Huffman编码
	private static void buildCode(String[] codes,Node root,String current)
	{
		if(root.isLeaf())
		{
			//建立完成一个
			codes[root.c]=current;
			return;
		}
		//向下递进
		buildCode(codes,root.left,current+'0');
		buildCode(codes,root.right,current+'1');
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Huffman huf=new Huffman();
		huf.compress();
	}

}
