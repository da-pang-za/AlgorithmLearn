package com.dpz.template;

import java.util.*;

public class Graph {
    //todo 图论模板   建图+算法
    //建图模板    注意！！！  这里是无向图！！！
    //建图 1 list
    List<Integer>[] build1(int n, int[][] edges) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        return adj;
    }

    //建图 2  set
    HashSet<Integer>[] build2(int n, int[][] edges) {
        HashSet<Integer>[] adj = new HashSet[n];
        for (int i = 0; i < n; i++) adj[i] = new HashSet<>();
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        return adj;
    }

    //建图3 带权
    List<int[]>[] build3(int n, int[][] edges) {
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] edge : edges) {
            adj[edge[0]].add(new int[]{edge[1], edge[2]});
            adj[edge[1]].add(new int[]{edge[0], edge[2]});
        }
        return adj;
    }

    //Dijkstra 单源最短路  复杂度 O(eloge)
    long[] dijkstra(int n, List<int[]>[] adj, int source) {
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

    //建图  dijkstra模板  前向星
    class Solution1 {
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

    //dijkstra模板 map
    class Solution6032 {
        long INF = Long.MAX_VALUE / 3 - 10;
        int n;

        public long minimumWeight(int _n, int[][] edges, int src1, int src2, int dest) {
            n = _n;
            HashMap<Long, Long> weight = new HashMap<>();
            HashMap<Long, Long> weightR = new HashMap<>();
            HashMap<Integer, List<Integer>> adj = new HashMap<>();
            HashMap<Integer, List<Integer>> adjR = new HashMap<>();
            for (int i = 0; i < n; i++) {
                adj.putIfAbsent(i, new ArrayList<>());
                adjR.putIfAbsent(i, new ArrayList<>());
                weight.put(getKey(i, i), 0L);
                weightR.put(getKey(i, i), 0L);
            }
            for (int[] edge : edges) {
                int from = edge[0], to = edge[1], w = edge[2];
                adj.get(from).add(to);
                adjR.get(to).add(from);
                weight.put(getKey(from, to), Math.min(w, weight.getOrDefault(getKey(from, to), INF)));
                weightR.put(getKey(to, from), Math.min(w, weightR.getOrDefault(getKey(to, from), INF)));

            }
            long[] dd = dijkstra(dest, adjR, weightR);
            if (dd[src1] == INF || dd[src2] == INF) return -1;
            long[] s1 = dijkstra(src1, adj, weight);
            long[] s2 = dijkstra(src2, adj, weight);
            System.out.println(Arrays.toString(s1));
            System.out.println(Arrays.toString(s2));
            long ans = INF;
            for (int i = 0; i < n; i++) {
                ans = Math.min(ans, dd[i] + s1[i] + s2[i]);
            }
            return ans;
        }


        long[] dijkstra(int src, HashMap<Integer, List<Integer>> adj, HashMap<Long, Long> weight) {
            boolean[] vis = new boolean[n];
            long[] dist = new long[n];
//        int[]pre=new int[n];
//        Arrays.fill(pre,-1);
            Arrays.fill(dist, INF);
            dist[src] = 0;
            PriorityQueue<long[]> q = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));//dist小的先出
            q.add(new long[]{src, 0});
            while (!q.isEmpty()) {
                long[] poll = q.poll();//最小的
                int id = (int) poll[0];
                if (vis[id]) continue;
                vis[id] = true;
                for (int nx : adj.get(id)) {
                    long w = weight.get(getKey(id, nx));
                    if (dist[nx] > dist[id] + w) {
                        dist[nx] = dist[id] + w;
//                    pre[nx]=id;
                        q.add(new long[]{nx, dist[nx]});
                    }
                }
            }
            return dist;
        }

        long getKey(int a, int b) {
            return (long) a * 1000_000L + b;
        }

    }

    //三色标记
    //lc.802
    class Solution802 {
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
