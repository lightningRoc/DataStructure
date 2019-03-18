

public class BinarySearchTree<T extends Comparable<? super T>> {

	private Node<T> root;//���ڵ�
	
	private static class Node<T>//���ڵ�
	{
		public T element;//����ֵ
		public Node<T> left;//���������ڵ�
		public Node<T> right;//���������ڵ�
		
		Node(T element)
		{
			this(element,null,null);
		}
		
		Node(T element,Node<T> left,Node<T> right)//�������ҽڵ㴴��һ�ڵ�
		{
			this.element=element;
			this.left=left;
			this.right=right;
		}
	}
	
	public BinarySearchTree()
	{
		root=null;
	}
	
	public void clear()//������������
	{
		root=null;
	}
	
	public boolean isEmpty()//���Ƿ�Ϊ��
	{
		return root==null;
	}
	
	public boolean contains(T element)//����Ԫ���Ƿ�������
	{
		return contains(element,root);
	}
	
	private boolean contains(T element,Node<T> root)//����һ�����ڵ㣬�ݹ�ز���Ԫ���Ƿ�������
	{
		if(root==null)return false;
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)return contains(element,root.left);
		else if(cmp>0)return contains(element,root.right);
		else return true;
	}
	
	public T findMin()//�ҳ����е���Сֵ
	{
		if(root==null)return null;
		else return findMin(root).element;
	}
	
	private Node<T> findMin(Node<T> root)//�ҳ��������ڵ�������Сֵ�ڵ�(�ݹ鷨)
	{
		if(root.left==null)return root;
		
		return findMin(root.left);
	}
	
	public T findMax()//�ҳ����е����ֵ
	{
		if(root==null)return null;
		else return findMax(root).element;
	}
	
	private Node<T> findMax(Node<T> root)//�ҳ��������ڵ�������Сֵ�ڵ�(�ǵݹ鷨)
	{
		while(root.right!=null)root=root.right;
		
		return root;
	}
	
	public void insert(T element)//�����в���һ��Ԫ��
	{
		root=insert(element,root);
	}
	
	private Node<T> insert(T element,Node<T> root)//�ݹ�ز������Ԫ������rootΪ���ڵ�����У������ش����ĸ��ڵ�
	{
		if(root==null)return new Node<>(element,null,null);
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)root.left=insert(element,root.left);
		else if(cmp>0)root.right=insert(element,root.right);
		
		return root;
	}
	
	public void remove(T element)//ɾ��ָ��Ԫ��
	{
		root=remove(element,root);
	}
	
	private Node<T> remove(T element,Node<T> root)//ɾ����rootΪ���ڵ�����еĸ���Ԫ�صĽڵ�
	{
		if(root==null)return root;
		
		int cmp=element.compareTo(root.element);
		
		//��Ҫɾ����ǰ�ڵ�
		/*
		 * 1�����ڵ������������ʱ���ƶ�����������Сֵ�ڵ���浱ǰ�ڵ㣬��Ӱ��Լ������
		 * 2�����ڵ�ֻ��һ������ʱ��ֱ��ɾ����ǰ�ڵ�
		 */
		if(cmp<0)root.left=remove(element,root.left);
		else if(cmp>0)root.right=remove(element,root.right);
		else if(root.left!=null&&root.right!=null)
		{
			root.element=findMin(root.right).element;
			root.right=remove(root.element,root.right);
		}
		else 
		{
			if(root.left!=null)
			{
				root=root.left;
			}
			else
			{
				root=root.right;
			}
		}
		
		return root;
	}
	
	public boolean isBST()//����������������ȷ��
	{
		return isBST(root);
	}
	
	private boolean isBST(Node<T> root)//���������ʽ����������������ȷ��
	{
		
		if(root==null)return true;
		
		if(root.left!=null&&(root.left.element.compareTo(root.element)>0))return false;
		if(root.right!=null&&(root.right.element.compareTo(root.element)<0))return false;
		
		if(!isBST(root.left))return false;
		if(!isBST(root.right))return false;
		
		return true;
	}
	
	public void printTree()//�������������ӡ���������
	{
		MyQueue<Node<T>> aux=new MyQueue<>();
		
		aux.enqueue(root);
		
		while(!aux.isEmpty())
		{
			int len=aux.size();
			for(int i=0;i<len;i++)
			{
				Node<T> next=aux.front();
				aux.dequeue();
				
				if(next==null)
				{
					System.out.print("null ");
				}
				else
				{
					if(next.left==null)aux.enqueue(null);
					else aux.enqueue(next.left);
					if(next.right==null)aux.enqueue(null);
					else aux.enqueue(next.right);
					System.out.print(next.element+" ");
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinarySearchTree<Integer> test=new BinarySearchTree<>();
		
		for(int i=4;i<6;i++)test.insert(i);
		test.insert(8);
		test.insert(2);
		test.insert(3);
		
		System.out.println(test.isBST());
		test.printTree();
		
		test.remove(8);
		test.remove(4);
		
		System.out.println(test.isBST());
		test.printTree();
	}

}
