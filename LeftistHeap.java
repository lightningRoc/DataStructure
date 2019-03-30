import java.util.Random;

public class LeftistHeap<T extends Comparable<? super T>> {
	//��ʽ��
	private Node<T> root;//�Ѹ��ڵ�
	
	public LeftistHeap()
	{
		root=null;
	}
	
	private static class Node<T>//��ʾһ���ڵ�
	{
		T element;
		Node<T> left;
		Node<T> right;
		int npl;//null path length(��ʾ�ڵ����·����)
		
		Node(T element)
		{
			this(element,null,null);
		}
		
		Node(T element,Node<T> left,Node<T> right)
		{
			this.element=element;
			this.left=left;
			this.right=right;
			npl=0;
		}
	}
	
	public boolean isEmpty()//���Ƿ�Ϊ��
	{
		return root==null;
	}
	
	public void clear()//��ն�
	{
		root=null;
	}
	
	public void merge(LeftistHeap<T> another)//��������ͬ����ʽ�Ѻϲ�,�����another
	{
		if(this==another)return;//��ֹ����ͬһ��
		
		root=merge(root,another.root);
		another.root=null;
	}
	
	private Node<T> merge(Node<T> h1,Node<T> h2)//ʵ�ֵݹ��merge�����ĸ�������(h2�ϲ���h1)
	{
		if(h1==null)return h2;
		if(h2==null)return h1;
		
		if(h1.element.compareTo(h2.element)<0)
		{
			return mergeAux(h1,h2);//h2�ϲ���h1
		}
		else
		{
			return mergeAux(h2,h1);//h1�ϲ���h2
		}
	}
	
	private Node<T> mergeAux(Node<T> h1,Node<T> h2)//����������h2�ϲ���h1
	{
		if(h1.left==null)h1.left=h2;//��֤��ʽ������
		else
		{
			h1.right=merge(h1.right,h2);
			if(h1.left.npl<h1.right.npl)
			{
				swapChildren(h1);
			}
			h1.npl=h1.right.npl+1;
		}
		
		return h1;
	}
	
	private void swapChildren(Node<T> root)//����һ���ڵ�����Һ���
	{
		Node<T> tmp=root.left;
		root.left=root.right;
		root.right=tmp;
	}
	
	public void insert(T element)//��Ԫ�ز�����ʽ����
	{
		root=merge(new Node<>(element),root);
	}
	
	public T findMin()//Ѱ����Сֵ�ڵ�
	{
		if(isEmpty())return null;
		return root.element;
	}
	
	public T deleteMin()//ɾ����������Сֵ�ڵ�
	{
		if(isEmpty())return null;
		
		T res=root.element;
		root=merge(root.left,root.right);
		return res;
	}
	
	private boolean isLeftistHeap()//������ʽ������
	{
		if(root==null)return true;
		
		return isLeftistHeap(root);
	}
	
	private boolean isLeftistHeap(Node<T> root)
	{
		if(root.left==null&&root.right==null)return true;
		if(root.left==null)return false;
		if(root.right==null)return true;
		
		if(root.left.npl<root.right.npl||root.left.element.compareTo(root.element)<0||root.right.element.compareTo(root.element)<0)return false;
		
		if(!isLeftistHeap(root.left))return false;
		if(!isLeftistHeap(root.right))return false;
		
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeftistHeap<Integer> heap1=new LeftistHeap<>();
		LeftistHeap<Integer> heap2=new LeftistHeap<>();
		Random rand=new Random();
		
		for(int i=0;i<30;i++)heap1.insert(rand.nextInt(200));
		for(int i=0;i<30;i++)heap2.insert(rand.nextInt(200));
		
		System.out.println("������");
		System.out.println(heap1.isLeftistHeap());
		System.out.println(heap2.isLeftistHeap());
		
		for(int i=0;i<10;i++)
		{
			heap1.deleteMin();
			heap2.deleteMin();
		}
		
		System.out.println("ɾ����");
		System.out.println(heap1.isLeftistHeap());
		System.out.println(heap2.isLeftistHeap());
		
		heap1.merge(heap2);
		System.out.println("�ϲ���");
		System.out.println(heap1.isLeftistHeap());
	}

}
