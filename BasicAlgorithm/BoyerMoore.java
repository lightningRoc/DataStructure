import java.util.Scanner;

public class BoyerMoore {
	//����Boyer-Moore�㷨�������ַ����Ĳ���
	
	public int[] leap;//��Ծ����¼ƥ��
	private String source;//��Ҫ���ҵ��ַ���
	
	public BoyerMoore(String source) 
	{
		//������Ծ��
		this.source=source;
		int len=source.length();
		int R=256;//��ĸ����
		leap=new int[R];
		
		//-1��ʾsouce�в������ַ�c
		for(int c=0;c<R;c++)
		{
			leap[c]=-1;
		}
		
		//i��ʾ�ַ�i������source�У���i��ֵΪ����source�г��ֵ����ұߵ�λ��
		for(int i=0;i<len;i++)
		{
			leap[source.charAt(i)]=i;
		}
	}
	
	//��txt��Ѱ�����ַ���source���ɹ�ʱ���ص�һ��ƥ����Ӵ�������ĸ������ʧ��ʱ���س���txt��������
	public int find(String txt)
	{
		int sLen=source.length();
		int txtLen=txt.length();
		int skip;//��¼��Ծ��
		
		//��txt�д������Ҳ�ѯ
		for(int i=0;i<=txtLen-sLen;i+=skip)
		{
			skip=0;
			//��ĳ������i��Ѱ��j,��������Ѱ���Ƿ�ƥ��
			for(int j=sLen-1;j>=0;j--)
			{
				if(source.charAt(j)!=txt.charAt(i+j))
				{
					//ƥ��ʧ��ʱ
					skip=j-leap[txt.charAt(i+j)];//������Ծ����������ʱleap[]Ϊ-1��ʹ��i�ƽ�j+1
					if(skip<1)skip=1;//���ڲ�����ʹ��i������ƶ�,��˵�skip���������ƶ�ʱ��ʹ�������ƶ�һ��
					break;
				}
			}
			//��skip���ı�ʱ����ʾ����λ��ƥ��ɹ�
			if(skip==0)return i;
		}
		
		return txtLen;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		do
		{
			System.out.println("��Ҫ���ҵ��ַ�����");
			String source=scanner.nextLine();
			System.out.println("�����ַ�����");
			String txt=scanner.nextLine();
			
			BoyerMoore bm=new BoyerMoore(source);
			System.out.println("����ĸ����:"+bm.find(txt));
		}while(scanner.hasNextLine());
	}

}
