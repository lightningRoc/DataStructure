import java.util.Random;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
	//二叉堆
	private T[] array;//用链表表示一颗完全二叉树
	private int size;//表示当前元素个数
	private static final int DEFAULT_SIZE=10;//默认堆大小
	
	public BinaryHeap()
	{
		this(DEFAULT_SIZE);
	}
	
	public BinaryHeap(int size)
	{
		size=0;
		array=(T[]) new Comparable[size+1];
	}
	
	public BinaryHeap(T[] items)//根据给定数组创建二叉堆
	{
		size=items.length;
		array=(T[]) new Comparable[(size+2)*11/10];//选取一个适合的大小，比原来大约[0.1*length+2.2]，可不需要
		
		int i=1;
		for(T item:items)
		{
			array[i++]=item;
		}
		
		for(i=size/2;i>0;i--)//对有可能破坏堆序的节点进行下沉操作
		{
			sink(i);
		}
	}
	
	public boolean isEmpty()//堆是否为空
	{
		return size==0;
	}
	
	public void clear()//清除二叉堆
	{
		size=0;
	}
	
	public void insert(T element)//插入一个节点
	{
		if(size==array.length-1)//数组满
			resize(array.length*2+1);
		
		size++;
		array[size]=element;
		swim(size);//将结点插入尾部并进行上浮操作
	}
	
	public T findMin()//查找最小值
	{
		if(isEmpty())return null;
		
		return array[1];//起点索引为1
	}
	
	public T deleteMin()//删除并返回最小值
	{
		if(isEmpty())return null;
		
		T min=findMin();
		array[1]=array[size--];//用最后一个节点代替最小值节点并进行下沉操作
		
		sink(1);
		
		return min;
	}
	
	private void sink(int hole)//对指定节点进行下沉操作
	{
		int child;
		T tmp=array[hole];//指定节点值
		
		for(;hole*2<=size;hole=child)
		{
			child=hole*2;//左孩子
			if(child!=size&&array[child+1].compareTo(array[child])<0)//当右孩子存在时选取较小的那一个孩子
			{
				child++;//右孩子
			}
			if(array[child].compareTo(tmp)<0)array[hole]=array[child];//将较小的结点上浮
			else break;//找到最终位置
		}
		
		array[hole]=tmp;//修改最终位置为指定节点
	}
	
	private void swim(int hole)//对指定节点进行上浮操作
	{
		T tmp=array[hole];//指定节点值
		
		for(array[0]=tmp;tmp.compareTo(array[hole/2])<0;hole/=2)//使array[0]为指定节点值便于终止
		{
			array[hole]=array[hole/2];
		}
		
		array[hole]=tmp;
	}
	
	private void resize(int newSize)//改变辅助数组的大小
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
		
		System.out.println("\n删除前：");
		test.printHeap();
		
		System.out.println("\n删除：");
		for(int i=0;i<20;i++)
		{
			System.out.println(test.deleteMin());
		}
	}

}
