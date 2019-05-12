import java.util.Scanner;

public class KMP {
	//����KMP�㷨�������ַ����Ĳ���
	
	private String source;//Դ�ַ���
	private int[][] dfa;//����������״̬�Զ���������dfa[i][j]��ʾ״̬j������ĸiʱӦ�����Ķ���
	
	public KMP(String source)
	{
		this.source=source;
		int len=source.length();
		int R=256;//�ַ����Ļ���
		dfa=new int[R][len];
		dfa[source.charAt(0)][0]=1;
		
		for(int back=0,i=1;i<len;i++)//backΪʧ��ʱ�Ļ���״̬
		{
			//����dfa������֮ǰ����õ�״̬������֮���״̬
			for(int c=0;c<R;c++)
			{
				dfa[c][i]=dfa[c][back];//ƥ��ʧ��ʱi��back״̬һ��
			}
			dfa[source.charAt(i)][i]=i+1;//ƥ��ɹ�
			
			back=dfa[source.charAt(i)][back];//����ʧ��״̬����ƽ�
		}
		
	}
	
	//��txt��Ѱ��source���ڵĵĵ�һ�����ַ�����λ��
	public int find(String txt)
	{
		int i,j,txtLen=txt.length(),sourceLen=source.length();//i��ʾsource�������±�,j��ʾ�ƽ���״̬
		
		for(i=0,j=0;i<txtLen&&j<sourceLen;i++)
		{
			j=dfa[txt.charAt(i)][j];//�ƽ�״̬j
		}
		
		if(j==sourceLen)return i-sourceLen;//�ɹ�ʱ��iλ�����ó�����ҪѰ�ҵ��ַ�����λ�ã������ҵ����ַ���λ�õ�����ĸ����
		else return txtLen;//Ѱ��ʧ�ܣ����س�����ҪѰ�ҵ��ַ����ĵ�һ������
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
			
			KMP kmp=new KMP(source);
			System.out.println("����ĸ����:"+kmp.find(txt));
		}while(scanner.hasNextLine());
	}

}
