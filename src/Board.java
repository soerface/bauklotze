import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public int[][] data;
    public BigInteger result;
    int height;
    int width;
    boolean saveToCache;

    public Board(int m, int n) {
        this(m, n, true, true);
    }

    public Board(int m, int n, boolean allowRotate, boolean saveToCache) {
        if (allowRotate) {
            this.width = n < m ? n : m;
            this.height = m > n ? m : n;
        } else {
            this.width = n;
            this.height = m;
        }
        this.saveToCache = saveToCache;
        this.data = new int[this.height][this.width];
        this.result = BigInteger.ZERO;
    }

    boolean boardIsClean() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.data[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public BigInteger calculateMutations() {
        BigInteger value = Tetris.getCache(this.data);
//        if (value >= 0) {
        if (value != null) {
            return value;
        }
        if (this.boardIsClean()) {
            value = Tetris.getCache(this.height, this.width);
//            if (value > 0) {
            if (value != null) {
                return value;
            }
        }
        if (this.boardIsClean() && this.height >= 6 && this.width >= 4) {
            this.splitBoard();
        } else {
            this.nextPosition(this.findNextPosition());
        }

        if (this.boardIsClean()) {
            Tetris.setCache(this.height, this.width, this.result);
        } else {
            Tetris.setCache(this.data, this.result);
        }
        return this.result;
    }

    void splitBoard() {
        // Splits the board into two smaller boards
        // The number of combinations will be boardA * boardB + all combinations, where both are overlapping
        int splitPosition = this.height / 2;
        // check if both blocks contains a number of squares dividable by three
        while (true) {
            if (this.width % 3 == 0) {
                break;
            }
            if (splitPosition % 3 == 0 && ((this.height - splitPosition) % 3) == 0) {
                break;
            }
            splitPosition--;
        }
        Board boardA = new Board(splitPosition, this.width);
        BigInteger mutationsA = boardA.calculateMutations();
        Board boardB = new Board(this.height - splitPosition, this.width);
        BigInteger mutationsB = boardB.calculateMutations();

        // both together have a total number of combinations of multiplying them
        // and additionally, every combination that is possible by melting the borders of the blocks together
        Board overlapBoard = new OverlapBoard(this.height, this.width, splitPosition);
        BigInteger overlapMutations = overlapBoard.calculateMutations();
        this.result = mutationsA.multiply(mutationsB).add(overlapMutations);
    }

    void nextPosition(Integer[] position) {
        BigInteger cacheValue = Tetris.getCache(this.data);
//        if (cacheValue >= 0) {
        if (cacheValue != null) {
            this.result = this.result.add(cacheValue);
            return;
        }
        if (this.unsolvable()) {
            return;
        }
        int[] subRect = this.isRect();
        int longSide = subRect[0];
        int shortSide = subRect[1];
        BigInteger resultBefore = this.result;
        boolean saveToRectCache = false;
        if (longSide > 0) {
            // we might have already computed the number of mutations
            // for the sub rectangle
            cacheValue = Tetris.getCache(longSide, shortSide);
//            if (cacheValue > 0) {
            if (cacheValue != null) {
                this.result = this.result.add(cacheValue);
                return;
            }
            saveToRectCache = true;
        }
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = this.findValidOffsets(block, position);
            for (Integer[] offset : validOffsets) {
                this.placeBlockAt(block, offset);
//                this.print();
                Integer[] nextPos = this.findNextPosition();
                if (this.isFull()) {
                    // if the board is full we have found one solution
                    this.result = this.result.add(BigInteger.ONE);
                } else if (nextPos[0] != -1) {
                    this.nextPosition(nextPos);
                }

                this.removeBlockAt(block, offset);
            }
        }

        if (saveToRectCache) {
            Tetris.setCache(longSide, shortSide, this.result.subtract(resultBefore));
        }
        if (this.saveToCache) {
            Tetris.setCache(this.data, this.result.subtract(resultBefore));
        }
    }

    private boolean isFull() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.data[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean unsolvable() {
        // returns true if there are gaps which cant be filled (smaller than 3 tiles)
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.data[i][j] == 0) {
                    int neighbours = this.numberOfFreeNeighbours(new int[]{i, j});
                    if (neighbours > 1) {
                        continue;
                    } else if (neighbours == 1) {
                        int[] neighbourPosition = this.freeNeighbour(new int[]{i, j});
                        if (this.numberOfFreeNeighbours(neighbourPosition) > 1) {
                            continue;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    int numberOfFreeNeighbours(int[] position) {
        // returns the number of free tiles around a give position
        int x = position[0];
        int y = position[1];
        int n = 0;
        if (x > 0 && this.data[x - 1][y] == 0) {
            n++;
        }
        if (y > 0 && this.data[x][y - 1] == 0) {
            n++;
        }
        if (x < this.height - 1 && this.data[x + 1][y] == 0) {
            n++;
        }
        if (y < this.width - 1 && this.data[x][y + 1] == 0) {
            n++;
        }
        return n;
    }

    int[] freeNeighbour(int[] position) {
        // returns one free neighbour position
        int x = position[0];
        int y = position[1];
        int n = 0;
        if (x > 0 && this.data[x - 1][y] == 0) {
            return new int[]{x - 1, y};
        }
        if (y > 0 && this.data[x][y - 1] == 0) {
            return new int[]{x, y - 1};
        }
        if (x < this.height - 1 && this.data[x + 1][y] == 0) {
            return new int[]{x + 1, y};
        }
        if (y < this.width - 1 && this.data[x][y + 1] == 0) {
            return new int[]{x, y + 1};
        }
        return new int[]{-1, -1};
    }

    protected Integer[] findNextPosition() {
        // to make best use of the cache, we should try to find
        // a position for the next block which makes a rectangle
        // TODO: not implemented anymore, the used method was actually slower. Maybe try again another way
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.data[i][j] == 0) {
                    return new Integer[]{i, j};
                }
            }
        }
        return new Integer[]{-1, -1};
    }

    protected void placeBlockAt(Block block, Integer[] offset) {
//        Tetris.setBlocks++;
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (block.data[i][j] != 0) {
                    this.data[i + offset[0]][j + offset[1]] = block.data[i][j];
                }
            }
        }
    }

    protected void removeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (block.data[i][j] != 0) {
                    this.data[i + offset[0]][j + offset[1]] = 0;
                }
            }
        }
    }

    ArrayList<Integer[]> findValidOffsets(Block block, Integer[] pos) {
        ArrayList<Integer[]> validOffsets = new ArrayList<Integer[]>();
        for (int[] coordinate : block.coordinates) {
            Integer[] offset = new Integer[]{
                    pos[0] - coordinate[0],
                    pos[1] - coordinate[1]
            };
            if (this.blockPlaceableAt(block, offset)) {
                validOffsets.add(offset);
            }
        }
        return validOffsets;
    }

    private boolean blockPlaceableAt(Block block, Integer[] offset) {
        if (offset[0] < 0 || offset[1] < 0) {
            return false;
        }
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (i + offset[0] >= this.height) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] >= this.width) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (block.data[i][j] != 0 && this.data[i + offset[0]][j + offset[1]] != 0) {
                    // there is already a block; can't place this one
                    return false;
                }
            }
        }
        // there was no collision with the board; block can be placed
        return true;
    }

    int[] isRect() {
        // returns height and width if the remaining fields are ordered in rectangle with
        // a number of blocks that is a multiple of 3
        // this is important for caching
        // returns {0, 0} if it is not a rectangle
        int top = -1;
        int right = -1;
        int bottom = -1;
        int left = -1;
        boolean rectangleEnded = false;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.data[i][j] == 0) {
                    if (left == -1) {
                        // found the top left edge
                        left = j;
                        top = i;
                        continue;
                    }
                    if (j < left) {
                        // the block is empty, but it is left from our
                        // top left edge. This can't be a rectangle
                        return new int[]{0, 0};
                    }
                    if (right != -1 && j > right) {
                        // right from our right edge. Can't be rect.
                        return new int[]{0, 0};
                    }
                    if (rectangleEnded) {
                        // we already found the end of the rect.
                        // therefore there can't be another empty tile.
                        return new int[]{0, 0};
                    }
                } else {
                    // filled block
                    if (left != -1 && right == -1) {
                        // we already found the left edge, but this block is
                        // filled - so this is the right border
                        right = j - 1;
                        continue;
                    }
                    if (!rectangleEnded && j > left && j <= right) {
                        // this looks kinda like that:
                        // ########
                        // ##    ##
                        // ##  ####
                        // ########
                        // obviously not a rectangle
                        return new int[]{0, 0};
                    } else if (!rectangleEnded && j == left) {
                        // the rectangle ends here. Remember that.
                        rectangleEnded = true;
                        bottom = i - 1;
                    }
                }
            }
            if (left != -1 && right == -1) {
                // we jumped to the next line, found the left edge,
                // but not the right. This means the rectangle goes
                // to the edge of the board
                right = this.width - 1;
            }
        }
        if (left == -1 || right == -1) {
            return new int[]{0, 0};
        }
        if (bottom == -1) {
            bottom = this.height - 1;
        }
        int width = right - left + 1;
        int height = bottom - top + 1;
        if (height > width) {
            // it doesn't matter for calculation number of calculation if it is rotated or not
            // but always putting the larger coordinate on one side uses the cache more efficiently
            int tmp = width;
            width = height;
            height = tmp;
        }
//        I think it isn't possible to get a rect with a wrong size,
//        so skip this calculation
//        if (height * width % 3 == 0) {
//            return true;
//        }
        return new int[]{width, height};
    }

    void print() {
        Board.print(this.data);
    }

    public static void print(int[][] data) {
        if (!Tetris.debugPrint) {
            return;
        }
        System.out.println();
        for (int[] row : data) {
            System.out.println();
            for (int value : row) {
                System.out.format("\u001B[4%dm %d \u001B[0m", value, value);
            }
        }
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
