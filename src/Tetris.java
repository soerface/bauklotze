import java.util.HashMap;

public class Tetris {
    public static Block[] blocks;
    private static long[][] cache;
    private static HashMap<String, Long> overlapCache;
    public static boolean debugPrint = false;
//    public static long setBlocks;
//    public static long getCaches;

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        if (args.length > 2 && args[2].equals("debug")) {
            Tetris.debugPrint = true;
        }

        System.out.println(Tetris.solve(m, n));
    }

    public static long solve(int m, int n) {
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

        Tetris.cache = new long[(m > n ? m : n) + 1][(m > n ? n : m) + 1];
        Tetris.overlapCache = new HashMap<String, Long>();
//        Tetris.getCaches = 0;
//        Tetris.setBlocks = 0;
        Board board = new Board(m, n);
        return board.calculateMutations();
    }

    public static long getCache(int m, int n) {
//        Tetris.getCaches++;
        if (m > n) {
            return Tetris.cache[m][n];
        } else {
            return Tetris.cache[n][m];
        }
    }

    public static void setCache(int m, int n, long value) {
        if (m < n) {
            int tmp = m;
            m = n;
            n = tmp;
        }
        if (Tetris.cache[m][n] != 0) {
            return;
        }
//        System.out.format("Set cache %d, %d\n", m, n);
        Tetris.cache[m][n] = value;
    }

    public static void saveToOverlapCache(int[][] data, long value) {
        Tetris.overlapCache.put(dataToString(data), value);
    }

    public static long getFromOverlapCache(int[][] data) {
        Long value = Tetris.overlapCache.get(dataToString(data));
        if (value != null) {
            return value;
        }
        return 0;
    }

    public static String dataToString(int[][] data) {
        // This method is used to provide a key for the "overlap cache"
        // Often, the same for the top or bottom board is beeing calculated, though it is usally not a rectangle
        // Therefore, we can save a lot of work by caching those situations.
        // TODO: Even more could be cached, if we try to detect symmetrical solutions
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                stringBuilder.append(data[i][j]);
            }
            stringBuilder.append("+");
        }
        return stringBuilder.toString();
    }
}