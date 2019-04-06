import java.util.Random;

public class SortMethodCmp {
	//各种排序方法效率比较
	
	public static void getResultByAMethod(String choice)//计算一个方法小路
	{
		Random rand=new Random();
		Integer[] array=new Integer[10000];
		long start=System.currentTimeMillis();
		for(int i=0;i<10;i++)
		{
			//计算10个不同的10000大小的随机数组
			for(int j=0;j<10000;j++)
			{
				array[j]=rand.nextInt(1000);
			}
			//选择相应的算法
			switch(choice)
			{
			case "bubble":
				BubbleSort.bubbleSort(array);
				break;
			case "select":
				SelectSort.selectSort(array);
				break;
			case "insert":
				InsertSort.insertSort(array);
				break;
			case "shell":
				ShellSort.shellSort(array);
				break;
			case "heap":
				HeapSort.heapSort(array);
				break;
			case "merge":
				MergeSort.mergeSort(array);
				break;
			case "quick":
				QuickSort.quickSort(array);
				break;
			case "bucket":
				BucketSort.bucketSort(array);
				break;
			case "radix":
				RadixSort.radixSort(array);
				break;
			}
		}
		long end=System.currentTimeMillis();
		System.out.println(choice+":"+(end-start)*1.0*1E-3+"s");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getResultByAMethod("bubble");
		getResultByAMethod("insert");
		getResultByAMethod("select");
		getResultByAMethod("shell");
		getResultByAMethod("heap");
		getResultByAMethod("merge");
		getResultByAMethod("quick");
		getResultByAMethod("bucket");
		getResultByAMethod("radix");
	}

}
