import java.util.Random;

public class BinomialQueue<T extends Comparable<? super T>> {
	//二项队列
	
	private Node<T>[] trees;//保存所有树的根节点，即表示森林
	private int size;//元素数量
	private static final int DEFAULT_SIZE=1;//默认元素数量
	
	//二项队列表示方法：通过一个一个左孩子来一层一层向下递进，通过一层的第一个节点建立链表来表示与一个节点直连的一个层
	private static class Node<T>
	{
		T element;
		Node<T> left;//下一个孩子
		Node<T> nextSibling;//指向其下一个兄弟节点
		
		Node(T element)
		{
			this(element,null,null);
		}
		
		Node(T element,Node<T> left,Node<T> nextSibling)
		{
			this.element=element;
			this.left=left;
			this.nextSibling=nextSibling;
		}
	}
	
	public BinomialQueue()
	{
		trees=new Node[DEFAULT_SIZE];
		clear();
	}
	
	public BinomialQueue(T element)
	{//便于利用merge方法来进行插入操作
		size=1;
		trees=new Node[1];
		trees[0]=new Node<>(element);
	}
	
	public boolean isEmpty()//二项队列是否为空
	{
		return size==0;
	}
	
	public void clear()//清空二项队列
	{
		size=0;
		for(int i=0;i<trees.length;i++)
		{
			trees[i]=null;
		}
	}
	
	//合并两个二项队列并清空another
	public void merge(BinomialQueue<T> another)
	{
		if(this==another)return;
		
		size+=another.size;
		
		if(size>capacity())//容量是否充足
		{
			int maxL=Math.max(trees.length, another.trees.length);
			expandTrees(maxL+1);
		}
		
		Node<T> carry=null;
		for(int i=0,j=1;j<=size;i++,j*=2)
		{
			//t1为本身当前的树，t2为another的当前树
			Node<T> t1=trees[i];
			Node<T> t2=i<another.trees.length?another.trees[i]:null;
			
			//使用数字代表各种选择
			int whichCase=t1==null?0:1;
			whichCase+=t2==null?0:2;
			whichCase+=carry==null?0:4;
			
			switch(whichCase)
			{
			case 0:break;//两棵树为空且没有新树生成
			case 1:break;//another的树为空且没有新树生成
			case 2://自己的当前树为空且没有新树生成
				trees[i]=t2;
				another.trees[i]=null;
				break;
			case 3://两棵树不为空且没有新树生成
				carry=combineTree(t1,t2);
				trees[i]=another.trees[i]=null;
				break;
			case 4://仅有新树生成
				trees[i]=carry;
				carry=null;
				break;
			case 5://仅有自己的树与进位的树
				carry=combineTree(t1,carry);
				trees[i]=null;
				break;
			case 6://仅有another的树与进位的树
				carry=combineTree(t2,carry);
				another.trees[i]=null;
				break;
			case 7://都存在
				trees[i]=carry;
				carry=combineTree(t1,t2);
				another.trees[i]=null;
				break;
			}
		}
		
		another.clear();
	}
	
	//合并两颗树,t1连接至t2
	private Node<T> combineTree(Node<T> t1,Node<T> t2)
	{
		if(t1.element.compareTo(t2.element)>0)
		{
			return combineTree(t2,t1);
		}
		//倒序生成链表的方式来插入
		t2.nextSibling=t1.left;
		t1.left=t2;
		return t1;
	}
	
	public void insert(T element)//插入一个元素
	{
		merge(new BinomialQueue<>(element));
	}
	
	public T deleteMin()//删除并返回最小值
	{
		if(isEmpty())return null;
		
		int minIndex=findMinIndex();
		T minItem=trees[minIndex].element;
		
		//获取要删除的树的除了树根之外的节点
		Node<T> deletedTree=trees[minIndex].left;
		
		//通过deletedTree来建立相关的二项队列
		BinomialQueue<T> deletedQueue=new BinomialQueue<>();
		deletedQueue.expandTrees(minIndex+1);//
		
		deletedQueue.size=(1<<minIndex)-1;//计算大小(一颗树分解必为满容量的一个二项队列)
		for(int j=minIndex-1;j>=0;j--)
		{
			//一个直连的节点即为一颗树
			deletedQueue.trees[j]=deletedTree;
			//下一课树
			deletedTree=deletedTree.nextSibling;
			deletedQueue.trees[j].nextSibling=null;
		}
		
		trees[minIndex]=null;
		size-=deletedQueue.size+1;//除去生成的新的二项队列以及根
		
		merge(deletedQueue);
		
		return minItem;
	}
	
	public T findMin()//返回最小元素
	{
		if(isEmpty())return null;
		
		return trees[findMinIndex()].element;
	}
	
	private int findMinIndex()//返回包含最小值的树的根的索引
	{
		int i;
		int minIndex;
		
		for(i=0;trees[i]==null;i++);//快速过滤前面的空树
		
		for(minIndex=i;i<trees.length;i++)
		{
			if(trees[i]!=null&&
					trees[i].element.compareTo(trees[minIndex].element)<0)
			{
				minIndex=i;
			}
		}
		
		return minIndex;
	}
	
	private void expandTrees(int newSize)//扩展二项队列
	{
		Node<T>[] old=trees;
		int oldSize=trees.length;
		
		trees=new Node[newSize];
		for(int i=0;i<Math.min(oldSize, newSize);i++)
		{
			trees[i]=old[i];
		}
		for(int i=oldSize;i<newSize;i++)//去除多余的值
		{
			trees[i]=null;
		}
	}
	
	private int capacity()//返回二项队列当前森林的容量大小
	{
		return (1<<trees.length)-1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinomialQueue<Integer> test1=new BinomialQueue<>();
		BinomialQueue<Integer> test2=new BinomialQueue<>();
		Random rand=new Random();
		
		for(int i=0;i<20;i++)
		{
			test1.insert(rand.nextInt(200));
			test2.insert(rand.nextInt(200));
		}
		
		test1.merge(test2);
		while(!test1.isEmpty())
		{
			System.out.println(test1.deleteMin());
		}
	}

}
