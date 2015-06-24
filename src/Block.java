public class Block {
    public int[][] data;
    public int[][] coordinates;
    public int height;
    public int width;

    public Block(int[][] data) {
        this.data = data;
        height = data.length;
        width = data[0].length;
        coordinates = new int[3][2];
        int k = 0;
        while (k < 3) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (data[i][j] != 0) {
                        coordinates[k][0] = i;
                        coordinates[k][1] = j;
                        k++;
                    }
                }
            }
        }
    }
}
