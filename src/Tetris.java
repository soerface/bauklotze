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
		char[] data = new char[height*length];
		System.out.print(fit(0, 0, hm, data));
	}
	
	public static BigInteger solve(int n, int m)
	{
		if(m > n)
		{
			int c = n;
			n = m;
			m = c;
		}
		HashMap<Integer, BigInteger> hm = new HashMap<Integer, BigInteger>();
		char[] data = new char[n*m];
		return fit(0, 0, hm, data);
	}
    
    private static BigInteger fit(int j, int i, HashMap<Integer, BigInteger> hm, char[] data)
	{
		int pos = j*length+i;
		while(data[pos] == '\u0001')
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
		data[pos] = '\u0001';
		if (i+1 < length && data[pos+1] == '\u0000') 
		{
			data[pos+1] = '\u0001';
			if (i+2 < length && data[pos+2] == '\u0000') 
			{
				data[pos+2] = '\u0001';
				s = Arrays.hashCode(data);
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
				data[pos+2] = '\u0000';
			}
			if (j+1 < height) 
			{
				if (data[pos + length] == '\u0000')
				{
					data[pos + length] = '\u0001';
					s = Arrays.hashCode(data);
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
					data[pos + length] = '\u0000';
				}
				if (data[pos + length + 1] == '\u0000')
				{
					data[pos + length + 1] = '\u0001';
					s = Arrays.hashCode(data);
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
					data[pos + length + 1] = '\u0000';
				}
			}
			data[pos+1] = '\u0000';
		}
		if (j + 1 < height && data[pos+length] == '\u0000') 
		{
			data[pos+length] = '\u0001';
			if (j + 2 < height && data[pos+2*length] == '\u0000') 
			{
				data[pos+2*length] = '\u0001';
				s = Arrays.hashCode(data);
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
				data[pos+2*length] = '\u0000';
			}
			if (i - 1 >= 0 && data[pos+length-1] == '\u0000') 
			{
				data[pos+length-1] = '\u0001';
				s = Arrays.hashCode(data);
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
				data[pos+length-1] = '\u0000';
			}
			if (i + 1 < length && data[pos+length+1] == '\u0000') 
			{
				data[pos+length+1] = '\u0001';
				s = Arrays.hashCode(data);
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
				data[pos+length+1] = '\u0000';
			}
			data[pos+length] = '\u0000';
		}
		data[pos] = '\u0000';
		return count;
	}
}
