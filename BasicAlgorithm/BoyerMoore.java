import java.util.Scanner;

public class BoyerMoore {
	//利用Boyer-Moore算法来进行字符串的查找
	
	public int[] leap;//跳跃表，记录匹配
	private String source;//需要查找的字符串
	
	public BoyerMoore(String source) 
	{
		//计算跳跃表
		this.source=source;
		int len=source.length();
		int R=256;//字母基数
		leap=new int[R];
		
		//-1表示souce中不包含字符c
		for(int c=0;c<R;c++)
		{
			leap[c]=-1;
		}
		
		//i表示字符i存在于source中，且i的值为其在source中出现的最右边的位置
		for(int i=0;i<len;i++)
		{
			leap[source.charAt(i)]=i;
		}
	}
	
	//在txt中寻找子字符串source，成功时返回第一个匹配的子串的首字母索引，失败时返回超出txt的首索引
	public int find(String txt)
	{
		int sLen=source.length();
		int txtLen=txt.length();
		int skip;//记录跳跃量
		
		//在txt中从左向右查询
		for(int i=0;i<=txtLen-sLen;i+=skip)
		{
			skip=0;
			//将某个索引i处寻找j,从左向右寻找是否匹配
			for(int j=sLen-1;j>=0;j--)
			{
				if(source.charAt(j)!=txt.charAt(i+j))
				{
					//匹配失败时
					skip=j-leap[txt.charAt(i+j)];//计算跳跃量，不存在时leap[]为-1能使得i推进j+1
					if(skip<1)skip=1;//由于不可能使得i向左边移动,因此当skip不能向右移动时，使其至少移动一次
					break;
				}
			}
			//当skip不改变时，表示所有位置匹配成功
			if(skip==0)return i;
		}
		
		return txtLen;
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
			
			BoyerMoore bm=new BoyerMoore(source);
			System.out.println("首字母索引:"+bm.find(txt));
		}while(scanner.hasNextLine());
	}

}
