import java.util.Random;

public class SortMethodCmp {
	//�������򷽷�Ч�ʱȽ�
	
	public static void getResultByAMethod(String choice)//����һ������С·
	{
		Random rand=new Random();
		Integer[] array=new Integer[1000];
		long start=System.currentTimeMillis();
		for(int i=0;i<1000;i++)
		{
			//����1000����ͬ��1000��С���������
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
