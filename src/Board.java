import java.math.BigInteger;

public class Board {

    public static BoardData boardData;
    public static int height;
    public static int width;
    // this variables are just for debugging output
    public static BigInteger totalResult;
    public static long progress;

    public Board(int m, int n) {
        if (m < 3 || n > m) {
            Board.height = n;
            Board.width = m;
        } else {
            Board.height = m;
            Board.width = n;
        }
        Board.boardData = new BoardData();
    }

    public BigInteger calculateMutations() {
        totalResult = BigInteger.ZERO;
        progress = 0;
        return calculateMutations(new Area(0, 0, width, height));
    }

    public BigInteger calculateMutations(Area area) {
        Board.progress++;
        if (Tetris.printDelay > 100) {
            Tetris.printDelay -= 50;
        }
        BigInteger result = null;
        int[] position;
        position = findNextPosition(area);
        if (position == null) {
            if (Board.progress < 50) {
                print();
            }
            totalResult = totalResult.add(BigInteger.ONE);
            return BigInteger.ONE;
        }
        area = new Area(area.x1, position[0], area.x2, area.y2);

        result = Tetris.getCache(area);
        if (result != null) {
            for (int i = 0; i < 4 && Tetris.printDelay > 0; i++) {
                int tmp = Tetris.printDelay;
                Tetris.printDelay = Math.max(Tetris.printDelay / 10, 100);
                print(true);
                print();
                Tetris.printDelay = tmp;
            }
            totalResult = totalResult.add(result);
            return result;
        }

        result = BigInteger.ZERO;
        int[] pos;
        for (Block block : Tetris.blocks) {
            for (int i = 0; i < 4 && Tetris.printDelay > 0 && Board.progress < 10; i++) {
                int tmp = Tetris.printDelay;
                Tetris.printDelay = Math.max(Tetris.printDelay / 10, 100);
                print(position, block);
                print(block);
                Tetris.printDelay = tmp;
            }
            if (Board.progress < 50) {
                print(position, block);
            }
            pos = findValidPosition(block, position);
            if (pos[0] != -1) {
                placeBlockAt(block, pos);
                print(position);
                result = result.add(calculateMutations(area));
                removeBlockAt(block, pos);
                print();
            }
        }
//        if (area.height == 7) {
//            print(result, area);
//        }
//        System.out.println(area.height);
        Tetris.setCache(result, area);
        return result;
    }

    int[] findValidPosition(Block block, int[] pos) {
        if (block.get(0, 0) != 0) {
            if (blockPlaceableAt(block, pos)) {
                return pos;
            }
            return new int[]{-1, -1};
        } else if (pos[1] > 0) {
            // this is the block which has a gap on the first field
            // .X.
            // XX.
            // It has to be shifted one to the left to be placed
            int[] newPosition = pos.clone();
            newPosition[1]--;
            if (blockPlaceableAt(block, newPosition)) {
                return newPosition;
            }
        }
        return new int[]{-1, -1};
    }

    int[] findNextPosition(Area area) {
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                if (boardData.get(i, j) == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    protected void placeBlockAt(Block block, int[] position) {
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                int value = block.get(i, j);
                if (value != 0) {
                    boardData.set(i + position[0], j + position[1], value);
                }
            }
        }
    }

    protected void removeBlockAt(Block block, int[] position) {
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (block.get(i, j) != 0) {
                    boardData.set(i + position[0], j + position[1], 0);
                }
            }
        }
    }

    private boolean blockPlaceableAt(Block block, int[] position) {
        if (position[0] + block.height > Board.height) {
            return false;
        }
        if (position[1] + block.width > Board.width) {
            return false;
        }
        // check if there is already a block
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (block.get(i, j) != 0 && boardData.get(i + position[0], j + position[1]) != 0) {
                    return false;
                }
            }
        }
        // there was no collision with the board / placed outside of area; block can be placed
        return true;
    }

    public static void print() {
        print(new int[]{-1, -1}, null);
    }

    public static void print(boolean success) {
        print(new int[]{-1, -1}, null, success);
    }

    public static void print(int[] highlightPosition) {
        print(highlightPosition, null);
    }

    public static void print(Block block) {
        print(new int[]{-1, -1}, block);
    }

    public static void print(int[] highlightPosition, Block block) {
        print(highlightPosition, block, false);
    }

    public static void print(int[] highlightPosition, Block block, boolean success) {
        if (Tetris.printDelay <= 0) {
            return;
        }
        System.out.println();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value = boardData.get(i, j);
                String content = String.format(" %d ", value);
                int color = value;
                if (i == highlightPosition[0] && j == highlightPosition[1]) {
                        color = 7;
                }
                if (success && value != 0) {
                    System.out.format("\u001B[30;102m%s\u001B[0m", content);
                } else {
                    System.out.format("\u001B[4%dm%s\u001B[0m", color, content);
                }
            }
            if (block != null && i < block.height) {
                System.out.print("  ");
                for (int j = 0; j < block.width; j++) {
                    int value = block.get(i, j);
                    String content = String.format(" %d ", value);
                    int color = value;
                    System.out.format("\u001B[4%dm\u001B[30m%s\u001B[0m", color, content);
                }
            }
            System.out.println();
        }
        System.out.format("Solutions: %15d\n", Board.totalResult);
        try {
            Thread.sleep(Tetris.printDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
