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
			Arrays.fill(data, '\u0001');
			hm.put(new String(data), BigInteger.ONE);
			Arrays.fill(data, '\u0000');
			return fit(0, 0, hm, data);
		}
