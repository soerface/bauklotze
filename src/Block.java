public class Block {
    public int[][] data;
    public int[][] coordinates;
    public int height;
    public int width;

    public Block(int[][] data) {
        this.data = data;
        this.height = data.length;
        this.width = data[0].length;
        this.coordinates = new int[3][2];
        int k = 0;
        while (k < 3) {
            for (int i = 0; i < this.data.length; i++) {
                for (int j = 0; j < this.data[i].length; j++) {
                    if (this.data[i][j] != 0) {
                        this.coordinates[k][0] = i;
                        this.coordinates[k][1] = j;
                        k++;
                    }
                }
            }
        }
    }
}
