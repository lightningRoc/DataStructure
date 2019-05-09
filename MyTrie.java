import javax.security.auth.x500.X500Principal;

public class MyTrie <T>{
	//���ʲ�����,���ٲ��Ҽ�Ϊ�ַ���ʱ�Ķ���
	
	private static int R=256;//���ʻ�����С
	private Node root=null;//�����ڵ�(������ֵ)
	
	private static class Node
	{
		//������ʽ�ı�ʾ��ʽ����ʾ���ʲ�����(ÿ���ڵ���ӽڵ����R��,����һ���������洢)
		private Object val;
		private Node[] next=new Node[R];
	}
	
	//��ѯkey��Ӧ��ֵ,������ʱ����null
	public T get(String key)
	{
		Node result=get(root, key, 0);
		if(result==null)return null;
		else return (T)result.val;
	}
	
	//�ݹ�ز�ѯkey��Ӧ��ֵ������d��ʾkey�ַ�����ǰƥ���λ��
	private Node get(Node root,String key,int index)
	{
		if(root==null)return null;//ƥ�䵽��λ��Ϊ��(������key��Ӧ��ֵ)
		if(index==key.length())return root;
		char nextC=key.charAt(index);
		return get(root.next[nextC], key, index+1);
	}
	
	//��Ӽ�ΪKey��ֵΪval�Ľ��
	public void put(String key,T val)
	{
		root=put(root, key, val, 0);
	}
	
	//�ݹ�����һ����Ϊkey�Ľ��
	private Node put(Node root,String key,T val,int index) 
	{
		if(root==null)root=new Node();//���õĽ�㲻��Ҫ��ֵ����������ĸ�ɸ��ڵ��next[]�����������ʽ����
		if(index==key.length())
		{
			root.val=val;
			return root;
		}
		
		char nextC=key.charAt(index);
		root.next[nextC]=put(root.next[nextC],key,val,index+1);
		return root;
	}
	
	//ɾ����Ϊkey��Ӧ�Ľ��
	public void delete(String key)
	{
		root=delete(root, key, 0);
	}
	
	//�ݹ��ɾ��һ�����
	private Node delete(Node root,String key,int index)
	{
		if(root==null)return null;
		if(index==key.length())
		{
			root.val=null;//��յ�ǰ���ֵ����ɾ�����еĽ�������
		}
		else
		{
			char nextC=key.charAt(index);
			root.next[nextC]=delete(root, key, index+1);
		}
		
		//�鿴root����Ƿ���Ч(������Ч���ӽ����Ч)
		if(root.val!=null)return root;
		for(char c=0;c<R;c++)
		{
			if(root.next[c]!=null)return root;
		}
		
		//root�����Ч
		return null;
	}
	
	//�������еļ�
	public Iterable<String> keys()
	{
		return keysWithPrefix("");
	}
	
	//����ǰ׺pre,Ѱ�Ҷ�Ӧ��keyֵ����
	public Iterable<String> keysWithPrefix(String pre)
	{
		MyQueue<String> queue=new MyQueue<>();
		collect(get(root, pre, 0), pre, queue);
		return queue;
	}
	
	//����ǰ׺pre,�ݹ�Ѱ�Ҷ�Ӧ��keyֵ����
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
	
	//�ݹ��Ѱ��s�����е��ǰ׺�ĳ���,index��ʾ��ǰ������len��ʾ�Ѿ��ҵ��������
	private int search(Node root,String s,int index,int len) 
	{
		if(root==null)return len;
		if(root.val!=null)len=index;//���ڶ�Ӧ��ǰ׺
		if(index==s.length())return len;
		char c=s.charAt(index);
		
		return search(root, s, index+1, len);
	}
	
	//���ص��ʲ������Ĵ�С
	public int size()
	{
		return size(root);
	}
	
	//�ݹ��Ѱ�ҵ��ʲ������Ĵ�С
	private int size(Node root) 
	{
		if(root==null) return 0;
		
		int count=0;
		if(root.val!=null)count++;//��ֵʱ��ʾ��һ������Ľ��
		
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
	}

}
