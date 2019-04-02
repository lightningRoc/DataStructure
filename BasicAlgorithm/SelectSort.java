import java.util.Random;

public class SelectSort {
	//选择排序
	public static void selectSort(Comparable[] array)
	{
		int len=array.length;
		for(int i=0;i<len;i++)
		{
			//寻找i至len-1中的最小元素，并放到位置i处
			int location=i;
			for(int j=i+1;j<len;j++)
			{
				if(array[location].compareTo(array[j])>0)location=j;
			}
			exchange(location,i,array);
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
		selectSort(a);
		System.out.println(isSorted(a));
	}

}
