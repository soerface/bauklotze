import java.math.BigInteger;

public class Test {

    static BigInteger[][] example_values = {
            {BigInteger.valueOf(3), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(3), BigInteger.valueOf(2), BigInteger.valueOf(3)},
            {BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(10)},
            {BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(23)},
            {BigInteger.valueOf(3), BigInteger.valueOf(5), BigInteger.valueOf(62)},
            {BigInteger.valueOf(6), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(6), BigInteger.valueOf(2), BigInteger.valueOf(11)},
            {BigInteger.valueOf(6), BigInteger.valueOf(3), BigInteger.valueOf(170)},
            {BigInteger.valueOf(6), BigInteger.valueOf(4), BigInteger.valueOf(939)},
            {BigInteger.valueOf(6), BigInteger.valueOf(5), BigInteger.valueOf(8342)},
            {BigInteger.valueOf(6), BigInteger.valueOf(6), BigInteger.valueOf(80092)},
            {BigInteger.valueOf(6), BigInteger.valueOf(7), BigInteger.valueOf(614581)},
            {BigInteger.valueOf(6), BigInteger.valueOf(8), BigInteger.valueOf(5271923)},
            {BigInteger.valueOf(9), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(9), BigInteger.valueOf(2), BigInteger.valueOf(41)},
            {BigInteger.valueOf(9), BigInteger.valueOf(3), BigInteger.valueOf(3127)},
            {BigInteger.valueOf(9), BigInteger.valueOf(4), BigInteger.valueOf(41813)},
            {BigInteger.valueOf(9), BigInteger.valueOf(5), BigInteger.valueOf(1269900)},
            {BigInteger.valueOf(9), BigInteger.valueOf(6), BigInteger.valueOf(45832761)},
            {BigInteger.valueOf(9), BigInteger.valueOf(7), BigInteger.valueOf(1064557805)},
            {BigInteger.valueOf(9), BigInteger.valueOf(8), BigInteger.valueOf(30860212081L)},
            {BigInteger.valueOf(9), BigInteger.valueOf(9), BigInteger.valueOf(928789262080L)},
            {BigInteger.valueOf(9), BigInteger.valueOf(10), BigInteger.valueOf(25020222581494L)},
            {BigInteger.valueOf(9), BigInteger.valueOf(11), BigInteger.valueOf(714819627084057L)},
            {BigInteger.valueOf(12), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(12), BigInteger.valueOf(2), BigInteger.valueOf(153)},
            {BigInteger.valueOf(12), BigInteger.valueOf(3), BigInteger.valueOf(58234)},
            {BigInteger.valueOf(12), BigInteger.valueOf(4), BigInteger.valueOf(1895145)},
            {BigInteger.valueOf(12), BigInteger.valueOf(5), BigInteger.valueOf(198253934)},
            {BigInteger.valueOf(12), BigInteger.valueOf(6), BigInteger.valueOf(27438555522L)},
            {BigInteger.valueOf(12), BigInteger.valueOf(7), BigInteger.valueOf(1949314526229L)},
            {BigInteger.valueOf(12), BigInteger.valueOf(8), BigInteger.valueOf(193553900967497L)},
            {BigInteger.valueOf(12), BigInteger.valueOf(9), BigInteger.valueOf(20574308184277971L)},
            {BigInteger.valueOf(12), BigInteger.valueOf(10), BigInteger.valueOf(1830607857363940042L)},
            {BigInteger.valueOf(15), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(15), BigInteger.valueOf(2), BigInteger.valueOf(571)},
            {BigInteger.valueOf(15), BigInteger.valueOf(3), BigInteger.valueOf(1086567)},
            {BigInteger.valueOf(15), BigInteger.valueOf(4), BigInteger.valueOf(86208957)},
            {BigInteger.valueOf(15), BigInteger.valueOf(5), BigInteger.valueOf(31111319376L)},
            {BigInteger.valueOf(15), BigInteger.valueOf(6), BigInteger.valueOf(16593169804557L)},
            {BigInteger.valueOf(15), BigInteger.valueOf(7), BigInteger.valueOf(3619365754064658L)},
            {BigInteger.valueOf(15), BigInteger.valueOf(8), BigInteger.valueOf(1235348565576072999L)},
            {BigInteger.valueOf(15), BigInteger.valueOf(9), new BigInteger("466431115279461257920")},
            {BigInteger.valueOf(18), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(18), BigInteger.valueOf(2), BigInteger.valueOf(2131)},
            {BigInteger.valueOf(18), BigInteger.valueOf(3), BigInteger.valueOf(20279829)},
            {BigInteger.valueOf(18), BigInteger.valueOf(4), BigInteger.valueOf(3924499731L)},
            {BigInteger.valueOf(18), BigInteger.valueOf(5), BigInteger.valueOf(4887323351972L)},
            {BigInteger.valueOf(18), BigInteger.valueOf(6), BigInteger.valueOf(10056816580083721L)},
            {BigInteger.valueOf(18), BigInteger.valueOf(7), BigInteger.valueOf(6743148875847013949L)},
            {BigInteger.valueOf(21), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(21), BigInteger.valueOf(2), BigInteger.valueOf(7953)},
            {BigInteger.valueOf(21), BigInteger.valueOf(3), BigInteger.valueOf(378522507)},
            {BigInteger.valueOf(21), BigInteger.valueOf(4), BigInteger.valueOf(178682349823L)},
            {BigInteger.valueOf(21), BigInteger.valueOf(5), BigInteger.valueOf(767919868804309L)},
            {BigInteger.valueOf(21), BigInteger.valueOf(6), BigInteger.valueOf(6098207777286812381L)},
            {BigInteger.valueOf(24), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(24), BigInteger.valueOf(2), BigInteger.valueOf(29681)},
            {BigInteger.valueOf(24), BigInteger.valueOf(3), BigInteger.valueOf(7065162260L)},
            {BigInteger.valueOf(24), BigInteger.valueOf(4), BigInteger.valueOf(8135650498647L)},
            {BigInteger.valueOf(24), BigInteger.valueOf(5), BigInteger.valueOf(120664440361104580L)},
            {BigInteger.valueOf(24), BigInteger.valueOf(6), new BigInteger("3698195100855040716832")},
            {BigInteger.valueOf(27), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(27), BigInteger.valueOf(2), BigInteger.valueOf(110771)},
            {BigInteger.valueOf(27), BigInteger.valueOf(3), BigInteger.valueOf(131872134232L)},
            {BigInteger.valueOf(27), BigInteger.valueOf(4), BigInteger.valueOf(370429531112741L)},
            {BigInteger.valueOf(27), BigInteger.valueOf(5), new BigInteger("18960353496615710993")},
            {BigInteger.valueOf(30), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(30), BigInteger.valueOf(2), BigInteger.valueOf(413403)},
            {BigInteger.valueOf(30), BigInteger.valueOf(3), BigInteger.valueOf(2461410223831L)},
            {BigInteger.valueOf(30), BigInteger.valueOf(4), BigInteger.valueOf(16866286184557689L)},
            {BigInteger.valueOf(33), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(33), BigInteger.valueOf(2), BigInteger.valueOf(1542841)},
            {BigInteger.valueOf(33), BigInteger.valueOf(3), BigInteger.valueOf(45942537263742L)},
            {BigInteger.valueOf(33), BigInteger.valueOf(4), BigInteger.valueOf(767950873073579951L)},
            {BigInteger.valueOf(36), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(36), BigInteger.valueOf(2), BigInteger.valueOf(5757961)},
            {BigInteger.valueOf(36), BigInteger.valueOf(3), BigInteger.valueOf(857523348947977L)},
            {BigInteger.valueOf(39), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(39), BigInteger.valueOf(2), BigInteger.valueOf(0)},
            {BigInteger.valueOf(39), BigInteger.valueOf(3), BigInteger.valueOf(16005783272212985L)},
            {BigInteger.valueOf(42), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(42), BigInteger.valueOf(2), BigInteger.valueOf(80198051)},
            {BigInteger.valueOf(42), BigInteger.valueOf(3), BigInteger.valueOf(298749997296405057L)},
            {BigInteger.valueOf(45), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(45), BigInteger.valueOf(2), BigInteger.valueOf(299303201)},
            {BigInteger.valueOf(45), BigInteger.valueOf(3), BigInteger.valueOf(5576207010172198758L)},
            {BigInteger.valueOf(48), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(48), BigInteger.valueOf(2), BigInteger.valueOf(1117014753)},
            {BigInteger.valueOf(51), BigInteger.valueOf(1), BigInteger.valueOf(1)},
            {BigInteger.valueOf(51), BigInteger.valueOf(2), BigInteger.valueOf(4168755811L)},
            {BigInteger.valueOf(54), BigInteger.valueOf(2), BigInteger.valueOf(15558008491L)},
            {BigInteger.valueOf(57), BigInteger.valueOf(2), BigInteger.valueOf(58063278153L)},
            {BigInteger.valueOf(60), BigInteger.valueOf(2), BigInteger.valueOf(216695104121L)},
            {BigInteger.valueOf(63), BigInteger.valueOf(2), BigInteger.valueOf(808717138331L)},
            {BigInteger.valueOf(66), BigInteger.valueOf(2), BigInteger.valueOf(3018173449203L)},
            {BigInteger.valueOf(69), BigInteger.valueOf(2), BigInteger.valueOf(11263976658481L)},
            {BigInteger.valueOf(72), BigInteger.valueOf(2), BigInteger.valueOf(42037733184721L)},
            {BigInteger.valueOf(75), BigInteger.valueOf(2), BigInteger.valueOf(156886956080403L)},
            {BigInteger.valueOf(78), BigInteger.valueOf(2), BigInteger.valueOf(585510091136891L)},
            {BigInteger.valueOf(81), BigInteger.valueOf(2), BigInteger.valueOf(2185153408467161L)},
            {BigInteger.valueOf(84), BigInteger.valueOf(2), BigInteger.valueOf(8155103542731753L)},
            {BigInteger.valueOf(87), BigInteger.valueOf(2), BigInteger.valueOf(30435260762459851L)},
            {BigInteger.valueOf(90), BigInteger.valueOf(2), BigInteger.valueOf(113585939507107651L)},
            {BigInteger.valueOf(93), BigInteger.valueOf(2), BigInteger.valueOf(423908497265970753L)},
            {BigInteger.valueOf(96), BigInteger.valueOf(2), BigInteger.valueOf(1582048049556775361L)},
            {BigInteger.valueOf(99), BigInteger.valueOf(2), BigInteger.valueOf(5904283700961130691L)},
    };

    public static void main(String[] args) {
        long start;
        long stop;
        long delta;
        if (args.length == 2) {
            Test.example_values = new BigInteger[1][3];
            Test.example_values[0][0] = new BigInteger(args[0]);
            Test.example_values[0][1] = new BigInteger(args[1]);
            Test.example_values[0][2] = BigInteger.ZERO;
        }
        for (int i = 0; i < Test.example_values.length; i++) {
            BigInteger[] values = Test.example_values[i];
            start = System.currentTimeMillis();
            int m = values[0].intValue();
            int n = values[1].intValue();
            BigInteger res = Tetris.solve(m, n);
            stop = System.currentTimeMillis();
            delta = stop - start;
            if (values[2].compareTo(BigInteger.ZERO) == 0 || res.compareTo(values[2]) == 0) {
//                System.out.format("OK %6dms mutations: %15d setBlock: %15d setCache: %15d\n", delta, res, Tetris.setBlocks, Tetris.getCaches);
                if (delta > 200 || true) {
                    System.out.format("%3d %3d - OK %6dms mutations: %25d\n", values[0], values[1], delta, res);
                }
            } else {
                System.out.format("%3d %3d - ERROR\n", values[0], values[1]);
                System.out.format("Expected %d, got %d\n", values[2], res);
                return;
            }
            if (i > 1 && i < Test.example_values.length - 1 && Test.example_values[i + 1][0].compareTo(values[0]) != 0 && Test.example_values[i - 1][0].compareTo(values[0]) == 0) {
                System.out.println();
            }
        }
    }
}
