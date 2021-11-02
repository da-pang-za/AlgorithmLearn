package com.dpz.test;

import com.dpz.dataStructure.TreeNode;

import java.util.*;

class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        //正常：两顶点在不同图(group/leader)上    可能有一个点不在当前任何图上
        UnionFind unionFind = new UnionFind(2*edges.length);//所有点都不同则有2e个点
        for (int[] edge : edges) {
            //注意顶点值从1开始
           int a= unionFind.find(edge[0]-1);
           int b= unionFind.find(edge[1]-1);
            if(a==b )return edge;
            unionFind.union(a,b);
        }
        return null;//never run
    }

    private class UnionFind {

        private int[] parent;


        public UnionFind(int n) {
            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            while (x != parent[x]) {
                parent[x] = parent[parent[x]];
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