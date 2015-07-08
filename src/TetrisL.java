import java.math.BigInteger;
import java.util.HashMap;

public class TetrisL
{
	static int length;
	static int height;
	public static BigInteger solve(int m, int n)
	{
		height = m;
		length = n;
		if(length > height)                                                    
		{            
			int c = height;                                                    
			height = length;                                                   
			length = c;                                                        
		}
		HashMap<String, BigInteger> hm = new HashMap<String, BigInteger>();
		char[] data = new char[height*length];
		for (int i = 0; i < height*length; i++) 
			data[i] = '\u0001';
		hm.put(new String(data), BigInteger.ONE);
		return fit(0, 0, hm, new char[height*length]);
	}
	
	private static BigInteger fit(int j, int i, HashMap<String, BigInteger> hm, char[] data)
	{
		String s = new String(data);
		if (hm.containsKey(s))
		{
			return hm.get(s);
		}
		int pos = j * length + i;
		while (data[pos] == '\u0001')
		{
			i = i + 1;
			pos = pos + 1;
			if (i >= length)
			{
				i = 0;
				j = j + 1;
			}
		}
		BigInteger count = BigInteger.ZERO;
		data[pos] = '\u0001';
		if (i + 1 < length && data[pos + 1] == '\u0000')
		{
			data[pos + 1] = '\u0001';
			if (i + 2 < length && data[pos + 2] == '\u0000')
			{
				data[pos + 2] = '\u0001';
				count = count.add((i + 3 < length) ? fit(j, i + 3, hm, data) : fit(j + 1, 0, hm, data));
				data[pos + 2] = '\u0000';
			}
			if (j + 1 < height)
			{
				if (data[pos + length] == '\u0000')
				{
					data[pos + length] = '\u0001';
					count = count.add((i + 2 < length) ? fit(j, i + 2, hm, data) : fit(j + 1, 0, hm, data));
					data[pos + length] = '\u0000';
				}
				if (data[pos + length + 1] == '\u0000')
				{
					data[pos + length + 1] = '\u0001';
					count = count.add((i + 2 < length) ? fit(j, i + 2, hm, data) : fit(j + 1, 0, hm, data));
					data[pos + length + 1] = '\u0000';
				}
			}
			data[pos + 1] = '\u0000';
		}
		if (j + 1 < height && data[pos + length] == '\u0000')
		{
			data[pos + length] = '\u0001';
			if (j + 2 < height && data[pos + 2 * length] == '\u0000')
			{
				data[pos + 2 * length] = '\u0001';
				count = count.add((i + 1 < length) ? fit(j, i + 1, hm, data) : fit(j + 1, 0, hm, data));
				data[pos + 2 * length] = '\u0000';
			}
			if (i - 1 >= 0 && data[pos + length - 1] == '\u0000')
			{
				data[pos + length - 1] = '\u0001';
				count = count.add((i + 1 < length) ? fit(j, i + 1, hm, data) : fit(j + 1, 0, hm, data));
				data[pos + length - 1] = '\u0000';
			}
			if (i + 1 < length && data[pos + length + 1] == '\u0000')
			{
				data[pos + length + 1] = '\u0001';
				count = count.add(fit(j, i + 1, hm, data));
				data[pos + length + 1] = '\u0000';
			}
			data[pos + length] = '\u0000';
		}
		data[pos] = '\u0000';
		hm.put(s, count);
		return count;
	}
}
