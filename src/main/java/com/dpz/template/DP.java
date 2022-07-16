package com.dpz.template;

//背包问题
public class DP {

    static class Backpack {
        //    //总容量不超过V的情况下的最大价值
//    int N = ni(), V = ni();
//    int[] v = new int[N];//容量
//    int[] w = new int[N];//价值
//        for (int i = 0; i < N; i++) {
//        v[i] = ni();
//        w[i] = ni();
//    }

        /**
         * 01背包 物品只有一个  求不超过容量时的最大价值
         */
        static class _01BackPack {
            /**
             * 二维解法
             *
             * @param v 容量
             * @param w 价值
             */
            static int _01bp2d(int N, int V, int[] v, int[] w) {
                //总容量不超过V的情况下的最大价值
                int[][] dp = new int[N + 1][V + 1];//前i个物品 容量不超过j的最大价值
                for (int i = 1; i <= N; i++) {
                    for (int j = 1; j <= V; j++) {
                        dp[i][j] = dp[i - 1][j];
                        if (j >= v[i - 1]) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - v[i - 1]] + w[i - 1]);
                    }
                }
                return dp[N][V];
            }

            static int _01bp1d(int N, int V, int[] v, int[] w) {
                //总容量不超过V的情况下的最大价值
                int[] dp = new int[V + 1];//前i个物品 容量不超过j的最大价值
                for (int i = 1; i <= N; i++) {
                    for (int j = V; j > 0; j--) {
                        if (j >= v[i - 1]) dp[j] = Math.max(dp[j], dp[j - v[i - 1]] + w[i - 1]);
                    }
                }
                return dp[V];
            }

        }

        /**
         * 完全背包  物品无限
         */
        static class InfBackPack {
            static int infBp(int N, int V, int[] v, int[] w) {
                //总容量不超过V的情况下的最大价值
                int[] dp = new int[V + 1];//容量不超过i的最大价值
                for (int i = 1; i <= V; i++) {
                    for (int j = 0; j < N; j++) {
                        if (v[j] <= i) {
                            dp[i] = Math.max(dp[i], dp[i - v[j]] + w[j]);
                        }
                    }
                }
                return dp[V];
            }
        }

        /**
         * 多重背包 每个物品的数量有限  二进制优化   转化为01背包问题  复杂度O(N*V*logS)
         * https://www.acwing.com/problem/content/5/
         */
        static class MultiBackPack {
        }

        /**
         * 分组背包 背个物品属于一个组  每个组最多只能选择一个物品
         */
        static class GroupBackPack {
            //@param N 物品组数
            static int gBp(int N, int V, int[][] v, int[][] w) {
                //总容量不超过V的情况下的最大价值
                int[] dp = new int[V + 1];//容量不超过j的最大价值
                for (int g = 1; g <= N; g++) {
                    for (int i = V; i > 0; i--) {
                        for (int j = 0; j < w[g - 1].length; j++) {
                            if (v[g - 1][j] <= i) dp[i] = Math.max(dp[i], dp[i - v[g - 1][j]] + w[g - 1][j]);
                        }
                    }
                }
                return dp[V];
            }
        }
    }

    //区间DP
    static class Interval {
        //https://www.acwing.com/problem/content/284/
        static int acwing282(int n, int[] nums) {
            int[] sum = new int[n + 1];
            for (int i = 0; i < n; i++) {
                sum[i + 1] = sum[i] + nums[i];
            }
            int[][] dp = new int[n][n];//区间[i,j]合并为一个的最小代价

            for (int len = 2; len <= n; len++) {
                for (int l = 0, r = l + len - 1; r < n; l++, r++) {
                    dp[l][r] = Integer.MAX_VALUE;
                    //[u,r]合并为一堆
                    for (int u = l + 1; u <= r; u++) {
                        dp[l][r] = Math.min(dp[l][r], dp[l][u - 1] + dp[u][r] + sum[r + 1] - sum[l]);
                    }
                }
            }
            return dp[0][n - 1];
        }
    }

    //记忆化搜索
    //https://www.acwing.com/problem/content/903/
    static int maxPath(int[][] grid) {
        int max = 0;
        int m = grid.length, n = grid[0].length;
        Integer[][] dp = new Integer[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, dfs903(grid, i, j, dp));
            }
        }
        return max;
    }

    private static int dfs903(int[][] grid, int i, int j, Integer[][] dp) {
        if (dp[i][j] == null) {
            int h = grid[i][j];
            int max = 0;
            if (i - 1 >= 0 && grid[i - 1][j] < h) max = Math.max(max, dfs903(grid, i - 1, j, dp));
            if (j - 1 >= 0 && grid[i][j - 1] < h) max = Math.max(max, dfs903(grid, i, j - 1, dp));
            if (i + 1 < grid.length && grid[i + 1][j] < h) max = Math.max(max, dfs903(grid, i + 1, j, dp));
            if (j + 1 < grid[i].length && grid[i][j + 1] < h) max = Math.max(max, dfs903(grid, i, j + 1, dp));
            dp[i][j] = max + 1;
        }
        return dp[i][j];
    }

    //树形DP
    //https://www.acwing.com/problem/content/287/


    //编辑距离
    static public int minDistance(String word1, String word2) {
        int m=word1.length(),n=word2.length();
        int[][]dp=new int[m+1][n+1];
        for(int i=1;i<=m;i++)dp[i][0]=i;
        for(int j=1;j<=n;j++)dp[0][j]=j;
        for(int i=1;i<=m;i++){
            char a=word1.charAt(i-1);
            for(int j=1;j<=n;j++){
                char b=word2.charAt(j-1);
                if(a==b)dp[i][j]=dp[i-1][j-1];
                else dp[i][j]=Math.min(dp[i-1][j],Math.min(dp[i][j-1],dp[i-1][j-1]))+1;
            }
        }
        return dp[m][n];
    }



}




