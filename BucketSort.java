import java.util.Random;

public class BucketSort {
	//Ͱ����(��������(0<=n<1000)������)
	public static void bucketSort(Integer[] array)
	{
		//��ʼ��
		Integer[] aux=new Integer[1000];
		for(int i=0;i<aux.length;i++)
		{
			aux[i]=0;
		}
		
		for(int i=0;i<array.length;i++)
		{
			aux[array[i]]++;
		}
		
		int cursion=0;
		for(int i=0;i<aux.length;i++)
		{
			while(aux[i]>0)
			{
				array[cursion++]=i;
				aux[i]--;
			}
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
		bucketSort(a);
		System.out.println(isSorted(a));
	}

}
