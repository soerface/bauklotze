import java.math.BigInteger;
import java.util.HashMap;

// After the contest is over, the sourcecode will be available at
// https://github.com/swege/bauklotze

public class Tetris {
    public static Block[] blocks;
    private static HashMap<BoardData, BigInteger> cache;
    public static int printDelay;

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        Tetris.printDelay = args.length > 2 ? Integer.parseInt(args[2]) : 0;
        System.out.format("Result: %d", Tetris.solve(m, n));
    }

    public static BigInteger solve(int m, int n) {
        Tetris.blocks = new Block[6];
        blocks[0] = new Block(new int[][]{
                {1, 0, 0},
                {1, 0, 0},
                {1, 0, 0}
        }, 3, 1);
        blocks[1] = new Block(new int[][]{
                {2, 2, 2},
                {0, 0, 0},
                {0, 0, 0}
        }, 1, 3);
        blocks[2] = new Block(new int[][]{
                {3, 3, 0},
                {3, 0, 0},
                {0, 0, 0}
        }, 2, 2);
        blocks[3] = new Block(new int[][]{
                {4, 4, 0},
                {0, 4, 0},
                {0, 0, 0}
        }, 2, 2);
        blocks[4] = new Block(new int[][]{
                {0, 5, 0},
                {5, 5, 0},
                {0, 0, 0}
        }, 2, 2);
        blocks[5] = new Block(new int[][]{
                {6, 0, 0},
                {6, 6, 0},
                {0, 0, 0}
        }, 2, 2);

        Tetris.cache = new HashMap<BoardData, BigInteger>();
        Board board = new Board(m, n);
        return board.calculateMutations();
    }


    public static void setCache(BigInteger value, Area area) {
        Board.boardData.setArea(area);
        BoardData copy = new BoardData(Board.boardData);
        Tetris.cache.put(copy, value);
    }

    public static BigInteger getCache(Area area) {
        // This method returns null if there is no solution available.
        BigInteger result;
        Board.boardData.setArea(area);
        result = Tetris.cache.get(Board.boardData);
        if (result == null) {
            BoardData copy = new BoardData(Board.boardData);
            copy.mirrorData();
            result = Tetris.cache.get(copy);
        }
        return result;
    }
}
