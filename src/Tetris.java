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
        return (m > 8 && n > 10) ? TetrisS.solve(m, n) : TetrisL.solve(m, n);
    }
}
