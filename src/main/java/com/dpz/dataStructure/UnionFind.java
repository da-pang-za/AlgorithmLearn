package com.dpz.dataStructure;

import java.util.Arrays;

/**
 * 并查集模板   优化版
 * https://oi-wiki.org/ds/dsu/
 * 复杂度科普：https://leetcode-cn.com/problems/number-of-provinces/solution/jie-zhe-ge-wen-ti-ke-pu-yi-xia-bing-cha-0unne/
 */
public
class UnionFind {
    int[] parent;
    int[] size;
    int n;
    // 当前连通分量数目
    int setCount;

    public UnionFind(int n) {
        this.n = n;
        this.setCount = n;
        this.parent = new int[n];
        this.size = new int[n];
        Arrays.fill(size, 1);
        for (int i = 0; i < n; ++i) parent[i] = i;
    }

    public int find(int x) {
        //路径压缩  父亲更新为原父亲的祖先
        if (x != parent[x])parent[x] = find(parent[x]);
        return parent[x];
    }

    public boolean union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) return false;
        //按秩合并  x是size较大的
        //如果我们将一棵点数与深度都较小的集合树连接到一棵更大的集合树下，显然相比于另一种连接方案
        if (size[x] < size[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        parent[y] = x;
        size[x] += size[y];
        --setCount;
        return true;
    }
}


//带权/距离并查集  dist[i]代表i到p[i]的距离
//https://www.acwing.com/problem/content/description/242/
//带权/距离并查集  只是用路径压缩优化
class UnionFind_d {
    int[] parent;
    int[] dist;
    int n;

    public UnionFind_d(int n) {
        this.n = n;
        this.parent = new int[n];
        for (int i = 0; i < n; ++i) {
            parent[i] = i;
        }
        dist = new int[n];
    }

    public int find(int x) {
        //路径压缩  父亲更新为原父亲的祖先
        if (x != parent[x]) {
            int p = find(parent[x]);
            dist[x] += dist[parent[x]];
            parent[x] = p;
        }
        return parent[x];
    }
}