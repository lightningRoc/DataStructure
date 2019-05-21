import javax.security.auth.x500.X500Principal;

public class MyTrie <T>{
	//单词查找树,快速查找键为字符串时的对象
	
	private static int R=256;//单词基数大小
	private Node root=null;//树根节点(不保存值)
	
	private static class Node
	{
		//采用隐式的表示方式来表示单词查找树(每个节点的子节点最多R个,采用一个数组来存储)
		private Object val;
		private Node[] next=new Node[R];
	}
	
	//查询key对应的值,不存在时返回null
	public T get(String key)
	{
		Node result=get(root, key, 0);
		if(result==null)return null;
		else return (T)result.val;
	}
	
	//递归地查询key对应的值，其中d表示key字符串当前匹配的位置
	private Node get(Node root,String key,int index)
	{
		if(root==null)return null;//匹配到的位置为空(不存在key对应的值)
		if(index==key.length())return root;
		char nextC=key.charAt(index);
		return get(root.next[nextC], key, index+1);
	}
	
	//添加键为Key，值为val的结点
	public void put(String key,T val)
	{
		root=put(root, key, val, 0);
	}
	
	//递归地添加一个键为key的结点
	private Node put(Node root,String key,T val,int index) 
	{
		if(root==null)root=new Node();//无用的结点不需要赋值，点代表的字母由父节点的next[]数组的索引隐式给出
		if(index==key.length())
		{
			root.val=val;
			return root;
		}
		
		char nextC=key.charAt(index);
		root.next[nextC]=put(root.next[nextC],key,val,index+1);
		return root;
	}
	
	//删除键为key对应的结点
	public void delete(String key)
	{
		root=delete(root, key, 0);
	}
	
	//递归地删除一个结点
	private Node delete(Node root,String key,int index)
	{
		if(root==null)return null;
		if(index==key.length())
		{
			root.val=null;//清空当前结点值即可删除树中的结点存在性
		}
		else
		{
			char nextC=key.charAt(index);
			root.next[nextC]=delete(root, key, index+1);
		}
		
		//查看root结点是否还有效(本身有效或子结点有效)
		if(root.val!=null)return root;
		for(char c=0;c<R;c++)
		{
			if(root.next[c]!=null)return root;
		}
		
		//root结点无效
		return null;
	}
	
	//返回所有的键
	public Iterable<String> keys()
	{
		return keysWithPrefix("");
	}
	
	//给定前缀pre,寻找对应的key值集合
	public Iterable<String> keysWithPrefix(String pre)
	{
		MyQueue<String> queue=new MyQueue<>();
		collect(get(root, pre, 0), pre, queue);
		return queue;
	}
	
	//给定前缀pre,递归寻找对应的key值集合
	private void collect(Node root,String pre,MyQueue<String> q)
	{
		if(root==null)return;
		
		if(root.val!=null)q.enqueue(pre);
		
		for(char c=0;c<R;c++)
		{
			collect(root.next[c], pre+c, q);
		}
	}
	
	public String longestPrefixOf(String s)
	{
		int len=search(root, s, 0, 0);
		return s.substring(0, len);
	}
	
	//递归地寻找s在树中的最长前缀的长度,index表示当前索引，len表示已经找到的最长长度
	private int search(Node root,String s,int index,int len) 
	{
		if(root==null)return len;
		if(root.val!=null)len=index;//存在对应的前缀
		if(index==s.length())return len;
		char c=s.charAt(index);
		
		return search(root.next[c], s, index+1, len);
	}
	
	//返回单词查找树的大小
	public int size()
	{
		return size(root);
	}
	
	//递归地寻找单词查找树的大小
	private int size(Node root) 
	{
		if(root==null) return 0;
		
		int count=0;
		if(root.val!=null)count++;//有值时表示有一个保存的结点
		
		for(char c=0;c<R;c++)
		{
			count+=size(root.next[c]);
		}
		
		return count;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTrie<Integer> trie=new MyTrie<>();
		trie.put("first", 1);
		trie.put("second", 2);
		trie.put("third", 3);
		for(String key:trie.keys())
		{
			System.out.println(key);
		}
		for(String pre:trie.keysWithPrefix("fir"))
		{
			System.out.println(pre);
		}
		System.out.println(trie.get("second"));
		System.out.println(trie.longestPrefixOf("firstabc"));
	}

}
