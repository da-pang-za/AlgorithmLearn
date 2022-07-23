package com.dpz.template;

import com.dpz.dataStructure.UnionFind;

import java.util.*;

public class Graph {
    static long INF = Long.MAX_VALUE / 2;

    //建图 1 list 不带权   注意！！！  这里是无向图！！！
    static List<Integer>[] build1(int n, int[][] edges) {
        List<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();

        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        return adj;
    }

    //建图 2  set 不带权   注意！！！  这里是无向图！！！
    static HashSet<Integer>[] build2(int n, int[][] edges) {
        HashSet<Integer>[] adj = new HashSet[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new HashSet<>();
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        return adj;
    }

    //建图3 带权   注意！！！  这里是无向图！！！
    static List<int[]>[] build3(int n, int[][] edges) {
        List<int[]>[] adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();
        for (int[] edge : edges) {
            adj[edge[0]].add(new int[]{edge[1], edge[2]});
            adj[edge[1]].add(new int[]{edge[0], edge[2]});
        }
        return adj;
    }

    //====================== 最短路 ==============================
    //等权（都为1）单源最短路  BFS
    static long[] bfs(int n, List<Integer>[] adj, int source) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[source] = 0;
        boolean[] vis = new boolean[n + 1];
        int d = 1;
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(source);
        vis[source] = true;
        while (!deque.isEmpty()) {
            int sz = deque.size();
            for (int i = 0; i < sz; i++) {
                int u = deque.pollFirst();
                for (var v : adj[u]) {
                    if (vis[v]) continue;
                    vis[v] = true;
                    dist[v] = d;
                    deque.addLast(v);
                }
            }
            d++;
        }
        return dist;
    }

    //Dijkstra 单源最短路  复杂度 O(eloge)  无负权
    static long[] dijkstra(int n, List<int[]>[] adj, int source) {
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

    //Floyd算法 任意两点最短路 边权有可能为负数 已去除重边 保留最短的边
    //复杂度O(N^3)
    static int[][] floyd(int[][] adj) {
        int n = adj.length - 1;
        int[][] dist = new int[n + 1][n + 1];//最短路
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                dist[i][j] = adj[i][j];


        //i到j 可以经过 [1,u]这些点 的最短路  i->[1,u]->j  [1,u]可以不包括 i j
        //dp[u][i][j]=min(dp[u-1][i][j],dp[u-1/u][i][u]+dp[u-1/u][u][j]);
        for (int u = 1; u <= n; u++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][u] == INF || dist[u][j] == INF) continue;
                    dist[i][j] = Math.min(dist[i][j], dist[i][u] + dist[u][j]);
                }
            }
        }
        return dist;
    }

    /**
     * Bellman-Ford 算法 不超过k条边的单源最短路    求有负权边的最短路
     * 复杂度 O(N*M)
     * for(k)  for(all(edges))   dist[b]=min(dist[b],dist[a]+w)
     * 每次循环路径最多增加一条边
     * 1--> a --> b    边[a,b,w] dist[b]=min(dist[b],dist[a]+w)
     * 注意 如果有负权回路 且负环在路径上 则  最短路不存在！！！
     * <p>
     * 有边数限制的最短路 https://www.acwing.com/problem/content/855/
     * 这个问题只能用BF算法
     * 边数有限制  负环不能无限转了
     */
    static long[] bf(int n, int[][] edges, int source, int k) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[source] = 0;
        for (int i = 0; i < k; i++) {
            long[] dist1 = dist.clone();
            for (int[] e : edges) {
                int a = e[0], b = e[1], w = e[2];
                dist1[b] = Math.min(dist1[b], dist[a] + w);
            }
            dist = dist1;
        }
        return dist;
        //ans > INF/2 ? "impossible" : ans   注意有负权   INF-x   也是无穷
    }

    /**
     * spfa   对BF算法的优化     有负权的最短路
     * 复杂度 一般O(M)   最坏O(N*M)
     * https://www.acwing.com/solution/content/9306/
     * dist1[b] = Math.min(dist1[b], dist[a] + w);   a变小  b 才会变小
     * 更新过的节点 才会用来更新其他节点
     */
    static long[] spfa(int n, List<int[]>[] adj, int source) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[source] = 0;
        boolean[] st = new boolean[n + 1];//是否在队列中
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(source);
        st[source] = true;

        while (!deque.isEmpty()) {
            int p = deque.pollFirst();
            st[p] = false;//注意出队后要设置st数组的值  可能会重新入队
            for (var e : adj[p]) {
                int v = e[0], d = e[1];
                if (d + dist[p] < dist[v]) {
                    dist[v] = d + dist[p];
                    if (!st[v]) {
                        //变小了才去更新其他点
                        deque.addLast(v);
                        st[v] = true;
                    }
                }
            }
        }
        //这里不会出现 INF-x 这种dist值 因为INF的dist不会去更新其他点
        return dist;
    }

    /**
     * spfa求负环   https://www.acwing.com/video/284/   抽屉原理
     * 记录最短路走过的边数
     * cnt[x]>=n  说明经过了n+1个点  一共n个点 所以一定重复了 即负环/负权回路
     * 抽象一个源点 和所有点的距离都是0  求这个点的最短路
     */
    static boolean negLoop(int n, List<int[]>[] adj) {
        long[] dist = new long[n + 1];//初始都设为0  直接更新负权
        int[] cnt = new int[n + 1];
        boolean[] st = new boolean[n + 1];//是否在队列中
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            deque.addLast(i);
        }
        while (!deque.isEmpty()) {
            int p = deque.pollFirst();
            st[p] = false;//注意出队后要设置st数组的值  可能会重新入队
            for (var e : adj[p]) {
                int v = e[0], d = e[1];
                if (d + dist[p] < dist[v]) {
                    dist[v] = d + dist[p];
                    cnt[v] = cnt[p] + 1;
                    if (cnt[v] >= n) return true;
                    if (!st[v]) {
                        //变小了才去更新其他点
                        deque.addLast(v);
                        st[v] = true;
                    }
                }
            }
        }
        //这里不会出现 INF-x 这种dist值 因为INF的dist不会去更新其他点
        return false;
    }


    //建图  dijkstra模板  前向星
    static class Solution1 {
        int N = 110, M = 6010;
        int[] he = new int[N], e = new int[M], ne = new int[M], w = new int[M];
        int[] dist = new int[N];
        boolean[] vis = new boolean[N];
        int n, k, idx;
        int INF = 0x3f3f3f3f;

        void add(int a, int b, int c) {
            e[idx] = b;
            ne[idx] = he[a];
            he[a] = idx;
            w[idx] = c;
            idx++;
        }

        public int networkDelayTime(int[][] ts, int _n, int _k) {
            n = _n;
            k = _k;
            Arrays.fill(he, -1);
            for (int[] t : ts) {
                int u = t[0], v = t[1], c = t[2];
                add(u, v, c);
            }
            dijkstra();
            int ans = 0;
            for (int i = 1; i <= n; i++) {
                ans = Math.max(ans, dist[i]);
            }
            return ans > INF / 2 ? -1 : ans;
        }

        void dijkstra() {
            Arrays.fill(vis, false);
            Arrays.fill(dist, INF);
            dist[k] = 0;
            PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);//dist小的先出
            q.add(new int[]{k, 0});
            while (!q.isEmpty()) {
                int[] poll = q.poll();//最小的
                int id = poll[0], step = poll[1];
                if (vis[id]) continue;
                vis[id] = true;
                for (int i = he[id]; i != -1; i = ne[i]) {
                    int j = e[i];
                    if (dist[j] > dist[id] + w[i]) {
                        dist[j] = dist[id] + w[i];
                        q.add(new int[]{j, dist[j]});
                    }
                }
            }
        }
    }


    //====================== 最短路 ==============================


    //====================== 最小生成树(无向图) ==========================

    /**
     * Kruskal算法  克鲁斯卡尔算法
     * 所有边按权重从小到大排序
     * 按顺序枚举每条边  如果枚举的两个顶点当前不连通 把当前边加到集合
     * 即尽量加最小边
     */
    //输出所有边
    static List<int[]> kruskal(int n, int[][] edges) {
        List<int[]> tree = new ArrayList<>();
        UnionFind uf = new UnionFind(n + 1);
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        for (var e : edges) {
            if (uf.find(e[0]) == uf.find(e[1])) continue;
            uf.union(e[0], e[1]);
            tree.add(e);
        }
        if (tree.size() < n - 1) return null;
        return tree;
    }

    //====================== 最小生成树 ==========================

    //====================== 二分图(无向图) ==========================
    //二分图定义：把图划分为两个集合 集合内部没有边

    /**
     * 染色法判定二分图
     * https://www.acwing.com/problem/content/862/
     * 一个图是二分图  当且仅当图中不含奇数环(环中边的数量是奇数)
     * (因为环上相邻的两点一定在不同的部分 奇数会出现矛盾)
     */
    static boolean isBinG(int n, List<Integer>[] adj) {
        int[] color = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            if (color[i] != 0) continue;
            Deque<Integer> deque = new ArrayDeque<>();
            deque.addLast(i);
            color[i] = 1;
            while (!deque.isEmpty()) {
                int p = deque.pollFirst();
                int c = 1;
                if (color[p] == 1) c++;
                for (Integer v : adj[p]) {
                    if (color[v] == 0) {
                        color[v] = c;
                        deque.addLast(v);
                    } else if (color[v] != c) return false;
                }
            }
        }

        return true;
    }

    /**
     * 匈牙利算法——二分图的最大匹配
     * 两边每个点只能连一条边 求最多连线数   例子：男女匹配doge 牵红线
     * a要匹配b 如果b已经匹配c了 尝试让c匹配其他点
     * 时间复杂度 最坏O(N*M)
     */
    static int maxMatch(int n1, int n2, List<Integer>[] adj) {
        int ans = 0;
        int[] match = new int[n2 + 1];//右侧匹配的左侧点
        for (int i = 1; i <= n1; i++) {
            boolean[] st = new boolean[n2 + 1];//标记当前递归中使用的点
            if (find(i, match, st, adj)) ans++;

        }
        return ans;
    }

    private static boolean find(int i, int[] match, boolean[] st, List<Integer>[] adj) {
        for (int v : adj[i]) {
            if (st[v]) continue;
            st[v] = true;
            if (match[v] == 0 || find(match[v], match, st, adj)) {
                match[v] = i;
                return true;
            }
        }
        return false;
    }

    //拓扑排序
    static class topo {
        static void go() {
            int n = 5, m = 11;
            int[][] edges = new int[m][];
//            for (int i = 0; i < m; i++) {
//                edges[i]=new int[]{ni(),ni()};
//            }
            List<Integer>[] adj = build1(n, edges);
            int[] deg = new int[n + 1];
            List<Integer> ans = new ArrayList<>();
            for (int[] edge : edges) {
                deg[edge[1]]++;
            }
            Deque<Integer> deque = new ArrayDeque<>();
            for (int i = 1; i <= n; i++) {
                if (deg[i] == 0) deque.add(i);
            }
            while (!deque.isEmpty()) {
                int p = deque.pollFirst();
                ans.add(p);
                for (Integer v : adj[p]) {
                    if (--deg[v] == 0) deque.addLast(v);
                }
            }
            if (ans.size() == n) {
                for (Integer v : ans) {
                    System.out.print(v + " ");
                }
                System.out.println();
            } else System.out.println(-1);
        }
    }


    /**
     * 棋盘
     */
    static int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    //左上角到右下角最短距离    BFS
    static int minDist(int[][] board) {
        int n = board.length, m = board[0].length;
        boolean[][] vis = new boolean[n][m];
        Deque<int[]> deque = new ArrayDeque<>();
        deque.addLast(new int[]{0, 0});
        vis[0][0] = true;
        int step = -1;
        while (!deque.isEmpty()) {
            step++;
            int size = deque.size();
            for (int u = 0; u < size; u++) {
                int[] p = deque.pollFirst();
                int i = p[0], j = p[1];
                if (i == n - 1 && j == m - 1) return step;
                for (int[] dir : dirs) {
                    int x = i + dir[0], y = j + dir[1];
                    if (x < 0 || x >= n || y < 0 || y >= m) continue;
                    if (board[x][y] == 1) continue;

                    if (vis[x][y]) continue;
                    vis[x][y] = true;
                    deque.addLast(new int[]{x, y});
                }
            }

        }
        return -1;
    }

    /**
     * other
     */

    //读入边
//    int[][]edges=new int[m][];
//        for (int i = 0; i < m; i++) {
//        edges[i]=new int[]{ni(),ni()};
//    }

    /**
     * 三色标记
     * lc.802
     */
    static class Solution802 {
        public List<Integer> eventualSafeNodes(int[][] graph) {
            int n = graph.length;
            int[] color = new int[n];
            List<Integer> ans = new ArrayList<Integer>();
            for (int i = 0; i < n; ++i) {
                if (safe(graph, color, i)) {
                    ans.add(i);
                }
            }
            return ans;
        }

        public boolean safe(int[][] graph, int[] color, int x) {
            if (color[x] > 0) {//访问过了  看是灰还是黑  得出是否安全
                return color[x] == 2;
            }
            color[x] = 1;//入栈
            for (int y : graph[x]) { //当前点能到达的点
                if (!safe(graph, color, y)) {
                    return false;//不安全还是灰色
                }
            }
            color[x] = 2;//如果安全标记为黑
            return true;
        }
    }


}
