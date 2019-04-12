
public class MyRandom {
	
	private static final int A=48271;
	private static final int M=2147483647;
	private static final int Q=M/A;
	private static final int R=M%A;
	
	private int state;
	
	public MyRandom()
	{
		//����ʱ���ȡ����
		state=(int)(System.currentTimeMillis()%Integer.MAX_VALUE);
	}
	
	public int randomInt()
	{
		//��ʽ:x[i+1]=A(x[i] mod Q)-R(x[i]/Q)+M*��(x[i])
		int tmp=A*(state%Q)-R*(state/Q);
		
		if(tmp>=0)state=tmp;
		else state=tmp+M;
		
		return state;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyRandom test=new MyRandom();
		
		int[][] show=new int[50][50];
		for(int[] array:show)//��ʼ��
		{
			for(int i=0;i<array.length;i++)
			{
				array[i]=0;
			}
		}
		
		for(int i=0;i<50;i++)
		{
			//�������50����
			int r=test.randomInt()%50;
			int c=test.randomInt()%50;
			
			show[r][c]=1;
		}
		
		int count=0;
		for(int[] array:show)//��ʾ�����
		{
			for(int i:array)
			{
				if(i==1)
				{
					System.out.print(i+" ");
					count++;
				}
				else System.out.print("  ");
			}
			System.out.println();
		}
		System.out.println("��ͬ�ĵ�ĸ�����"+count);
		
		int res=0;
		for(int i=0;i<10000;i++)
		{
			if(test.randomInt()%2==1)
			{
				res++;
			}
		}
		System.out.println("����ֵ:0.5--�������ɼ����������ƽ��ֵ��"+res*1.0/10000);
		
		
		res=0;
		for(int i=0;i<10000;i++)
		{
			int add=test.randomInt()%3;
			if(add==1)
			{
				res++;
			}
			else if(add==2)
			{
				res+=2;
			}
			
		}
		System.out.println("����ֵ:1--�������ɼ����������ƽ��ֵ��"+res*1.0/10000);
		
		res=0;
		for(int i=0;i<10000;i++)
		{
			int add=test.randomInt()%4;
			if(add==1)
			{
				res++;
			}
			else if(add==2)
			{
				res+=2;
			}
			else if(add==3)
			{
				res+=3;
			}
			
		}
		System.out.println("����ֵ:1.5--�������ɼ����������ƽ��ֵ��"+res*1.0/10000);
	}

}
