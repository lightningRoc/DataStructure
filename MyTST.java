
public class MyTST<T> {
	//三向单词查找树，由于trie会消耗掉许多无用的空间，通过三向单词查找树来节省空间，不过会牺牲一些时间效率
	
	private Node root;//树根结点
	
	private class Node
	{
		char c;//显示存储字符
		Node left,mid,right;//左中右分别表示大于，等于，小于当前字符串对应的结点
		T val;
	}
	
	//查询key对应的值,不存在时返回null
	public T get(String key)
	{
		Node result=get(root, key, 0);
		if(result==null)return null;
		else return (T)result.val;
	}
	
	//通过递归的方式来查找键key对应的值是否存在
	private Node get(Node root,String key,int index)
	{
		if(root==null)return null;
		char nextC=key.charAt(index);
		
		if(nextC<root.c)return get(root.left, key, index);
		else if(nextC>root.c)return get(root.right, key, index);
		else if(index<key.length()-1)return get(root.mid, key, index+1);//若等于，index索引才能向右滑动，index与key长度-1相等时即成功匹配
		else return root;
	}
	
	//添加键为key，值为val的结点
	public void put(String key,T val)
	{
		root=put(root, key, val, 0);
	}
	
	//递归添加结点
	private Node put(Node root,String key,T val,int index)
	{
		char nextC=key.charAt(index);
		
		if(root==null)
		{
			root=new Node();
			root.c=nextC;
		}
		
		if(nextC<root.c)
		{
			root.left=put(root.left, key, val, index);
		}
		else if(nextC>root.c)
		{
			root.right=put(root.right, key, val, index);
		}
		else if(index<key.length()-1)//index与key长度-1相等时即成功匹配
		{
			root.mid=put(root.mid, key, val, index+1);//若等于，index索引才能向右滑动
		} 
		else root.val=val;
		
		return root;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTST<Integer> tst=new MyTST<>();
		tst.put("first", 1);
		tst.put("second", 2);
		tst.put("third", 3);
		System.out.println(tst.get("first"));
		System.out.println(tst.get("second"));
		System.out.println(tst.get("third"));
		System.out.println(tst.get("seco"));
		
	}

}
