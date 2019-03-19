
public class AVLTree<T extends Comparable<? super T>> {
	
	private Node<T> root;
	
	private static class Node<T>
	{
		T element;
		Node<T> left;
		Node<T> right;
		int height;//�����ڵ�߶ȷ����ж��Ƿ�ƽ��
		
		Node(T element)
		{
			this(element,null,null);
		}
		
		Node(T element,Node<T> left,Node<T> right)//���ݸ������ҽڵ������½ڵ�
		{
			this.element=element;
			this.left=left;
			this.right=right;
			height=0;
		}
	}
	
	public AVLTree()
	{
		root=null;
	}
	
	public void clear()//���AVL��
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
	
	private int height(Node<T> root)//�����ڵ�߶ȣ����ڵ�Ϊ�գ���߶�Ϊ-1
	{
		return root==null?-1:root.height;
	}
	
	public void insert(T element)//����һ���ڵ�
	{
		root=insert(element,root);
	}
	
	private Node<T> insert(T element,Node<T> root)//�ݹ����һ���ڵ�
	{
		if(root==null)return new Node<>(element,null,null);
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)root.left=insert(element,root.left);
		else if(cmp>0)root.right=insert(element,root.right);
		
		return balance(root);
	}
	
	private Node<T> balance(Node<T> root)//ʹ��rootΪ���Ľڵ�ƽ��
	{
		if(root==null)return root;
		
		if(height(root.left)-height(root.right)>1)//����������ʹ��rootʧȥƽ��
		{
			if(height(root.left.left)>=height(root.left.right))//��������
			{
				root=rotateWithLeft(root);
			}
			else//��������
			{
				root=doubleWithLeft(root);
			}
		}
		else if(height(root.right)-height(root.left)>1)//����������ʹ��rootʧȥƽ��
		{
			if(height(root.right.right)>=height(root.right.left))//��������
			{
				root=rotateWithRight(root);
			}
			else//��������
			{
				root=doubleWithRight(root);
			}
		}
		
		root.height=Math.max(height(root.left), height(root.right))+1;//������������(���ڸղ���Ľڵ�ĸ��ڵ�)
		return root;
	}
	
	private Node<T> rotateWithLeft(Node<T> root)//�ҵ���ת
	{
		Node<T> nextRoot=root.left;
		
		root.left=nextRoot.right;
		nextRoot.right=root;
		
		root.height=Math.max(height(root.left), height(root.right))+1;
		nextRoot.height=Math.max(height(nextRoot.left), root.height)+1;
		
		return nextRoot;
	}
	
	private Node<T> rotateWithRight(Node<T> root)//����ת
	{
		Node<T> nextRoot=root.right;
		
		root.right=nextRoot.left;
		nextRoot.left=root;
		
		root.height=Math.max(height(root.left), height(root.right))+1;
		nextRoot.height=Math.max(height(nextRoot.right), root.height)+1;
		
		return nextRoot;
	}
	
	private Node<T> doubleWithLeft(Node<T> root)//��-��˫��ת
	{
		root.left=rotateWithRight(root.left);
		return rotateWithLeft(root);
	}
	
	private Node<T> doubleWithRight(Node<T> root)//��-��˫��ת
	{
		root.right=rotateWithLeft(root.right);
		return rotateWithRight(root);
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
	
	public void remove(T element)//ɾ��һ��Ԫ��
	{
		root=remove(element,root);
	}
	
	private Node<T> remove(T element,Node<T> root)//�ݹ��ɾ��һ���ڵ�
	{
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
			if(root.left!=null)
			{
				root=root.left;
			}
			else
			{
				root=root.right;
			}
		}
		
		return balance(root);
	}
	
	public boolean isAVL()//����������������ȷ��
	{
		return isAVL(root);
	}
	
	private boolean isAVL(Node<T> root)//���������ʽ����������������ȷ��
	{
		
		if(root==null)return true;
		
		if(root.left!=null&&(root.left.element.compareTo(root.element)>0))return false;
		if(root.right!=null&&(root.right.element.compareTo(root.element)<0))return false;
		if(Math.abs(height(root.left)-height(root.right))>1)return false;
		
		if(!isAVL(root.left))return false;
		if(!isAVL(root.right))return false;
		
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
		AVLTree<Integer> test=new AVLTree<>();
		
		for(int i=4;i<6;i++)test.insert(i);
		test.insert(8);
		test.insert(2);
		test.insert(3);
		
		System.out.println(test.isAVL());
		test.printTree();
		
		test.remove(8);
		test.remove(4);
		
		System.out.println(test.isAVL());
		test.printTree();
	}

}
