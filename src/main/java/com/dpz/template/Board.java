package com.dpz.template;

import java.util.*;

//棋盘模板题   BFS
public class Board {

    static class Solution {
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public List<List<Integer>> highestRankedKItems(int[][] grid, int[] pricing, int[] start, int k) {
            Deque<List<Integer>> dq = new ArrayDeque<>();
            int low = pricing[0], high = pricing[1];
            int m = grid.length, n = grid[0].length;
            List<Integer> s = new ArrayList<>();
            s.add(start[0]);
            s.add(start[1]);
            dq.addLast(s);
            int step = -1;
            List<List<Integer>> ans = new ArrayList<>();
            int[][] dist = new int[m][n];
            for (int i = 0; i < m; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
            }

            boolean[][] vis = new boolean[m][n];

            while (!dq.isEmpty()) {
                step++;
                int sz = dq.size();
                for (int ii = 0; ii < sz; ii++) {
                    List<Integer> pos = dq.pollFirst();
                    int i = pos.get(0), j = pos.get(1);
                    vis[i][j] = true;
                    int p = grid[i][j];
                    if (p != 1 && p >= low && p <= high) {
                        ans.add(pos);
                        dist[i][j] = step;
                    }
                    for (int[] dir : dirs) {
                        int ni = i + dir[0], nj = j + dir[1];
                        if (ni < 0 || ni >= m || nj < 0 || nj >= n || grid[ni][nj] == 0 || vis[ni][nj]) continue;
                        vis[ni][nj] = true;
                        List<Integer> t = new ArrayList<>();
                        t.add(ni);
                        t.add(nj);
                        dq.addLast(t);
                    }
                }

            }

            return null;
        }
    }
    //棋盘问题  不出界
//    boolean validPos(int x, int y){
//        return x>=0&&x<m&&y>=0&&y<n;
//    }
}
