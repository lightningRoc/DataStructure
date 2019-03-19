
public class AVLTree<T extends Comparable<? super T>> {
	
	private Node<T> root;
	
	private static class Node<T>
	{
		T element;
		Node<T> left;
		Node<T> right;
		int height;//给出节点高度方便判断是否平衡
		
		Node(T element)
		{
			this(element,null,null);
		}
		
		Node(T element,Node<T> left,Node<T> right)//根据给定左右节点生成新节点
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
	
	public void clear()//清除AVL树
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
	
	private int height(Node<T> root)//给出节点高度，若节点为空，则高度为-1
	{
		return root==null?-1:root.height;
	}
	
	public void insert(T element)//插入一个节点
	{
		root=insert(element,root);
	}
	
	private Node<T> insert(T element,Node<T> root)//递归插入一个节点
	{
		if(root==null)return new Node<>(element,null,null);
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)root.left=insert(element,root.left);
		else if(cmp>0)root.right=insert(element,root.right);
		
		return balance(root);
	}
	
	private Node<T> balance(Node<T> root)//使以root为根的节点平衡
	{
		if(root==null)return root;
		
		if(height(root.left)-height(root.right)>1)//左子树增长使得root失去平衡
		{
			if(height(root.left.left)>=height(root.left.right))//左左增长
			{
				root=rotateWithLeft(root);
			}
			else//左右增长
			{
				root=doubleWithLeft(root);
			}
		}
		else if(height(root.right)-height(root.left)>1)//右子树增长使得root失去平衡
		{
			if(height(root.right.right)>=height(root.right.left))//右右增长
			{
				root=rotateWithRight(root);
			}
			else//右左增长
			{
				root=doubleWithRight(root);
			}
		}
		
		root.height=Math.max(height(root.left), height(root.right))+1;//连带更新作用(对于刚插入的节点的父节点)
		return root;
	}
	
	private Node<T> rotateWithLeft(Node<T> root)//右单旋转
	{
		Node<T> nextRoot=root.left;
		
		root.left=nextRoot.right;
		nextRoot.right=root;
		
		root.height=Math.max(height(root.left), height(root.right))+1;
		nextRoot.height=Math.max(height(nextRoot.left), root.height)+1;
		
		return nextRoot;
	}
	
	private Node<T> rotateWithRight(Node<T> root)//左单旋转
	{
		Node<T> nextRoot=root.right;
		
		root.right=nextRoot.left;
		nextRoot.left=root;
		
		root.height=Math.max(height(root.left), height(root.right))+1;
		nextRoot.height=Math.max(height(nextRoot.right), root.height)+1;
		
		return nextRoot;
	}
	
	private Node<T> doubleWithLeft(Node<T> root)//左-右双旋转
	{
		root.left=rotateWithRight(root.left);
		return rotateWithLeft(root);
	}
	
	private Node<T> doubleWithRight(Node<T> root)//右-左双旋转
	{
		root.right=rotateWithLeft(root.right);
		return rotateWithRight(root);
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
	
	public void remove(T element)//删除一个元素
	{
		root=remove(element,root);
	}
	
	private Node<T> remove(T element,Node<T> root)//递归地删除一个节点
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
	
	public boolean isAVL()//检验二叉查找树的正确性
	{
		return isAVL(root);
	}
	
	private boolean isAVL(Node<T> root)//先序遍历方式检验二叉查找树的正确性
	{
		
		if(root==null)return true;
		
		if(root.left!=null&&(root.left.element.compareTo(root.element)>0))return false;
		if(root.right!=null&&(root.right.element.compareTo(root.element)<0))return false;
		if(Math.abs(height(root.left)-height(root.right))>1)return false;
		
		if(!isAVL(root.left))return false;
		if(!isAVL(root.right))return false;
		
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
