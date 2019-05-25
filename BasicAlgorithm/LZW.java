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
	//利用LZW算法来对字符流进行压缩
	
	private static int R=256;//字母基数
	private static int L=4096;//编码最大值
	
	private static MyQueue<Integer> q=new MyQueue<>();//辅助演示队列
	
	public static void compress() throws IOException
	{
		//输入源字符序列
		System.out.print("请输入源字符序列:");
		Scanner in=new Scanner(System.in);
		
		//使用单词查找树来辅助进行最长前缀的匹配查找
		MyTrie<Integer> trie=new MyTrie<>();
		
		//基本字母对应的编码
		for(int i=0;i<R;i++)
		{
			trie.put(""+(char)i, i);
		}
		int code=R+1;//R留下来作为文件结束标志的编码
		
		//此处仅用一串字符做演示
		//while(in.hasNext())
		{
			String line=in.next();
			while(line.length()>0)
			{
				//获得已经存在编码值的最长前缀
				String s=trie.longestPrefixOf(line);
				
				//获取编码值
				q.enqueue(trie.get(s));//保存字符值
				
				//保存下一个获取的最长前缀(存在的最长前缀加上下一位)
				if(s.length()<line.length()&&code<L)
				{
					trie.put(line.substring(0, s.length()+1), code++);
				}
				line=line.substring(s.length());
			}
		}
		
		q.enqueue(R);//文件结束标志
	}
	
	public static void expand() throws IOException
	{
		String[] st=new String[L];//保存各个字符串对应的编码值
		
		int i;//下一个待补全的编码值
		
		for(i=0;i<R;i++)
		{
			st[i]=""+(char)i;
		}
		st[i++]=" ";//将R处的编码留给文件结束标志
				
		int res=q.dequeue();//编码值
		String val=st[res];//上一个解析出的字符串
		System.out.println("解码结果:");
		while(true)
		{
			if(res==R)break;//文件为空
			System.out.print(val);//输出解析出的源文件数据
			
			res=q.dequeue();//获取下一个编码
			
			if(res==R)break;//读取到文件结束标志
			
			String s=st[res];//下一个编码对应的字符串
			if(i==res)//当要生成的字符串的编码值与要解析的下一个编码值相同时(需要的编码值未生成)
			{	
				s=val+val.charAt(0);//缺少的字符必定为本身的首字符
			}	
			if(i<L)//如果编码值数量足够时
			{
				st[i++]=val+s.charAt(0);//保存新生成的编码值(当前字符串加上下一个字符串首字母对应的编码值)
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


