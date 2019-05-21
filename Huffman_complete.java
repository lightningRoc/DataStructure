import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Scanner;

public class Huffman {
	//实现huffman算法,对于文本文件进行压缩
	
	private static int R=256;//字母基数
	private static int resSize;//压缩后文件大小(比特数)
	private static int sourceSize;//原文件大小(比特数)
	private static Node root;//树根节点
	private static String[] codes;//编码表
	private static int[] f;//各个字符的权重
	private static String inputPath;
	private static String outputPath;
	
	
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
//		int[] f=getInput();
		try 
		{
			f=getFileInput();
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		System.out.println("input complete");
		//构造Huffman树
		root=buildTree(f);
		System.out.println("tree complete");
		//构造相应的编码
		codes=buildCode(root);
		System.out.println("codes complete");
		
//		统计结果文件大小输出编码值
		for(int i=0;i<codes.length;i++)
		{
			if(codes[i]!=null)
			{
//				System.out.println((char)i+":"+codes[i]);
				resSize+=codes[i].length()*f[i];
			}
		}
		System.out.println("压缩率："+(1.0*resSize)/sourceSize);
		
		try 
		{
			outToFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("out complete");
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
			String s=scan.nextLine()+"\n";
			for(int i=0;i<s.length();i++)
			{
				f[s.charAt(i)%R]++;
			}
			sourceSize+=s.length()*8;
		}
		return f;
	}
	
	//从文件获取输入
	private static int[] getFileInput() throws IOException
	{
		sourceSize=0;
		resSize=0;
		
		int[] f=new int[R];
		Scanner scan=new Scanner(System.in);
//		DataInputStream in=null;
		System.out.print("请输入源文件路径:");
		inputPath=scan.nextLine();
		
		try 
		{
			//同时注意编码的问题
			scan=new Scanner(new File(inputPath),"UTF-8");
//			in=new DataInputStream(new FileInputStream(inputPath));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		while(scan.hasNextLine())
		{
			String s=scan.nextLine();
			System.out.print(s);
			if(scan.hasNextLine())s+="\r\n";//注意换行符不被读取的问题,解决windows下的换行补齐
			for(int i=0;i<s.length();i++)
			{
				f[s.charAt(i)%R]++;
			}
			sourceSize+=s.length()*8;
		}
		
//		while(in.available()!=0)
//		{
//			f[in.readByte()%R]++;
//			sourceSize+=8;
//		}
//		System.out.println("大小:"+sourceSize);
		return f;
	}
	
	//输出压缩文件文件
	private static void outToFile() throws IOException
	{
		//scan获取文件名
		//为防止数据发生损失，使用字节级别的形式来处理文件的数据
		Scanner scan=new Scanner(System.in);
		System.out.print("请输入输出文件路径:");
		outputPath=scan.nextLine();
//		PrintWriter out=null;
		
		DataOutput out=null;
//		DataInputStream in=null;
		
		try 
		{
//			out=new PrintWriter(new File(outputPath),"UTF-8");
			out=new DataOutputStream(new FileOutputStream(outputPath));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//输出源文件大小(字节数)
//		out.println(sourceSize/8);	
		out.writeInt(sourceSize/8);
//		out.flush();
		//输出字符表
		for(int i=0;i<R;i++)
		{
//			out.println(f[i]);
			out.writeInt(f[i]);
		}
//		out.flush();
		//输出文件内容
		Scanner in=null;
		String buffer="";
//		String res="";
		try 
		{
//			in=new DataInputStream(new FileInputStream(inputPath));
			in=new Scanner(new File(inputPath),"UTF-8");
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		int currentSize=0;//辅助变量以输出进度
		//输出压缩文件，以字符的方式处理源文件，以二进制形式输出压缩文件
		while(in.hasNextLine())
		{
			String line=in.nextLine();
			if(in.hasNextLine())line+="\r\n";//windows系统补齐换行
			for(int i=0;i<line.length();i++)
			{
				buffer+=codes[line.charAt(i)%R];
//				System.out.println(buffer);
				while(buffer.length()>=8)
				{
					//输出一个字符
//					out.print((char)getByte(buffer));
//					System.out.println(getByte(buffer));										
//					res+=(char)getByte(buffer.substring(0,8));				
//					out.flush();
					out.writeByte(getByte(buffer));
					currentSize+=8;
					if(currentSize>resSize)currentSize=resSize;
					System.out.println("进度:"+currentSize*100.0/resSize+"%");
					buffer=buffer.substring(8);
				}
			}
		}
//		while(in.available()!=0)
//		{
//			buffer+=codes[in.readByte()%R];
//	//		System.out.println(buffer);
//			while(buffer.length()>=8)
//			{
//				//输出一个字符
//	//			out.print((char)getByte(buffer));
//	//			System.out.println(getByte(buffer));
//				out.writeByte(getByte(buffer));
//				
//	//			res+=(char)getByte(buffer.substring(0,8));
//				
//	//			out.flush();
//				buffer=buffer.substring(8);
//			}
//		}
		
		//输出剩余数据
		if(buffer.length()>0)
		{
//			System.out.println(buffer);
			while(buffer.length()<8)
			{
				buffer+="0";
			}
//			res+=(char)getByte(buffer);
//			out.print((char)getByte(buffer));
//			System.out.println(getByte(buffer));
//			out.flush();
			out.writeByte(getByte(buffer));
			currentSize+=8;
			if(currentSize>resSize)currentSize=resSize;
			System.out.println("进度:"+currentSize*100.0/resSize+"%");
		}
//		System.out.println("结果编码："+res);
	}
	
	//解压函数
	public static void expand()
	{
		//获取输入信息并构造相应的数据
		try 
		{
			f=getCFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("input complete");
		root=buildTree(f);
		System.out.println("tree complete");
		codes=buildCode(root);
		System.out.println("code complete");
		
		for(int i=0;i<codes.length;i++)
		{
			if(codes[i]!=null)
			{
//				System.out.println((char)i+":"+codes[i]);
				resSize+=codes[i].length()*f[i];
			}
		}
		
		try 
		{
			outSFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("out complete");
	}
	
	//获取压缩文件
	private static int[] getCFile() throws IOException
	{
		//读取压缩文件辅助信息
		Scanner scan=new Scanner(System.in);
		DataInput in=null;
		System.out.print("请输入压缩文件路径：");
		inputPath=scan.nextLine();
		try 
		{
//			scan=new Scanner(new FileInputStream(inputPath),"UTF-8");
			in=new DataInputStream(new FileInputStream(inputPath));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//读取原文件大小以及频率表
		f=new int[R];
//		sourceSize=Integer.parseInt(scan.nextLine())*8;
		sourceSize=in.readInt()*8;
		for(int i=0;i<R;i++)
		{
//			f[i]=Integer.parseInt(scan.nextLine());
			f[i]=in.readInt();
		}
		
		return f;
	}
	
	//通过压缩文件输出源文件
	private static void outSFile() throws IOException
	{
		//scan获取文件名输入，in获取压缩文件输入
		Scanner scan=new Scanner(System.in);
		DataInputStream in=null;
		System.out.print("请输入输出文件路径:");
		outputPath=scan.nextLine();
		try 
		{
//			scan=new Scanner(new File(inputPath),"UTF-8");
			in=new DataInputStream(new FileInputStream(inputPath));
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//跳过辅助数据
		for(int i=0;i<R+1;i++)
		{
//			scan.nextLine();
			in.readInt();
		}
		
		//获取数据并还愿
		PrintWriter out=null;
//		DataOutputStream out=null;
		
		int size=0;
		try 
		{
			out=new PrintWriter(new File(outputPath),"UTF-8");
//			out=new DataOutputStream(new FileOutputStream(outputPath));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		String line="";
		String buffer="";
//		String res="";
		
		int currentSize=0;//辅助变量以输出进度
		//获取二进制数据并还愿
		while(in.available()!=0)
		{
			buffer+=getBits(in.readByte());
			
			while(buffer.length()>=100)
			{
				if(size>=sourceSize)return;
				
				char c=find(root, buffer, 0);
//				System.out.println(c);				
//				res+=buffer.substring(0,codes[c].length());
				buffer=buffer.substring(codes[c].length());
				
				out.print(c);
				out.flush();
				
				currentSize+=8;
				if(currentSize>sourceSize)currentSize=sourceSize;
				System.out.println("进度:"+currentSize*100.0/sourceSize+"%");
//				out.writeByte(c);
				size+=8;
			}
			
		}
		
		//获取文本数据并还原
//		for(int i=0;i<R;i++)if(codes[i]!=null)System.out.println(codes[i]);
//		while(scan.hasNextLine())
//		{
//			
//			line=scan.nextLine();
//			for(int i=0;i<line.length();i++)
//			{
////			System.out.println(getBits(line.charAt(i))+getBits(line.charAt(i+1)));
//				//获取还愿的字符并输出
//				buffer+=getBits(line.charAt(i));
//				res+=line.charAt(i);
//				
////				System.out.println(buffer);
////				System.out.println((int)line.charAt(i));
//				
//				while(buffer.length()>=8)
//				{
//					if(size>=sourceSize)return;
//					
//					char c=find(root, buffer, 0);
//					System.out.println(c);
//					
//					buffer=buffer.substring(codes[c].length());
//					out.print(c);
//					
//					out.flush();
//					size+=8;
//				}
//			}
//		}
		
//		res+=buffer;
//		System.out.println("还原编码："+res);
		
		//输出剩余数据
		while(buffer.length()>=1)
		{
			if(size>=sourceSize)return;
			char c=find(root, buffer, 0);
//			System.out.println(c);	
//			res+=buffer.substring(codes[c].length());
			buffer=buffer.substring(codes[c].length());
			out.print(c);
			out.flush();
			
			currentSize+=8;
			if(currentSize>sourceSize)currentSize=sourceSize;
			System.out.println("进度:"+currentSize*100.0/sourceSize+"%");
//			out.writeByte(c);
			size+=8;
		}
		
	}
	
	//递归寻找前缀码对应的字符
	private static char find(Node root,String bits,int index)
	{
		if(root.isLeaf())
		{
			return root.c;
		}
		
		if(bits.charAt(index)=='1')
		{
			return find(root.right, bits, index+1);
		}
		else
		{
			return find(root.left, bits, index+1);
		}
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
		if(root==null)return codes;
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
		//向下递进,左0右1
		buildCode(codes,root.left,current+'0');
		buildCode(codes,root.right,current+'1');
	}
	
	//获取一个字节对应的比特字符串
	private static String getBits(int B)
	{
		StringBuilder res=new StringBuilder();
		for(int i=7;i>=0;i--)
		{
			res.append((((B>>>i)%2==0)?"0":"1"));
		}
		return res.toString();
	}
	
	//获取一串比特对应的字节
	private static int getByte(String bits)
	{
		int res=0;
		for(int i=0;i<8;i++)
		{
			res*=2;
			res+=(bits.charAt(i)=='1'?1:0);
		}
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Huffman huf=new Huffman();
		Scanner choice=new Scanner(System.in);
		System.out.print("选择操作(1、压缩，2、解压):");
		while(choice.hasNext())
		{
			int num=choice.nextInt();
			if(num==1)
			{
				compress();
			}
			else if(num==2)
			{
				expand();
			}
			System.out.print("选择操作(1、压缩，2、解压):");
		}
	}

}
