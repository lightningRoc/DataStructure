import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T>{
	
	private int size;//链表元素数量
	private Node<T> head;//头节点
	private Node<T> tail;//尾节点
	
	public MyLinkedList()
	{
		clear();
	}
	
	private static class Node<T>//表示节点
	{
		public T value;
		public Node<T> pre;
		public Node<T> next;
		
		public Node(T value,Node<T> pre,Node<T> next)//给定两个节点，在两点间插入一个值为value的新节点
		{
			this.value=value;
			this.pre=pre;
			this.next=next;
		}
	}
	
	public void clear()//清除链表
	{
		head=new Node<T>(null,null,null);
		tail=new Node<T>(null,head,null);
		
		head.next=tail;
		size=0;
	}
	
	public int size()//返回链表大小
	{
		return size;
	}
	
	public boolean isEmpty()//判断链表是否为空
	{
		return size==0;
	}
	
	private void addPrevious(Node<T> pre,T element)//将新节点插入指定节点之前
	{
		Node<T> newNode=new Node<>(element,pre.pre,pre);
		newNode.pre.next=newNode;
		pre.pre=newNode;
		
		size++;
	}
	
	public void add(int index,T element)//将元素插入指定索引处
	{
		addPrevious(getNode(index,0,size()),element);
	}
	
	public void add(T element)//将元素插入链表后
	{
		add(size,element);
	}
	
	public void addFirst(T element)//将元素插入链表头
	{
		add(0,element);
	}
	
	public void addLast(T element)//同add(T element)
	{
		add(element);
	}
	
	public void removeFirst()//删除第一个元素
	{
		remove(head.next);
	}
	
	public void removeLast()//删除最后一个元素
	{
		remove(tail.pre);
	}
	
	public T getFirst()//获取第一个元素
	{
		return get(0);
	}
	
	public T getLast()//获取最后一个元素
	{
		return get(size-1);
	}
	
	public T get(int index)//获取指定索引处的元素值
	{
		return getNode(index,0,size-1).value;
	}
	
	public void set(int index,T newValue)//改变索引处的元素
	{
		Node<T> node=getNode(index,0,size-1);
		node.value=newValue;
	}
	
	public void remove(int index)//删除指定索引处的元素
	{
		Node<T> node=getNode(index,0,size-1);
		
		remove(node);
	}
	
	private void remove(Node<T> node)//删除指定节点
	{
		node.next.pre=node.pre;
		node.pre.next=node.next;
		size--;
	}
	
	//返回索引值值为index并且位于lower与upper之间的节点
	private Node<T> getNode(int index,int lower,int upper)
	{
		Node<T> res=null;
		
		if(index<lower||index>upper)throw new IndexOutOfBoundsException();
		
		if(index<size/2)
		{
			res=head.next;
			for(int i=0;i<index;i++)res=res.next;
		}
		else
		{
			res=tail;
			for(int i=size;i>index;i--)
			{
				res=res.pre;
			}
		}
		
		return res;
	}
	
	public boolean contains(T element)//元素是否在链表中
	{
		Node<T> p=head.next;
		while(p!=tail&&!(p.value.equals(p)))
		{
			p=p.next;
		}
		
		return p!=tail;
	}
	
	public Iterator<T> iterator()//返回迭代器
	{
		return new LinkedListIterator();
	}
	
	private class LinkedListIterator implements Iterator<T>
	{
		private Node<T> cursion=head.next;
		private boolean okToRemove=false;
		
		public boolean hasNext()
		{
			return cursion!=tail;
		}
		
		public T next()
		{
			if(!hasNext())return null;
			
			T res=cursion.value;
			cursion=cursion.next;
			okToRemove=true;
			
			return res;
		}
		
		public void remove()//删除next越过的元素，并且仅当next()被调用后才可执行
		{
			if(!okToRemove)throw new IllegalStateException();
			
			MyLinkedList.this.remove(cursion.pre);
			okToRemove=false;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyLinkedList<Integer> test=new MyLinkedList<>();
		for(int i=0;i<10;i++)test.add(i);
		
		for(int n:test)
		{
			System.out.println(n);
		}
		
		Iterator<Integer> itr=test.iterator();
		
		while(itr.hasNext())
		{
			itr.next();
			itr.remove();
		}
		
		System.out.println(test.size());
	}

}
