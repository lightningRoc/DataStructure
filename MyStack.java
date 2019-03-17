import java.util.Iterator;
import java.util.Scanner;

public class MyStack<T> implements Iterable<T>{
	
	private MyLinkedList<T> stack;
	
	public MyStack()
	{
		stack=new MyLinkedList<>();
	}
	
	public int size()//��ȡ��С
	{
		return stack.size();
	}
	
	public void push(T element)//��һ��Ԫ����ջ
	{
		stack.addLast(element);
	}
	
	public T pop()//��һ��Ԫ�س�ջ
	{
		T res=stack.getLast();
		stack.removeLast();
		return res;
	}
	
	public T top()//�鿴ջ��Ԫ��
	{
		return stack.getLast();
	}
	
	public boolean isEmpty()//�ж�ջ�Ƿ�Ϊ��
	{
		return stack.isEmpty();
	}
	
	public void clear()//���ջ
	{
		stack.clear();
	}
	
	public Iterator<T> iterator()//���ص�����
	{
		return new StackIterator();
	}
	
	private class StackIterator implements Iterator<T>
	{
		private int cursion=stack.size()-1;
		
		public boolean hasNext()
		{
			return cursion>=0;
		}
		
		public T next()
		{
			if(!hasNext())return null;
			
			return stack.get(cursion--);
		}
		
		public void remove()//ɾ��nextԽ����Ԫ�أ����ҽ���next()�����ú�ſ�ִ��
		{	
			stack.remove(cursion+1);
		}
	}
	
	//����ջ������������-��׺ʽת��׺ʽ����
	public void InFixToPostFix()
	{
		MyStack<Character> stack=new MyStack<>();
		String expresion=null;
		char value;
		int curison=0;
		
		System.out.println("������һ�����ʽ����#��β����");
		Scanner scan=new Scanner(System.in);
		expresion=scan.next();
		
		while((value=expresion.charAt(curison++))!='#')
		{
			
			if((value>='a'&&value<='z')||(value>='A'&&value<='Z')||(value>='0'&&value<='9'))
			{
				//��Ϊ�������ֱ�����
				System.out.print(value+" ");
			}
			else
			{
				//���������ʱ����ջ�����ȼ����ڵ�ǰ�������Ԫ�ص��������
				//���У�'('ֻ�ܱ�')'������'('����������ȼ�����')'����������ȼ�
				//ֱ��ջ����Ԫ�ز��ɵ���Ϊֹ
				switch(value)
				{
				case ')':
					while(!stack.isEmpty()&&stack.top()!='(')
					{
						System.out.print(stack.pop()+" ");
					}
					stack.pop();//����'('
					break;
				case '(':
					stack.push(value);
					break;
				case '+'://+��-���ȼ���ͬ
				case '-':
					while(!stack.isEmpty()&&stack.top()!='(')
					{
						System.out.print(stack.pop()+" ");
					}
					stack.push(value);
					break;
				case '*'://*��/���ȼ���ͬ
				case '/':
					while(!stack.isEmpty()&&stack.top()!='+'&&stack.top()!='-'&&stack.top()!='(')
					{
						System.out.print(stack.pop()+" ");
					}
					stack.push(value);
					break;
				}
			}
		}
		
		while(!stack.isEmpty())
		{
			System.out.print(stack.pop()+" ");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyStack<Integer> test=new MyStack<>();
		for(int i=0;i<10;i++)test.push(i);
		
		for(int n:test)
		{
			System.out.println(n);
		}
		
		Iterator<Integer> itr=test.iterator();
		System.out.println();
		
		while(itr.hasNext())
		{
			itr.next();
			itr.remove();
		}
		
		System.out.println("\n"+test.size());
		
		test.InFixToPostFix();
	}

}
