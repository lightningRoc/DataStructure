import java.util.Random;

public class SortMethodCmp {
	//各种排序方法效率比较
	
	public static void getResultByAMethod(String choice)//计算一个方法小路
	{
		Random rand=new Random();
		Integer[] array=new Integer[1000];
		long start=System.currentTimeMillis();
		for(int i=0;i<1000;i++)
		{
			//计算1000个不同的1000大小的随机数组
			for(int j=0;j<1000;j++)
			{
				array[j]=rand.nextInt(100);
			}
			switch(choice)
			{
			case "bubble":
				BubbleSort.BubbleSort(array);
				break;
			case "select":
				SelectSort.SelectSort(array);
				break;
			case "insert":
				InsertSort.InsertSort(array);
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
	}

}
