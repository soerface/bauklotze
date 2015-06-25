import java.nio.ByteBuffer;

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
        for (int i = 0; i < height; i++) {
            System.arraycopy(boardData.data[i], 0, this.data[i], 0, width);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardData boardData = (BoardData) o;

        if (height != boardData.height) return false;
        if (width != boardData.width) return false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((data[i][j] == 0) != (boardData.data[i][j] == 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int k = 0;
        byte[] key = new byte[size];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                key[k] = (byte) (data[i][j] != 0 ? 1 : 0);
                k++;
            }
        }
        return ByteBuffer.wrap(key).hashCode();
    }
}
