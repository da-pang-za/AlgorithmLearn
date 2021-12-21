package com.dpz.dataStructure;
//并查集demo
public class UnionFindDemo {


    private class UnionFind {

        private int[] parent;

        public UnionFind(int n) {
            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        //找到leader
        public int find(int x) {
            while (x != parent[x]) {
//                parent[x] = parent[parent[x]];  可有可无
                x = parent[x];
            }
            return x;
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            parent[rootX] = rootY;
        }
    }
}

