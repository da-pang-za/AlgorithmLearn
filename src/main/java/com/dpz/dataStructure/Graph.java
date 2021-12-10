package com.dpz.dataStructure;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Graph {

    //建图  dijkstra模板
    class Solution {
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
            n = _n; k = _k;
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
            PriorityQueue<int[]> q = new PriorityQueue<>((a, b)->a[1]-b[1]);//dist小的先出
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


}
