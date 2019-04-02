import java.util.Random;

public class HeapSort {
	//堆排序
	public static void heapSort(Comparable[] array)
	{
		//使用简单的额外使用一个辅助数组的方式来实现(也可以通过使用一个max堆来通过不断替换最后一个元素来节省空间)
		int len=array.length;
		BinaryHeap auxHeap=new BinaryHeap(array);
		for(int i=0;i<len;i++)
		{
			array[i]=auxHeap.deleteMin();
		}
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
		heapSort(a);
		System.out.println(isSorted(a));
	}

}
