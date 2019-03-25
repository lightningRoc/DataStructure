import java.util.Random;

public class CuckooHashTable<T> {
	//布谷鸟散列(单数组的简单实现)
	
	private T[] list;//使用一个列表代替两个来实现布谷鸟散列
	private static final int DEFAULT_SIZE=101;//默认大小
	private int size;//当前元素的数量
	private static final double MAX_LOAD=0.4;//最大填装因子
	private final HashFamily<? super T> hashFunctions;
	private final int numHashFunctions;
	
	public CuckooHashTable(HashFamily<? super T> hashFunctions)
	{
		this(hashFunctions, DEFAULT_SIZE);
	}
	
	public CuckooHashTable(HashFamily<? super T> hashFunctions,int size)
	{
		list=(T[]) new Object[nextPrime(size)];
		clear();
		this.hashFunctions=hashFunctions;
		numHashFunctions=hashFunctions.getNumberOfFunctions();
	}
	
	public void clear()//清除哈希表
	{
		size=0;
		for(int i=0;i<list.length;i++)list[i]=null;
	}
	
	public boolean contains(T element)//元素是否在哈希表内
	{
		return findPos(element)!=-1;
	}
	
	public boolean insert(T element)//插入一个元素
	{
		if(contains(element))
		{
			return false;
		}
		
		if(size>=list.length*MAX_LOAD)//填装因子过大时
		{
			expand();
		}
		
		return insertHelper1(element);
			
	}
	
	private int rehashes=0;//insertHelper辅助变量
	private Random rand=new Random();
	
	private boolean insertHelper1(T element)
	{
		final int COUNT_LIMIT=100;//最大搜寻次数
		
		while(true)
		{
			int lastPos=-1;//记录上一个位置以尽量避免循环
			int pos;
			
			for(int count=0;count<COUNT_LIMIT;count++)
			{
				for(int i=0;i<numHashFunctions;i++)
				{//通过不同的哈希函数遍历一个元素的各个哈希值
					pos=hashMethod(element,i);
					
					if(list[pos]==null)
					{//寻找到空位
						list[pos]=element;
						size++;
						return true;
					}
				}
				
				int i=0;//当没有空位时
				do
				{
					pos=hashMethod(element,rand.nextInt(numHashFunctions));
				}while(pos==lastPos&&i++<5);//在限定次数内选取非循环的随机哈希函数作为将要占用的点
				
				T tmp=list[lastPos=pos];//替换选中的点
				list[pos]=element;
				element=tmp;
			}
			
			if(++rehashes>1)
			{//转换哈希函数的次数达到限制(最多有2个哈希函数可用)->扩展哈希表
				expand();
				rehashes=0;
			}
			else
			{
				rehash();//通过新的哈希函数进行散列
			}
		}
	}
	
	public boolean remove(T element)//删除元素
	{
		int pos=findPos(element);
		
		if(pos!=1)
		{
			list[pos]=null;
			size--;
		}
		
		return pos!=-1;
	}
	
	private int hashMethod(T element,int which)//哈希方法
	{
		int hashV=hashFunctions.hash(element, which);
		
		hashV%=list.length;
		if(hashV<0)hashV+=list.length;
		
		return hashV;
	}
	
	private int findPos(T element)//寻找元素的位置(不存在则返回-1)
	{
		for(int i=0;i<numHashFunctions;i++)
		{//查找元素的所有可能的哈希值
			int pos=hashMethod(element,i);
			if(list[pos]!=null&&list[pos].equals(element))return pos;
		}
		
		return -1;
	}
	
	private void expand()//通过扩展哈希表进行再散列
	{
		rehash((int)(list.length/MAX_LOAD));
	}
	
	private void rehash()//根据新的散列函数进行再散列
	{
		hashFunctions.generateNewFunctions();
		rehash(list.length);
	}
	
	private void rehash(int newLength)//根据给定新的长度进行再散列
	{
		T[] oldList=list;
		list=(T[]) new Object[nextPrime(newLength)];
		size=0;
		
		for(T element:oldList)
		{
			if(element!=null)
			{
				insert(element);
			}
		}
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
	
	public void printTable()
	{
		for(int i=0;i<list.length;i++)
		{
			if(list[i]!=null)System.out.println(list[i]);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringHashFamily hfTest=new StringHashFamily(2);
		CuckooHashTable test=new CuckooHashTable(hfTest);
		
		for(int i=0;i<10;i++)
		{
			test.insert(""+i);
		}
		test.insert("test1");
		test.insert("test2");
		test.insert("test3");
		test.printTable();
		
		test.remove("test2");
		test.remove("2");
		System.out.println("删除后");
		test.printTable();
	}

}

interface HashFamily<T>//散列函数
{
	int hash(T element,int which);//用位置which指定的数字对元素进行散列
	int getNumberOfFunctions();
	void generateNewFunctions();
}

class StringHashFamily implements HashFamily<String>
{
	//简单的处理字符串的散列函数
	private final int[] MULTIPLIERS;
	private final Random rand=new Random();
	
	public StringHashFamily(int i)
	{
		MULTIPLIERS=new int[i];
		generateNewFunctions();
	}
	
	public int getNumberOfFunctions()//获得散列函数的数量
	{
		return MULTIPLIERS.length;
	}
	
	public void generateNewFunctions()//生成一组新的哈希函数
	{
		for(int i=0;i<MULTIPLIERS.length;i++)
		{
			MULTIPLIERS[i]=rand.nextInt();
		}
	}
	
	public int hash(String element,int which)//求哈希值
	{
		final int multiplier=MULTIPLIERS[which];
		int hashV=0;
		
		for(int i=0;i<element.length();i++)
		{
			hashV=multiplier*hashV+element.charAt(i);
		}
		
		return hashV;
	}
}
