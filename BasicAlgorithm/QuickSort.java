import java.util.Random;

public class QuickSort {
	//��������
	
	//ʹ��������ֵ�ָ�����ڱ�ֵ(��Ŧֵ)
	private static void median3Partition(Comparable[] array,int lo,int hi)
	{
		int center=lo+(hi-lo)/2;//ȡ��͵㡢��ߵ㡢�м����м�ֵ
		//�����м�ֵ������loλ��
		if(array[center].compareTo(array[lo])<0)
		{
			exchange(center,lo,array);
		}
		if(array[hi].compareTo(array[lo])<0)
		{
			exchange(hi,lo,array);
		}
		if(array[hi].compareTo(array[center])<0)
		{
			exchange(hi,center,array);
		}
		
		exchange(lo,center,array);
	}
	
	public static void quickSort(Comparable[] array)
	{
		quickSort(array,0,array.length-1);
	}
	
	public static void quickSort(Comparable[] array,int lo,int hi)//�ݹ鸨������
	{
		if(hi<=lo)return;
		
		int j=partition(array,lo,hi);
		quickSort(array,lo,j-1);
		quickSort(array,j+1,hi);
	}
	
	private static int partition(Comparable[] array,int lo,int hi)//�ָ�����
	{
		int i=lo,j=hi+1;
		median3Partition(array,lo,hi);
		Comparable pivot=array[lo];
		while(true) {
			while(array[++i].compareTo(pivot)<0)//��ָ����ڵ�����Ŧֵʱʱֹͣ
			{			
				if(i==hi)break;//��ֹԽ��
			}
			while(array[--j].compareTo(pivot)>0)//��ָ��С�ڵ�����Ŧֵʱֹͣ
			{
				if(j==lo)break;
			}
			if(i>=j)break;
			/*���Ϲ��̱�֤����ѭ������ʱj��λ��ΪС�ڵ���pivot��λ��
			 * ������Ŧֵ���õ���lo��λ�ã�hi�ڵ�����֮ǰֹͣ�����۳�����������ʱֹͣ������Ҳ����
			 *  */
			
			exchange(i,j,array);
		}
		exchange(lo,j,array);//��lo������ȷλ��
		return j;//�Ż���Ŧֵ��λ��
	}
	
	private static void exchange(int i,int j,Comparable[] array)//����λ��i��j�ϵ�Ԫ��
	{
		Comparable tmp=array[i];
		array[i]=array[j];
		array[j]=tmp;
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
		quickSort(a);
		System.out.println(isSorted(a));
	}

}
