import java.util.Random;

public class HeapSort {
	//������
	public static void heapSort(Comparable[] array)
	{
		//ʹ�ü򵥵Ķ���ʹ��һ����������ķ�ʽ��ʵ��(Ҳ����ͨ��ʹ��һ��max����ͨ�������滻���һ��Ԫ������ʡ�ռ�)
		int len=array.length;
		BinaryHeap auxHeap=new BinaryHeap(array);
		for(int i=0;i<len;i++)
		{
			array[i]=auxHeap.deleteMin();
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
		heapSort(a);
		System.out.println(isSorted(a));
	}

}
