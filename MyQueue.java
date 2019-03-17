import java.util.Iterator;


public class MyQueue<T> implements Iterable<T>{
	
private MyLinkedList<T> queue;
	
	public MyQueue()
	{
		queue=new MyLinkedList<>();
	}
	
	public int size()//获取大小
	{
		return queue.size();
	}
	
	public void enqueue(T element)//将一个元素入队列
	{
		queue.addLast(element);
	}
	
	public T dequeue()//将一个元素出队列
	{
		T res=queue.getFirst();
		queue.removeFirst();
		return res;
	}
	
	public T front()//查看队列头元素
	{
		return queue.getFirst();
	}
	
	public boolean isEmpty()//判断队列是否为空
	{
		return queue.isEmpty();
	}
	
	public void clear()//清空队列
	{
		queue.clear();
	}
	
	public Iterator<T> iterator()//返回迭代器
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
		
		public void remove()//删除next越过的元素，并且仅当next()被调用后才可执行
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
