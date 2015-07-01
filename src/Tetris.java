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
		System.out.print(Fit.fit(0, 0, hm, data));
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
		return Fit.fit(0, 0, hm, data);
	}
}
