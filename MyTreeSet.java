import java.util.Iterator;

public class MyTreeSet<T extends Comparable<? super T>> implements Iterable<T>{
	
	private Node<T> root;
	
	private static class Node<T>
	{
		T element;
		Node<T> left;
		Node<T> right;
		Node<T> parent;
		
		Node(T element)
		{
			this(element,null,null,null);
		}
		
		Node(T element,Node<T> left,Node<T> right,Node<T> parent)
		{
			this.element=element;
			this.left=left;
			this.right=right;
			this.parent=parent;
		}
	}
	
	public MyTreeSet()
	{
		root=null;
	}
	
	public void clear()//�������
	{
		root=null;
	}
	
	public boolean isEmpty()//�����Ƿ�Ϊ��
	{
		return root==null;
	}
	
	public boolean contains(T element)//�ж�Ԫ���Ƿ��ڼ�����
	{
		return contains(element,root);
	}
	
	private boolean contains(T element,Node<T> root)//�ݹ��ѯԪ���Ƿ��ڼ�����
	{
		if(root==null)return false;
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)return contains(element,root.left);
		else if(cmp>0)return contains(element,root.right);
		else return true;
	}
	
	public T findMin()//������СԪ��
	{
		return findMin(root).element;
	}
	
	private Node<T> findMin(Node<T> root)//�ݹ�ز�����СԪ��
	{
		if(root==null)return null;
		else if(root.left==null)return root;
		
		return findMin(root.left);
	}
	
	public T findMax()//�������Ԫ��
	{
		return findMax(root).element;
	}
	
	private Node<T> findMax(Node<T> root)//�ݹ�ز������Ԫ��
	{
		if(root==null)return null;
		else if(root.right==null)return root;
		
		return findMax(root.right);
	}
	
	public void insert(T element)//����һ��Ԫ�ص�������
	{
		root=insert(element,root,null);
	}
	
	private Node<T> insert(T element,Node<T> root,Node<T> parent)
	{
		//�ݹ�ز���һ��Ԫ����������
		if(root==null)return new Node(element,null,null,parent);
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)root.left=insert(element,root.left,root);
		else if(cmp>0)root.right=insert(element,root.right,root);
		
		return root;
	}
	
	public void remove(T element)//ɾ�������е�Ԫ��
	{
		root=remove(element,root);
	}
	
	private Node<T> remove(T element,Node<T> root)
	{
		//�ݹ��ɾ��һ��Ԫ��
		if(root==null)return root;
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)root.left=remove(element,root.left);
		else if(cmp>0)root.right=remove(element,root.right);
		else if(root.left!=null&&root.right!=null)
		{
			root.element=findMin(root.right).element;
			root.right=remove(root.element,root.right);
		}
		else
		{
			Node<T> nextChild=null;
			if(root.left!=null)
			{
				nextChild=root.left;
			}
			else
			{
				nextChild=root.right;
			}
			
			if(nextChild!=null)nextChild.parent=root.parent;
			root=nextChild;
		}
		
		return root;
	}
	
	public Iterator<T> iterator()
	{
		return new MyTreeSetIterator();
	}
	
	private class MyTreeSetIterator implements Iterator<T>
	{
		//������
		private Node<T> current=findMin(root);//����СԪ�ؿ�ʼ
		private Node<T> previous=null;//��һ�ڵ�
		private boolean okToRemove=false;//remove()֮ǰ�Ƿ�next()
		private boolean end=false;//�Ƿ����
		
		public boolean hasNext()
		{
			return !end;
		}
		
		public T next()
		{
			if(!hasNext())return null;
			
			T nextEle=current.element;
			previous=current;
			
			if(current.right!=null)
			{
				//����ɾ��������ʱ���ȱ���������
				current=findMin(current.right);
			}
			else
			{ 
				Node<T> child=current;
				current=current.parent;
				
				/*ֹͣ�������ӵĽڵ㴦
				 * �ǵݹ��������
				 * ����ԭ��
				 * 1��Ҫ������ĳһ��root��������ʼ���ҵ�����Сֵ�Ľڵ�
				 * 
				 * 2�����ڿ�ʼλ����С�ڵ㴦������ڴ���������֮ǰ
				 * �������Լ����Ѿ�������
				 * 
				 * 3������ڷ���ʱֻ��Ҫ�ڴ��������丸ĸ���ƶ����ֹͣ�����Һ��ӷ�������Ҫֹͣ
				 * 
				 * ��������һ���߶�Ϊ1���������ڵ㣬�������׵ó��÷��������е�ͨ
				 * �ݹ��
				 * ����һ���Ը�����yΪ���ӵĸ�x�����Գɹ��������нڵ�
				 * ������x���������������ù���1��2��3��ɱ���������x�Ѿ�������
				 * ���x����Ҫ�ٱ������Ӷ����һ�������ı�����Ȼ�������һ�ֹ���
				 * ֱ������ĳһ�ڵ�ĸ�ĸΪnull����root��Ϊֹ
				 */
				while(current!=null&&current.left!=child)
				{
					child=current;
					current=current.parent;
				}
				
				if(current==null)end=true;
				
			}
			
			
			okToRemove=true;
			return nextEle;
		}
		
		public void remove()
		{
			if(!okToRemove)return;
			
			MyTreeSet.this.remove(previous.element);
			okToRemove=false;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTreeSet<Integer> test=new MyTreeSet<>();
		for(int i=0;i<10;i++)test.insert(i);
		
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
		
	}

}
