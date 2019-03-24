
public class QuadraticProbingHashTable<T> {
	//平方探测解决冲突的哈希表
	
	private Item<T>[] list;//保存各个位置的链表
	private int size;//记录大小以方便操作
	private static final int DEFAULT_SIZE=11;//默认大小
	
	//实现惰性删除
	private static class Item<T>
	{
		public T element;
		public boolean isActive;//元素是否被删除
		
		public Item(T element)
		{
			this(element,true);
		}
		
		public Item(T element,boolean isActive)
		{
			this.element=element;
			this.isActive=isActive;
		}
	}
	
	public QuadraticProbingHashTable(int size)
	{
		list=new Item[nextPrime(size)];
		clear();
	}
	
	public QuadraticProbingHashTable()
	{
		this(DEFAULT_SIZE);
	}
	
	public void clear()//清除哈希表
	{
		size=0;
		for(int i=0;i<list.length;i++)list[i]=null;
	}
	
	public boolean contains(T element)//元素是否在哈希表中
	{
		int pos=findPosition(element);
		return isActive(pos);
	}
	
	public void insert(T element)//插入一个元素
	{
		int pos=findPosition(element);
		if(isActive(pos))return;//元素已经存在
		
		list[pos]=new Item<>(element,true);
		size++;
		
		if(size>list.length/2)rehash();//当填装因子大于0.5，则扩大容量
	}
	
	public void remove(T element)//删除一个元素
	{
		int pos=findPosition(element);
		if(isActive(pos))
		{
			list[pos].isActive=false;
		}
	}
	
	private int findPosition(T element)//寻找element的插入位置
	{
		int cursion=hashMethod(element);
		//改进平方计算f(i)=i^2->f(i)=f(i-1)+2i-1
		int add=1;
		
		while(list[cursion]!=null&&!list[cursion].element.equals(element))
		{
			cursion+=add;
			add+=2;
			while(cursion>=list.length)cursion-=list.length;
		}
		
		return cursion;
	}
	
	private boolean isActive(int pos)//元素是否被删除
	{
		return list[pos]!=null&&list[pos].isActive;
	}
	
	private void rehash()//再散列以扩充哈希表
	{
		Item<T>[] oldList=list;
		list=new Item[nextPrime(oldList.length*2)];
		size=0;
		
		for(int i=0;i<oldList.length;i++)
		{
			if(oldList[i]!=null&&oldList[i].isActive)
			{
				insert(oldList[i].element);
			}
		}
		
	}
	
	private int hashMethod(T element)//hash方法
	{
		int hashCode=element.hashCode();
		
		hashCode%=list.length;//保证hash值的范围
		if(hashCode<0)hashCode+=list.length;
		
		return hashCode;
	}
	
	private static int nextPrime(int n)//搜索比n大的第一个素数(仅搜索奇数)
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }
	
	private static boolean isPrime(int n)//判断一个数是否是素数(一般方法稍改进版(仅搜索奇数))
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
	
	private void printHashTable()//测试用方法
	{
		for(int i=0;i<list.length;i++)
		{
			if(list[i]!=null&&list[i].isActive)System.out.println(list[i].element);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuadraticProbingHashTable<Integer> test=new QuadraticProbingHashTable<>();
		
		for(int i=0;i<30;i+=2)test.insert(i);
		
		test.insert(5);
		test.insert(7);
		test.insert(3);
		
		System.out.println("删除前：");
		test.printHashTable();
		
		test.remove(2);
		test.remove(7);
		test.remove(8);
		
		System.out.println("删除后：");
		test.printHashTable();
	}

}
