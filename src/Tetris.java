import java.math.BigInteger;
import java.util.HashMap;

public class Tetris
{
	static int length;
	static int height;
	public static void main(String[] args) 
	{
		length = Integer.parseInt(args[0]);
		height = Integer.parseInt(args[1]);
		if(length > height)
		{
			int c = height;
			height = length;
			length = c;
		}
		HashMap<Integer, BigInteger> hm = new HashMap<Integer, BigInteger>();
		boolean[] data = new boolean[height*length];
		System.out.print(fit(0, 0, hm, data));
	}
	
	public static BigInteger solve(n, m)
	{
		if(m > n)
		{
			int c = n;
			n = m;
			m = c;
		}
		HashMap<Integer, BigInteger> hm = new HashMap<Integer, BigInteger>();
		boolean[] data = new boolean[n*m];
		return fit(0, 0, hm, data);
	}

	private static BigInteger fit(int j, int i, HashMap<Integer, BigInteger> hm, boolean[] data)
	{
		int pos = j*length+i;
		while(data[pos] == true)
		{
			i=i+1;
			pos=pos+1;
			if(i >=length)
			{
				i=0;
				j=j+1;
				if(j >= height)
				{
					return BigInteger.ONE;
				}
			}
		}
		int s;
		BigInteger count = BigInteger.ZERO;
		data[pos] = true;
		if (i+1 < length && data[pos+1] == false) 
		{
			data[pos+1] = true;
			if (i+2 < length && data[pos+2] == false) 
			{
				data[pos+2] = true;
				s = hashCode(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+3 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(s, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(s, fit(j+1, 0, hm, data));
							count = count.add(hm.get(s));
						}
					}
					else
					{
						hm.put(s, fit(j, i+3, hm, data));
						count = count.add(hm.get(s));
					}
				}
				data[pos+2] = false;
			}
			if (j+1 < height) 
			{
				if (data[pos + length] == false)
				{
					data[pos + length] = true;
					s = hashCode(data);
					if (hm.containsKey(s))
					{
						count = count.add(hm.get(s));
					} 
					else
					{
						if(i+2 >= length)
						{
							if(j+1 >= height)
							{
								hm.put(s, BigInteger.ONE);
								count = count.add(BigInteger.ONE);
							}
							else
							{
								hm.put(s, fit(j+1, 0, hm, data));
								count = count.add(hm.get(s));
							}
						}
						else
						{
							hm.put(s, fit(j, i+2, hm, data));
							count = count.add(hm.get(s));
						}
					}
					data[pos + length] = false;
				}
				if (data[pos + length + 1] == false)
				{
					data[pos + length + 1] = true;
					s = hashCode(data);
					if (hm.containsKey(s))
					{
						count = count.add(hm.get(s));
					} 
					else
					{
						if(i+2 >= length)
						{
							if(j+1 >= height)
							{
								hm.put(s, BigInteger.ONE);
								count = count.add(BigInteger.ONE);
							}
							else
							{
								hm.put(s, fit(j+1, 0, hm, data));
								count = count.add(hm.get(s));
							}
						}
						else
						{
							hm.put(s, fit(j, i+2, hm, data));
							count = count.add(hm.get(s));
						}
					}
					data[pos + length + 1] = false;
				}
			}
			data[pos+1] = false;
		}
		if (j + 1 < height && data[pos+length] == false) 
		{
			data[pos+length] = true;
			if (j + 2 < height && data[pos+2*length] == false) 
			{
				data[pos+2*length] = true;
				s = hashCode(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+1 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(s, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(s, fit(j+1, 0, hm, data));
							count = count.add(hm.get(s));
						}
					}
					else
					{
						hm.put(s, fit(j, i+1, hm, data));
						count = count.add(hm.get(s));
					}
				}
				data[pos+2*length] = false;
			}
			if (i - 1 >= 0 && data[pos+length-1] == false) 
			{
				data[pos+length-1] = true;
				s = hashCode(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+1 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(s, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(s, fit(j+1, 0, hm, data));
							count = count.add(hm.get(s));
						}
					}
					else
					{
						hm.put(s, fit(j, i+1, hm, data));
						count = count.add(hm.get(s));
					}
				}
				data[pos+length-1] = false;
			}
			if (i + 1 < length && data[pos+length+1] == false) 
			{
				data[pos+length+1] = true;
				s = hashCode(data);
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					if(i+1 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(s, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(s, fit(j+1, 0, hm, data));
							count = count.add(hm.get(s));
						}
					}
					else
					{
						hm.put(s, fit(j, i+1, hm, data));
						count = count.add(hm.get(s));
					}
				}
				data[pos+length+1] = false;
			}
			data[pos+length] = false;
		}
		data[pos] = false;
		return count;
	}
	
	public static int hashCode(boolean a[]) {
        if (a == null)
            return 0;

        int result = 1;
        for (boolean element : a)
            result = 31 * result + (element ? 0 : 1);

        return result;
    }
}
