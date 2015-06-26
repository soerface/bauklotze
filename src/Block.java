public class Block {
    public short data;
    public int height;
    public int width;

    public Block(short data, int height, int width) {
        this.data = data;
        this.height = height;
        this.width = width;
    }

    public boolean get(int y, int x) {
        return (data & (1 << (y * 3 + x))) != 0;
    }
}
