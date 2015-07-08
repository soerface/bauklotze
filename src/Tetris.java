import java.math.BigInteger;

public class Tetris {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        if (args.length > 2) {
            TetrisS.debugPrint = true;
            TetrisS.printDelay = Integer.parseInt(args[2]);
        }
        System.out.println(Tetris.solve(m, n));
    }

    public static BigInteger solve(int m, int n) {
        if(n > m)                                                    
	{            
		int c = m;                                                    
		m = n;                                                   
		n = c;                                                        
	}
        if (m > 10 && n > 8) {
            return TetrisS.solve(m, n);
        } else {
            return TetrisL.solve(m, n);
        }
    }
}
