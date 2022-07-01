package com.dpz.main;


import java.io.*;
import java.util.*;

public class Main {
    //	static String INPUT = "10 60 1 60 2 60 3 60 4 60 5 60 6 60 7 60 8 60 9 60 10";
    static String INPUT = "";

    static void solve() {
        for (int T = 1; T > 0; T--) go();
    }

    static void go() {
        long INF = Long.MAX_VALUE / 2;
        int n = ni(), m = ni();
        List<int[]>[] adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();
        while (m-- > 0) {
            int[] edge = new int[]{ni(), ni(), ni()};
            adj[edge[0]].add(new int[]{edge[1], edge[2]});
        }
        long[] dist = dijkstra(n, adj, 1);
        out.println(dist[n] == INF ? -1 : dist[n]);
    }

    //Dijkstra
    static long[] dijkstra(int n, List<int[]>[] adj, int source) {
        long INF = Long.MAX_VALUE / 2;
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[source] = 0;
        boolean[] vis = new boolean[n + 1];

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.add(new long[]{1, 0});
        while (!pq.isEmpty()) {
            long[] p = pq.poll();
            int u = (int) p[0];
            long d = p[1];

            if (vis[u]) continue;
            vis[u] = true;
            for (var v : adj[u]) {
                if (d + v[1] < dist[v[0]]) {
                    dist[v[0]] = d + v[1];
                    pq.add(new long[]{v[0], dist[v[0]]});
                }
            }
        }
        return dist;
    }


    public static void main(String[] args) throws Exception {
        long S = System.currentTimeMillis();
        is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        out = new PrintWriter(System.out);

        solve();
        out.flush();
        long G = System.currentTimeMillis();
        tr(G - S + "ms 提交时记得清空INPUT！");
    }

    static InputStream is;
    static PrintWriter out;


    private static boolean eof() {
        if (lenbuf == -1) return true;
        int lptr = ptrbuf;
        while (lptr < lenbuf) if (!isSpaceChar(inbuf[lptr++])) return false;

        try {
            is.mark(1000);
            while (true) {
                int b = is.read();
                if (b == -1) {
                    is.reset();
                    return true;
                } else if (!isSpaceChar(b)) {
                    is.reset();
                    return false;
                }
            }
        } catch (IOException e) {
            return true;
        }
    }

    private static byte[] inbuf = new byte[1024];
    static int lenbuf = 0, ptrbuf = 0;

    private static int readByte() {
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

    private static boolean isSpaceChar(int c) {
        return !(c >= 33 && c <= 126);
    }

    //	private static boolean isSpaceChar(int c) { return !(c >= 32 && c <= 126); }
    private static int skip() {
        int b;
        while ((b = readByte()) != -1 && isSpaceChar(b)) ;
        return b;
    }

    private static double nd() {
        return Double.parseDouble(ns());
    }

    private static char nc() {
        return (char) skip();
    }

    private static String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!(isSpaceChar(b))) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    private static char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while (p < n && !(isSpaceChar(b))) {
            buf[p++] = (char) b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }

    private static char[][] nm(int n, int m) {
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) map[i] = ns(m);
        return map;
    }

    private static int[] na(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = ni();
        return a;
    }

    private static int ni() {
        int num = 0, b;
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

    private static long nl() {
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
        if (INPUT.length() != 0) System.out.println(Arrays.deepToString(o));
    }
}
