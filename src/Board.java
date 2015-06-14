import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public int[][] data;
    int height;
    int width;

    public Board(int m, int n) {
        height = m > n ? m : n;
        width = n < m ? n : m;
        data = new int[height][width];
    }

    public BigInteger calculateMutations() {
        Area area = new Area(0, 0, height, width);
        return processNextPosition(findNextPosition(area), area);
    }

    BigInteger processNextPosition(Integer[] position, Area area) {
        BigInteger result = BigInteger.ZERO;
        if (position == null) {
            if (isFull(area)) {
                return BigInteger.ONE;
            }
            return BigInteger.ZERO;
        }
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                print(result);
                if (isFull(area)) {
                    result = result.add(BigInteger.ONE);
                } else {
                    Integer[] nextPosition = findNextPosition(area);
                    if (nextPosition == null) {
                        Area[] areas = shrinkArea(area);
                        BigInteger resultA = processNextPosition(findNextPosition(areas[0]), areas[0]);
                        BigInteger resultB = processNextPosition(findNextPosition(areas[1]), areas[1]);
//                        System.out.format("a: %d b: %d", resultA, resultB);
                        result = result.add(resultA.multiply(resultB));
                    } else {
                        result = result.add(processNextPosition(nextPosition, area));
                    }
                }
                removeBlockAt(block, offset);
                print(result);
            }
        }
        return result;
    }

    private Area[] shrinkArea(Area area) {
        Area[] areas = new Area[2];
        if (area.height > area.width) {
            areas[0] = new Area(area.x1, area.y1, area.x2, area.y1 + area.height / 2);
            areas[1] = new Area(area.x1, area.y1 + area.height / 2, area.x2, area.y2);
        } else {
            areas[0] = new Area(area.x1, area.y1, area.x1 + area.width / 2, area.y2);
            areas[1] = new Area(area.x1 + area.width / 2, area.y1, area.x2, area.y2);
        }
        return areas;
    }

    protected Integer[] findNextPosition(Area area) {
        if (area.width > 6 || area.height > 6) {
            if (area.height > area.width) {
                int j = area.y1 + area.height / 2;
                for (int i = area.x1; i < area.x2; i++) {
                    if (data[i][j] == 0) {
                        return new Integer[]{i, j};
                    }
                }
            } else {
                int j = area.x1 + area.width / 2;
                for (int i = area.y1; i < area.y2; i++) {
                    if (data[j][i] == 0) {
                        return new Integer[]{j, i};
                    }
                }
            }
        } else {
            for (int i = area.x1; i < area.x2; i++) {
                for (int j = area.y1; j < area.y2; j++) {
                    if (data[i][j] == 0) {
                        return new Integer[]{i, j};
                    }
                }
            }
        }
        return null;
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

    private boolean isFull(Area area) {
        for (int i = area.x1; i < area.x2; i++) {
            for (int j = area.y1; j < area.y2; j++) {
                if (data[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    void print(BigInteger result) {
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
