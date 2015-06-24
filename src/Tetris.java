import java.math.BigInteger;
import java.util.HashMap;

// After the contest is over, the sourcecode will be available at
// https://github.com/swege/bauklotze

public class Tetris {
    public static Block[] blocks;
    private static HashMap<String, BigInteger> cache;
    private static BigInteger[][] rectCache;
    public static boolean debugPrint = false;
    public static int printDelay;
    public static int setCaches;
    public static int getCaches;
    public static int getCachesNull;
    public static int setBlocks;

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

        Tetris.cache = new HashMap<String, BigInteger>();
        Tetris.rectCache = m > n ? new BigInteger[m][n] : new BigInteger[n][m];
        Tetris.getCaches = 0;
        Tetris.getCachesNull = 0;
        Tetris.setCaches = 0;
        Tetris.setBlocks = 0;
        Board board = new Board(m, n);
        return board.calculateMutations();
    }


    public static void setCache(BigInteger value, Area area) {
        setCaches++;
        if (area.isEmpty()) {
            Tetris.setCache(value, area.width, area.height);
        } else {
            Tetris.cache.put(Tetris.dataToString(Board.data, area), value);
        }
    }

    private static void setCache(BigInteger value, int m, int n) {
//        if (m == 0 || n == 0) {
//            return;
//        }
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
//        Area reducedArea = Area.reducedArea(area);
        BigInteger result;
        if (area.isEmpty()) {
            result = Tetris.getCache(area.width, area.height);
        } else {
            result = Tetris.cache.get(dataToString(Board.data, area));
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

    public static String dataToString(int[][] data, Area area) {
        // This method is used to provide a key for the "overlap cache"
        // Often, the same for the top or bottom board is being calculated, though it is usually not a rectangle
        // Therefore, we can save a lot of work by caching those situations.
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = area.y1; i < area.y2; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = area.x1; j < area.x2; j++) {
                if (data[i][j] != 0) {
                    row.append(1);
                } else {
                    row.append(0);
                }
            }
            stringBuilder.append(row);
            stringBuilder.append("+");
        }
        return stringBuilder.toString();
    }

    public static String[] dataToStrings(int[][] data, Area area) {
        // Basically the same as dataToString, but it returns all mirrored and rotated solutions
        String[] strings = new String[8];
        for (int i = 0; i < 8; i += 2) {
            strings[i] = dataToString(data, area);
            strings[i + 1] = dataToString(mirrorData(data, area), area);
            data = rotateData(data, area);
            // this rotates the area
            area = new Area(area.y1, area.x1, area.y2, area.x2);
        }
        return strings;
    }

    public static int[][] rotateData(int[][] data, Area area) {
        // on purpose two times Board.height, because one side might be smaller
        // this could be problematic when rotating
        int[][] newData = new int[Board.height][Board.height];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[area.x2 - (j - area.x1) - 1][i] = data[i][j];
            }
        }
        return newData;
    }

    public static int[][] mirrorData(int[][] data, Area area) {
        int[][] newData = new int[Board.height][Board.height];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[i][area.x2 - (j - area.x1) - 1] = data[i][j];
            }
        }
        return newData;
    }
}