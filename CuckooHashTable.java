import java.util.Random;

public class CuckooHashTable<T> {
	//������ɢ��(������ļ�ʵ��)
	
	private T[] list;//ʹ��һ���б����������ʵ�ֲ�����ɢ��
	private static final int DEFAULT_SIZE=101;//Ĭ�ϴ�С
	private int size;//��ǰԪ�ص�����
	private static final double MAX_LOAD=0.4;//�����װ����
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
	
	public void clear()//�����ϣ��
	{
		size=0;
		for(int i=0;i<list.length;i++)list[i]=null;
	}
	
	public boolean contains(T element)//Ԫ���Ƿ��ڹ�ϣ����
	{
		return findPos(element)!=-1;
	}
	
	public boolean insert(T element)//����һ��Ԫ��
	{
		if(contains(element))
		{
			return false;
		}
		
		if(size>=list.length*MAX_LOAD)//��װ���ӹ���ʱ
		{
			expand();
		}
		
		return insertHelper1(element);
			
	}
	
	private int rehashes=0;//insertHelper��������
	private Random rand=new Random();
	
	private boolean insertHelper1(T element)
	{
		final int COUNT_LIMIT=100;//�����Ѱ����
		
		while(true)
		{
			int lastPos=-1;//��¼��һ��λ���Ծ�������ѭ��
			int pos;
			
			for(int count=0;count<COUNT_LIMIT;count++)
			{
				for(int i=0;i<numHashFunctions;i++)
				{//ͨ����ͬ�Ĺ�ϣ��������һ��Ԫ�صĸ�����ϣֵ
					pos=hashMethod(element,i);
					
					if(list[pos]==null)
					{//Ѱ�ҵ���λ
						list[pos]=element;
						size++;
						return true;
					}
				}
				
				int i=0;//��û�п�λʱ
				do
				{
					pos=hashMethod(element,rand.nextInt(numHashFunctions));
				}while(pos==lastPos&&i++<5);//���޶�������ѡȡ��ѭ���������ϣ������Ϊ��Ҫռ�õĵ�
				
				T tmp=list[lastPos=pos];//�滻ѡ�еĵ�
				list[pos]=element;
				element=tmp;
			}
			
			if(++rehashes>1)
			{//ת����ϣ�����Ĵ����ﵽ����(�����2����ϣ��������)->��չ��ϣ��
				expand();
				rehashes=0;
			}
			else
			{
				rehash();//ͨ���µĹ�ϣ��������ɢ��
			}
		}
	}
	
	public boolean remove(T element)//ɾ��Ԫ��
	{
		int pos=findPos(element);
		
		if(pos!=1)
		{
			list[pos]=null;
			size--;
		}
		
		return pos!=-1;
	}
	
	private int hashMethod(T element,int which)//��ϣ����
	{
		int hashV=hashFunctions.hash(element, which);
		
		hashV%=list.length;
		if(hashV<0)hashV+=list.length;
		
		return hashV;
	}
	
	private int findPos(T element)//Ѱ��Ԫ�ص�λ��(�������򷵻�-1)
	{
		for(int i=0;i<numHashFunctions;i++)
		{//����Ԫ�ص����п��ܵĹ�ϣֵ
			int pos=hashMethod(element,i);
			if(list[pos]!=null&&list[pos].equals(element))return pos;
		}
		
		return -1;
	}
	
	private void expand()//ͨ����չ��ϣ�������ɢ��
	{
		rehash((int)(list.length/MAX_LOAD));
	}
	
	private void rehash()//�����µ�ɢ�к���������ɢ��
	{
		hashFunctions.generateNewFunctions();
		rehash(list.length);
	}
	
	private void rehash(int newLength)//���ݸ����µĳ��Ƚ�����ɢ��
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
	
	private static int nextPrime(int n)//������n��ĵ�һ������(����������)
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }
	
	private static boolean isPrime(int n)//�ж�һ�����Ƿ�������(һ�㷽���ԸĽ���(����������))
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
		System.out.println("ɾ����");
		test.printTable();
	}

}

interface HashFamily<T>//ɢ�к���
{
	int hash(T element,int which);//��λ��whichָ�������ֶ�Ԫ�ؽ���ɢ��
	int getNumberOfFunctions();
	void generateNewFunctions();
}

class StringHashFamily implements HashFamily<String>
{
	//�򵥵Ĵ����ַ�����ɢ�к���
	private final int[] MULTIPLIERS;
	private final Random rand=new Random();
	
	public StringHashFamily(int i)
	{
		MULTIPLIERS=new int[i];
		generateNewFunctions();
	}
	
	public int getNumberOfFunctions()//���ɢ�к���������
	{
		return MULTIPLIERS.length;
	}
	
	public void generateNewFunctions()//����һ���µĹ�ϣ����
	{
		for(int i=0;i<MULTIPLIERS.length;i++)
		{
			MULTIPLIERS[i]=rand.nextInt();
		}
	}
	
	public int hash(String element,int which)//���ϣֵ
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
