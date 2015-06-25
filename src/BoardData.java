public class BoardData {
    public int data[][];
    public final int height;
    public final int width;
    public Area area;

    public BoardData(int height, int width) {
        this.height = height;
        this.width = width;
        this.area = new Area(0, 0, width, height);
        data = new int[height][width];
    }
}
