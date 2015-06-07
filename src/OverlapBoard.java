/**
 * The OverlapBoard only calculates mutations which consist of blocks
 * overlapping the half of the board It will be splitted by side m,
 * remember that for calculation in Board.java!
 * <p/>
 * n
 * +------+
 * |222222|
 * |122233|v Over-
 * |122235|^ -lap   m
 * |122255|
 * +------+
 */
public class OverlapBoard extends Board {
    private Integer[][] positions;
    private int currentPosition;
    private int splitPosition;

    public OverlapBoard(int m, int n, int splitPosition) {
        this(m, n, splitPosition, false);
    }

    public OverlapBoard(int m, int n, int splitPosition, boolean allowRotate) {
        super(m, n, allowRotate);
        this.positions = new Integer[n][2];
        this.currentPosition = 0;
        this.splitPosition = splitPosition;
        for (int i = 0; i < n; i++) {
            positions[i][0] = splitPosition - 1;
            positions[i][1] = i;
        }
    }

    public long calculateMutations() {
        this.nextPosition(this.findNextPosition());
        return this.result;
    }

    @Override
    protected Integer[] findNextPosition() {
        int offset = 0;

        while (this.currentPosition + offset < this.positions.length) {
            Integer[] coordinate = this.positions[this.currentPosition + offset];
            int m = coordinate[0];
            int n = coordinate[1];
            if (this.data[m][n] == 0) {
                return coordinate;
            }
            offset++;
        }
        if (!this.correctlySplitted()) {
            // since we are returning "board is full", the overridden
            // method will add 1, so we need to fix this
            this.result--;
            return new Integer[]{-1, -1};
        }
//        System.out.println("SPLitTinG!");
//        this.print();
        // the board is now separated in half;
        // we just need to calculate the combinations of the top and
        // the bottom half and multiply them
        Board topBoard = new Board(this.splitPosition, this.data[0].length, false);
        Board bottomBoard = new Board(this.height - this.splitPosition, this.data[0].length, false);
        // copy the data from our current board to the two new ones
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                if (i < this.splitPosition) {
                    // "7" for better visualization while debugging; could be any other number != 0
                    // mirror the data while copying; gives a little speedup
                    // due to the way the next position is being chosen
                    topBoard.data[i][j] = this.data[this.splitPosition - i - 1][j] != 0 ? 7 : 0;
                } else {
                    // "7" for better visualization while debugging; could be any other number != 0
                    bottomBoard.data[i - this.splitPosition][j] = this.data[i][j] != 0 ? 7 : 0;
                }
            }
        }
//        System.out.println("calcing top and and bottom");
//        topBoard.print();
//        bottomBoard.print();
        long top = topBoard.calculateMutations();
        long bottom = bottomBoard.calculateMutations();
        this.result += top * bottom;
        // since we are returning "board is full", the nextPosition
        // method will add 1, so we need to fix this
        this.result--;
        return new Integer[]{-1, -1};
    }

    private boolean correctlySplitted() {
        // we have now placed all blocks which are separating top and bottom rect.
        // To make sure they are overlapping, both top half and bottom half need to have
        // blocks inside them. Additionally, both of them need a number of blocks dividable
        // by 3 inside, otherwise, it won't be possible to find a solution
        int blocksInsideTopHalf = 0;
        int blocksInsideBottomHalf = 0;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.data[i][j] != 0) {
                    if (i < this.splitPosition) {
                        blocksInsideTopHalf++;
                    } else {
                        blocksInsideBottomHalf++;
                    }
                }
            }
        }
        if (blocksInsideTopHalf == 0 || blocksInsideBottomHalf == 0) {
            return false;
        }
        if (blocksInsideTopHalf % 3 != 0) {
            return false;
        }
        return true;
    }

    @Override
    int[] isRect() {
        int[] res = super.isRect();
        int m = this.data.length;
        int n = this.data[0].length;
        if (res[0] == Math.max(m, n) && res[1] == Math.min(m, n)) {
            return new int[]{-1, -1};
        }
        return res;
    }

    @Override
    protected void placeBlockAt(Block block, Integer[] offset) {
        super.placeBlockAt(block, offset);
        this.currentPosition++;
    }

    @Override
    protected void removeBlockAt(Block block, Integer[] offset) {
        super.removeBlockAt(block, offset);
        this.currentPosition--;
    }
}
