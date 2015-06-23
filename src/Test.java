import java.math.BigInteger;

public class Test {

    static BigInteger[][] example_values = {
            {new BigInteger("3"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("3"), new BigInteger("2"), new BigInteger("3")},
            {new BigInteger("3"), new BigInteger("3"), new BigInteger("10")},
            {new BigInteger("3"), new BigInteger("4"), new BigInteger("23")},
            {new BigInteger("3"), new BigInteger("5"), new BigInteger("62")},
            {new BigInteger("6"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("6"), new BigInteger("2"), new BigInteger("11")},
            {new BigInteger("6"), new BigInteger("3"), new BigInteger("170")},
            {new BigInteger("6"), new BigInteger("4"), new BigInteger("939")},
            {new BigInteger("6"), new BigInteger("5"), new BigInteger("8342")},
            {new BigInteger("6"), new BigInteger("6"), new BigInteger("80092")},
            {new BigInteger("6"), new BigInteger("7"), new BigInteger("614581")},
            {new BigInteger("6"), new BigInteger("8"), new BigInteger("5271923")},
            {new BigInteger("9"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("9"), new BigInteger("2"), new BigInteger("41")},
            {new BigInteger("9"), new BigInteger("3"), new BigInteger("3127")},
            {new BigInteger("9"), new BigInteger("4"), new BigInteger("41813")},
            {new BigInteger("9"), new BigInteger("5"), new BigInteger("1269900")},
            {new BigInteger("9"), new BigInteger("6"), new BigInteger("45832761")},
            {new BigInteger("9"), new BigInteger("7"), new BigInteger("1064557805")},
            {new BigInteger("9"), new BigInteger("8"), new BigInteger("30860212081")},
            {new BigInteger("9"), new BigInteger("9"), new BigInteger("928789262080")},
            {new BigInteger("9"), new BigInteger("10"), new BigInteger("25020222581494")},
            {new BigInteger("9"), new BigInteger("11"), new BigInteger("714819627084057")},
            {new BigInteger("12"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("12"), new BigInteger("2"), new BigInteger("153")},
            {new BigInteger("12"), new BigInteger("3"), new BigInteger("58234")},
            {new BigInteger("12"), new BigInteger("4"), new BigInteger("1895145")},
            {new BigInteger("12"), new BigInteger("5"), new BigInteger("198253934")},
            {new BigInteger("12"), new BigInteger("6"), new BigInteger("27438555522")},
            {new BigInteger("12"), new BigInteger("7"), new BigInteger("1949314526229")},
            {new BigInteger("12"), new BigInteger("8"), new BigInteger("193553900967497")},
            {new BigInteger("12"), new BigInteger("9"), new BigInteger("20574308184277971")},
            {new BigInteger("12"), new BigInteger("10"), new BigInteger("1830607857363940042")},
            {new BigInteger("12"), new BigInteger("11"), new BigInteger("178792253082742021463")},
            {new BigInteger("15"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("15"), new BigInteger("2"), new BigInteger("571")},
            {new BigInteger("15"), new BigInteger("3"), new BigInteger("1086567")},
            {new BigInteger("15"), new BigInteger("4"), new BigInteger("86208957")},
            {new BigInteger("15"), new BigInteger("5"), new BigInteger("31111319376")},
            {new BigInteger("15"), new BigInteger("6"), new BigInteger("16593169804557")},
            {new BigInteger("15"), new BigInteger("7"), new BigInteger("3619365754064658")},
            {new BigInteger("15"), new BigInteger("8"), new BigInteger("1235348565576072999")},
            {new BigInteger("15"), new BigInteger("9"), new BigInteger("466431115279461257920")},
            {new BigInteger("15"), new BigInteger("10"), new BigInteger("137787968143635937039786")},
            {new BigInteger("15"), new BigInteger("11"), new BigInteger("46227964439989344569208830")},
            {new BigInteger("18"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("18"), new BigInteger("2"), new BigInteger("2131")},
            {new BigInteger("18"), new BigInteger("3"), new BigInteger("20279829")},
            {new BigInteger("18"), new BigInteger("4"), new BigInteger("3924499731")},
            {new BigInteger("18"), new BigInteger("5"), new BigInteger("4887323351972")},
            {new BigInteger("18"), new BigInteger("6"), new BigInteger("10056816580083721")},
            {new BigInteger("18"), new BigInteger("7"), new BigInteger("6743148875847013949")},
            {new BigInteger("18"), new BigInteger("8"), new BigInteger("7918615596845276941783")},
            {new BigInteger("18"), new BigInteger("9"), new BigInteger("10635949030048313308159352")},
            {new BigInteger("18"), new BigInteger("10"), new BigInteger("10448677822353537147395706384")},
            {new BigInteger("21"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("21"), new BigInteger("2"), new BigInteger("7953")},
            {new BigInteger("21"), new BigInteger("3"), new BigInteger("378522507")},
            {new BigInteger("21"), new BigInteger("4"), new BigInteger("178682349823")},
            {new BigInteger("21"), new BigInteger("5"), new BigInteger("767919868804309")},
            {new BigInteger("21"), new BigInteger("6"), new BigInteger("6098207777286812381")},
            {new BigInteger("21"), new BigInteger("7"), new BigInteger("12573396789552627635974")},
            {new BigInteger("21"), new BigInteger("8"), new BigInteger("50812059004074711375396005")},
            {new BigInteger("21"), new BigInteger("9"), new BigInteger("242880323977628870678330721569")},
            {new BigInteger("21"), new BigInteger("10"), new BigInteger("793868918375370204415242288244707")},
            {new BigInteger("24"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("24"), new BigInteger("2"), new BigInteger("29681")},
            {new BigInteger("24"), new BigInteger("3"), new BigInteger("7065162260")},
            {new BigInteger("24"), new BigInteger("4"), new BigInteger("8135650498647")},
            {new BigInteger("24"), new BigInteger("5"), new BigInteger("120664440361104580")},
            {new BigInteger("24"), new BigInteger("6"), new BigInteger("3698195100855040716832")},
            {new BigInteger("27"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("27"), new BigInteger("2"), new BigInteger("110771")},
            {new BigInteger("27"), new BigInteger("3"), new BigInteger("131872134232")},
            {new BigInteger("27"), new BigInteger("4"), new BigInteger("370429531112741")},
            {new BigInteger("27"), new BigInteger("5"), new BigInteger("18960353496615710993")},
            {new BigInteger("27"), new BigInteger("5"), new BigInteger("0")},
            {new BigInteger("30"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("30"), new BigInteger("2"), new BigInteger("413403")},
            {new BigInteger("30"), new BigInteger("3"), new BigInteger("2461410223831")},
            {new BigInteger("30"), new BigInteger("4"), new BigInteger("16866286184557689")},
            {new BigInteger("30"), new BigInteger("5"), new BigInteger("0")},
            {new BigInteger("30"), new BigInteger("6"), new BigInteger("0")},
            {new BigInteger("30"), new BigInteger("7"), new BigInteger("0")},
            {new BigInteger("30"), new BigInteger("8"), new BigInteger("0")},
            {new BigInteger("30"), new BigInteger("9"), new BigInteger("0")},
            {new BigInteger("30"), new BigInteger("10"), new BigInteger("0")},
            {new BigInteger("33"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("33"), new BigInteger("2"), new BigInteger("1542841")},
            {new BigInteger("33"), new BigInteger("3"), new BigInteger("45942537263742")},
            {new BigInteger("33"), new BigInteger("4"), new BigInteger("767950873073579951")},
            {new BigInteger("33"), new BigInteger("5"), new BigInteger("0")},
            {new BigInteger("33"), new BigInteger("6"), new BigInteger("0")},
            {new BigInteger("33"), new BigInteger("7"), new BigInteger("0")},
            {new BigInteger("33"), new BigInteger("8"), new BigInteger("0")},
            {new BigInteger("36"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("36"), new BigInteger("2"), new BigInteger("5757961")},
            {new BigInteger("36"), new BigInteger("3"), new BigInteger("857523348947977")},
            {new BigInteger("36"), new BigInteger("4"), new BigInteger("0")},
            {new BigInteger("36"), new BigInteger("5"), new BigInteger("0")},
            {new BigInteger("36"), new BigInteger("6"), new BigInteger("0")},
            {new BigInteger("39"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("39"), new BigInteger("2"), new BigInteger("21489003")},
            {new BigInteger("39"), new BigInteger("3"), new BigInteger("16005783272212985")},
            {new BigInteger("39"), new BigInteger("4"), new BigInteger("0")},
            {new BigInteger("39"), new BigInteger("5"), new BigInteger("0")},
            {new BigInteger("39"), new BigInteger("6"), new BigInteger("0")},
            {new BigInteger("39"), new BigInteger("7"), new BigInteger("0")},
            {new BigInteger("42"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("42"), new BigInteger("2"), new BigInteger("80198051")},
            {new BigInteger("42"), new BigInteger("3"), new BigInteger("298749997296405057")},
            {new BigInteger("42"), new BigInteger("4"), new BigInteger("0")},
            {new BigInteger("42"), new BigInteger("5"), new BigInteger("0")},
            {new BigInteger("42"), new BigInteger("6"), new BigInteger("0")},
            {new BigInteger("42"), new BigInteger("7"), new BigInteger("0")},
            {new BigInteger("42"), new BigInteger("8"), new BigInteger("0")},
            {new BigInteger("45"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("45"), new BigInteger("2"), new BigInteger("299303201")},
            {new BigInteger("45"), new BigInteger("3"), new BigInteger("5576207010172198758")},
            {new BigInteger("48"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("48"), new BigInteger("2"), new BigInteger("1117014753")},
            {new BigInteger("51"), new BigInteger("1"), new BigInteger("1")},
            {new BigInteger("51"), new BigInteger("2"), new BigInteger("4168755811")},
            {new BigInteger("54"), new BigInteger("2"), new BigInteger("15558008491")},
            {new BigInteger("57"), new BigInteger("2"), new BigInteger("58063278153")},
            {new BigInteger("60"), new BigInteger("2"), new BigInteger("216695104121")},
            {new BigInteger("63"), new BigInteger("2"), new BigInteger("808717138331")},
            {new BigInteger("66"), new BigInteger("2"), new BigInteger("3018173449203")},
            {new BigInteger("69"), new BigInteger("2"), new BigInteger("11263976658481")},
            {new BigInteger("72"), new BigInteger("2"), new BigInteger("42037733184721")},
            {new BigInteger("75"), new BigInteger("2"), new BigInteger("156886956080403")},
            {new BigInteger("78"), new BigInteger("2"), new BigInteger("585510091136891")},
            {new BigInteger("81"), new BigInteger("2"), new BigInteger("2185153408467161")},
            {new BigInteger("84"), new BigInteger("2"), new BigInteger("8155103542731753")},
            {new BigInteger("87"), new BigInteger("2"), new BigInteger("30435260762459851")},
            {new BigInteger("90"), new BigInteger("2"), new BigInteger("113585939507107651")},
            {new BigInteger("93"), new BigInteger("2"), new BigInteger("423908497265970753")},
            {new BigInteger("96"), new BigInteger("2"), new BigInteger("1582048049556775361")},
            {new BigInteger("99"), new BigInteger("2"), new BigInteger("5904283700961130691")},
    };

    public static void main(String[] args) {
        long start;
        long stop;
        long delta;
        int loopStart = 0;
        if (args.length == 1) {
            loopStart = Integer.valueOf(args[0]) - 6; // so I can read the line number above
        }
        else if (args.length == 2) {
            Test.example_values = new BigInteger[1][3];
            Test.example_values[0][0] = new BigInteger(args[0]);
            Test.example_values[0][1] = new BigInteger(args[1]);
            Test.example_values[0][2] = BigInteger.ZERO;
        }
        for (int i = loopStart; i < Test.example_values.length; i++) {
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
                    System.out.format("%3d %3d - %6dms mutations: %40d Cache set / get / setBlocks: %8d / %8d / %8d\n", values[0], values[1], delta, res, Tetris.setCaches, Tetris.getCaches - Tetris.getCachesNull, Tetris.setBlocks);
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
