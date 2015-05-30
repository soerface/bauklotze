import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Board {

    private int[][] data;
    private Block[] blocks;
    private HashSet<String> results;
    private int[][][] blocksCoordinates;

    public Board(int m, int n, Block[] blocks) {
        this.data = new int[m][n];
        this.blocks = blocks;
        this.results = new HashSet();
    }

    public long calculateMutations() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(0);
        HashSet<ArrayList<Integer>> positions = new HashSet<ArrayList<Integer>>();
        positions.add(list);
        this.nextPositions(positions);
        return this.results.size();
    }

    void nextPositions(HashSet<ArrayList<Integer>> positions) {
//        System.out.println("Positions:");
        for (ArrayList<Integer> pos : positions) {
//            System.out.format("(%d, %d)\n", pos.get(0), pos.get(1));
        }
        for (Block block : this.blocks) {
            HashSet<ArrayList<Integer>> validOffsets = new HashSet<ArrayList<Integer>>();
            for (ArrayList<Integer> pos : positions) {
                validOffsets.addAll(this.findValidOffsets(block, pos));
            }
            for (ArrayList<Integer> offset : validOffsets) {
                this.placeBlockAt(block, offset);
//                this.print();
//                System.out.println();
                HashSet<ArrayList<Integer>> nextPos = this.findNextPositions(block, offset);
                if (nextPos.size() > 0) {
                    this.nextPositions(nextPos);
                } else {
                    // could not find a free neighbour; if the board is full we have found one solution
                    if (this.isFull()) {
//                        this.print();
//                        System.out.format(" (Last Block at %d, %d)\n", offset.get(0), offset.get(1));
                        this.results.add(Arrays.deepToString(this.data));
                    }
                }
                this.removeBlockAt(block, offset);
            }
        }
    }

    private boolean isFull() {
        for (int[] row : this.data) {
            for (int value : row) {
                if (value == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private HashSet<ArrayList<Integer>> findNextPositions(Block block, ArrayList<Integer> offset) {
        // HashSet go and fuck yourself
        // http://stackoverflow.com/q/16657905/458274
        //HashSet<int[]> nextPositions = new HashSet<int[]>();
        HashSet<ArrayList<Integer>> nextPositions = new HashSet<ArrayList<Integer>>();
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                if (this.data[i][j] == 0) {
                    ArrayList<Integer> pos = new ArrayList<Integer>();
                    pos.add(i);
                    pos.add(j);
                    nextPositions.add(pos);
                    return nextPositions;
                }
            }
        }
        return nextPositions;
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

    HashSet<ArrayList<Integer>> findValidOffsets(Block block, ArrayList<Integer> pos) {
        HashSet<ArrayList<Integer>> validOffsets = new HashSet<ArrayList<Integer>>();
        for (int[] coordinate : block.coordinates) {
            ArrayList<Integer> offset = new ArrayList<Integer>();
            offset.add(pos.get(0) - coordinate[0]);
            offset.add(pos.get(1) - coordinate[1]);
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

    void print() {
        for (int[] row : this.data) {
            System.out.println();
            for (int value : row) {
                System.out.format("\u001B[4%dm %d \u001B[0m", value, value);
            }
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
