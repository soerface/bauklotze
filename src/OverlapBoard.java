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
        super(m, n);
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
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                if (this.data[i][j] == 0) {
                    return new Integer[]{i, j};
                }
            }
        }
        return new Integer[]{-1, -1};
    }

    @Override
    int[] isRect() {
        int[] res = super.isRect();
        int m = this.data.length;
        int n = this.data[0].length;
        if (res[0]+1 == Math.max(m, n) && res[1]+1 == Math.min(m, n)) {
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

    @Override
    protected boolean unsolveable() {
        if (super.unsolveable()) {
            return true;
        }
        if (this.currentPosition == this.positions.length) {
            // we have now placed all blocks which are separating top and bottom rect
            // to make sure they are overlapping, both top half and bottom half need to have
            // blocks inside them. Additionally, both of them need a number of blocks divideable
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
                return true;
            }
            if (blocksInsideTopHalf % 3 != 0) {
                return true;
            }
        }
        return false;
    }
}
