import java.util.Random;

public class BinomialQueue<T extends Comparable<? super T>> {
	//�������
	
	private Node<T>[] trees;//�����������ĸ��ڵ㣬����ʾɭ��
	private int size;//Ԫ������
	private static final int DEFAULT_SIZE=1;//Ĭ��Ԫ������
	
	//������б�ʾ������ͨ��һ��һ��������һ��һ�����µݽ���ͨ��һ��ĵ�һ���ڵ㽨����������ʾ��һ���ڵ�ֱ����һ����
	private static class Node<T>
	{
		T element;
		Node<T> left;//��һ������
		Node<T> nextSibling;//ָ������һ���ֵܽڵ�
		
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
	{//��������merge���������в������
		size=1;
		trees=new Node[1];
		trees[0]=new Node<>(element);
	}
	
	public boolean isEmpty()//��������Ƿ�Ϊ��
	{
		return size==0;
	}
	
	public void clear()//��ն������
	{
		size=0;
		for(int i=0;i<trees.length;i++)
		{
			trees[i]=null;
		}
	}
	
	//�ϲ�����������в����another
	public void merge(BinomialQueue<T> another)
	{
		if(this==another)return;
		
		size+=another.size;
		
		if(size>capacity())//�����Ƿ����
		{
			int maxL=Math.max(trees.length, another.trees.length);
			expandTrees(maxL+1);
		}
		
		Node<T> carry=null;
		for(int i=0,j=1;j<=size;i++,j*=2)
		{
			//t1Ϊ����ǰ������t2Ϊanother�ĵ�ǰ��
			Node<T> t1=trees[i];
			Node<T> t2=i<another.trees.length?another.trees[i]:null;
			
			//ʹ�����ִ������ѡ��
			int whichCase=t1==null?0:1;
			whichCase+=t2==null?0:2;
			whichCase+=carry==null?0:4;
			
			switch(whichCase)
			{
			case 0:break;//������Ϊ����û����������
			case 1:break;//another����Ϊ����û����������
			case 2://�Լ��ĵ�ǰ��Ϊ����û����������
				trees[i]=t2;
				another.trees[i]=null;
				break;
			case 3://��������Ϊ����û����������
				carry=combineTree(t1,t2);
				trees[i]=another.trees[i]=null;
				break;
			case 4://������������
				trees[i]=carry;
				carry=null;
				break;
			case 5://�����Լ��������λ����
				carry=combineTree(t1,carry);
				trees[i]=null;
				break;
			case 6://����another�������λ����
				carry=combineTree(t2,carry);
				another.trees[i]=null;
				break;
			case 7://������
				trees[i]=carry;
				carry=combineTree(t1,t2);
				another.trees[i]=null;
				break;
			}
		}
		
		another.clear();
	}
	
	//�ϲ�������,t1������t2
	private Node<T> combineTree(Node<T> t1,Node<T> t2)
	{
		if(t1.element.compareTo(t2.element)>0)
		{
			return combineTree(t2,t1);
		}
		//������������ķ�ʽ������
		t2.nextSibling=t1.left;
		t1.left=t2;
		return t1;
	}
	
	public void insert(T element)//����һ��Ԫ��
	{
		merge(new BinomialQueue<>(element));
	}
	
	public T deleteMin()//ɾ����������Сֵ
	{
		if(isEmpty())return null;
		
		int minIndex=findMinIndex();
		T minItem=trees[minIndex].element;
		
		//��ȡҪɾ�������ĳ�������֮��Ľڵ�
		Node<T> deletedTree=trees[minIndex].left;
		
		//ͨ��deletedTree��������صĶ������
		BinomialQueue<T> deletedQueue=new BinomialQueue<>();
		deletedQueue.expandTrees(minIndex+1);//
		
		deletedQueue.size=(1<<minIndex)-1;//�����С(һ�����ֽ��Ϊ��������һ���������)
		for(int j=minIndex-1;j>=0;j--)
		{
			//һ��ֱ���Ľڵ㼴Ϊһ����
			deletedQueue.trees[j]=deletedTree;
			//��һ����
			deletedTree=deletedTree.nextSibling;
			deletedQueue.trees[j].nextSibling=null;
		}
		
		trees[minIndex]=null;
		size-=deletedQueue.size+1;//��ȥ���ɵ��µĶ�������Լ���
		
		merge(deletedQueue);
		
		return minItem;
	}
	
	public T findMin()//������СԪ��
	{
		if(isEmpty())return null;
		
		return trees[findMinIndex()].element;
	}
	
	private int findMinIndex()//���ذ�����Сֵ�����ĸ�������
	{
		int i;
		int minIndex;
		
		for(i=0;trees[i]==null;i++);//���ٹ���ǰ��Ŀ���
		
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
	
	private void expandTrees(int newSize)//��չ�������
	{
		Node<T>[] old=trees;
		int oldSize=trees.length;
		
		trees=new Node[newSize];
		for(int i=0;i<Math.min(oldSize, newSize);i++)
		{
			trees[i]=old[i];
		}
		for(int i=oldSize;i<newSize;i++)//ȥ�������ֵ
		{
			trees[i]=null;
		}
	}
	
	private int capacity()//���ض�����е�ǰɭ�ֵ�������С
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
