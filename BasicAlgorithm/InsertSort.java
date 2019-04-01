import java.util.Random;

public class InsertSort {
	//插入排序
	public static void InsertSort(Comparable[] array)
	{
		int len=array.length;
		for(int i=1;i<len;i++)
		{
			Comparable num=array[i];
			for(int j=i-1;;j--)
			{
				//搜寻将j位置的元素插入到前面的有序数组中的合适位置
				if(j>=0&&(array[j].compareTo(num)>0))exchange(j,j+1,array);
				else
				{
					array[j+1]=num;
					break;
				}
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
		InsertSort(a);
		System.out.println(isSorted(a));
	}

}
