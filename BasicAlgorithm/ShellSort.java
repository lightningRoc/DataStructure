import java.util.Random;

public class ShellSort {
	//ϣ������
	public static void shellSort(Comparable[] array)
	{
		int len=array.length;
		int h=1;
		while(h<len/3)h=3*h+1;//��������:1,7,10,13...(��ʼ�ڿ��õ����ֵ)
		while(h>=1) {
			//����һ�˲��ö���i,i+1...N-1(�൱�ڶ���ÿ�ֿ��ܵ�����)���в�������
			for(int i=h;i<len;i++) {
				for(int j=i;j>=h&&array[j].compareTo(array[j-h])<0;j-=h) {
					exchange(j,j-h,array);//������ʾ�����Ĳ�������
				}
			}
			h=h/3;
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
		shellSort(a);
		System.out.println(isSorted(a));
	}

}
