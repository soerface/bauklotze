public class BoardData {
    private static int hashStartPosition;
    public int data[][];
    public final int height;
    public final int width;
    public final int size;
    public Area area;

    public BoardData(int height, int width) {
        this.height = height;
        this.width = width;
        size = width * height;
        this.area = new Area(0, 0, width, height);
        data = new int[height][width];
        int pos = 0;
        while(height >> pos != 0) {
            pos++;
        }
        hashStartPosition = pos;
    }

    public BoardData(BoardData boardData, boolean withData) {
        this.height = boardData.height;
        this.width = boardData.width;
        size = width * height;
        this.area = boardData.area;
        if (withData) {
            this.data = new int[height][width];
            for (int i = area.y1; i < area.y2; i++) {
                System.arraycopy(boardData.data[i], area.x1, this.data[i], area.x1, area.width);
            }
        }
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
                if ((data[i][j] == 0) != (boardData.data[i][j] == 0)) {
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
        for (int i = iStart; i >= area.y1; i--) {
            for (j = area.x2 - 1; j >= area.x1; j--) {
                key ^= (data[i][j] != 0 ? 1 : 0) << pos;
                pos++;
                if (pos == 32) {
                    pos = hashStartPosition;
                }
            }
        }
        return key;
    }
}
