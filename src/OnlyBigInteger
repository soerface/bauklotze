import java.math.BigInteger;
import java.util.HashMap;

public class OnlyBigInteger
{
	static int length;
	static int height;
	public static void main(String[] args) 
	{                                                                          
		long t;                                                                
		long ti;                                                               
		int time;                                                              
		length = Integer.parseInt(args[0]);                                    
		height = Integer.parseInt(args[1]);                                    
		height = 12;                                                           
		length = 10;                                    
		t = System.currentTimeMillis();                                        
		if(length > height)                                                    
		{                                                                      
			int c = height;                                                    
			height = length;                                                   
			length = c;                                                        
		}
		BigInteger dat = BigInteger.ZERO;
		HashMap<BigInteger, BigInteger> hm = new HashMap<BigInteger, BigInteger>();                          
		System.out.print(fit(0, 0, hm, dat, BigInteger.ONE, BigInteger.TEN.pow(length)));                                 
		ti = System.currentTimeMillis();                                       
		time = (int) (ti - t);                                                 
		System.out.print("\n" + time + "ms");
	}

	private static BigInteger fit(int j, int i, HashMap<BigInteger, BigInteger> hm, BigInteger dat, BigInteger mult, BigInteger fact) 
	{
		int pos = j*length+i;
		while(dat.divide(mult).mod(BigInteger.TEN).equals(BigInteger.ONE))
		{
			i=i+1;
			mult = mult.multiply(BigInteger.TEN);
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
		BigInteger count = BigInteger.ZERO;
		dat = dat.add(mult);
		if (i+1 < length && dat.divide(mult.multiply(BigInteger.TEN)).mod(BigInteger.TEN).equals(BigInteger.ZERO)) 
		{
			dat = dat.add(mult.multiply(BigInteger.TEN));
			if (i+2 < length && dat.divide(mult.multiply(BigInteger.valueOf(100))).mod(BigInteger.TEN).equals(BigInteger.ZERO)) 
			{
				dat = dat.add(mult.multiply(BigInteger.valueOf(100)));
				if(hm.containsKey(dat))
				{
					count = count.add(hm.get(dat));
				}
				else
				{
					if(i+3 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(dat, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(dat, fit(j+1, 0, hm, dat, mult.multiply(BigInteger.valueOf(1000)), fact));
							count = count.add(hm.get(dat));
						}
					}
					else
					{
						hm.put(dat, fit(j, i+3, hm, dat, mult.multiply(BigInteger.valueOf(1000)), fact));
						count = count.add(hm.get(dat));
					}
				}
				dat = dat.subtract(mult.multiply(BigInteger.valueOf(100)));
			}
			if (j+1 < height) 
			{
				if (dat.divide(mult.multiply(fact)).mod(BigInteger.TEN).equals(BigInteger.ZERO))
				{
					dat = dat.add(mult.multiply(fact));
					if (hm.containsKey(dat))
					{
						count = count.add(hm.get(dat));
					} 
					else
					{
						if(i+2 >= length)
						{
							if(j+1 >= height)
							{
								hm.put(dat, BigInteger.ONE);
								count = count.add(BigInteger.ONE);
							}
							else
							{
								hm.put(dat, fit(j+1, 0, hm, dat, mult.multiply(BigInteger.valueOf(100)), fact));
								count = count.add(hm.get(dat));
							}
						}
						else
						{
							hm.put(dat, fit(j, i+2, hm, dat, mult.multiply(BigInteger.valueOf(100)), fact));
							count = count.add(hm.get(dat));
						}
					}
					dat = dat.subtract(mult.multiply(fact));
				}
				if (dat.divide(mult.multiply(fact).multiply(BigInteger.TEN)).mod(BigInteger.TEN).equals(BigInteger.ZERO))
				{
					dat = dat.add(mult.multiply(fact).multiply(BigInteger.TEN));
					if (hm.containsKey(dat))
					{
						count = count.add(hm.get(dat));
					} 
					else
					{
						if(i+2 >= length)
						{
							if(j+1 >= height)
							{
								hm.put(dat, BigInteger.ONE);
								count = count.add(BigInteger.ONE);
							}
							else
							{
								hm.put(dat, fit(j+1, 0, hm, dat, mult.multiply(BigInteger.valueOf(100)), fact));
								count = count.add(hm.get(dat));
							}
						}
						else
						{
							hm.put(dat, fit(j, i+2, hm, dat, mult.multiply(BigInteger.valueOf(100)), fact));
							count = count.add(hm.get(dat));
						}
					}
					dat = dat.subtract(mult.multiply(fact).multiply(BigInteger.TEN));
				}
			}
			dat = dat.subtract(mult.multiply(BigInteger.TEN));
		}
		if (j + 1 < height && dat.divide(mult.multiply(fact)).mod(BigInteger.TEN).equals(BigInteger.ZERO)) 
		{
			dat = dat.add(mult.multiply(fact));
			if (j + 2 < height && dat.divide(mult.multiply(fact).multiply(fact)).mod(BigInteger.TEN).equals(BigInteger.ZERO)) 
			{
				dat = dat.add(mult.multiply(fact).multiply(fact));
				if(hm.containsKey(dat))
				{
					count = count.add(hm.get(dat));
				}
				else
				{
					if(i+1 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(dat, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(dat, fit(j+1, 0, hm, dat, mult.multiply(BigInteger.TEN), fact));
							count = count.add(hm.get(dat));
						}
					}
					else
					{
						hm.put(dat, fit(j, i+1, hm, dat, mult.multiply(BigInteger.TEN), fact));
						count = count.add(hm.get(dat));
					}
				}
				dat = dat.subtract(mult.multiply(fact).multiply(fact));
			}
			if (i - 1 >= 0 && dat.divide(mult.multiply(fact).divide(BigInteger.TEN)).mod(BigInteger.TEN).equals(BigInteger.ZERO)) 
			{
				dat = dat.add(mult.multiply(fact).divide(BigInteger.TEN));
				if(hm.containsKey(dat))
				{
					count = count.add(hm.get(dat));
				}
				else
				{
					if(i+1 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(dat, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(dat, fit(j+1, 0, hm, dat, mult.multiply(BigInteger.TEN), fact));
							count = count.add(hm.get(dat));
						}
					}
					else
					{
						hm.put(dat, fit(j, i+1, hm, dat, mult.multiply(BigInteger.TEN), fact));
						count = count.add(hm.get(dat));
					}
				}
				dat = dat.subtract(mult.multiply(fact).divide(BigInteger.TEN));
			}
			if (i + 1 < length && dat.divide(mult.multiply(fact).multiply(BigInteger.TEN)).mod(BigInteger.TEN).equals(BigInteger.ZERO)) 
			{
				dat = dat.add(mult.multiply(fact).multiply(BigInteger.TEN));
				if(hm.containsKey(dat))
				{
					count = count.add(hm.get(dat));
				}
				else
				{
					if(i+1 >= length)
					{
						if(j+1 >= height)
						{
							hm.put(dat, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else
						{
							hm.put(dat, fit(j+1, 0, hm, dat, mult.multiply(BigInteger.TEN), fact));
							count = count.add(hm.get(dat));
						}
					}
					else
					{
						hm.put(dat, fit(j, i+1, hm, dat, mult.multiply(BigInteger.TEN), fact));
						count = count.add(hm.get(dat));
					}
				}
				dat = dat.subtract(mult.multiply(fact).multiply(BigInteger.TEN));
			}
			dat = dat.subtract(mult.multiply(fact));
		}
		dat = dat.subtract(mult);
		return count;
	}
}
