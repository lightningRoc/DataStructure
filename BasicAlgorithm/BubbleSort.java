import java.util.Random;

public class BubbleSort {
	//冒泡排序
	public static void BubbleSort(Comparable[] array)
	{
		for(int i=array.length-2;i>=0;i--)
		{
			for(int j=0;j<=i;j++)
			{
				//每一趟将一个最大元素推到最后的位置
				if(array[j].compareTo(array[j+1])>0)exchange(j,j+1,array);
			}
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
		BubbleSort(a);
		System.out.println(isSorted(a));
	}

}
