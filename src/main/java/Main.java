import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Main {

    //#0.00代表保留两位小数
    static DecimalFormat df = new DecimalFormat("#0.00000000");

    void solve() {
        for (int T = 1; T > 0; T--) {
            go();
            goAC(out);
        }
    }

    void go() {
        for (int i = 0; i < 10; i++) {
            out.println(i*i);
        }
    }

    void go1() {

    }

    boolean TEST = false;//对拍  只需要改这里


    //===================== MAIN =============================
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    void run() throws Exception {
        if (INPUT.length() > 0)
            is = oj ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        else is = oj ? System.in : new ByteArrayInputStream(new FileInputStream("input/a.test").readAllBytes());
        out = new UWI.FastWriter(System.out);
        if(oj)TEST=false;
        if (TEST) {
            out = testRun;
            testRun.print("");
            testAC.print("");
        }
        long s = System.currentTimeMillis();
        solve();
        out.flush();
        testRun.flush();
        testAC.flush();
        tr(System.currentTimeMillis() - s + "ms");
    }

    void goAC(UWI.FastWriter pre) {
        if(!TEST)return;
        out = testAC;
        go1();
        out=pre;
    }

    InputStream is;
    UWI.FastWriter out;
    UWI.FastWriter testRun = new UWI.FastWriter("output/run.out");
    UWI.FastWriter testAC = new UWI.FastWriter("output/ac.out");
    String INPUT = "";

    private static byte[] inbuf = new byte[1024];
    static int lenbuf = 0, ptrbuf = 0;

    private  int readByte() {
        if (lenbuf == -1) throw new InputMismatchException();
        if (ptrbuf >= lenbuf) {
            ptrbuf = 0;
            try {
                lenbuf = is.read(inbuf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];
    }

    private  boolean isSpaceChar(int c) {
        return !(c >= 33 && c <= 126);
    }

    //	private static boolean isSpaceChar(int c) { return !(c >= 32 && c <= 126); }
    private  int skip() {
        int b;
        while ((b = readByte()) != -1 && isSpaceChar(b)) ;
        return b;
    }

    private  double nd() {
        return Double.parseDouble(ns());
    }

    private  char nc() {
        return (char) skip();
    }

    private  String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!(isSpaceChar(b))) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    private  char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while (p < n && !(isSpaceChar(b))) {
            buf[p++] = (char) b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }

    private  char[][] nm(int n, int m) {
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) map[i] = ns(m);
        return map;
    }

    private  int[] na(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = ni();
        return a;
    }

    private long[] nal(int n) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = nl();
        return a;
    }

    private  int ni() {
        return (int) nl();
    }

    //long
    private  long nl() {
        long num = 0;
        int b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
        if (b == '-') {
            minus = true;
            b = readByte();
        }

        while (true) {
            if (b >= '0' && b <= '9') {
                num = num * 10 + (b - '0');
            } else {
                return minus ? -num : num;
            }
            b = readByte();
        }
    }

    private static void tr(Object... o) {
        if (!oj) System.out.println(Arrays.deepToString(o));
    }

    static boolean local() {
        try {
            String user = System.getProperty("user.name");
            return user.contains("dpz");
        } catch (Exception ignored) {
        }
        return false;
    }
    static private final boolean oj = !local();

}
