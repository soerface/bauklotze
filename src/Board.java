import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public static int[][] data;
    public static int height;
    public static int width;

    public Board(int m, int n) {
        if (m % 3 == 0) {
            Board.height = m;
            Board.width = n;
        } else {
            Board.height = n;
            Board.width = m;
        }
    }

    public BigInteger calculateMutations() {
        Board.data = new int[height][width];
        return calculateMutations(new Area(0, 0, width, height));
    }

    public BigInteger calculateMutations(Area area) {
        if (area.height == 3) {
            return calculateStripeMutations(area);
        }
        BigInteger result;
        Area bottomStripe = new Area(area.x1, area.y2-3, area.x2, area.y2);
        Area remainingArea = new Area(area.x1, area.y1, area.x2, area.y2-3);
        result = calculateStripeMutations(bottomStripe).multiply(calculateMutations(remainingArea));
        result = result.add(calculateOverlapMutations(area));
        return result;
    }

    BigInteger calculateStripeMutations(Area area) {
        BigInteger result;
        Integer[] position = findNextPositionFromLeft(area);
        if (position == null) {
            return area.isFull() ? BigInteger.ONE : BigInteger.ZERO;
        }
        if (position[0] == area.y1) {
            area = new Area(position[1], area.y1, area.x2, area.y2);
        }
        result = Tetris.getCache(area);
        if (result != null) {
            return result;
        }
        result = BigInteger.ZERO;
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position, area);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                print(result, area);
                result = result.add(calculateStripeMutations(area));
                removeBlockAt(block, offset);
                print(result, area);
            }
        }
        Tetris.setCache(result, area);
        return result;
    }

    BigInteger calculateOverlapMutations(Area area) {
        BigInteger result = BigInteger.ZERO;
        Area bottomStripe = new Area(area.x1, area.y2-3, area.x2, area.y2);
        Integer[] position = findNextPositionFromBottom(bottomStripe);
        if (position == null) {
            return BigInteger.ZERO;
        }
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position, area);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                print(result, area);
                result = result.add(calculateOverlapMutations(area));
                removeBlockAt(block, offset);
                print(result, area);
            }
        }
        return result;
    }

    Integer[] findNextPositionFromLeft(Area area) {
        for (int i = area.x1; i < area.x2; i++) {
            for (int j = area.y1; j < area.y2; j++) {
                if (data[j][i] == 0) {
                    return new Integer[]{j, i};
                }
            }
        }
        return null;
    }

    Integer[] findNextPositionFromBottom(Area area) {
        for (int i = area.y2-1; i >= area.y1; i--) {
            for (int j = area.x1; j < area.x2; j++) {
                if (data[i][j] == 0) {
                    return new Integer[]{i, j};
                }
            }
        }
        return null;
    }

    protected void placeBlockAt(Block block, Integer[] offset) {
        Tetris.setBlocks++;
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (block.data[i][j] != 0) {
                    data[i + offset[0]][j + offset[1]] = block.data[i][j];
                }
            }
        }
    }

    protected void removeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (block.data[i][j] != 0) {
                    data[i + offset[0]][j + offset[1]] = 0;
                }
            }
        }
    }

    ArrayList<Integer[]> findValidOffsets(Block block, Integer[] pos, Area area) {
        ArrayList<Integer[]> validOffsets = new ArrayList<Integer[]>();
        for (int[] coordinate : block.coordinates) {
            Integer[] offset = new Integer[]{
                    pos[0] - coordinate[0],
                    pos[1] - coordinate[1]
            };
            if (blockPlaceableAt(block, offset, area)) {
                validOffsets.add(offset);
            }
        }
        return validOffsets;
    }

    private boolean blockPlaceableAt(Block block, Integer[] offset, Area area) {
        if (offset[0] < 0 || offset[1] < 0) {
            return false;
        }
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (i + offset[0] >= area.y2) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (i + offset[0] < area.y1) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] >= area.x2) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] < area.x1) {
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
        // there was no collision with the board / placed outside of area; block can be placed
        return true;
    }

    public static void print(BigInteger result, Area area) {
        print(result, data, area);
    }

    public static void print(BigInteger result, int[][] data, Area area) {
        if (!Tetris.debugPrint) {
            return;
        }
        System.out.println();
        for (int i = 0; i < Math.max(height, area.height); i++) {
            for (int j = 0; j < Math.max(width, area.width); j++) {
                int value = data[i][j];
                String fontColor = "\u001B[30m"; // black font
                String content = String.format(" %d ", value);
                int color = 4; // dark bg colors
                if (i >= area.y1 && i < area.y2 && j >= area.x1 && j < area.x2) {
                    fontColor = "";
//                    if (value != 0) {
//                        color = 10; // bright bg colors
//                    }
                }
                System.out.format("\u001B[%d%dm%s%s\u001B[0m", color, value, fontColor, content);
            }
            System.out.println();
        }
        System.out.format("Solutions: %15d SetBlocks: %10d\n", result, Tetris.setBlocks);
        try {
            Thread.sleep(Tetris.printDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
