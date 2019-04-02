import java.util.Random;

public class MergeSort {
	//归并排序
	private static Comparable[] aux;//只通过使用一个辅助数组来节省栈空间
	
	public static void mergeSort(Comparable[] array)
	{
		aux=new Comparable[array.length];
		mergeSort(array,0,array.length-1);
	}
	
	private static void mergeSort(Comparable[] array,int lo,int hi)//递归辅助方法
	{
		if(hi<=lo)return;
		int mid=lo+(hi-lo)/2;
		//将两个子数组排序
		mergeSort(array,lo,mid);
		mergeSort(array,mid+1,hi);
		
		//合并
		merge(array,lo,mid,hi);
	}
	
	private static void merge(Comparable[] array,int lo,int mid,int hi) {
		//将子数组lo至mid、子数组mid+1至hi合并
		int i=lo,j=mid+1;
		for(int h=lo;h<=hi;h++)aux[h]=array[h];//复制至辅助数组再复制回来
		
		for(int k=lo;k<=hi;k++) {
			if(i>mid)
			{
				while(k<=hi)
				{
					array[k]=aux[j++];//前一个子数组遍历完毕
					k++;
				}
			}
			else if(j>hi)
			{
				while(k<=hi)
				{
					array[k]=aux[i++];//后一个子数组遍历完毕
					k++;
				}
			}
			else if(aux[j].compareTo(aux[i])<0)array[k]=aux[j++];//复制较小那一个
			else array[k]=aux[i++];
		}
	}
	
	public static void mergeSortNR(Comparable[] array) {//非递归式归并排序
		int len=array.length;
		aux=new Comparable[len];
		for(int sz=1;sz<len;sz=sz+sz)
			for(int lo=0;lo<len-sz;lo+=sz+sz)
				merge(array,lo,lo+sz-1,Math.min(lo+sz+sz-1, len-1));
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
		mergeSort(a);
		System.out.println(isSorted(a));
	}

}
