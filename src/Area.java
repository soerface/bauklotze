public class Area {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    public final int width;
    public final int height;

    public Area(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        width = x2 - x1;
        height = y2 - y1;
    }

    public boolean solvable() {
        if (freeBlocks() % 3 != 0) {
            return false;
        }
        for (int i = y1; i < y2; i++) {
            for (int j = x1; j < x2; j++) {
                if (Board.data[i][j] == 0) {
                    int neighbours = numberOfFreeNeighbours(new int[]{i, j});
                    if (neighbours > 1) {
                        continue;
                    } else if (neighbours == 1) {
                        int[] neighbourPosition = freeNeighbour(new int[]{i, j});
                        if (numberOfFreeNeighbours(neighbourPosition) > 1) {
                            continue;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    int numberOfFreeNeighbours(int[] position) {
        // returns the number of free tiles around a give position
        int y = position[0];
        int x = position[1];
        int n = 0;
        if (y > y1 && Board.data[y - 1][x] == 0) {
            n++;
        }
        if (x > x1 && Board.data[y][x - 1] == 0) {
            n++;
        }
        if (y < y2 - 1 && Board.data[y + 1][x] == 0) {
            n++;
        }
        if (x < x2 - 1 && Board.data[y][x + 1] == 0) {
            n++;
        }
        return n;
    }

    int[] freeNeighbour(int[] position) {
        // returns one free neighbour position
        int y = position[0];
        int x = position[1];
        if (y > y1 && Board.data[y - 1][x] == 0) {
            return new int[]{y - 1, x};
        }
        if (x > x1 && Board.data[y][x - 1] == 0) {
            return new int[]{y, x - 1};
        }
        if (y < y2 - 1 && Board.data[y + 1][x] == 0) {
            return new int[]{y + 1, x};
        }
        if (x < x2 - 1 && Board.data[y][x + 1] == 0) {
            return new int[]{y, x + 1};
        }
        return new int[]{-1, -1};
    }

    public int freeBlocks() {
        int freeBlocks = 0;
        for (int i = y1; i < y2; i++) {
            for (int j = x1; j < x2; j++) {
                if (Board.data[i][j] == 0) {
                    freeBlocks++;
                }
            }
        }
        return freeBlocks;
    }

    @Override
    public String toString() {
        return String.format("%d / %d  %d / %d", x1, y1, x2, y2);
    }

    public static Area reducedArea(Area area) {
        /* return an area which is shrinked to a rect containing only the empty blocks
         * example:
         * _________
         * |########|      ____
         * |### ####|     |# ##|
         * |##    ##|  -->|    |
         * |#### ###|     |## #|
         * |########|     -----
         * --------
         **/
        int x1 = -1;
        int x2 = -1;
        int y1 = -1;
        int y2 = -1;
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                if (Board.data[i][j] == 0) {
                    if (x1 == -1 || j < x1) {
                        x1 = j;
                    }
                    if (y1 == -1 || i < y1) {
                        y1 = i;
                    }
                    y2 = i + 1;
                    x2 = j + 1 > x2 ? j + 1 : x2;
                }
            }
        }
        if (x1 == -1) { // or any other, doesn't matter
            return new Area(0, 0, 0, 0);
        }
        return new Area(x1, y1, x2, y2);
    }
}
