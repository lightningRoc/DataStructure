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
	
	public void clear()//清除集合
	{
		root=null;
	}
	
	public boolean isEmpty()//集合是否为空
	{
		return root==null;
	}
	
	public boolean contains(T element)//判断元素是否在集合中
	{
		return contains(element,root);
	}
	
	private boolean contains(T element,Node<T> root)//递归查询元素是否在集合中
	{
		if(root==null)return false;
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)return contains(element,root.left);
		else if(cmp>0)return contains(element,root.right);
		else return true;
	}
	
	public T findMin()//查找最小元素
	{
		return findMin(root).element;
	}
	
	private Node<T> findMin(Node<T> root)//递归地查找最小元素
	{
		if(root==null)return null;
		else if(root.left==null)return root;
		
		return findMin(root.left);
	}
	
	public T findMax()//查找最大元素
	{
		return findMax(root).element;
	}
	
	private Node<T> findMax(Node<T> root)//递归地查找最大元素
	{
		if(root==null)return null;
		else if(root.right==null)return root;
		
		return findMax(root.right);
	}
	
	public void insert(T element)//插入一个元素到集合中
	{
		root=insert(element,root,null);
	}
	
	private Node<T> insert(T element,Node<T> root,Node<T> parent)
	{
		//递归地插入一个元素至集合中
		if(root==null)return new Node(element,null,null,parent);
		
		int cmp=element.compareTo(root.element);
		
		if(cmp<0)root.left=insert(element,root.left,root);
		else if(cmp>0)root.right=insert(element,root.right,root);
		
		return root;
	}
	
	public void remove(T element)//删除集合中的元素
	{
		root=remove(element,root);
	}
	
	private Node<T> remove(T element,Node<T> root)
	{
		//递归地删除一个元素
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
		//迭代器
		private Node<T> current=findMin(root);//从最小元素开始
		private Node<T> previous=null;//上一节点
		private boolean okToRemove=false;//remove()之前是否next()
		private boolean end=false;//是否结束
		
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
				//可以删除右子树时，先遍历右子树
				current=findMin(current.right);
			}
			else
			{ 
				Node<T> child=current;
				current=current.parent;
				
				/*停止在有左孩子的节点处
				 * 非递归中序遍历
				 * 遍历原理：
				 * 1、要遍历以某一根root的树，开始先找到其最小值的节点
				 * 
				 * 2、由于开始位于最小节点处，因此在处理右子树之前
				 * 左子树以及根已经被遍历
				 * 
				 * 3、因此在返回时只需要在从左孩子向其父母的移动后后停止，从右孩子返回则不需要停止
				 * 
				 * 对于任意一个高度为1的子树根节点，可以容易得出该方法可以行得通
				 * 递归地
				 * 对于一个以该树根y为左孩子的根x，可以成功遍历所有节点
				 * 而对于x，其右子树可以用过程1、2、3完成遍历，由于x已经遍历过
				 * 因此x不需要再遍历，从而完成一个子树的遍历，然后进行下一轮过程
				 * 直至发现某一节点的父母为null（即root）为止
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
