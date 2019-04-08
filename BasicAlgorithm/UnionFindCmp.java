import java.util.Random;

public class UnionFindCmp {
	
	//���ֺϲ�����Ч�ʱȽ�
	
	public static void getResultByAMethod(String choice)//����һ������Ч��
	{
		long start=System.currentTimeMillis();
		for(int i=0;i<100;i++)
		{
			//ѡ����Ӧ���㷨
			switch(choice)
			{
			case "weighted":
				UnionFindWeighted.randTest();
				break;
			case "weightedCompression":
				UnionFindWeightedCompression.randTest();
				break;
			}
		}
		long end=System.currentTimeMillis();
		System.out.println(choice+":"+(end-start)*1.0*1E-3+"s");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getResultByAMethod("weighted");
		getResultByAMethod("weightedCompression");
	}

}
