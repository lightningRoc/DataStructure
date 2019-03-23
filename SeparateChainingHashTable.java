
public class SeparateChainingHashTable<T> {
	//�������ӷ�ʵ�ֹ�ϣ��
	
	private MyLinkedList<T>[] lists;//�������λ�õ�����
	private int size;//��¼��С�������
	private static final int DEFAULT_SIZE=101;//Ĭ�ϴ�С
	
	public SeparateChainingHashTable()
	{
		this(DEFAULT_SIZE);
	}
	
	public SeparateChainingHashTable(int size)
	{
		lists=new MyLinkedList[nextPrime(size)];//��֤��ϣ���СΪ����
		for(int i=0;i<lists.length;i++)lists[i]=new MyLinkedList<>();
	}
	
	public void clear()//�����ϣ��
	{
		for(int i=0;i<lists.length;i++)lists[i].clear();
		size=0;
	}
	
	public void insert(T element)//����һ��Ԫ��
	{
		MyLinkedList<T> location=lists[hashMethod(element)];
		
		if(!location.contains(element))
		{
			location.add(element);
			size++;
			
			if(size>lists.length)rehash();//����װ���Ӵ���1������������
		}
	}
	
	public void remove(T element)//ɾ��һ��Ԫ��
	{
		MyLinkedList<T> location=lists[hashMethod(element)];
		if(location.contains(element))
		{
			location.remove(element);
			size--;
		}
	}
	
	public boolean contains(T element)//���Ԫ���Ƿ���hash����
	{
		MyLinkedList<T> location=lists[hashMethod(element)];
		return location.contains(element);
	}
	
	private void rehash()//ʹ����ɢ�еķ�ʽ����
	{
		MyLinkedList<T>[] oldLists=lists;
		
		lists=new MyLinkedList[nextPrime(2*lists.length)];//��������2��
		for(int i=0;i<lists.length;i++)lists[i]=new MyLinkedList<>();
		
		size=0;
		for(int i=0;i<oldLists.length;i++)for(T element:oldLists[i])insert(element);
	}
	
	private int hashMethod(T element)//hash����
	{
		int hashCode=element.hashCode();
		
		hashCode%=lists.length;//��֤hashֵ�ķ�Χ
		if(hashCode<0)hashCode+=lists.length;
		
		return hashCode;
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
	
	private void printHashTable()//�����÷���
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
		
		System.out.println("ɾ��ǰ��");
		test.printHashTable();
		
		test.remove(2);
		test.remove(7);
		test.remove(8);
		
		System.out.println("ɾ����");
		test.printHashTable();
	}

}
