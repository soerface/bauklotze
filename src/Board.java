import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public int[][] data;
    public BigInteger result;
    int height;
    int width;

    public Board(int m, int n) {
        width = n;
        height = m;
        data = new int[height][width];
        result = BigInteger.ZERO;
    }

    public BigInteger calculateMutations() {
        nextPosition(findNextPosition());
        return result;
    }

    void nextPosition(Integer[] position) {
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                print();
                if (!isFull()) {
                    nextPosition(findNextPosition());
                } else {
                    result = result.add(BigInteger.ONE);
                }
                removeBlockAt(block, offset);
                print();
            }
        }
    }

    protected Integer[] findNextPosition() {
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

    void print() {
        Board.print(data, result);
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
