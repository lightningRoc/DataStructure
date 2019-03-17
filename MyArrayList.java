import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T> {

	private static int DEFAULT_CAPACITY=10;//初始默认大小
	private int size;//数组大小
	private T[] content;//存储数组内容
	
	public MyArrayList()
	{
		clear();
	}
	
	public void clear()//清除数组元素
	{
		size=0;
		ensureCapacity(DEFAULT_CAPACITY);//保证数组大于等于默认容量
	}
	
	public int size()//获取数组元素数量
	{
		return size;
	}
	
	public boolean isEmpty()//数组是否为空
	{
		return size==0;
	}
	
	public void trimToSize()//清除多余容量
	{
		ensureCapacity(size);
	}
	
	public T get(int index)//获取指定索引位置的元素
	{
		if(index<0||index>=size)throw new ArrayIndexOutOfBoundsException();
		return content[index];
	}
	
	public void set(int index,T newValue)//改变指定索引位置的元素值
	{
		if(index<0||index>=size)throw new ArrayIndexOutOfBoundsException();
		content[index]=newValue;
	}
	
	public void add(int index,T element)//在指定索引处插入一个元素
	{
		if(index<0||index>size)throw new ArrayIndexOutOfBoundsException();
		
		if(content.length==size)//数组满时
		{
			ensureCapacity(size*2+1);
		}
		
		for(int i=size;i>index;i--)//索引位之后的元素后移一位
		{
			content[i]=content[i-1];
		}
		
		content[index]=element;
		size++;
	}
	
	public void add(T element)//向数组后插入一个元素
	{
		add(size,element);
	}
	
	public void remove(int index)//删除指定索引处的元素
	{
		if(index<0||index>=size)throw new ArrayIndexOutOfBoundsException();
		
		for(int i=index;i<size-1;i++)
		{
			content[i]=content[i+1];
		}
		
		size--;
	}
	
	public void ensureCapacity(int newCapacity)//扩展数组容量
	{
		if(newCapacity<size)return;
		
		T[] oldContent=content;
		content=(T[]) new Object[newCapacity];
		
		for(int i=0;i<size;i++)
		{
			content[i]=oldContent[i];
		}
	}
	
	public Iterator<T> iterator()//返回迭代器
	{
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<T>//迭代器
	{
		private int cursion=0;
		
		public boolean hasNext()
		{
			return cursion<size;
		}
		
		public T next()
		{
			if(!hasNext())return null;
			
			return content[cursion++];
		}
		
		public void remove()//删除next越过的元素
		{
			MyArrayList.this.remove(--cursion);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyArrayList<Integer> test=new MyArrayList<>();
		for(int i=0;i<10;i++)test.add(i);
		
		for(int n:test)
		{
			System.out.println(n);
		}
		
		Iterator<Integer> itr=test.iterator();
		System.out.println();
		
		while(itr.hasNext())
		{
			System.out.println(itr.next());
			itr.remove();
		}
		
		System.out.println("\n"+test.size());
	}

}
