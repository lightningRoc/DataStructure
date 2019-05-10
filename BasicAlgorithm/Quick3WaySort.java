import java.util.Random;

public class Quick3WaySort {
	//三向切分的快速排序，优化重复元素较多时的快速排序
	
	//使用三数中值分割法给出哨兵值(枢纽值)
	private static void median3Partition(Comparable[] array,int lo,int hi)
	{
		int center=lo+(hi-lo)/2;//取最低点、最高点、中间点的中间值
		//并将中间值放置在lo位置
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
		
		median3Partition(array,lo,hi);//将合适的哨兵值放到lo处
		//lt为将小于哨兵值的点放到的索引，其左边的值为小于哨兵值，i索引的左边的值小于或等于索引值,gt的右边的值大于哨兵值
		int lt=lo,i=lo+1,gt=hi;
		Comparable v=array[lo];
		
		while(i<=gt) 
		{
			int cmp=array[i].compareTo(v);
			if(cmp<0)exchange(lt++,i++,array);//索引i的值小于哨兵值，则将i处的值放到lt处
			else if(cmp>0)exchange(i,gt--,array);//索引i的值大于哨兵值
			else i++;
		}
		quick3WaySort(array,lo,lt-1);
		quick3WaySort(array,gt+1,hi);
	}
	
	private static void exchange(int i,int j,Comparable[] array)//交换位置i与j上的元素
	{
		Comparable tmp=array[i];
		array[i]=array[j];
		array[j]=tmp;
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
		quick3WaySort(a);
		System.out.println(isSorted(a));
	}

}
