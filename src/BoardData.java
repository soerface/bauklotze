public class BoardData {
    private static int hashStartPosition;
    private boolean data[][];
    public final int height;
    public final int width;
    public final int size;
    public Area area;

    public BoardData(int height, int width) {
        this.height = height;
        this.width = width;
        size = width * height;
        this.area = new Area(0, 0, width, height);
        data = new boolean[height][width];
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
        this.data = new boolean[height][width];
        for (int i = area.y1; i < area.y2; i++) {
            System.arraycopy(boardData.data[i], area.x1, this.data[i], area.x1, area.width);
        }
    }

    public boolean get(int y, int x) {
        return data[y][x];
    }

    public void set(int y, int x, boolean value) {
        data[y][x] = value;
    }

    public void mirrorData() {
        boolean[][] newData = new boolean[height][width];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[i][area.x2 - (j - area.x1) - 1] = data[i][j];
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
            for (int j = area.x1; j < area.x2; j++) {
                if (data[i][j] != boardData.data[i][j]) {
                    return false;
                }
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
                key ^= (data[i][j] ? 1 : 0) << pos;
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
