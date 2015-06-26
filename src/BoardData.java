public class BoardData {
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
    }

    public BoardData(BoardData boardData) {
        this.height = boardData.height;
        this.width = boardData.width;
        size = width * height;
        this.area = boardData.area;
        this.data = new int[height][width];
        for (int i = area.y1; i < area.y2; i++) {
            System.arraycopy(boardData.data[i], area.x1, this.data[i], area.x1, area.width);
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
//        if (hashCode != 0) {
//            return hashCode;
//        }
//        int k = 0;
//        byte[] key = new byte[size];
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                key[k] = (byte) (data[i][j] != 0 ? 1 : 0);
//                k++;
//            }
//        }
//        ByteBuffer buffer = ByteBuffer.wrap(key);
//        System.out.format("limit: %d\nposition: %d\n", buffer.limit(), buffer.position());
//        return buffer.hashCode();
        int key = area.height;
        int startPos = 6;
        int pos = startPos;
        for (int i = area.y1; i < area.y1+2; i++) {
            for (int j = area.x2 - 1; j >= area.x1; j--) {
                key ^= (data[i][j] != 0 ? 1 : 0) << pos;
//                System.out.println(Integer.toBinaryString((data[i][j] != 0 ? 1 : 0) << pos));
                pos++;
                if (pos == 32) {
                    pos = startPos;
                }
            }
        }
        return key;
    }
}
