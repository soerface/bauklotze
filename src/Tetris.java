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
        Board board = new Board(m, n);
        return board.calculateMutations();
    }


    public static void setCache(BigInteger value, Area area) {
        Area reducedArea = Area.reducedArea(area);
        for (String key : Tetris.dataToStrings(Board.data, reducedArea)) {
            Tetris.partialsCache.put(key, value);
        }
    }

    public static BigInteger getCache(Area area) {
        Area reducedArea = Area.reducedArea(area);
        // This method returns null if there is no solution available.
        // "0" as a solution is valid, since not all boards with pre set blocks are solvable!
        return Tetris.partialsCache.get(dataToString(Board.data, reducedArea));
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
        String[] strings = new String[4];
        for (int i=0; i<4; i++) {
            Board.print(BigInteger.ZERO, data, area);
            strings[i] = dataToString(data, area);
            data = rotateData(data, area);
        }
//        strings[3] = dataToString(data, area);
        return strings;
    }

    public static int[][] rotateData(int[][] data, Area area) {
        int[][] newData = new int[Board.height][Board.width];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[i][j] = data[i][j];
            }
        }
        return newData;
    }
}