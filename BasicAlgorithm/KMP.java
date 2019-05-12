import java.util.Scanner;

public class KMP {
	//利用KMP算法来进行字符串的查找
	
	private String source;//源字符串
	private int[][] dfa;//辅助的优先状态自动机，其中dfa[i][j]表示状态j遇到字母i时应该做的动作
	
	public KMP(String source)
	{
		this.source=source;
		int len=source.length();
		int R=256;//字符集的基数
		dfa=new int[R][len];
		dfa[source.charAt(0)][0]=1;
		
		for(int back=0,i=1;i<len;i++)//back为失败时的回退状态
		{
			//计算dfa，利用之前计算好的状态来计算之后的状态
			for(int c=0;c<R;c++)
			{
				dfa[c][i]=dfa[c][back];//匹配失败时i与back状态一致
			}
			dfa[source.charAt(i)][i]=i+1;//匹配成功
			
			back=dfa[source.charAt(i)][back];//计算失败状态如何推进
		}
		
	}
	
	//在txt中寻找source存在的的第一个首字符索引位置
	public int find(String txt)
	{
		int i,j,txtLen=txt.length(),sourceLen=source.length();//i表示source的索引下标,j表示推进的状态
		
		for(i=0,j=0;i<txtLen&&j<sourceLen;i++)
		{
			j=dfa[txt.charAt(i)][j];//推进状态j
		}
		
		if(j==sourceLen)return i-sourceLen;//成功时，i位于正好超出需要寻找的字符串的位置，计算找到的字符串位置的首字母索引
		else return txtLen;//寻找失败，返回超出需要寻找的字符集的第一个索引
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		do
		{
			System.out.println("需要查找的字符串：");
			String source=scanner.nextLine();
			System.out.println("整个字符串：");
			String txt=scanner.nextLine();
			
			KMP kmp=new KMP(source);
			System.out.println("首字母索引:"+kmp.find(txt));
		}while(scanner.hasNextLine());
	}

}
