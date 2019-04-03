import java.util.Random;

public class QuickSort {
	//快速排序
	
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
	
	public static void quickSort(Comparable[] array)
	{
		quickSort(array,0,array.length-1);
	}
	
	public static void quickSort(Comparable[] array,int lo,int hi)//递归辅助方法
	{
		if(hi<=lo)return;
		
		int j=partition(array,lo,hi);
		quickSort(array,lo,j-1);
		quickSort(array,j+1,hi);
	}
	
	private static int partition(Comparable[] array,int lo,int hi)//分割数组
	{
		int i=lo,j=hi+1;
		median3Partition(array,lo,hi);
		Comparable pivot=array[lo];
		while(true) {
			while(array[++i].compareTo(pivot)<0)//左指针大于等于枢纽值时时停止
			{			
				if(i==hi)break;//防止越界
			}
			while(array[--j].compareTo(pivot)>0)//右指针小于等于枢纽值时停止
			{
				if(j==lo)break;
			}
			if(i>=j)break;
			/*以上过程保证在总循环结束时j的位置为小于等于pivot的位置
			 * 由于枢纽值放置到了lo的位置，hi在到达它之前停止，结论成立，等于它时停止，结论也成立
			 *  */
			
			exchange(i,j,array);
		}
		exchange(lo,j,array);//将lo放置正确位置
		return j;//放回枢纽值的位置
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
		quickSort(a);
		System.out.println(isSorted(a));
	}

}
