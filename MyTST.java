
public class MyTST<T> {
	//���򵥴ʲ�����������trie�����ĵ�������õĿռ䣬ͨ�����򵥴ʲ���������ʡ�ռ䣬����������һЩʱ��Ч��
	
	private Node root;//�������
	
	private class Node
	{
		char c;//��ʾ�洢�ַ�
		Node left,mid,right;//�����ҷֱ��ʾ���ڣ����ڣ�С�ڵ�ǰ�ַ�����Ӧ�Ľ��
		T val;
	}
	
	//��ѯkey��Ӧ��ֵ,������ʱ����null
	public T get(String key)
	{
		Node result=get(root, key, 0);
		if(result==null)return null;
		else return (T)result.val;
	}
	
	//ͨ���ݹ�ķ�ʽ�����Ҽ�key��Ӧ��ֵ�Ƿ����
	private Node get(Node root,String key,int index)
	{
		if(root==null)return null;
		char nextC=key.charAt(index);
		
		if(nextC<root.c)return get(root.left, key, index);
		else if(nextC>root.c)return get(root.right, key, index);
		else if(index<key.length()-1)return get(root.mid, key, index+1);//�����ڣ�index�����������һ�����index��key����-1���ʱ���ɹ�ƥ��
		else return root;
	}
	
	//��Ӽ�Ϊkey��ֵΪval�Ľ��
	public void put(String key,T val)
	{
		root=put(root, key, val, 0);
	}
	
	//�ݹ���ӽ��
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
		else if(index<key.length()-1)//index��key����-1���ʱ���ɹ�ƥ��
		{
			root.mid=put(root.mid, key, val, index+1);//�����ڣ�index�����������һ���
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
