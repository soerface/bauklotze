public class BoardData {
    private static int hashStartPosition;
    private int data[][];
    private int hash;
    private Area area;

    public BoardData() {
        this.area = new Area(0, 0, Board.width, Board.height);
        data = new int[Board.height][Board.width];
        int pos = 0;
        if (hashStartPosition == 0) {
            while (Board.height >> pos != 0) {
                pos++;
            }
            hashStartPosition = pos;
        }
        hash = 0;
    }

    public BoardData(BoardData boardData) {
        area = boardData.area;
        data = new int[Board.height][Board.width];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                data[i][j] = boardData.data[i][j];
            }
        }
    }

    public void setArea(Area area) {
        this.area = area;
        hash = 0;
    }

    public boolean get(int y, int x) {
        return data[y][x] != 0;
    }


    public void set(int y, int x, boolean value) {
        data[y][x] = value ? 1 : 0;
    }

    public void mirrorData() {
        int[][] newData = new int[Board.height][Board.width];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[i][j] = data[i][area.x2 - j - 1];
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
        if (hash != 0) {
            return hash;
        }
        int key = area.height;
        int pos = hashStartPosition;
        int iStart = area.y1 + 2;
        if (area.y2 - 1 < iStart) {
            iStart = area.y2 - 1;
        }
//        long start = System.currentTimeMillis();
        for (int i = iStart; i >= area.y1; i--) {
            for (int j = area.x2 - 1; j >= area.x1; j--) {
                key ^= (data[i][j] != 0 ? 1 : 0) << pos;
                pos++;
                if (pos == 32) {
                    pos = hashStartPosition;
                }
            }
        }
//        Tetris.fooCounter += System.currentTimeMillis() - start;
        hash = key;
        return key;
    }
}
