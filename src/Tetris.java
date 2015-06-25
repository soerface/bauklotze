import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;

// After the contest is over, the sourcecode will be available at
// https://github.com/swege/bauklotze

public class Tetris {
    public static Block[] blocks;
    private static HashMap<ByteBuffer, BigInteger> cache;
    private static BigInteger[][] rectCache;
    public static boolean debugPrint = false;
    public static int printDelay;
    public static int setCaches;
    public static int getCaches;
    public static int getCachesNull;
    public static int setBlocks;
    public static long fooCounter;
    private static byte[] key;

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
        blocks[0] = new Block(new int[][]{
                {1, 0, 0},
                {1, 0, 0},
                {1, 0, 0}
        });
        blocks[1] = new Block(new int[][]{
                {2, 2, 2},
                {0, 0, 0},
                {0, 0, 0}
        });
        blocks[2] = new Block(new int[][]{
                {3, 3, 0},
                {3, 0, 0},
                {0, 0, 0}
        });
        blocks[3] = new Block(new int[][]{
                {4, 4, 0},
                {0, 4, 0},
                {0, 0, 0}
        });
        blocks[4] = new Block(new int[][]{
                {0, 5, 0},
                {5, 5, 0},
                {0, 0, 0}
        });
        blocks[5] = new Block(new int[][]{
                {6, 0, 0},
                {6, 6, 0},
                {0, 0, 0}
        });

        Tetris.cache = new HashMap<ByteBuffer, BigInteger>();
        Tetris.rectCache = m > n ? new BigInteger[m][n] : new BigInteger[n][m];
        Tetris.getCaches = 0;
        Tetris.getCachesNull = 0;
        Tetris.setCaches = 0;
        Tetris.setBlocks = 0;
        Tetris.fooCounter = 0;
        key = new byte[m * n];
        Board board = new Board(m, n);
        return board.calculateMutations();
    }


    public static void setCache(BigInteger value, Area area) {
        setCaches++;
        if (area.isEmpty()) {
            Tetris.setCache(value, area.width, area.height);
        } else {
            Board.boardData.area = area;
            long start = System.currentTimeMillis();
            Tetris.cache.put(Tetris.dataToKey(Board.boardData), value);
//            int[][] mirroredData = mirrorData(Board.data.data);
//            Tetris.cache.put(Tetris.dataToKey(mirroredData, area), value);
            Tetris.fooCounter += System.currentTimeMillis() - start;
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
        getCaches++;
        BigInteger result;
        if (area.isEmpty()) {
            result = Tetris.getCache(area.width, area.height);
        } else {
            Board.boardData.area = area;
            long start = System.currentTimeMillis();
            result = Tetris.cache.get(dataToKey(Board.boardData));
            Tetris.fooCounter += System.currentTimeMillis() - start;
        }
        if (result == null) {
            getCachesNull++;
        }
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

    public static ByteBuffer dataToKey(BoardData boardData) {
        int[][] data = boardData.data;
        int k = 0;
        // TODO: WHY THE HELL WE NEED A NEW ONE?!
        key = new byte[boardData.size];
        for (int i = 0; i < boardData.height; i++) {
            for (int j = 0; j < boardData.width; j++) {
                key[k] = (byte) (data[i][j] != 0 ? 1 : 0);
                k++;
            }
        }
        return ByteBuffer.wrap(key);
    }

    public static int[][] mirrorData(int[][] data, Area area) {
        int[][] newData = new int[Board.height][Board.width];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[i][area.x2 - (j - area.x1) - 1] = data[i][j];
            }
        }
        return newData;
    }
}
