import java.util.Random;

public class ShellSort {
	//希尔排序
	public static void shellSort(Comparable[] array)
	{
		int len=array.length;
		int h=1;
		while(h<len/3)h=3*h+1;//增量序列:1,7,10,13...(开始于可用的最大值)
		while(h>=1) {
			//对于一趟采用对于i,i+1...N-1(相当于对于每种可能的序列)进行插入排序
			for(int i=h;i<len;i++) {
				for(int j=i;j>=h&&array[j].compareTo(array[j-h])<0;j-=h) {
					exchange(j,j-h,array);//进行显示交换的插入排序
				}
			}
			h=h/3;
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
		shellSort(a);
		System.out.println(isSorted(a));
	}

}
