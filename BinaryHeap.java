import java.util.Random;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
	//�����
	private T[] array;//�������ʾһ����ȫ������
	private int size;//��ʾ��ǰԪ�ظ���
	private static final int DEFAULT_SIZE=10;//Ĭ�϶Ѵ�С
	
	public BinaryHeap()
	{
		this(DEFAULT_SIZE);
	}
	
	public BinaryHeap(int size)
	{
		size=0;
		array=(T[]) new Comparable[size+1];
	}
	
	public BinaryHeap(T[] items)//���ݸ������鴴�������
	{
		size=items.length;
		array=(T[]) new Comparable[(size+2)*11/10];//ѡȡһ���ʺϵĴ�С����ԭ����Լ[0.1*length+2.2]���ɲ���Ҫ
		
		int i=1;
		for(T item:items)
		{
			array[i++]=item;
		}
		
		for(i=size/2;i>0;i--)//���п����ƻ�����Ľڵ�����³�����
		{
			sink(i);
		}
	}
	
	public boolean isEmpty()//���Ƿ�Ϊ��
	{
		return size==0;
	}
	
	public void clear()//��������
	{
		size=0;
	}
	
	public void insert(T element)//����һ���ڵ�
	{
		if(size==array.length-1)//������
			resize(array.length*2+1);
		
		size++;
		array[size]=element;
		swim(size);//��������β���������ϸ�����
	}
	
	public T findMin()//������Сֵ
	{
		if(isEmpty())return null;
		
		return array[1];//�������Ϊ1
	}
	
	public T deleteMin()//ɾ����������Сֵ
	{
		if(isEmpty())return null;
		
		T min=findMin();
		array[1]=array[size--];//�����һ���ڵ������Сֵ�ڵ㲢�����³�����
		
		sink(1);
		
		return min;
	}
	
	private void sink(int hole)//��ָ���ڵ�����³�����
	{
		int child;
		T tmp=array[hole];//ָ���ڵ�ֵ
		
		for(;hole*2<=size;hole=child)
		{
			child=hole*2;//����
			if(child!=size&&array[child+1].compareTo(array[child])<0)//���Һ��Ӵ���ʱѡȡ��С����һ������
			{
				child++;//�Һ���
			}
			if(array[child].compareTo(tmp)<0)array[hole]=array[child];//����С�Ľ���ϸ�
			else break;//�ҵ�����λ��
		}
		
		array[hole]=tmp;//�޸�����λ��Ϊָ���ڵ�
	}
	
	private void swim(int hole)//��ָ���ڵ�����ϸ�����
	{
		T tmp=array[hole];//ָ���ڵ�ֵ
		
		for(array[0]=tmp;tmp.compareTo(array[hole/2])<0;hole/=2)//ʹarray[0]Ϊָ���ڵ�ֵ������ֹ
		{
			array[hole]=array[hole/2];
		}
		
		array[hole]=tmp;
	}
	
	private void resize(int newSize)//�ı丨������Ĵ�С
	{
		T[] old=array;
		array=(T[]) new Comparable[newSize];
		
		for(int i=0;i<old.length;i++)
		{
			array[i]=old[i];
		}
	}
	
	private void printHeap()
	{
		for(int i=1;i<=size;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	
	public int size()
	{
		return size;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinaryHeap<Integer> test=new BinaryHeap<>();
		
		Random rand=new Random();
		for(int i=0;i<20;i++)
		{
			int num=rand.nextInt(100);
			System.out.print(num+" ");
			test.insert(num);
		}
		
		System.out.println("\nɾ��ǰ��");
		test.printHeap();
		
		System.out.println("\nɾ����");
		for(int i=0;i<20;i++)
		{
			System.out.println(test.deleteMin());
		}
	}

}
