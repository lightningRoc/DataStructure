import java.util.Random;

public class MergeSort {
	//�鲢����
	private static Comparable[] aux;//ֻͨ��ʹ��һ��������������ʡջ�ռ�
	
	public static void mergeSort(Comparable[] array)
	{
		aux=new Comparable[array.length];
		mergeSort(array,0,array.length-1);
	}
	
	private static void mergeSort(Comparable[] array,int lo,int hi)//�ݹ鸨������
	{
		if(hi<=lo)return;
		int mid=lo+(hi-lo)/2;
		//����������������
		mergeSort(array,lo,mid);
		mergeSort(array,mid+1,hi);
		
		//�ϲ�
		merge(array,lo,mid,hi);
	}
	
	private static void merge(Comparable[] array,int lo,int mid,int hi) {
		//��������lo��mid��������mid+1��hi�ϲ�
		int i=lo,j=mid+1;
		for(int h=lo;h<=hi;h++)aux[h]=array[h];//���������������ٸ��ƻ���
		
		for(int k=lo;k<=hi;k++) {
			if(i>mid)
			{
				while(k<=hi)
				{
					array[k]=aux[j++];//ǰһ��������������
					k++;
				}
			}
			else if(j>hi)
			{
				while(k<=hi)
				{
					array[k]=aux[i++];//��һ��������������
					k++;
				}
			}
			else if(aux[j].compareTo(aux[i])<0)array[k]=aux[j++];//���ƽ�С��һ��
			else array[k]=aux[i++];
		}
	}
	
	public static void mergeSortNR(Comparable[] array) {//�ǵݹ�ʽ�鲢����
		int len=array.length;
		aux=new Comparable[len];
		for(int sz=1;sz<len;sz=sz+sz)
			for(int lo=0;lo<len-sz;lo+=sz+sz)
				merge(array,lo,lo+sz-1,Math.min(lo+sz+sz-1, len-1));
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
		mergeSort(a);
		System.out.println(isSorted(a));
	}

}
