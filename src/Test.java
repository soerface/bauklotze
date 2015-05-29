public class Test {

    static int[][] example_values = {
            {1, 3, 1},
            {6, 1, 1},
            {2, 3, 3},
            {3, 3, 10},
            {6, 2, 11},
            {3, 4, 23},
            {9, 2, 41},
            {3, 5, 62},
            {6, 4, 939},
            {9, 3, 3127},
            {6, 5, 8342},
            {9, 4, 41813},
            {6, 6, 80092},
            {7, 6, 614581},
            {9, 5, 1269900},
            {18, 3, 20279829},
            {19, 3, 53794224},
            {42, 2, 80198051},
            {45, 2, 299303201},
    };

    public static void main(String[] args) {
        long start;
        long stop;
        long delta;
        for (int[] values : Test.example_values) {
            System.out.format("%d %d - ", values[0], values[1]);
            start = System.currentTimeMillis();
            long res = Tetris.solve(values[0], values[1]);
            stop = System.currentTimeMillis();
            delta = stop - start;
            if (res == values[2]) {
                System.out.format("OK (%dms)\n", delta);
            }
        }
    }
}
