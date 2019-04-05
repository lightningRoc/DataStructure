import java.util.Random;

public class RadixSort {
	//��������(�����ʵ�֣���������(0<=n<1000)������)
	public static void radixSort(Integer[] array)
	{
		MyArrayList<Integer>[] buckets=new MyArrayList[10];//ʹ��Ͱ�����ĳһλ��ֵ��������
		
		for(int i=0;i<10;i++)
		{
			buckets[i]=new MyArrayList<>();
		}
		
		//����ȡ������ĳһλ����(�����ַ������Ϊ��)
		int divisor=1;
		int mod=10;
		for(int i=1;i<3;i++)
		{
			for(Integer n:array)
			{
				buckets[(n%mod)/divisor].add(n);//ȡ�ӵ�λ����λ�����ֽ�������
			}
			
			int cursion=0;
			for(MyArrayList<Integer> b:buckets)//�����ݰ�ĳλ����Ż�ԭ����
			{
				for(Integer n:b)
				{
					array[cursion++]=n;
				}
				
				b.clear();
			}
			
			divisor*=10;
			mod*=10;
		}
	}
	
	private static boolean isSorted(Comparable[] array)//�ж������Ƿ�Ϊ��������
	{
		for(int i=0;i<array.length-1;i++)
		{
			if(array[i].compareTo(array[i+1])>0)return false;
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] a=new Integer[100];
		Random rand=new Random();
		for(int i=0;i<100;i++)
		{
			a[i]=rand.nextInt(100);
		}
		radixSort(a);
		System.out.println(isSorted(a));
	}

}
