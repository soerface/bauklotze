import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;

// After the contest is over, the sourcecode will be available at
// https://github.com/swege/bauklotze

public class Tetris {
    public static Block[] blocks;
    private static HashMap<BoardData, BigInteger> cache;
    private static BigInteger[][] rectCache;
    public static boolean debugPrint = false;
    public static int printDelay;
//    public static int setCaches;
//    public static int getCaches;
//    public static int getCachesNull;
//    public static int setBlocks;
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
        blocks[0] = new Block(new boolean[][]{
                {true, false, false},
                {true, false, false},
                {true, false, false}
        });
        blocks[1] = new Block(new boolean[][]{
                {true, true, true},
                {false, false, false},
                {false, false, false}
        });
        blocks[2] = new Block(new boolean[][]{
                {true, true, false},
                {true, false, false},
                {false, false, false}
        });
        blocks[3] = new Block(new boolean[][]{
                {true, true, false},
                {false, true, false},
                {false, false, false}
        });
        blocks[4] = new Block(new boolean[][]{
                {false, true, false},
                {true, true, false},
                {false, false, false}
        });
        blocks[5] = new Block(new boolean[][]{
                {true, false, false},
                {true, true, false},
                {false, false, false}
        });

        Tetris.cache = new HashMap<BoardData, BigInteger>();
        Tetris.rectCache = m > n ? new BigInteger[m][n] : new BigInteger[n][m];
//        Tetris.getCaches = 0;
//        Tetris.getCachesNull = 0;
//        Tetris.setCaches = 0;
//        Tetris.setBlocks = 0;
//        Tetris.fooCounter = 0;
        Board board = new Board(m, n);
        return board.calculateMutations();
    }


    public static void setCache(BigInteger value, Area area) {
//        setCaches++;
        if (area.isEmpty()) {
            Tetris.setCache(value, area.width, area.height);
        } else {
//            long start = System.currentTimeMillis();
            Board.boardData.area = area;
            BoardData copy = new BoardData(Board.boardData);
            Tetris.cache.put(copy, value);
//            int[][] mirroredData = mirrorData(Board.boardData.data, area);
            copy = new BoardData(Board.boardData);
            copy.mirrorData();
            Tetris.cache.put(copy, value);
//            Tetris.fooCounter += System.currentTimeMillis() - start;
        }
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
//        getCaches++;
        BigInteger result;
        if (area.isEmpty()) {
            result = Tetris.getCache(area.width, area.height);
        } else {
            Board.boardData.area = area;
            result = Tetris.cache.get(Board.boardData);
//            if (result == null) {
//                int[][] mirroredData = mirrorData(Board.boardData.data, area);
//                BoardData copy = new BoardData(Board.boardData, false);
//                copy.data = mirroredData;
//                result = Tetris.cache.get(copy);
//                if (result != null) {
//                    Tetris.cache.put(new BoardData(Board.boardData, true), result);
//                }
//            }
        }
//        if (result == null) {
//            getCachesNull++;
//        }
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
