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


    public static void setCache(int[][] data, BigInteger value, Area area) {
//        for (String key : Tetris.dataToStrings(data, area)) {
            Tetris.partialsCache.put(dataToString(data, area), value);
//        }
    }

    public static BigInteger getCache(int[][] data, Area area) {
        // This method returns null if there is no solution available.
        // "0" as a solution is valid, since not all boards with pre set blocks are solvable!
        return Tetris.partialsCache.get(dataToString(data, area));
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
        // Basically the same as dataToString, but it returns four keys, since for one calculated solution we can save
        // four solutions with the symmetrical ones (rotates not included; not necessary since we always split horizontal)
        String[] strings = new String[4];
//        int[][] mirrowedData = new int[area.height][area.width];
        strings[0] = dataToString(data, area);
//        for (int i = area.y1; i < area.y2; i++) {
//            System.arraycopy(data[data.length - i - 1], 0, mirrowedData[i - area.y1], 0, area.width);
//        }
        strings[1] = dataToString(data, area);
//        for (int i = area.y1; i < area.y2; i++) {
//            for (int j = area.x1; j < area.x2; j++) {
//                mirrowedData[i - area.y1][j - area.x1] = data[area.y2 - i - 1][area.width - j - 1];
//            }
//        }
        strings[2] = dataToString(data, area);
//        for (int i = area.y1; i < area.y2; i++) {
//            for (int j = area.x1; j < area.x2; j++) {
//                mirrowedData[i - area.y1][j - area.x1] = data[i][area.width - j - 1];
//            }
//        }
        strings[3] = dataToString(data, area);
        return strings;
    }
}