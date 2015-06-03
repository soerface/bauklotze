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

    public OverlapBoard(int m, int n) {
        this(m, n, false);
    }

    public OverlapBoard(int m, int n, boolean allowRotate) {
        super(m, n, allowRotate);
        this.positions = new Integer[n][2];
        this.currentPosition = 0;
        for (int i = 0; i < n; i++) {
            positions[i][0] = m / 2 - 1;
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
        // the board is now separated in half;
        // we just need to calculate the combinations of the top and
        // the bottom half and multiply them
        Board topBoard = new Board(this.data.length / 2, this.data[0].length, false);
        Board bottomBoard = new Board(this.data.length / 2, this.data[0].length, false);
        // copy the data from our current board to the two new ones
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                if (i < this.data.length / 2) {
                    topBoard.data[i][j] = this.data[i][j];
                } else {
                    bottomBoard.data[i - this.data.length / 2][j] = this.data[i][j];
                }
            }
        }

        long top = topBoard.calculateMutations();
        long bottom = bottomBoard.calculateMutations();
        this.result += top * bottom;
        // since we are returning "board is full", the overridden
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

        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                if (this.data[i][j] != 0) {
                    if (i < this.data.length / 2) {
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
