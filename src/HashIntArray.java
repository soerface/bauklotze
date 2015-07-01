import java.math.BigInteger;
import java.util.HashMap;

public class HashIntArray
{
	public static void main(String[] args) 
	{
		long t;
		long ti;
		int time = 0;
		int length = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		if(length > height)
		{
			int c = height;
			height = length;
			length = c;
		}
		HashMap<String, BigInteger> hm = new HashMap<String, BigInteger>();
		int a[][] = new int[height][length];
		t = System.currentTimeMillis();
		System.out.print(fit(a, a.length, a[0].length, 0, 0, hm));
		ti = System.currentTimeMillis();
		time = (int) (ti - t);
		System.out.print("\n" + time + "ms");
	}

	private static BigInteger fit(int[][] a, int height, int length, int j, int i, HashMap<String, BigInteger> hm) 
	{ 
		if(i >= length)
		{
			i=0;
			j++;
			if(j >= height)
			{
				System.out.println(toS(a)); //print full arrays
				return BigInteger.valueOf(1);
			}
		}
		BigInteger count = BigInteger.valueOf(0);
		while(a[j][i] != 0)
		{
			i++;
			if(i >= length)
			{
				i=0;
				j++;
				if(j >= height)
				{
					return BigInteger.valueOf(1);
				}
			}
		}
		if(i+1 < length && a[j][i+1] == 0)
		{
			if(i+2 < length && a[j][i+2] == 0) 					//[][][]
			{
				a[j][i] = 1;
				a[j][i+1] = 1;
				a[j][i+2] = 1;
				count = count.add(fit(a, height, length, j, i+3, hm));
				a[j][i] = 0;
				a[j][i+1] = 0;
				a[j][i+2] = 0;
			}
			if(j+1 < height)
			{
				if(a[j+1][i] == 0)								//[][]
				{												//[]
					a[j][i] = 2;
					a[j][i+1] = 2;
					a[j+1][i] = 2;
					count = count.add(fit(a, height, length, j, i+2, hm));
					a[j][i] = 0;
					a[j][i+1] = 0;
					a[j+1][i] = 0;
				}
				if(a[j+1][i+1] == 0)							//[][]
				{												//  []
					a[j][i] = 3;
					a[j][i+1] = 3;
					a[j+1][i+1] = 3;
					count = count.add(fit(a, height, length, j, i+2, hm));
					a[j][i] = 0;
					a[j][i+1] = 0;
					a[j+1][i+1] = 0;
				}
			}
		}
		if(j+1 < height && a[j+1][i] == 0)
		{
			if(j+2 < height && a[j+2][i] == 0) 					//[]
			{													//[]
				a[j][i] = 4;									//[]
				a[j+1][i] = 4;
				a[j+2][i] = 4;
				count = count.add(fit(a, height, length, j, i+1, hm));
				a[j][i] = 0;																												
				a[j+1][i] = 0;
				a[j+2][i] = 0;
			}
			if(i-1 >= 0 && a[j+1][i-1] == 0)					//  []
			{													//[][]
				a[j][i] = 5;
				a[j+1][i] = 5;
				a[j+1][i-1] = 5;
				count = count.add(fit(a, height, length, j, i+1, hm));
				a[j][i] = 0;
				a[j+1][i] = 0;
				a[j+1][i-1] = 0;
			}
			if(i+1 < length && a[j+1][i+1] == 0)				//[]
			{													//[][]
				a[j][i] = 6;
				a[j+1][i] = 6;
				a[j+1][i+1] = 6;
				count = count.add(fit(a, height, length, j, i+1, hm));
				a[j][i] = 0;
				a[j+1][i] = 0;
				a[j+1][i+1] = 0;
			}
		}
		return count;
	}
}
