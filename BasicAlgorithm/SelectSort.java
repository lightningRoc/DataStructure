import java.util.Random;

public class SelectSort {
	//ѡ������
	public static void selectSort(Comparable[] array)
	{
		int len=array.length;
		for(int i=0;i<len;i++)
		{
			//Ѱ��i��len-1�е���СԪ�أ����ŵ�λ��i��
			int location=i;
			for(int j=i+1;j<len;j++)
			{
				if(array[location].compareTo(array[j])>0)location=j;
			}
			exchange(location,i,array);
		}
	}
	
	private static void exchange(int i,int j,Comparable[] array)//����λ��i��j�ϵ�Ԫ��
	{
		Comparable tmp=array[i];
		array[i]=array[j];
		array[j]=tmp;
	}
	
	private static boolean isSorted(Comparable[] array)//�ж������Ƿ�Ϊ��������
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
