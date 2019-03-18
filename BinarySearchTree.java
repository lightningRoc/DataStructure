

public class BinarySearchTree<T extends Comparable<? super T>> {

	private Node<T> root;//根节点
	
	private static class Node<T>//树节点
	{
		public T element;//保存值
		public Node<T> left;//左子树根节点
		public Node<T> right;//右子树根节点
		
		Node(T element)
		{
			this(element,null,null);
		}
		
		Node(T element,Node<T> left,Node<T> right)//给定左右节点创建一节点
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
	
	public void clear()//清除二叉查找树
	{
		root=null;
	}
	
	public boolean isEmpty()//树是否为空
	{
		return root==null;
	}
	
	public boolean contains(T element)//给定元素是否在树中
	{
		return contains(element,root);
	}
	
	private boolean contains(T element,Node<T> root)//给定一个根节点，递归地查找元素是否在树中
	{
		if(root==null)return false;
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)return contains(element,root.left);
		else if(cmp>0)return contains(element,root.right);
		else return true;
	}
	
	public T findMin()//找出树中的最小值
	{
		if(root==null)return null;
		else return findMin(root).element;
	}
	
	private Node<T> findMin(Node<T> root)//找出给定根节点树的最小值节点(递归法)
	{
		if(root.left==null)return root;
		
		return findMin(root.left);
	}
	
	public T findMax()//找出树中的最大值
	{
		if(root==null)return null;
		else return findMax(root).element;
	}
	
	private Node<T> findMax(Node<T> root)//找出给定根节点树的最小值节点(非递归法)
	{
		while(root.right!=null)root=root.right;
		
		return root;
	}
	
	public void insert(T element)//向树中插入一个元素
	{
		root=insert(element,root);
	}
	
	private Node<T> insert(T element,Node<T> root)//递归地插入给定元素至以root为根节点的树中，并返回处理后的根节点
	{
		if(root==null)return new Node<>(element,null,null);
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)root.left=insert(element,root.left);
		else if(cmp>0)root.right=insert(element,root.right);
		
		return root;
	}
	
	public void remove(T element)//删除指定元素
	{
		root=remove(element,root);
	}
	
	private Node<T> remove(T element,Node<T> root)//删除以root为根节点的树中的给定元素的节点
	{
		if(root==null)return root;
		
		int cmp=element.compareTo(root.element);
		
		//若要删除当前节点
		/*
		 * 1、当节点存在左右子树时，移动右子树的最小值节点代替当前节点，不影响约束条件
		 * 2、当节点只有一个子树时，直接删除当前节点
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
	
	public boolean isBST()//检验二叉查找树的正确性
	{
		return isBST(root);
	}
	
	private boolean isBST(Node<T> root)//先序遍历方式检验二叉查找树的正确性
	{
		
		if(root==null)return true;
		
		if(root.left!=null&&(root.left.element.compareTo(root.element)>0))return false;
		if(root.right!=null&&(root.right.element.compareTo(root.element)<0))return false;
		
		if(!isBST(root.left))return false;
		if(!isBST(root.right))return false;
		
		return true;
	}
	
	public void printTree()//宽度优先搜索打印二叉查找树
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
