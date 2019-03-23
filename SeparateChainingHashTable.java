
public class SeparateChainingHashTable<T> {
	//分离链接法实现哈希表
	
	private MyLinkedList<T>[] lists;//保存各个位置的链表
	private int size;//记录大小方便操作
	private static final int DEFAULT_SIZE=101;//默认大小
	
	public SeparateChainingHashTable()
	{
		this(DEFAULT_SIZE);
	}
	
	public SeparateChainingHashTable(int size)
	{
		lists=new MyLinkedList[nextPrime(size)];//保证哈希表大小为素数
		for(int i=0;i<lists.length;i++)lists[i]=new MyLinkedList<>();
	}
	
	public void clear()//清除哈希表
	{
		for(int i=0;i<lists.length;i++)lists[i].clear();
		size=0;
	}
	
	public void insert(T element)//插入一个元素
	{
		MyLinkedList<T> location=lists[hashMethod(element)];
		
		if(!location.contains(element))
		{
			location.add(element);
			size++;
			
			if(size>lists.length)rehash();//当填装因子大于1，则扩大容量
		}
	}
	
	public void remove(T element)//删除一个元素
	{
		MyLinkedList<T> location=lists[hashMethod(element)];
		if(location.contains(element))
		{
			location.remove(element);
			size--;
		}
	}
	
	public boolean contains(T element)//检查元素是否在hash表中
	{
		MyLinkedList<T> location=lists[hashMethod(element)];
		return location.contains(element);
	}
	
	private void rehash()//使用再散列的方式扩容
	{
		MyLinkedList<T>[] oldLists=lists;
		
		lists=new MyLinkedList[nextPrime(2*lists.length)];//扩容至少2倍
		for(int i=0;i<lists.length;i++)lists[i]=new MyLinkedList<>();
		
		size=0;
		for(int i=0;i<oldLists.length;i++)for(T element:oldLists[i])insert(element);
	}
	
	private int hashMethod(T element)//hash方法
	{
		int hashCode=element.hashCode();
		
		hashCode%=lists.length;//保证hash值的范围
		if(hashCode<0)hashCode+=lists.length;
		
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
		for(int i=0;i<lists.length;i++)
		{
			for(T element:lists[i])
			{
				System.out.println(element);
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SeparateChainingHashTable<Integer> test=new SeparateChainingHashTable<>();
		
		for(int i=0;i<10;i+=2)test.insert(i);
		
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
