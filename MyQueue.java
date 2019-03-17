import java.util.Iterator;


public class MyQueue<T> implements Iterable<T>{
	
private MyLinkedList<T> queue;
	
	public MyQueue()
	{
		queue=new MyLinkedList<>();
	}
	
	public int size()//��ȡ��С
	{
		return queue.size();
	}
	
	public void enqueue(T element)//��һ��Ԫ�������
	{
		queue.addLast(element);
	}
	
	public T dequeue()//��һ��Ԫ�س�����
	{
		T res=queue.getFirst();
		queue.removeFirst();
		return res;
	}
	
	public T front()//�鿴����ͷԪ��
	{
		return queue.getFirst();
	}
	
	public boolean isEmpty()//�ж϶����Ƿ�Ϊ��
	{
		return queue.isEmpty();
	}
	
	public void clear()//��ն���
	{
		queue.clear();
	}
	
	public Iterator<T> iterator()//���ص�����
	{
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<T>
	{
		private int cursion=0;
		
		public boolean hasNext()
		{
			return cursion<queue.size();
		}
		
		public T next()
		{
			if(!hasNext())return null;
			
			return queue.get(cursion++);
		}
		
		public void remove()//ɾ��nextԽ����Ԫ�أ����ҽ���next()�����ú�ſ�ִ��
		{	
			queue.remove(cursion-1);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyQueue<Integer> test=new MyQueue<>();
		for(int i=0;i<10;i++)test.enqueue(i);
		
		for(int n:test)
		{
			System.out.println(n);
		}
		
		Iterator<Integer> itr=test.iterator();
		System.out.println();
		
		while(itr.hasNext())
		{
			itr.next();
			itr.remove();
		}
		
		System.out.println("\n"+test.size());
	}

}
