import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T> {

	private static int DEFAULT_CAPACITY=10;//��ʼĬ�ϴ�С
	private int size;//�����С
	private T[] content;//�洢��������
	
	public MyArrayList()
	{
		clear();
	}
	
	public void clear()//�������Ԫ��
	{
		size=0;
		ensureCapacity(DEFAULT_CAPACITY);//��֤������ڵ���Ĭ������
	}
	
	public int size()//��ȡ����Ԫ������
	{
		return size;
	}
	
	public boolean isEmpty()//�����Ƿ�Ϊ��
	{
		return size==0;
	}
	
	public void trimToSize()//�����������
	{
		ensureCapacity(size);
	}
	
	public T get(int index)//��ȡָ������λ�õ�Ԫ��
	{
		if(index<0||index>=size)throw new ArrayIndexOutOfBoundsException();
		return content[index];
	}
	
	public void set(int index,T newValue)//�ı�ָ������λ�õ�Ԫ��ֵ
	{
		if(index<0||index>=size)throw new ArrayIndexOutOfBoundsException();
		content[index]=newValue;
	}
	
	public void add(int index,T element)//��ָ������������һ��Ԫ��
	{
		if(index<0||index>size)throw new ArrayIndexOutOfBoundsException();
		
		if(content.length==size)//������ʱ
		{
			ensureCapacity(size*2+1);
		}
		
		for(int i=size;i>index;i--)//����λ֮���Ԫ�غ���һλ
		{
			content[i]=content[i-1];
		}
		
		content[index]=element;
		size++;
	}
	
	public void add(T element)//����������һ��Ԫ��
	{
		add(size,element);
	}
	
	public void remove(int index)//ɾ��ָ����������Ԫ��
	{
		if(index<0||index>=size)throw new ArrayIndexOutOfBoundsException();
		
		for(int i=index;i<size-1;i++)
		{
			content[i]=content[i+1];
		}
		
		size--;
	}
	
	public void ensureCapacity(int newCapacity)//��չ��������
	{
		if(newCapacity<size)return;
		
		T[] oldContent=content;
		content=(T[]) new Object[newCapacity];
		
		for(int i=0;i<size;i++)
		{
			content[i]=oldContent[i];
		}
	}
	
	public Iterator<T> iterator()//���ص�����
	{
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<T>//������
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
		
		public void remove()//ɾ��nextԽ����Ԫ��
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
