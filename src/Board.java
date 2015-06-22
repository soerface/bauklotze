import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public static int[][] data;
    public static int height;
    public static int width;

    public Board(int m, int n) {
        Board.height = m > n ? m : n;
        Board.width = n < m ? n : m;

    }

    public BigInteger calculateMutations() {
        Board.data = new int[height][width];
        Area area = new Area(0, 0, width, height);
        return calculateMutations(area);
    }

    BigInteger calculateMutations(Area area) {
        BigInteger result;
        if (!area.solvable()) {
            return BigInteger.ZERO;
        }
        BigInteger cacheValue = Tetris.getCache(area);
        if (cacheValue != null) {
            return cacheValue;
        }
        Tetris.getCachesNull++;

        boolean splitOnXAxis = area.width < area.height;
        int splitPosition = findSplitPosition(area, splitOnXAxis);
        Area[] areas = divideArea(area, splitOnXAxis, splitPosition);
        Area firstArea = areas[0];
        Area secondArea = areas[1];
        if (firstArea.freeBlocks() != 0 && secondArea.freeBlocks() != 0) {
            BigInteger firstAreaResult = calculateMutations(firstArea);
            BigInteger secondAreaResult = calculateMutations(secondArea);
            result = firstAreaResult.multiply(secondAreaResult);
            int firstAreaFreeBlocks = firstArea.freeBlocks();
            int secondAreaFreeBlocks = secondArea.freeBlocks();
            result = result.add(calculateOverlapMutations(area, firstArea, firstAreaFreeBlocks, secondArea, secondAreaFreeBlocks, splitOnXAxis, splitPosition));
        } else {
            // no split up possible, calculate it ordinary
            result = calculateSimpleMutations(area);
        }
        Tetris.setCache(result, area);
        print(result, area);
        return result;
    }

    private BigInteger calculateSimpleMutations(Area area) {
        Integer[] position = findNextPosition(area);
        if (position == null) {
            return area.isFull() ? BigInteger.ONE : BigInteger.ZERO;
        }
        BigInteger result = BigInteger.ZERO;
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position, area);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                result = result.add(calculateSimpleMutations(area));
                removeBlockAt(block, offset);
            }
        }
        return result;
    }

    private BigInteger calculateOverlapMutations(Area area, Area firstArea, int firstAreaFreeBlocks, Area secondArea, int secondAreaFreeBlocks, boolean splitOnXAxis, int splitPosition) {
        BigInteger result = BigInteger.ZERO;
        Integer[] position = findNextPosition(area, splitOnXAxis, splitPosition);
        if (position == null) {
//            System.out.println("pos is null!");
            if (area.isFull()) {
                return BigInteger.ONE;
            } else {
                return BigInteger.ZERO;
            }
//            return calculateMutations(firstArea).multiply(calculateMutations(secondArea));
        }
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position, area);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                print(result, area);
                if (area.isFull()) {
                    result = result.add(BigInteger.ONE);
                } else {
                    Integer[] nextPosition = findNextPosition(area, splitOnXAxis, splitPosition);
                    if (nextPosition == null) {
                        if (firstArea.freeBlocks() == firstAreaFreeBlocks || secondArea.freeBlocks() == secondAreaFreeBlocks) {
//                            System.out.format("not an overlap (%d vs %d | %d vs %d)\n", firstArea.freeBlocks(), firstAreaFreeBlocks, secondArea.freeBlocks(), secondAreaFreeBlocks);
//                            print(new BigInteger("42"), firstArea);
//                            print(new BigInteger("42"), secondArea);
                            // this is not a combination where top and bottom are overlapping
                        } else {
//                            System.out.format("overlap (%d vs %d | %d vs %d)\n", firstArea.freeBlocks(), firstAreaFreeBlocks, secondArea.freeBlocks(), secondAreaFreeBlocks);
                            BigInteger firstAreaResult = firstArea.isFull() ? BigInteger.ONE : calculateMutations(firstArea);
                            BigInteger secondAreaResult = secondArea.isFull() ? BigInteger.ONE : calculateMutations(secondArea);
                            result = result.add(firstAreaResult.multiply(secondAreaResult));
//                            print(firstAreaResult.multiply(secondAreaResult), area);
                        }
                    } else {
                        result = result.add(calculateOverlapMutations(area, firstArea, firstAreaFreeBlocks, secondArea, secondAreaFreeBlocks, splitOnXAxis, splitPosition));
                    }
                }
                removeBlockAt(block, offset);
                print(result, area);
            }
        }
        return result;
    }

    private Area[] divideArea(Area area, boolean splitOnXAxis, int splitPosition) {
        Area[] areas = new Area[2];
        if (splitOnXAxis) {
            areas[0] = new Area(area.x1, area.y1, area.x2, splitPosition);
            areas[1] = new Area(area.x1, splitPosition, area.x2, area.y2);
        } else {
            areas[0] = new Area(area.x1, area.y1, splitPosition, area.y2);
            areas[1] = new Area(splitPosition, area.y1, area.x2, area.y2);
        }
        return areas;
    }

    private int findSplitPosition(Area area, boolean splitOnXAxis) {
        Area[] areas = new Area[2];
        int offset = 0;
        while (true) {
            if (splitOnXAxis) {
                areas[0] = new Area(area.x1, area.y1, area.x2, area.y1 + area.height / 2 + offset);
                areas[1] = new Area(area.x1, area.y1 + area.height / 2 + offset, area.x2, area.y2);
            } else {
                areas[0] = new Area(area.x1, area.y1, area.x1 + area.width / 2 + offset, area.y2);
                areas[1] = new Area(area.x1 + area.width / 2 + offset, area.y1, area.x2, area.y2);
            }
            if (!(areas[0].size() % 3 == 0 && areas[1].size() % 3 == 0)) {
                switch (offset) {
                    case 0:
                        offset = 1;
                        break;
                    case 1:
                        offset = -1;
                }
            } else {
                break;
            }
        }
        if (splitOnXAxis) {
            return area.y1 + area.height / 2 + offset;
        } else {
            return area.x1 + area.width / 2 + offset;
        }
    }

    Integer[] findNextPosition(Area area) {
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                if (data[i][j] == 0) {
                    return new Integer[]{i, j};
                }
            }
        }
        return null;
    }

    protected Integer[] findNextPosition(Area area, boolean splitOnXAxis, int splitPosition) {
        if (splitOnXAxis) {
            for (int i = area.x1; i < area.x2; i++) {
                if (data[splitPosition][i] == 0) {
                    return new Integer[]{splitPosition, i};
                }
            }
        } else {
            for (int i = area.y1; i < area.y2; i++) {
                if (data[i][splitPosition] == 0) {
                    return new Integer[]{i, splitPosition};
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

    void printArea(BigInteger result, Area area) {
        if (!Tetris.debugPrint) {
            return;
        }
        System.out.println();
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                int value = data[i][j];
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
        System.out.format("Solutions: %d\n", result);
        try {
            Thread.sleep(Tetris.printDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
