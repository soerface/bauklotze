public class BoardData {
    private static int hashStartPosition;
    private long data[];
    public final int height;
    public final int width;
    public final int size;
    public Area area;

    public BoardData(int height, int width) {
        this.height = height;
        this.width = width;
        size = width * height;
        this.area = new Area(0, 0, width, height);
        data = new long[height];
        int pos = 0;
        while (height >> pos != 0) {
            pos++;
        }
        hashStartPosition = pos;
    }

    public BoardData(BoardData boardData) {
        this.height = boardData.height;
        this.width = boardData.width;
        size = width * height;
        this.area = boardData.area;
        this.data = new long[height];
        System.arraycopy(boardData.data, area.y1, data, area.y1, area.y2 - area.y1);
    }

    public boolean get(int y, int x) {
        return (data[y] & 1 << x) != 0;
    }

    public void set(int y, int x, boolean value) {
        if (value) {
            data[y] |= 1 << x;
        } else {
            data[y] &= ~(1 << x);
        }
    }

    public void mirrorData() {
        long[] newData = new long[height];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[i] |= ((data[i] & 1 << j) >> j) << (area.x2 - (j - area.x1) - 1);
                // For better reading:
                // long bit = (data[i] & 1 << j) >> j;
                // newData[i] |= bit << (area.x2 - (j - area.x1) - 1);
            }
        }
        data = newData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardData boardData = (BoardData) o;

        if (area.height != boardData.area.height) return false;
        if (area.width != boardData.area.width) return false;
        for (int i = area.y1; i < area.y2; i++) {
            if (data[i] != boardData.data[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int key = area.height > 3 ? area.height : area.width;
        int pos = hashStartPosition;
        int iStart = area.y1 + 2;
        if (area.y2 - 1 < iStart) {
            iStart = area.y2 - 1;
        }
        int j;
        long start = System.currentTimeMillis();
        for (int i = iStart; i >= area.y1; i--) {
            for (j = area.x2 - 1; j >= area.x1; j--) {
                key ^= (get(i, j) ? 1 : 0) << pos;
                pos++;
                if (pos == 32) {
                    pos = hashStartPosition;
                }
            }
        }
        Tetris.fooCounter += System.currentTimeMillis() - start;
        return key;
    }
}
