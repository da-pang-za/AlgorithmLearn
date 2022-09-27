package com.dpz.template;


public class DP {
    //背包
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
         * 二维费用的01背包：https://www.acwing.com/problem/content/8/
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

            /**
             * 一维优化 注意内层驯化是倒序的
             */
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
         * 二维费用的完全背包: https://codeforces.com/contest/543/problem/A
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
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) dp[i][0] = i;
        for (int j = 1; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            char a = word1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char b = word2.charAt(j - 1);
                if (a == b) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
            }
        }
        return dp[m][n];
    }

    //状压DP
    static class BitMask {
        //蒙德里安的梦想
        //https://www.acwing.com/activity/content/problem/content/1010/
        static void go() {
            //不含连续奇数0的状态   预处理
            boolean[] valid = new boolean[1 << 11];

            int N = 11, M = 10;

            for (int i = 0; i < 1 << 11; i++) {
                valid[i] = true;
                int u = i | (1 << M);
                while (u > 0) {
                    if (u % 2 == 1) u /= 2;
                    else {
                        if (u % 4 != 0) {
                            valid[i] = false;
                            break;
                        } else u /= 4;
                    }
                }
            }
            long[][] dp = new long[N + 1][1 << M];//前i行已经排好了 i行伸到i+1行的状态为j
            dp[0][0] = 1;
            for (int i = 1; i <= N; i++) {
                for (int j = 0; j < 1 << M; j++) {
                    for (int k = 0; k < 1 << M; k++) {
                        if ((j & k) != 0 || !valid[j | k]) continue;
                        dp[i][j] += dp[i - 1][k];
                    }
                }
            }
            System.out.println(dp[N][0]);

        }


    }
    //=======================算法提高课===========================

    /**
     * 数位DP
     * 不降数的个数https://www.acwing.com/problem/content/1084/
     * https://www.acwing.com/video/488/
     * 数n 表示为N= a[n-1] a[n-2] ... a[0]
     * 构造树形结构  左侧为当前位的值ui<ai 右边为ui=ai 直到a0
     */

    /**
     * https://leetcode.cn/problems/count-special-integers/
     * 参考@0x3F
     * 数位dp要点:
     * 1. 是否是最高位isHigh 最高位可以跳过
     * 2. 是否处理过的位都和n相同limit  则当前位不能超过num当前位h / 受限
     * 3. dp[i][j] 表示前i个位置已经处理过，状态为j(状态定义根据题目具体分析) //非最高位 非受限
     * 4. 考虑的是[1,n]    0 比较特殊
     */

    //无重复数字的个数  https://leetcode.cn/submissions/detail/354119636/
    static class COUNT_SPECIAL_INTEGERS {
        int[] num;
        Integer[][] dp;//可复用 非最高位 非受限

        public int countSpecialNumbers(int n) {
            char[] t = Integer.toString(n).toCharArray();
            num = new int[t.length];
            for (int i = 0; i < t.length; i++) {
                num[i] = t[i] - '0';
            }
            var len = num.length;
            dp = new Integer[len][1 << 10];
            return f(0, 0, true, true);
        }

        int f(int i, int used, boolean limit, boolean high) {
            if (i == num.length) return high ? 0 : 1;
            //可复用 非最高位 非受限
            if (!limit && !high && dp[i][used] != null)
                return dp[i][used];
            var ans = 0;
            if (high) //当前是最高位 可以跳过当前数位   位数低于num 一定不受限
                ans = f(i + 1, used, false, true);
            for (int u = high ? 1 : 0; u <= (limit ? num[i] : 9); ++u) // 枚举要填入的数字 d
                if (((used >> u) & 1) == 0) // u 不在 used 中   题目要求
                    ans += f(i + 1, used | (1 << u), limit && u == num[i], false);
            if (!limit && !high) dp[i][used] = ans;
            return ans;
        }
    }

    //数字1的个数  https://leetcode.cn/problems/number-of-digit-one/
    static class COUNT_DIGIT_ONE {
        int[] num;
        Integer[][] dp;//可复用 非最高位 非受限

        public int countDigitOne(int n) {
            char[] t = Integer.toString(n).toCharArray();
            num = new int[t.length];
            for (int i = 0; i < t.length; i++) {
                num[i] = t[i] - '0';
            }
            var len = num.length;
            dp = new Integer[len][len];
            return f(0, 0, true, true);
        }

        int f(int i, int cnt, boolean limit, boolean high) {
            if (i == num.length) return high ? 0 : cnt;
            //可复用 非最高位 非受限
            if (!limit && !high && dp[i][cnt] != null)
                return dp[i][cnt];
            var ans = 0;
            if (high) //当前是最高位 可以跳过当前数位   位数低于num 一定不受限
                ans = f(i + 1, cnt, false, true);
            for (int u = high ? 1 : 0; u <= (limit ? num[i] : 9); ++u) // 枚举要填入的数字 d
                ans += f(i + 1, cnt + (u == 1 ? 1 : 0), limit && u == num[i], false);
            if (!limit && !high) dp[i][cnt] = ans;
            return ans;
        }

    }

    //费用提前计算思想：https://www.acwing.com/solution/content/68062/

    /**
     * 斜率优化DP todo
     */
    /**
     * 单调队列优化DP todo
     */
    class MonoDequeDP{
        //公式变型优化 修剪草坪 https://www.acwing.com/problem/content/1089/
        //当前为i 用i则i-k不能用
        // 前i合法的最大值 f[i]=max(f[i-k-1]+s[i]-s[i-k],f[i-1])
        //

    }




}





