import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;

// After the contest is over, the sourcecode will be available at
// https://github.com/swege/bauklotze

public class Tetris {
    public static Block[] blocks;
    private static HashMap<BoardData, BigInteger> cache;
    private static BigInteger[][] rectCache;
    private static BitSet possiblyInCache;
    public static boolean debugPrint = false;
    public static int printDelay;
    public static int setCaches;
    public static int getCaches;
    public static int setBlocks;
    public static long fooCounter;

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        if (args.length > 2) {
            Tetris.debugPrint = true;
            Tetris.printDelay = Integer.parseInt(args[2]);
        }

        System.out.println(Tetris.solve(m, n));
    }

    public static BigInteger solve(int m, int n) {
        Tetris.blocks = new Block[6];
        /// TODO: Could use binary literals (0b00101...), but stay compatible with Java 6. Not sure what version the contest server runs
        // X      100
        // X   -> 100 -> 001001001 -> 73
        // X      100
        blocks[0] = new Block((short) 73, 3, 1);
        // XXX    111
        //     -> 000 -> 000000111 -> 7
        //        000
        blocks[1] = new Block((short) 7, 1, 3);
        // XX     110
        // X   -> 100 -> 000001011 -> 11
        //        000
        blocks[2] = new Block((short) 11, 2, 2);
        // XX     110
        //  X  -> 010 -> 000010011 -> 19
        //        000
        blocks[3] = new Block((short) 19, 2, 2);
        //  X     010
        // XX  -> 110 -> 000011010 -> 26
        //        000
        blocks[4] = new Block((short) 26, 2, 2);
        // X      100
        // XX  -> 110 -> 000011001 -> 25
        //        000
        blocks[5] = new Block((short) 25, 2, 2);

        Tetris.cache = new HashMap<BoardData, BigInteger>();
        Tetris.rectCache = m > n ? new BigInteger[m][n] : new BigInteger[n][m];
        Tetris.possiblyInCache = new BitSet(m * n);
        Tetris.getCaches = 0;
        Tetris.setCaches = 0;
        Tetris.setBlocks = 0;
        Tetris.fooCounter = 0;
        Board board = new Board(m, n);
        return board.calculateMutations();
    }


    public static void setCache(BigInteger value, Area area) {
        setCaches++;
        if (area.isEmpty()) {
            Tetris.setCache(value, area.width, area.height);
        } else {
            Board.boardData.area = area;
            BoardData copy = new BoardData(Board.boardData);
            Tetris.cache.put(copy, value);
        }
        possiblyInCache.set(area.freeBlocks(), true);
    }

    private static void setCache(BigInteger value, int m, int n) {
        // decrement since you should pass the width and height of the board to the function
        m--;
        n--;
        if (m > n) {
            rectCache[m][n] = value;
        } else {
            rectCache[n][m] = value;
        }
    }

    public static BigInteger getCache(Area area) {
        // This method returns null if there is no solution available.
        // "0" as a solution is valid, since not all boards with pre set blocks are solvable!
//        long start = System.currentTimeMillis();
        if (!possiblyInCache.get(area.freeBlocks())) {
            return null;
        }
        BigInteger result;
        if (area.isEmpty()) {
            result = Tetris.getCache(area.width, area.height);
        } else {
            Board.boardData.area = area;
            result = Tetris.cache.get(Board.boardData);
            if (result == null) {
                BoardData copy = new BoardData(Board.boardData);
                copy.mirrorData();
                result = Tetris.cache.get(copy);
            }
        }
        if (result != null) {
            getCaches++;
        }
//        Tetris.fooCounter += System.currentTimeMillis() - start;
        return result;
    }

    private static BigInteger getCache(int m, int n) {
//        if (m == 0 || n == 0) {
//            return BigInteger.ONE;
//        }
        // decrement since you should pass the width and height of the board to the function
        m--;
        n--;
        if (m > n) {
            return rectCache[m][n];
        } else {
            return rectCache[n][m];
        }
    }
}
