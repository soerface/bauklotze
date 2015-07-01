import java.math.BigInteger;
import java.util.HashMap;

public class StringHash
{
	public static void main(String[] args) 
	{
		long t;
		long ti;
		int time;
		int length = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		height = 12;
		length = 10;
		if(length > height)
		{
			int c = height;
			height = length;
			length = c;
		}
		HashMap<String, BigInteger> hm = new HashMap<String, BigInteger>();
		t = System.currentTimeMillis();
		System.out.print(fit(height, length, 0, 0, hm, toS(height*length)));
		ti = System.currentTimeMillis();
		time = (int) (ti - t);
		System.out.print("\n" + time + "ms");
	}

	private static BigInteger fit(int height, int length, int j, int i, HashMap<String, BigInteger> hm, String s) 
	{
		if(i >= length)
		{
			i=0;
			j++;
			if(j >= height)
			{
				return BigInteger.valueOf(1);
			}
		}
		int pos = j*length + i;
		while(s.charAt(pos) == '1')
		{
			i++;
			pos++;
			if(i >=length)
			{
				i=0;
				j++;
				if(j >= height)
				{
					return BigInteger.valueOf(1);
				}
			}
		}
		BigInteger count = BigInteger.valueOf(0);
		if (i+1 < length && s.charAt(pos+1) == '0') 
		{
			if (i+2 < length && s.charAt(pos+2) == '0') 
			{
				s = s.substring(0, pos).concat(String.valueOf(1)).concat(String.valueOf(1)).concat(String.valueOf(1)).concat(s.substring(pos+3));
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					hm.put(s, fit(height, length, j, i+3, hm, s));
					count = count.add(hm.get(s));
				}
				s = s.substring(0, pos).concat(String.valueOf(0)).concat(String.valueOf(0)).concat(String.valueOf(0)).concat(s.substring(pos+3));
			}
			if (j+1 < height) 
			{
				if (s.charAt(pos+length) == '0') 
				{
					s = s.substring(0, pos).concat(String.valueOf(1)).concat(String.valueOf(1)).concat(s.substring(pos+2, pos+length)).concat(String.valueOf(1)).concat(s.substring(pos+length+1));
					if(hm.containsKey(s))
					{
						count = count.add(hm.get(s));
					}
					else
					{
						hm.put(s, fit(height, length, j, i+2, hm, s));
						count = count.add(hm.get(s));
					}
					s = s.substring(0, pos).concat(String.valueOf(0)).concat(String.valueOf(0)).concat(s.substring(pos+2, pos+length)).concat(String.valueOf(0)).concat(s.substring(pos+length+1));
				}
				if (s.charAt(pos+length+1) == '0') 
				{
					s = s.substring(0, pos).concat(String.valueOf(1)).concat(String.valueOf(1)).concat(s.substring(pos+2, pos+length+1)).concat(String.valueOf(1)).concat(s.substring(pos+length+2));
					if(hm.containsKey(s))
					{
						count = count.add(hm.get(s));
					}
					else
					{
						hm.put(s, fit(height, length, j, i+2, hm, s));
						count = count.add(hm.get(s));
					}
					s = s.substring(0, pos).concat(String.valueOf(0)).concat(String.valueOf(0)).concat(s.substring(pos+2, pos+length+1)).concat(String.valueOf(0)).concat(s.substring(pos+length+2));
				}
			}
		}
		if (j + 1 < height && s.charAt(pos+length) == '0') 
		{
			if (j + 2 < height && s.charAt(pos+2*length) == '0') 
			{
				s = s.substring(0, pos).concat(String.valueOf(1)).concat(s.substring(pos+1, pos+length)).concat(String.valueOf(1)).concat(s.substring(pos+length+1, pos+2*length).concat(String.valueOf(1)).concat(s.substring(pos+2*length+1)));
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					hm.put(s, fit(height, length, j, i+1, hm, s));
					count = count.add(hm.get(s));
				}
				s = s.substring(0, pos).concat(String.valueOf(0)).concat(s.substring(pos+1, pos+length)).concat(String.valueOf(0)).concat(s.substring(pos+length+1, pos+2*length).concat(String.valueOf(0)).concat(s.substring(pos+2*length+1)));
			}
			if (i - 1 >= 0 && s.charAt(pos+length-1) == '0') 
			{
				s = s.substring(0, pos).concat(String.valueOf(1)).concat(s.substring(pos+1, pos+length-1)).concat(String.valueOf(1)).concat(String.valueOf(1)).concat(s.substring(pos+length+1));
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					hm.put(s, fit(height, length, j, i + 1, hm, s));
					count = count.add(hm.get(s));
				}
				s = s.substring(0, pos).concat(String.valueOf(0)).concat(s.substring(pos+1, pos+length-1)).concat(String.valueOf(0)).concat(String.valueOf(0)).concat(s.substring(pos+length+1));
			}
			if (i + 1 < length && s.charAt(pos+length+1) == '0') 
			{
				s = s.substring(0, pos).concat(String.valueOf(1)).concat(s.substring(pos+1, pos+length)).concat(String.valueOf(1)).concat(String.valueOf(1)).concat(s.substring(pos+length+2));
				if(hm.containsKey(s))
				{
					count = count.add(hm.get(s));
				}
				else
				{
					hm.put(s, fit(height, length, j, i + 1, hm, s));
					count = count.add(hm.get(s));
				}
				s = s.substring(0, pos).concat(String.valueOf(0)).concat(s.substring(pos+1, pos+length)).concat(String.valueOf(0)).concat(String.valueOf(0)).concat(s.substring(pos+length+2));
			}
		}
		return count;
	}
	
	private static String toS(int a) 
	{
		String s = "";
		for(int j = 0; j < a; j++)
		{
			s = s.concat("0");
		}
		return s;
	}
}
