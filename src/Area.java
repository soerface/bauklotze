public class Area {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    public final int width;
    public final int height;
    public final int size;

    public Area(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        width = x2 - x1;
        height = y2 - y1;
        size = width * height;
    }

    public boolean isFull() {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if (!Board.boardData.get(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if (Board.boardData.get(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int freeBlocks() {
        int n = 0;
        for (int i = y1; i < y2; i++) {
            int row = Board.boardData.get(i);
            // this magic comes from http://stackoverflow.com/a/109025/458274
            row = row - ((row >>> 1) & 0x55555555);
            row = (row & 0x33333333) + ((row >>> 2) & 0x33333333);
            n += (((row + (row >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
        }
        return n;
    }

}
