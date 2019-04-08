import java.util.Random;
import java.util.Scanner;

public class UnionFindWeightedCompression {
	
	//使用按大小合并的union-find算法
	private int[] id;//表示各个值对应的父节点
	private int count;//连通分量的数量(不同的集合数量)
	private int[] size;//各个根节点对应的分量大小
	
	public UnionFindWeightedCompression(int n) 
	{
		//构建拥有数值0至n-1的初始集合
		count=n;
		id=new int[n];
		for(int i=0;i<n;i++) {//初始化
			id[i]=i;//开始时各个集合只有自己本身
		}
		size=new int[n];
		for(int i=0;i<n;i++)size[i]=1;
	}
	
	public int count()//返回不同的集合的数量
	{
		return count;
	}
	
	public boolean connected(int p,int q) {//两个数值是否属于同一个集合(是否连通)
		return find(p)==find(q);
	}
	
	public int find(int p)//查找数值属于的集合的根节点
	{
		if(p==id[p])return p;
		else
		{
			id[p]=find(id[p]);
			return id[p];
		}
	}
	
	public void union(int p,int q)//按树的大小来合并两个数值对应的各自的集合(小的合并至大的当中)
	{
		//找到数值对应的根节点
		int i=find(p);
		int j=find(q);
		if(i==j)return;
		
		if(size[i]<size[j]) 
		{
			id[i]=j;//p对应的根节点i的集合更小
			size[j]+=size[i];
		}
		else {
			id[j]=i;
			size[i]+=size[j];
		}
		count--;
		
	}
	
	public static void test()//测试算法
	{
		Scanner scan=new Scanner(System.in);
		int n=scan.nextInt();//输入数值的个数
		scan.nextLine();
		UnionFindWeightedCompression test=new UnionFindWeightedCompression(n);

		while(scan.hasNext())
		{
			//一行输入两个数值，表示两个数值相互连通
			int i=scan.nextInt();
			int j=scan.nextInt();
			scan.nextLine();
			if(i==-1&&j==-1)break;
			
			if((i>=0&&i<n)&&(j>=0&&j<n))
			{
				//输入合法
				if(!test.connected(i, j))test.union(i, j);//进行find与union的混合操作
			}
		}
		
		System.out.println("不同的集合的个数："+test.count());
	}
	
	public static void randTest()//随机测试
	{
		
		UnionFindWeightedCompression test=new UnionFindWeightedCompression(10000);
		Random rand=new Random();
		
		for(int n=0;n<1000000;n++)
		{
			int i=rand.nextInt(10000);
			int j=rand.nextInt(10000);
			
			if(!test.connected(i, j))test.union(i, j);//进行find与union的混合操作
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		test();
		long end=System.currentTimeMillis();
		System.out.println("时间"+":"+(end-start)*1.0*1E-3+"s");
	}

}
