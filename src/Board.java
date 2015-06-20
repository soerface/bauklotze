import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public int[][] data;
    int height;
    int width;
    boolean saveToCache;

    public Board(int m, int n) {
        this(m, n, true, true);
    }

    public Board(int m, int n, boolean allowRotate, boolean saveToCache) {
        if (allowRotate) {
            width = n < m ? n : m;
            height = m > n ? m : n;
        } else {
            width = n;
            height = m;
        }
//        hasBeenRotated = height != m && width != n;
        this.saveToCache = saveToCache;
        data = new int[height][width];
    }


    boolean isClean() {
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
        BigInteger result;
        // assignment to not call it multiple times inside this method
        boolean isClean = isClean();
        result = isClean ? Tetris.getCache(height, width) : Tetris.getCache(data);
        if (result != null) {
            return result;
        }

        if (isClean && height >= 6 && width >= 4) {
            result = splitBoard();
        } else {
            result = nextPosition(findNextPosition());
        }
        if (isClean) {
            Tetris.setCache(height, width, result);
        } else {
            Tetris.setCache(data, result);
        }
        return result;
    }

    BigInteger splitBoard() {
//        Board.allBoards.remove(this);
        // Splits the board into two smaller boards
        // The number of combinations will be boardA * boardB + all combinations, where both are overlapping
        int splitPosition = height / 2;
        // check if both blocks contains a number of squares dividable by three
        // TODO: may need better check for prefilled boards
        while (true) {
            if (width % 3 == 0) {
                break;
            }
            if (splitPosition % 3 == 0 && ((height - splitPosition) % 3) == 0) {
                break;
            }
            splitPosition--;
        }
        Board boardA = new Board(splitPosition, width);
        Board boardB = new Board(height - splitPosition, width);
        // both together have a total number of combinations of multiplying them
        // and additionally, every combination that is possible by melting the borders of the blocks together
        OverlapBoard overlapBoard = new OverlapBoard(height, width, splitPosition);
        BigInteger mutationsA = boardA.calculateMutations();
        BigInteger mutationsB = boardB.calculateMutations();

        BigInteger overlapMutations = overlapBoard.calculateMutations();
        return mutationsA.multiply(mutationsB).add(overlapMutations);
    }

    BigInteger nextPosition(Integer[] position) {
        BigInteger result = Tetris.getCache(data);
        if (result != null) {
            return result;
        }
        if (unsolvable()) {
            return BigInteger.ZERO;
        }
        int[] subRect = isRect();
        int longSide = subRect[0];
        int shortSide = subRect[1];
        boolean saveToRectCache = false;
        if (longSide > 0) {
            // we might have already computed the number of mutations
            // for the sub rectangle
            result = Tetris.getCache(longSide, shortSide);
            if (result != null) {
                return result;
            } else if (height >= 6 && width >= 4) {
                Board subBoard = new Board(longSide, shortSide);
                return subBoard.calculateMutations();
            }
            saveToRectCache = true;
        }
        result = BigInteger.ZERO;
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                Integer[] nextPos = findNextPosition();
                if (isFull()) {
                    // if the board is full we have found one solution
                    result = result.add(BigInteger.ONE);
                } else if (nextPos[0] != -1) {
                    result = result.add(nextPosition(nextPos));
                }
                removeBlockAt(block, offset);
            }
        }

        if (saveToRectCache) {
            Tetris.setCache(longSide, shortSide, result);
        }
        if (saveToCache) {
            Tetris.setCache(data, result);
        }
        return result;
    }

    private boolean isFull() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (data[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean unsolvable() {
        // returns true if there are gaps which cant be filled (smaller than 3 tiles)
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (data[i][j] == 0) {
                    int neighbours = numberOfFreeNeighbours(new int[]{i, j});
                    if (neighbours > 1) {
                        continue;
                    } else if (neighbours == 1) {
                        int[] neighbourPosition = this.freeNeighbour(new int[]{i, j});
                        if (numberOfFreeNeighbours(neighbourPosition) > 1) {
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
        if (x > 0 && data[x - 1][y] == 0) {
            n++;
        }
        if (y > 0 && data[x][y - 1] == 0) {
            n++;
        }
        if (x < height - 1 && data[x + 1][y] == 0) {
            n++;
        }
        if (y < width - 1 && data[x][y + 1] == 0) {
            n++;
        }
        return n;
    }

    int[] freeNeighbour(int[] position) {
        // returns one free neighbour position
        int x = position[0];
        int y = position[1];
        int n = 0;
        if (x > 0 && data[x - 1][y] == 0) {
            return new int[]{x - 1, y};
        }
        if (y > 0 && data[x][y - 1] == 0) {
            return new int[]{x, y - 1};
        }
        if (x < height - 1 && data[x + 1][y] == 0) {
            return new int[]{x + 1, y};
        }
        if (y < width - 1 && data[x][y + 1] == 0) {
            return new int[]{x, y + 1};
        }
        return new int[]{-1, -1};
    }

    protected Integer[] findNextPosition() {
        // to make best use of the cache, we should try to find
        // a position for the next block which makes a rectangle
        // TODO: not implemented anymore, the used method was actually slower. Maybe try again another way
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (data[i][j] == 0) {
                    return new Integer[]{i, j};
                }
            }
        }
        return new Integer[]{-1, -1};
    }

    protected void placeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (block.data[i][j] != 0) {
                    data[i + offset[0]][j + offset[1]] = block.data[i][j];
                }
            }
        }
    }

    protected void removeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (block.data[i][j] != 0) {
                    data[i + offset[0]][j + offset[1]] = 0;
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
            if (blockPlaceableAt(block, offset)) {
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
                if (i + offset[0] >= height) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] >= width) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (block.data[i][j] != 0 && data[i + offset[0]][j + offset[1]] != 0) {
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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (data[i][j] == 0) {
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
            bottom = height - 1;
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
        Board.print(data, BigInteger.ZERO);
    }

    public static void print(int[][] data, BigInteger result) {
        if (!Tetris.debugPrint) {
            return;
        }
        System.out.println();
        for (int[] row : data) {
            for (int value : row) {
                System.out.format("\u001B[4%dm %d \u001B[0m", value, value);
            }
            System.out.println();
        }
        System.out.format("Solutions: %d\n", result);
        try {
            Thread.sleep(Tetris.printDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
