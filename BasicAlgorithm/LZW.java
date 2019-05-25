import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.plaf.multi.MultiButtonUI;

public class LZW {
	//����LZW�㷨�����ַ�������ѹ��
	
	private static int R=256;//��ĸ����
	private static int L=4096;//�������ֵ
	
	private static MyQueue<Integer> q=new MyQueue<>();//������ʾ����
	
	public static void compress() throws IOException
	{
		//����Դ�ַ�����
		System.out.print("������Դ�ַ�����:");
		Scanner in=new Scanner(System.in);
		
		//ʹ�õ��ʲ����������������ǰ׺��ƥ�����
		MyTrie<Integer> trie=new MyTrie<>();
		
		//������ĸ��Ӧ�ı���
		for(int i=0;i<R;i++)
		{
			trie.put(""+(char)i, i);
		}
		int code=R+1;//R��������Ϊ�ļ�������־�ı���
		
		//�˴�����һ���ַ�����ʾ
		//while(in.hasNext())
		{
			String line=in.next();
			while(line.length()>0)
			{
				//����Ѿ����ڱ���ֵ���ǰ׺
				String s=trie.longestPrefixOf(line);
				
				//��ȡ����ֵ
				q.enqueue(trie.get(s));//�����ַ�ֵ
				
				//������һ����ȡ���ǰ׺(���ڵ��ǰ׺������һλ)
				if(s.length()<line.length()&&code<L)
				{
					trie.put(line.substring(0, s.length()+1), code++);
				}
				line=line.substring(s.length());
			}
		}
		
		q.enqueue(R);//�ļ�������־
	}
	
	public static void expand() throws IOException
	{
		String[] st=new String[L];//��������ַ�����Ӧ�ı���ֵ
		
		int i;//��һ������ȫ�ı���ֵ
		
		for(i=0;i<R;i++)
		{
			st[i]=""+(char)i;
		}
		st[i++]=" ";//��R���ı��������ļ�������־
				
		int res=q.dequeue();//����ֵ
		String val=st[res];//��һ�����������ַ���
		System.out.println("������:");
		while(true)
		{
			if(res==R)break;//�ļ�Ϊ��
			System.out.print(val);//�����������Դ�ļ�����
			
			res=q.dequeue();//��ȡ��һ������
			
			if(res==R)break;//��ȡ���ļ�������־
			
			String s=st[res];//��һ�������Ӧ���ַ���
			if(i==res)//��Ҫ���ɵ��ַ����ı���ֵ��Ҫ��������һ������ֵ��ͬʱ(��Ҫ�ı���ֵδ����)
			{	
				s=val+val.charAt(0);//ȱ�ٵ��ַ��ض�Ϊ��������ַ�
			}	
			if(i<L)//�������ֵ�����㹻ʱ
			{
				st[i++]=val+s.charAt(0);//���������ɵı���ֵ(��ǰ�ַ���������һ���ַ�������ĸ��Ӧ�ı���ֵ)
			}
			val=s;
		}
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		compress();
		expand();
	}

}


