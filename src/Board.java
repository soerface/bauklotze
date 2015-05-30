import java.util.ArrayList;

public class Board {

    private int[][] data;
    private Block[] blocks;
    public int result;
    private int[][][] blocksCoordinates;
    private int[][] cache;

    public Board(int m, int n, Block[] blocks) {
        this.data = new int[m][n];
        this.cache = new int[m > n ? m : n][m > n ? n : m];
        this.blocks = blocks;
        this.result = 0;
    }

    public long calculateMutations() {
        this.nextPosition(new int[]{0, 0});
        return this.result;
    }

    void nextPosition(int[] position) {
        for (Block block : this.blocks) {
            ArrayList<Integer[]> validOffsets = this.findValidOffsets(block, position);
            for (Integer[] offset : validOffsets) {
                this.placeBlockAt(block, offset);
//                this.print();
                int[] subRect = this.isRect();
                int resultBefore = 0;
                boolean saveToCache = false;
                if (subRect[0] > 0) {
                    // we might have already computed the number of mutations
                    // for the sub rectangle
                    int value = this.cache[subRect[0]][subRect[1]];
                    if (value > 0) {
                        this.result += value;
                        this.removeBlockAt(block, offset);
                        continue;
                    } else {
                        resultBefore = this.result;
                        saveToCache = true;
                    }
                }
                int[] nextPos = this.findNextPosition(block);
                if (nextPos[0] == -1) {
                    // no free position; if the board is full we have found one solution
                    this.result++;
                } else {
                    this.nextPosition(nextPos);
                }
                this.removeBlockAt(block, offset);
                if (saveToCache) {
                    this.cache[subRect[0]][subRect[1]] = this.result - resultBefore;
//                    System.out.format("Saving to cache: %d, %d = %d\n", subRect[0] + 1, subRect[1] + 1, this.result - resultBefore);
                }
            }
        }
    }

    private int[] findNextPosition(Block block) {
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                if (this.data[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private void placeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.data.length; i++) {
            for (int j = 0; j < block.data[i].length; j++) {
                if (block.data[i][j] != 0) {
                    this.data[i + offset[0]][j + offset[1]] = block.data[i][j];
                }
            }
        }
    }

    private void removeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.data.length; i++) {
            for (int j = 0; j < block.data[i].length; j++) {
                if (block.data[i][j] != 0) {
                    this.data[i + offset[0]][j + offset[1]] = 0;
                }
            }
        }
    }

    ArrayList<Integer[]> findValidOffsets(Block block, int[] pos) {
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
        for (int i = 0; i < block.data.length; i++) {
            for (int j = 0; j < block.data[i].length; j++) {
                if (i + offset[0] >= this.data.length) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] >= this.data[i].length) {
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
        // returns width and height if the remaining fields are ordered in rectangle with
        // a number of blocks that is a multiple of 3
        // this is important for caching
        // returns {0, 0} if it is not a rectangle
        int top = -1;
        int right = -1;
        int bottom = -1;
        int left = -1;
        boolean rectangleEnded = false;
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
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
                        return new int[] {0, 0};
                    }
                    if (right != -1 && j > right) {
                        // right from our right edge. Can't be rect.
                        return new int[] {0, 0};
                    }
                    if (rectangleEnded) {
                        // we already found the end of the rect.
                        // therefore there can't be another empty tile.
                        return new int[] {0, 0};
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
                        return new int[] {0, 0};
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
                right = this.data[i].length - 1;
            }
        }
        if (left == -1 || right == -1) {
            return new int[] {0, 0};
        }
        bottom = this.data.length - 1;
        int width = right - left;
        int height = bottom - top;
        if (height > width) {
            // it doesn't matter for calculation number of calculation if it is rotated or not
            // but always putting the larger coordinate on one side uses the cache more efficiently
            int tmp = width;
            width = height;
            height = tmp;
        }
//        I think it isn't possible to get a rect with a wrong size,
//        so skip this calculation
//        if (width * height % 3 == 0) {
//            return true;
//        }
        return new int[] {width, height};
    }

    void print() {
        System.out.println();
        for (int[] row : this.data) {
            System.out.println();
            for (int value : row) {
                System.out.format("\u001B[4%dm %d \u001B[0m", value, value);
            }
        }
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
