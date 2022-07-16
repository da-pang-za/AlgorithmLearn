package com.dpz.template;

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

    //等权（都为1）单源最短路  BFS
    static long[] bfs(int n, List<Integer>[] adj, int source) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[source] = 0;
        boolean[] vis = new boolean[n + 1];
        int d = 1;
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(source);vis[source]=true;
        while (!deque.isEmpty()) {
            int sz = deque.size();
            for (int i = 0; i < sz; i++) {
                int u = deque.pollFirst();
                for (var v : adj[u]) {
                    if(vis[v])continue;
                    vis[v]=true;
                    dist[v] = d;
                    deque.addLast(v);
                }
            }
            d++;
        }
        return dist;
    }

    //Dijkstra 单源最短路  复杂度 O(eloge)
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


    //Floyd算法 边权有可能为负数 已去除重边 保留最短的边
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

    //三色标记
    //lc.802
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
