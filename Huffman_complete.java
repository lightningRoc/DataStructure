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
	//ʵ��huffman�㷨,�����ı��ļ�����ѹ��
	
	private static int R=256;//��ĸ����
	private static int resSize;//ѹ�����ļ���С(������)
	private static int sourceSize;//ԭ�ļ���С(������)
	private static Node root;//�����ڵ�
	private static String[] codes;//�����
	private static int[] f;//�����ַ���Ȩ��
	private static String inputPath;
	private static String outputPath;
	
	
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
		//����Huffman��
		root=buildTree(f);
		System.out.println("tree complete");
		//������Ӧ�ı���
		codes=buildCode(root);
		System.out.println("codes complete");
		
//		ͳ�ƽ���ļ���С�������ֵ
		for(int i=0;i<codes.length;i++)
		{
			if(codes[i]!=null)
			{
//				System.out.println((char)i+":"+codes[i]);
				resSize+=codes[i].length()*f[i];
			}
		}
		System.out.println("ѹ���ʣ�"+(1.0*resSize)/sourceSize);
		
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
	
	//��ȡ���벢���������ĸ���ֵ�Ƶ��
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
	
	//���ļ���ȡ����
	private static int[] getFileInput() throws IOException
	{
		sourceSize=0;
		resSize=0;
		
		int[] f=new int[R];
		Scanner scan=new Scanner(System.in);
//		DataInputStream in=null;
		System.out.print("������Դ�ļ�·��:");
		inputPath=scan.nextLine();
		
		try 
		{
			//ͬʱע����������
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
			if(scan.hasNextLine())s+="\r\n";//ע�⻻�з�������ȡ������,���windows�µĻ��в���
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
//		System.out.println("��С:"+sourceSize);
		return f;
	}
	
	//���ѹ���ļ��ļ�
	private static void outToFile() throws IOException
	{
		//scan��ȡ�ļ���
		//Ϊ��ֹ���ݷ�����ʧ��ʹ���ֽڼ������ʽ�������ļ�������
		Scanner scan=new Scanner(System.in);
		System.out.print("����������ļ�·��:");
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
		
		//���Դ�ļ���С(�ֽ���)
//		out.println(sourceSize/8);	
		out.writeInt(sourceSize/8);
//		out.flush();
		//����ַ���
		for(int i=0;i<R;i++)
		{
//			out.println(f[i]);
			out.writeInt(f[i]);
		}
//		out.flush();
		//����ļ�����
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
		
		int currentSize=0;//�����������������
		//���ѹ���ļ������ַ��ķ�ʽ����Դ�ļ����Զ�������ʽ���ѹ���ļ�
		while(in.hasNextLine())
		{
			String line=in.nextLine();
			if(in.hasNextLine())line+="\r\n";//windowsϵͳ���뻻��
			for(int i=0;i<line.length();i++)
			{
				buffer+=codes[line.charAt(i)%R];
//				System.out.println(buffer);
				while(buffer.length()>=8)
				{
					//���һ���ַ�
//					out.print((char)getByte(buffer));
//					System.out.println(getByte(buffer));										
//					res+=(char)getByte(buffer.substring(0,8));				
//					out.flush();
					out.writeByte(getByte(buffer));
					currentSize+=8;
					if(currentSize>resSize)currentSize=resSize;
					System.out.println("����:"+currentSize*100.0/resSize+"%");
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
//				//���һ���ַ�
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
		
		//���ʣ������
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
			System.out.println("����:"+currentSize*100.0/resSize+"%");
		}
//		System.out.println("������룺"+res);
	}
	
	//��ѹ����
	public static void expand()
	{
		//��ȡ������Ϣ��������Ӧ������
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
	
	//��ȡѹ���ļ�
	private static int[] getCFile() throws IOException
	{
		//��ȡѹ���ļ�������Ϣ
		Scanner scan=new Scanner(System.in);
		DataInput in=null;
		System.out.print("������ѹ���ļ�·����");
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
		
		//��ȡԭ�ļ���С�Լ�Ƶ�ʱ�
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
	
	//ͨ��ѹ���ļ����Դ�ļ�
	private static void outSFile() throws IOException
	{
		//scan��ȡ�ļ������룬in��ȡѹ���ļ�����
		Scanner scan=new Scanner(System.in);
		DataInputStream in=null;
		System.out.print("����������ļ�·��:");
		outputPath=scan.nextLine();
		try 
		{
//			scan=new Scanner(new File(inputPath),"UTF-8");
			in=new DataInputStream(new FileInputStream(inputPath));
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//������������
		for(int i=0;i<R+1;i++)
		{
//			scan.nextLine();
			in.readInt();
		}
		
		//��ȡ���ݲ���Ը
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
		
		int currentSize=0;//�����������������
		//��ȡ���������ݲ���Ը
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
				System.out.println("����:"+currentSize*100.0/sourceSize+"%");
//				out.writeByte(c);
				size+=8;
			}
			
		}
		
		//��ȡ�ı����ݲ���ԭ
//		for(int i=0;i<R;i++)if(codes[i]!=null)System.out.println(codes[i]);
//		while(scan.hasNextLine())
//		{
//			
//			line=scan.nextLine();
//			for(int i=0;i<line.length();i++)
//			{
////			System.out.println(getBits(line.charAt(i))+getBits(line.charAt(i+1)));
//				//��ȡ��Ը���ַ������
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
//		System.out.println("��ԭ���룺"+res);
		
		//���ʣ������
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
			System.out.println("����:"+currentSize*100.0/sourceSize+"%");
//			out.writeByte(c);
			size+=8;
		}
		
	}
	
	//�ݹ�Ѱ��ǰ׺���Ӧ���ַ�
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
		if(root==null)return codes;
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
		//���µݽ�,��0��1
		buildCode(codes,root.left,current+'0');
		buildCode(codes,root.right,current+'1');
	}
	
	//��ȡһ���ֽڶ�Ӧ�ı����ַ���
	private static String getBits(int B)
	{
		StringBuilder res=new StringBuilder();
		for(int i=7;i>=0;i--)
		{
			res.append((((B>>>i)%2==0)?"0":"1"));
		}
		return res.toString();
	}
	
	//��ȡһ�����ض�Ӧ���ֽ�
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
		System.out.print("ѡ�����(1��ѹ����2����ѹ):");
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
			System.out.print("ѡ�����(1��ѹ����2����ѹ):");
		}
	}

}
