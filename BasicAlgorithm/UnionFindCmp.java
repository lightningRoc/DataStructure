import java.util.Random;

public class UnionFindCmp {
	
	//各种合并方法效率比较
	
	public static void getResultByAMethod(String choice)//计算一个方法效率
	{
		long start=System.currentTimeMillis();
		for(int i=0;i<100;i++)
		{
			//选择相应的算法
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
