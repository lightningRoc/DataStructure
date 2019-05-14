import java.util.Scanner;

public class RabinKarp {
	//使用Rabin-Karp算法来进行子字符串的查找(基于概率的算法)
	
	private String source;//要查找的字符串
	private long sourceHash;//source的hash值
	private int sourceLen;
	private long Q;//表示一个大的素数
	private int R=256;//字母基数
	private long RM;//R^(M-1)%Q(一个hash值的最高位的权重%Q)
	private int primes[]= 
	{
			1413751,1414393,1415069,1415647,1416143,1416769,1417319,1417831,1418299,
			1246489,1247009,1247527,1248011,1248469,1249013,1249477,1249939,1250479,
			1058671,1059137,1059683,1060207,1060673,1061297,1061779,1062427,1062989,
			951781,952277,952811,953297,953873,954367,954911,955363,955901,956401,957071 
	};//简单给出一些大的素数
	
	public RabinKarp(String source)
	{
		MyRandom rand=new MyRandom();
		this.source=source;
		this.sourceLen=source.length();
		Q=primes[rand.randomInt()%primes.length];//随机取一个大素数
		
		RM=1;
		for(int i=1;i<=sourceLen-1;i++)
		{
			RM=(R*RM)%Q;
		}
		sourceHash=hash(source, sourceLen);
	}
	
	private long hash(String s,int len)//计算s的前长度为M部分的散列值
	{
		long res=0;
		for(int i=0;i<len;i++)
		{
			res=(R*res+s.charAt(i))%Q;
		}
		return res;
	}
	
	public boolean check(String txt,int index)//检查长度为sourceLen,起始索引为index的txt中的子字符串是否真的和source匹配
	{
		for(int i=0;i<sourceLen;i++,index++)
		{
			if(source.charAt(i)!=txt.charAt(index))
			{
				return false;
			}
		}
		
		return true;
	}
	
	private int find(String txt)//在txt中查找子字符串
	{
		int txtLen=txt.length();
		if(txtLen<sourceLen)return txtLen;
		long txtHash=hash(txt,sourceLen);
		if(sourceHash==txtHash&&check(txt,0))return 0;
		
		for(int i=sourceLen;i<txtLen;i++)
		{
			//快速计算已知一个子串的hash值时的右移一个字母的下一个hash值
			txtHash=(txtHash+Q-RM*txt.charAt(i-sourceLen)%Q)%Q;//减去最高位,+Q为保证结果为正
			txtHash=(txtHash*R+txt.charAt(i))%Q;//整体左移一位并加上新的最低位
			
			if(sourceHash==txtHash)
			{
				if(check(txt,i-sourceLen+1))return i-sourceLen+1;//找到子字符串
			}
		}
		
		return txtLen;
	}
	
	private static int nextPrime(int n)//搜索比n大的第一个素数(仅搜索奇数)
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }
	
//	private long RandomPrime()
//	{
//		//返回一个大的素数
//	}
	
	private static boolean isPrime(int n)//判断一个数是否是素数(一般方法稍改进版(仅搜索奇数))
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
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
			
			RabinKarp rk=new RabinKarp(source);
			System.out.println("首字母索引:"+rk.find(txt));
		}while(scanner.hasNextLine());
	}

}
