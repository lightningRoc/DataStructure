import java.util.Random;

public class Quick3WaySort {
	//�����зֵĿ��������Ż��ظ�Ԫ�ؽ϶�ʱ�Ŀ�������
	
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
	
	public static void quick3WaySort(Comparable[] array)
	{
		quick3WaySort(array,0,array.length-1);
	}
	
	public static void quick3WaySort(Comparable[] array,int lo,int hi)
	{
		if(hi<=lo)return;
		
		median3Partition(array,lo,hi);//�����ʵ��ڱ�ֵ�ŵ�lo��
		//ltΪ��С���ڱ�ֵ�ĵ�ŵ�������������ߵ�ֵΪС���ڱ�ֵ��i��������ߵ�ֵС�ڻ��������ֵ,gt���ұߵ�ֵ�����ڱ�ֵ
		int lt=lo,i=lo+1,gt=hi;
		Comparable v=array[lo];
		
		while(i<=gt) 
		{
			int cmp=array[i].compareTo(v);
			if(cmp<0)exchange(lt++,i++,array);//����i��ֵС���ڱ�ֵ����i����ֵ�ŵ�lt��
			else if(cmp>0)exchange(i,gt--,array);//����i��ֵ�����ڱ�ֵ
			else i++;
		}
		quick3WaySort(array,lo,lt-1);
		quick3WaySort(array,gt+1,hi);
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
		quick3WaySort(a);
		System.out.println(isSorted(a));
	}

}
