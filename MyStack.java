import java.util.Iterator;
import java.util.Scanner;

public class MyStack<T> implements Iterable<T>{
	
	private MyLinkedList<T> stack;
	
	public MyStack()
	{
		stack=new MyLinkedList<>();
	}
	
	public int size()//获取大小
	{
		return stack.size();
	}
	
	public void push(T element)//将一个元素入栈
	{
		stack.addLast(element);
	}
	
	public T pop()//将一个元素出栈
	{
		T res=stack.getLast();
		stack.removeLast();
		return res;
	}
	
	public T top()//查看栈顶元素
	{
		return stack.getLast();
	}
	
	public boolean isEmpty()//判断栈是否为空
	{
		return stack.isEmpty();
	}
	
	public void clear()//清空栈
	{
		stack.clear();
	}
	
	public Iterator<T> iterator()//返回迭代器
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
		
		public void remove()//删除next越过的元素，并且仅当next()被调用后才可执行
		{	
			stack.remove(cursion+1);
		}
	}
	
	//利用栈解决问题的例子-中缀式转后缀式问题
	public void InFixToPostFix()
	{
		MyStack<Character> stack=new MyStack<>();
		String expresion=null;
		char value;
		int curison=0;
		
		System.out.println("请输入一个表达式（以#结尾）：");
		Scanner scan=new Scanner(System.in);
		expresion=scan.next();
		
		while((value=expresion.charAt(curison++))!='#')
		{
			
			if((value>='a'&&value<='z')||(value>='A'&&value<='Z')||(value>='0'&&value<='9'))
			{
				//不为运算符则直接输出
				System.out.print(value+" ");
			}
			else
			{
				//当是运算符时，将栈顶优先级大于当前运算符的元素弹出并输出
				//其中：'('只能被')'弹出，'('具有最高优先级，而')'具有最低优先级
				//直到栈顶的元素不可弹出为止
				switch(value)
				{
				case ')':
					while(!stack.isEmpty()&&stack.top()!='(')
					{
						System.out.print(stack.pop()+" ");
					}
					stack.pop();//弹出'('
					break;
				case '(':
					stack.push(value);
					break;
				case '+'://+、-优先级相同
				case '-':
					while(!stack.isEmpty()&&stack.top()!='(')
					{
						System.out.print(stack.pop()+" ");
					}
					stack.push(value);
					break;
				case '*'://*、/优先级相同
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
