import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public static BoardData boardData;
    public static int height;
    public static int width;

    public Board(int m, int n) {
        if (m < 3 || n > m) {
            Board.height = n;
            Board.width = m;
        } else {
            Board.height = m;
            Board.width = n;
        }
        Board.boardData = new BoardData(height, width);
    }

    public BigInteger calculateMutations() {
        return calculateMutations(new Area(0, 0, width, height));
    }

    public BigInteger calculateMutations(Area area) {
        BigInteger result;
        Integer[] position;
        if (area.height > 3) {
            position = findNextPositionFromTop(area);
            if (position == null) {
                return area.isFull() ? BigInteger.ONE : BigInteger.ZERO;
            }
            area = new Area(area.x1, position[0], area.x2, area.y2);
            if (area.height <= 3) {
                position = findNextPositionFromRight(area);
                area = new Area(area.x1, area.y1, position[1] + 1, area.y2);
            }
        } else {
            position = findNextPositionFromRight(area);
            if (position == null) {
                return area.isFull() ? BigInteger.ONE : BigInteger.ZERO;
            }
            area = new Area(area.x1, area.y1, position[1] + 1, area.y2);
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
//                print(result, area);
                result = result.add(calculateMutations(area));
                removeBlockAt(block, offset);
//                print(result, area);
            }
        }
//        if (area.height == 7) {
//            print(result, area);
//        }
        Tetris.setCache(result, area);
        return result;
    }

    Integer[] findNextPositionFromTop(Area area) {
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                if (boardData.data[i][j] == 0) {
                    return new Integer[]{i, j};
                }
            }
        }
        return null;
    }

    Integer[] findNextPositionFromRight(Area area) {
        for (int i = area.x2 - 1; i >= area.x1; i--) {
            for (int j = area.y1; j < area.y2; j++) {
                if (boardData.data[j][i] == 0) {
                    return new Integer[]{j, i};
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
                    boardData.data[i + offset[0]][j + offset[1]] = block.data[i][j];
                }
            }
        }
    }

    protected void removeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (block.data[i][j] != 0) {
                    boardData.data[i + offset[0]][j + offset[1]] = 0;
                }
            }
        }
    }

    ArrayList<Integer[]> findValidOffsets(Block block, Integer[] pos, Area area) {
        ArrayList<Integer[]> validOffsets = new ArrayList<Integer[]>(block.coordinates.length);
        for (int[] coordinate : block.coordinates) {
            if (coordinate[0] == 2) {
                continue;
            }
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
                if (block.data[i][j] != 0 && boardData.data[i + offset[0]][j + offset[1]] != 0) {
                    // there is already a block; can't place this one
                    return false;
                }
            }
        }
        // there was no collision with the board / placed outside of area; block can be placed
        return true;
    }

    public static void print(BigInteger result, Area area) {
        print(result, boardData.data, area);
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
