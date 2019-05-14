import java.util.Scanner;

public class RabinKarp {
	//ʹ��Rabin-Karp�㷨���������ַ����Ĳ���(���ڸ��ʵ��㷨)
	
	private String source;//Ҫ���ҵ��ַ���
	private long sourceHash;//source��hashֵ
	private int sourceLen;
	private long Q;//��ʾһ���������
	private int R=256;//��ĸ����
	private long RM;//R^(M-1)%Q(һ��hashֵ�����λ��Ȩ��%Q)
	private int primes[]= 
	{
			1413751,1414393,1415069,1415647,1416143,1416769,1417319,1417831,1418299,
			1246489,1247009,1247527,1248011,1248469,1249013,1249477,1249939,1250479,
			1058671,1059137,1059683,1060207,1060673,1061297,1061779,1062427,1062989,
			951781,952277,952811,953297,953873,954367,954911,955363,955901,956401,957071 
	};//�򵥸���һЩ�������
	
	public RabinKarp(String source)
	{
		MyRandom rand=new MyRandom();
		this.source=source;
		this.sourceLen=source.length();
		Q=primes[rand.randomInt()%primes.length];//���ȡһ��������
		
		RM=1;
		for(int i=1;i<=sourceLen-1;i++)
		{
			RM=(R*RM)%Q;
		}
		sourceHash=hash(source, sourceLen);
	}
	
	private long hash(String s,int len)//����s��ǰ����ΪM���ֵ�ɢ��ֵ
	{
		long res=0;
		for(int i=0;i<len;i++)
		{
			res=(R*res+s.charAt(i))%Q;
		}
		return res;
	}
	
	public boolean check(String txt,int index)//��鳤��ΪsourceLen,��ʼ����Ϊindex��txt�е����ַ����Ƿ���ĺ�sourceƥ��
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
	
	private int find(String txt)//��txt�в������ַ���
	{
		int txtLen=txt.length();
		if(txtLen<sourceLen)return txtLen;
		long txtHash=hash(txt,sourceLen);
		if(sourceHash==txtHash&&check(txt,0))return 0;
		
		for(int i=sourceLen;i<txtLen;i++)
		{
			//���ټ�����֪һ���Ӵ���hashֵʱ������һ����ĸ����һ��hashֵ
			txtHash=(txtHash+Q-RM*txt.charAt(i-sourceLen)%Q)%Q;//��ȥ���λ,+QΪ��֤���Ϊ��
			txtHash=(txtHash*R+txt.charAt(i))%Q;//��������һλ�������µ����λ
			
			if(sourceHash==txtHash)
			{
				if(check(txt,i-sourceLen+1))return i-sourceLen+1;//�ҵ����ַ���
			}
		}
		
		return txtLen;
	}
	
	private static int nextPrime(int n)//������n��ĵ�һ������(����������)
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }
	
//	private long RandomPrime()
//	{
//		//����һ���������
//	}
	
	private static boolean isPrime(int n)//�ж�һ�����Ƿ�������(һ�㷽���ԸĽ���(����������))
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
			System.out.println("��Ҫ���ҵ��ַ�����");
			String source=scanner.nextLine();
			System.out.println("�����ַ�����");
			String txt=scanner.nextLine();
			
			RabinKarp rk=new RabinKarp(source);
			System.out.println("����ĸ����:"+rk.find(txt));
		}while(scanner.hasNextLine());
	}

}
