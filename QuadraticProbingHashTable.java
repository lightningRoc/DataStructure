
public class QuadraticProbingHashTable<T> {
	//ƽ��̽������ͻ�Ĺ�ϣ��
	
	private Item<T>[] list;//�������λ�õ�����
	private int size;//��¼��С�Է������
	private static final int DEFAULT_SIZE=11;//Ĭ�ϴ�С
	
	//ʵ�ֶ���ɾ��
	private static class Item<T>
	{
		public T element;
		public boolean isActive;//Ԫ���Ƿ�ɾ��
		
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
	
	public void clear()//�����ϣ��
	{
		size=0;
		for(int i=0;i<list.length;i++)list[i]=null;
	}
	
	public boolean contains(T element)//Ԫ���Ƿ��ڹ�ϣ����
	{
		int pos=findPosition(element);
		return isActive(pos);
	}
	
	public void insert(T element)//����һ��Ԫ��
	{
		int pos=findPosition(element);
		if(isActive(pos))return;//Ԫ���Ѿ�����
		
		list[pos]=new Item<>(element,true);
		size++;
		
		if(size>list.length/2)rehash();//����װ���Ӵ���0.5������������
	}
	
	public void remove(T element)//ɾ��һ��Ԫ��
	{
		int pos=findPosition(element);
		if(isActive(pos))
		{
			list[pos].isActive=false;
		}
	}
	
	private int findPosition(T element)//Ѱ��element�Ĳ���λ��
	{
		int cursion=hashMethod(element);
		//�Ľ�ƽ������f(i)=i^2->f(i)=f(i-1)+2i-1
		int add=1;
		
		while(list[cursion]!=null&&!list[cursion].element.equals(element))
		{
			cursion+=add;
			add+=2;
			while(cursion>=list.length)cursion-=list.length;
		}
		
		return cursion;
	}
	
	private boolean isActive(int pos)//Ԫ���Ƿ�ɾ��
	{
		return list[pos]!=null&&list[pos].isActive;
	}
	
	private void rehash()//��ɢ���������ϣ��
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
	
	private int hashMethod(T element)//hash����
	{
		int hashCode=element.hashCode();
		
		hashCode%=list.length;//��֤hashֵ�ķ�Χ
		if(hashCode<0)hashCode+=list.length;
		
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
		
		System.out.println("ɾ��ǰ��");
		test.printHashTable();
		
		test.remove(2);
		test.remove(7);
		test.remove(8);
		
		System.out.println("ɾ����");
		test.printHashTable();
	}

}
