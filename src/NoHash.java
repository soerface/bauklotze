public class NoHashNorBigInteger
{
	public static void main(String[] args) 
	{
		int length = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		if(length > height)
		{
			int c = height;
			height = length;
			length = c;
		}
		boolean a[][] = new boolean[length][height];
		System.out.print(fit(a, length, height, 0, 0));
	}

	private static long fit(boolean[][] a, int length, int height, int j, int i) 
	{ 
		long count = 0;
		if(i >=length)
		{
			i=0;
			j++;
			if(j >= height)
			{
				return 1;
			}
		}
		while(a[i][j] == true)
		{
			i++;
			if(i >=length)
			{
				i=0;
				j++;
				if(j >= height)
				{
					return 1;
				}
			}
		}
		
		if (i + 1 < length && a[i + 1][j] == false) 
		{
			if (i + 2 < length && a[i + 2][j] == false) 
			{
				a[i][j] = true;
				a[i + 1][j] = true;
				a[i + 2][j] = true;
				count = count + fit(a, length, height, j, i + 3);
				a[i][j] = false;
				a[i + 1][j] = false;
				a[i + 2][j] = false;
			}
			if (j + 1 < height) 
			{
				if (a[i][j + 1] == false) 
				{
					a[i][j] = true;
					a[i + 1][j] = true;
					a[i][j + 1] = true;
					count = count + fit(a, length, height, j, i + 2);
					a[i][j] = false;
					a[i + 1][j] = false;
					a[i][j + 1] = false;
				}
				if (a[i + 1][j + 1] == false) 
				{
					a[i][j] = true;
					a[i + 1][j] = true;
					a[i + 1][j + 1] = true;
					count = count + fit(a, length, height, j, i + 2);
					a[i][j] = false;
					a[i + 1][j] = false;
					a[i + 1][j + 1] = false;
				}
			}
		}
		if (j + 1 < height && a[i][j + 1] == false) 
		{
			if (j + 2 < height && a[i][j + 2] == false) 
			{
				a[i][j] = true;
				a[i][j + 1] = true;
				a[i][j + 2] = true;
				count = count + fit(a, length, height, j, i + 1);
				a[i][j] = false;
				a[i][j + 1] = false;
				a[i][j + 2] = false;
			}
			if (i - 1 >= 0 && a[i - 1][j + 1] == false) 
			{
				a[i][j] = true;
				a[i][j + 1] = true;
				a[i - 1][j + 1] = true;
				count = count + fit(a, length, height, j, i + 1);
				a[i][j] = false;
				a[i][j + 1] = false;
				a[i - 1][j + 1] = false;
			}
			if (i + 1 < length && a[i + 1][j + 1] == false) 
			{
				a[i][j] = true;
				a[i][j + 1] = true;
				a[i + 1][j + 1] = true;
				count = count + fit(a, length, height, j, i + 1);
				a[i][j] = false;
				a[i][j + 1] = false;
				a[i + 1][j + 1] = false;
			}
		}
		return count;
	}
}
