import java.util.ArrayList;
import java.util.HashSet;

public class Board {

    private int[][] data;
    private Block[] blocks;
    public int result;
    private int[][][] blocksCoordinates;

    public Board(int m, int n, Block[] blocks) {
        this.data = new int[m][n];
        this.blocks = blocks;
        this.result = 0;
    }

    public long calculateMutations() {
        this.nextPosition(new int[]{0, 0});
        return this.result;
    }

    void nextPosition(int[] position) {
        for (Block block : this.blocks) {
            HashSet<ArrayList<Integer>> validOffsets = this.findValidOffsets(block, position);
            for (ArrayList<Integer> offset : validOffsets) {
                this.placeBlockAt(block, offset);
                this.print();
                int[] nextPos = this.findNextPosition(block, offset);
                if (nextPos[0] == -1) {
                    // no free position; if the board is full we have found one solution
                    this.result++;
                } else if (this.stillSolveable()) {
                    this.nextPosition(nextPos);
                }
                this.removeBlockAt(block, offset);
            }
        }
    }

    private int[] findNextPosition(Block block, ArrayList<Integer> offset) {
        // HashSet go and fuck yourself
        // http://stackoverflow.com/q/16657905/458274
        //HashSet<int[]> nextPosition = new HashSet<int[]>();
        HashSet<ArrayList<Integer>> nextPositions = new HashSet<ArrayList<Integer>>();
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                if (this.data[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private void placeBlockAt(Block block, ArrayList<Integer> offset) {
        for (int i = 0; i < block.data.length; i++) {
            for (int j = 0; j < block.data[i].length; j++) {
                if (block.data[i][j] != 0) {
                    this.data[i + offset.get(0)][j + offset.get(1)] = block.data[i][j];
                }
            }
        }
    }

    private void removeBlockAt(Block block, ArrayList<Integer> offset) {
        for (int i = 0; i < block.data.length; i++) {
            for (int j = 0; j < block.data[i].length; j++) {
                if (block.data[i][j] != 0) {
                    this.data[i + offset.get(0)][j + offset.get(1)] = 0;
                }
            }
        }
    }

    HashSet<ArrayList<Integer>> findValidOffsets(Block block, int[] pos) {
        HashSet<ArrayList<Integer>> validOffsets = new HashSet<ArrayList<Integer>>();
        for (int[] coordinate : block.coordinates) {
            ArrayList<Integer> offset = new ArrayList<Integer>();
            offset.add(pos[0] - coordinate[0]);
            offset.add(pos[1] - coordinate[1]);
            if (this.blockPlaceableAt(block, offset)) {
                //System.out.format("%d %d\n", offset[0], offset[1]);
                validOffsets.add(offset);
            }
        }
        return validOffsets;
    }

    private boolean blockPlaceableAt(Block block, ArrayList<Integer> offset) {
        if (offset.get(0) < 0 || offset.get(1) < 0) {
            return false;
        }
        for (int i = 0; i < block.data.length; i++) {
            for (int j = 0; j < block.data[i].length; j++) {
                if (i + offset.get(0) >= this.data.length) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset.get(1) >= this.data[i].length) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (block.data[i][j] != 0 && this.data[i + offset.get(0)][j + offset.get(1)] != 0) {
                    // there is already a block; can't place this one
                    return false;
                }
            }
        }
        // there was no collision with the board; block can be placed
        return true;
    }

    boolean stillSolveable() {
        return true;
    }

    void print() {
//        for (int[] row : this.data) {
//            for (int value : row) {
//                System.out.format("\u001B[4%dm %d \u001B[0m", value, value);
//            }
//            System.out.println();
//        }
//        System.out.println();
//        try {
//            Thread.sleep(80);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
