
public class MyHashMap<KeyT,ValueT> {
	
	//ʹ��ƽ��̽������ͻ�ķ����Ĺ�ϣ����ʵ��HashMap
	private QuadraticProbingHashTable<Pair<KeyT,ValueT>> list;
	
	public MyHashMap()
	{
		list=new QuadraticProbingHashTable<Pair<KeyT,ValueT>>();
	}
	
	public boolean isEmpty()//���Ƿ�Ϊ��
	{
		return list.isEmpty();
	}
	
	public void clear()//��ձ�
	{
		list.clear();
	}
	
	public void put(KeyT key,ValueT value)//����һ��Ԫ��ֵ
	{
		Pair<KeyT,ValueT> element=new Pair<>(key,value);
		list.insert(element);
	}
	
	public ValueT get(KeyT key)//��ȡ����Ӧ��ֵ
	{
		ValueT val=(ValueT) new Object();
		
		Pair<KeyT,ValueT> element=new Pair<KeyT,ValueT>(key,val);
		Pair<KeyT,ValueT> res=list.find(element);
		if(res==null)return null;
		else return res.value;
	}
	
	private static class Pair<KeyT,ValueT>//�洢��ֵ��
	{
		private KeyT key;
		private ValueT value;
		
		Pair(KeyT key,ValueT value)
		{
			this.key=key;
			this.value=value;
		}
		
		public int hashCode()//Ϊʹ�ù�ϣ�����дhashCode()������equals()����
		{
			return key.hashCode();
		}
		
		public boolean equals(Object others)
		{
			if(!(others instanceof Pair))return false;
			
			return key.equals(((Pair<KeyT,ValueT>)others).key);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyHashMap<Integer,String> test=new MyHashMap<>();
		
		for(int i=0;i<10;i++)
		{
			test.put(i, i+"string");
		}
		
		System.out.println(test.get(3));
		System.out.println(test.get(8));
	}

}
