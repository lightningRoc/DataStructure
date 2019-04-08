import java.util.Random;
import java.util.Scanner;

public class UnionFindWeighted {
	//ʹ�ð���С�ϲ���union-find�㷨
	
	private int[] id;//��ʾ����ֵ��Ӧ�ĸ��ڵ�
	private int count;//��ͨ����������(��ͬ�ļ�������)
	private int[] size;//�������ڵ��Ӧ�ķ�����С
	
	public UnionFindWeighted(int n) 
	{
		//����ӵ����ֵ0��n-1�ĳ�ʼ����
		count=n;
		id=new int[n];
		for(int i=0;i<n;i++) {//��ʼ��
			id[i]=i;//��ʼʱ��������ֻ���Լ�����
		}
		size=new int[n];
		for(int i=0;i<n;i++)size[i]=1;
	}
	
	public int count()//���ز�ͬ�ļ��ϵ�����
	{
		return count;
	}
	
	public boolean connected(int p,int q) {//������ֵ�Ƿ�����ͬһ������(�Ƿ���ͨ)
		return find(p)==find(q);
	}
	
	public int find(int p)//������ֵ���ڵļ��ϵĸ��ڵ�
	{
		while(p!=id[p])p=id[p];
		return p;
	}
	
	public void union(int p,int q)//�����Ĵ�С���ϲ�������ֵ��Ӧ�ĸ��Եļ���(С�ĺϲ�����ĵ���)
	{
		//�ҵ���ֵ��Ӧ�ĸ��ڵ�
		int i=find(p);
		int j=find(q);
		if(i==j)return;
		
		if(size[i]<size[j]) 
		{
			id[i]=j;//p��Ӧ�ĸ��ڵ�i�ļ��ϸ�С
			size[j]+=size[i];
		}
		else {
			id[j]=i;
			size[i]+=size[j];
		}
		count--;
		
	}
	
	public static void test()//�����㷨
	{
		Scanner scan=new Scanner(System.in);
		int n=scan.nextInt();//������ֵ�ĸ���
		scan.nextLine();
		UnionFindWeighted test=new UnionFindWeighted(n);

		while(scan.hasNext())
		{
			//һ������������ֵ����ʾ������ֵ�໥��ͨ
			int i=scan.nextInt();
			int j=scan.nextInt();
			scan.nextLine();
			if(i==-1&&j==-1)break;
			
			if((i>=0&&i<n)&&(j>=0&&j<n))
			{
				//����Ϸ�
				if(!test.connected(i, j))test.union(i, j);//����find��union�Ļ�ϲ���
			}
		}
		
		System.out.println("��ͬ�ļ��ϵĸ�����"+test.count());
	}
	
	public static void randTest()//�������
	{
		
		UnionFindWeighted test=new UnionFindWeighted(10000);
		Random rand=new Random();
		
		for(int n=0;n<1000000;n++)
		{
			int i=rand.nextInt(10000);
			int j=rand.nextInt(10000);
			
			if(!test.connected(i, j))test.union(i, j);//����find��union�Ļ�ϲ���
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		test();
		long end=System.currentTimeMillis();
		System.out.println("ʱ��"+":"+(end-start)*1.0*1E-3+"s");
	}

}
