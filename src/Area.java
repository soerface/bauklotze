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
        return freeBlocks() % 3 == 0;
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
         return area;
    }
}
