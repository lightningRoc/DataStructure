import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T>{
	
	private int size;//����Ԫ������
	private Node<T> head;//ͷ�ڵ�
	private Node<T> tail;//β�ڵ�
	
	public MyLinkedList()
	{
		clear();
	}
	
	private static class Node<T>//��ʾ�ڵ�
	{
		public T value;
		public Node<T> pre;
		public Node<T> next;
		
		public Node(T value,Node<T> pre,Node<T> next)//���������ڵ㣬����������һ��ֵΪvalue���½ڵ�
		{
			this.value=value;
			this.pre=pre;
			this.next=next;
		}
	}
	
	public void clear()//�������
	{
		head=new Node<T>(null,null,null);
		tail=new Node<T>(null,head,null);
		
		head.next=tail;
		size=0;
	}
	
	public int size()//���������С
	{
		return size;
	}
	
	public boolean isEmpty()//�ж������Ƿ�Ϊ��
	{
		return size==0;
	}
	
	private void addPrevious(Node<T> pre,T element)//���½ڵ����ָ���ڵ�֮ǰ
	{
		Node<T> newNode=new Node<>(element,pre.pre,pre);
		newNode.pre.next=newNode;
		pre.pre=newNode;
		
		size++;
	}
	
	public void add(int index,T element)//��Ԫ�ز���ָ��������
	{
		addPrevious(getNode(index,0,size()),element);
	}
	
	public void add(T element)//��Ԫ�ز��������
	{
		add(size,element);
	}
	
	public void addFirst(T element)//��Ԫ�ز�������ͷ
	{
		add(0,element);
	}
	
	public void addLast(T element)//ͬadd(T element)
	{
		add(element);
	}
	
	public void removeFirst()//ɾ����һ��Ԫ��
	{
		remove(head.next);
	}
	
	public void removeLast()//ɾ�����һ��Ԫ��
	{
		remove(tail.pre);
	}
	
	public T getFirst()//��ȡ��һ��Ԫ��
	{
		return get(0);
	}
	
	public T getLast()//��ȡ���һ��Ԫ��
	{
		return get(size-1);
	}
	
	public T get(int index)//��ȡָ����������Ԫ��ֵ
	{
		return getNode(index,0,size-1).value;
	}
	
	public void set(int index,T newValue)//�ı���������Ԫ��
	{
		Node<T> node=getNode(index,0,size-1);
		node.value=newValue;
	}
	
	public void remove(int index)//ɾ��ָ����������Ԫ��
	{
		Node<T> node=getNode(index,0,size-1);
		
		remove(node);
	}
	
	public void remove(T element)//����Ԫ�ؽ���ɾ��
	{
		Node<T> cursion=head.next;
		
		while(cursion!=tail)
		{
			if(cursion.value.equals(element))
			{
				remove(cursion);
				break;
			}
			cursion=cursion.next;
		}
	}
	
	private void remove(Node<T> node)//ɾ��ָ���ڵ�
	{
		node.next.pre=node.pre;
		node.pre.next=node.next;
		size--;
	}
	
	//��������ֵֵΪindex����λ��lower��upper֮��Ľڵ�
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
	
	public boolean contains(T element)//Ԫ���Ƿ���������
	{
		Node<T> p=head.next;
		while(p!=tail&&!(p.value.equals(p.value)))
		{
			p=p.next;
		}
		
		return p!=tail;
	}
	
	public Iterator<T> iterator()//���ص�����
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
		
		public void remove()//ɾ��nextԽ����Ԫ�أ����ҽ���next()�����ú�ſ�ִ��
		{
			if(!okToRemove)throw new IllegalStateException();
			
			MyLinkedList.this.remove(cursion.pre);
			okToRemove=false;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyLinkedList<Integer> test=new MyLinkedList<>();
		for(int i=0;i<10;i++)test.add(i*2);
		test.remove(new Integer(2));//��test.remove(2)��ͬ
		
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
		
		System.out.println("ɾ����");
		System.out.println(test.size());
	}

}
