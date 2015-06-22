import java.math.BigInteger;
import java.util.HashMap;

// After the contest is over, the sourcecode will be available at
// https://github.com/swege/bauklotze

public class Tetris {
    public static Block[] blocks;
    private static BigInteger[][] cache;
    private static HashMap<String, BigInteger> partialsCache;
    public static boolean debugPrint = false;
    public static int printDelay;
//    public static long setBlocks;
//    public static long getCaches;

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

        Tetris.cache = new BigInteger[(m > n ? m : n) + 1][(m > n ? n : m) + 1];
        Tetris.partialsCache = new HashMap<String, BigInteger>();
//        Tetris.debugPrint = false;
//        Tetris.getCaches = 0;
//        Tetris.setBlocks = 0;
        Board board = new Board(m, n);
        return board.calculateMutations();
    }

    public static void setCache(int m, int n, BigInteger value) {
        if (m < n) {
            int tmp = m;
            m = n;
            n = tmp;
        }
        Tetris.cache[m][n] = value;
    }

    public static void setCache(int[][] data, BigInteger value) {
        for (String key : Tetris.dataToStrings(data)) {
            Tetris.partialsCache.put(key, value);
        }
    }

    public static BigInteger getCache(int m, int n) {
        // This method returns null if there is no solution available.
        if (m > n) {
            return Tetris.cache[m][n];
        } else {
            return Tetris.cache[n][m];
        }
    }

    public static BigInteger getCache(int[][] data) {
        // This method returns null if there is no solution available.
        // "0" as a solution is valid, since not all boards with pre set blocks are solvable!
        return Tetris.partialsCache.get(dataToString(data));
    }

    public static String dataToString(int[][] data) {
        // This method is used to provide a key for the "overlap cache"
        // Often, the same for the top or bottom board is being calculated, though it is usually not a rectangle
        // Therefore, we can save a lot of work by caching those situations.
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            StringBuilder row = new StringBuilder();
            boolean filledRow = true;
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] != 0) {
                    row.append(1);
                } else {
                    row.append(0);
                    filledRow = false;
                }
            }
            // _______    _______
            // |#####|    |# ###|
            // |# ###| == |     |
            // |     |    -------
            // -------
            // For calculating the combinations, those are both equal. Therefore, completely ignore filled rows
            if (!filledRow) {
                stringBuilder.append(row);
            }
            stringBuilder.append("+");
        }
        return stringBuilder.toString();
    }

    public static String[] dataToStrings(int[][] data) {
        // Basically the same as dataToString, but it returns four keys, since for one calculated solution we can save
        // four solutions with the symmetrical ones (rotates not included; not necessary since we always split horizontal)
        String[] strings = new String[4];
        int[][] mirrowedData = new int[data.length][data[0].length];
        strings[0] = dataToString(data);
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[data.length - i - 1], 0, mirrowedData[i], 0, data[0].length);
        }
        strings[1] = dataToString(mirrowedData);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                mirrowedData[i][j] = data[data.length - i - 1][data[0].length - j - 1];
            }
        }
        strings[2] = dataToString(mirrowedData);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                mirrowedData[i][j] = data[i][data[0].length - j - 1];
            }
        }
        strings[3] = dataToString(mirrowedData);
        return strings;
    }
}
