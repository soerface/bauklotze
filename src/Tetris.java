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
	
	//j = row, i = column 
	public static BigInteger fit(int j, int i, HashMap<String, BigInteger> hm, char[] data)
	{
		int pos = j*length+i; // position in data
		
		//find next empty position
		while(data[pos] == '\u0001') //check row j
		{
			i=i+1;
			pos=pos+1;
			if(i >=length) //checked that row - go for the next one
			{
				i=0;
				j=j+1;
				if(j >= height) //no empty position -> full -> return 1
				{
					return BigInteger.ONE;
				}
			}
		}
		
		BigInteger count = BigInteger.ZERO; //count for all mutations found with this combination of blocks
		data[pos] = '\u0001'; //set position 1
		if (i+1 < length && data[pos+1] == '\u0000') //check field on the right for emptyness
		{
			data[pos+1] = '\u0001'; //fill that field
			if (i+2 < length && data[pos+2] == '\u0000') //check the next field to the right
			{
				data[pos+2] = '\u0001';	//fill that field - we have [][][] now
				String s = new String(data); //get a nice string for hash
				if(hm.containsKey(s)) //check hashmap...
				{
					count = count.add(hm.get(s));
				}
				else //not in hashmap
				{
					if(i+3 >= length) //row full?
					{
						if(j+1 >= height) //array full?
						{
							hm.put(s, BigInteger.ONE);
							count = count.add(BigInteger.ONE);
						}
						else //start the recursion! (but start in next row...)
						{
							hm.put(s, fit(j+1, 0, hm, data));
							count = count.add(hm.get(s));
						}
					}
					else //start the recursion! (start behind our block...)
					{
						hm.put(s, fit(j, i+3, hm, data));
						count = count.add(hm.get(s));
					}
				}
				data[pos+2] = '\u0000'; //remove third square - only [][] left... still useable
			}
			if (j+1 < height) //is there another row?
			{
				 //seems like it...
				if (data[pos + length] == '\u0000') //field below position empty?
				{
					data[pos + length] = '\u0001'; 	//fill that field! 	[][] <- from before
					String s = new String(data);	//			[] <- new!
					if (hm.containsKey(s)) //like before...
					{
						count = count.add(hm.get(s));
					} 
					else //like before...
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
							hm.put(s, fit(j, i+2, hm, data)); //start 	[][][here]
							count = count.add(hm.get(s));	//		[]
						}
					}
					data[pos + length] = '\u0000'; //remove the square again..
				}
				if (data[pos + length + 1] == '\u0000') //guess what..
				{
					data[pos + length + 1] = '\u0001'; //guess!
					String s = new String(data);
					if (hm.containsKey(s))
					{
						count = count.add(hm.get(s)); //stop right there and think!
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
						else //fine it is this one	[][]
						{//				  []
							hm.put(s, fit(j, i+2, hm, data));
							count = count.add(hm.get(s));
						}
					}
					data[pos + length + 1] = '\u0000'; //remove third one
				}
			}
			data[pos+1] = '\u0000';//remove another one - only the one on position left...
		}
		if (j + 1 < height && data[pos+length] == '\u0000') 
		{
			data[pos+length] = '\u0001';//give him a friend
			if (j + 2 < height && data[pos+2*length] == '\u0000') 
			{
				data[pos+2*length] = '\u0001';//another one
				String s = new String(data);
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
				data[pos+2*length] = '\u0000';//kill that one again
			}
			if (i - 1 >= 0 && data[pos+length-1] == '\u0000') 
			{
				data[pos+length-1] = '\u0001';//give him a new one
				String s = new String(data);
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
				data[pos+length-1] = '\u0000';//kill it with fire
			}
			if (i + 1 < length && data[pos+length+1] == '\u0000') 
			{
				data[pos+length+1] = '\u0001';//revive it somewhere else
				String s = new String(data);
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
				data[pos+length+1] = '\u0000';//burn them
			}
			data[pos+length] = '\u0000';//with
		}
		data[pos] = '\u0000';//FIRE! 
		return count; //return number of mutations if you are done...
	}
}
