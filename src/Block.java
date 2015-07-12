public class Block {
    public int[][] data;
    public int height;
    public int width;

    public Block(int[][] data, int height, int width) {
        this.data = data;
        this.height = height;
        this.width = width;
    }

    public int get(int y, int x) {
        return data[y][x];
    }
}
