
public class MyHashMap<KeyT,ValueT> {
	
	//使用平方探测解决冲突的方法的哈希表来实现HashMap
	private QuadraticProbingHashTable<Pair<KeyT,ValueT>> list;
	
	public MyHashMap()
	{
		list=new QuadraticProbingHashTable<Pair<KeyT,ValueT>>();
	}
	
	public boolean isEmpty()//表是否为空
	{
		return list.isEmpty();
	}
	
	public void clear()//清空表
	{
		list.clear();
	}
	
	public void put(KeyT key,ValueT value)//插入一个元素值
	{
		Pair<KeyT,ValueT> element=new Pair<>(key,value);
		list.insert(element);
	}
	
	public ValueT get(KeyT key)//获取键对应的值
	{
		ValueT val=(ValueT) new Object();
		
		Pair<KeyT,ValueT> element=new Pair<KeyT,ValueT>(key,val);
		Pair<KeyT,ValueT> res=list.find(element);
		if(res==null)return null;
		else return res.value;
	}
	
	private static class Pair<KeyT,ValueT>//存储键值对
	{
		private KeyT key;
		private ValueT value;
		
		Pair(KeyT key,ValueT value)
		{
			this.key=key;
			this.value=value;
		}
		
		public int hashCode()//为使用哈希表而重写hashCode()方法与equals()方法
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
