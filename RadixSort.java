import java.util.Random;

public class RadixSort {
	//基数排序(数组简单实现，对于整数(0<=n<1000)的情形)
	public static void radixSort(Integer[] array)
	{
		MyArrayList<Integer>[] buckets=new MyArrayList[10];//使用桶排序对某一位数值进行排序
		
		for(int i=0;i<10;i++)
		{
			buckets[i]=new MyArrayList<>();
		}
		
		//用于取整数的某一位数字(对于字符串则更为简单)
		int divisor=1;
		int mod=10;
		for(int i=1;i<3;i++)
		{
			for(Integer n:array)
			{
				buckets[(n%mod)/divisor].add(n);//取从低位至高位对数字进行排序
			}
			
			int cursion=0;
			for(MyArrayList<Integer> b:buckets)//将数据按某位排序放回原数组
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
	
	private static boolean isSorted(Comparable[] array)//判断数组是否为升序序列
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
