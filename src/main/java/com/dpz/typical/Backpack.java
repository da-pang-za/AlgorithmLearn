package com.dpz.typical;

public class Backpack {

    //
    class Solution01背包 {
        //每个物品只能用一次  价值最大  N 物品数   C 容量
        //dp[N][C+1]  解法
        public int maxValue1(int N, int C, int[] v, int[] w) {
            int[][] dp = new int[N][C + 1];
            // 先处理「考虑第一件物品」的情况
            for (int i = 0; i <= C; i++) {
                dp[0][i] = i >= v[0] ? w[0] : 0;
            }
            // 再处理「考虑其余物品」的情况
            for (int i = 1; i < N; i++) {
                for (int j = 0; j < C + 1; j++) {
                    // 不选该物品
                    int n = dp[i - 1][j];
                    // 选择该物品，前提「剩余容量」大于等于「物品体积」
                    int y = j >= v[i] ? dp[i - 1][j - v[i]] + w[i] : 0;
                    dp[i][j] = Math.max(n, y);
                }
            }
            return dp[N - 1][C];
        }

        //计算当前行的时候  只需要前1行
        //存两行    即滚动数组优化
        public int maxValue2(int N, int C, int[] v, int[] w) {
            int[][] dp = new int[2][C+1];
            // 先处理「考虑第一件物品」的情况
            for (int i = 0; i < C + 1; i++) {
                dp[0][i] = i >= v[0] ? w[0] : 0;
            }
            // 再处理「考虑其余物品」的情况
            for (int i = 1; i < N; i++) {
                for (int j = 0; j < C + 1; j++) {
                    // 不选该物品
                    int n = dp[(i-1)&1][j];//第一行为dp[1]
                    // 选择该物品
                    int y = j >= v[i] ? dp[(i-1)&1][j-v[i]] + w[i] : 0;
                    dp[i&1][j] = Math.max(n, y);
                }
            }
            return dp[(N-1)&1][C];
        }
        //dp[C+1] 解法     根据状态转移方程：
        //当前格子只依赖于「上一个格子的位置」以及「上一个格子的左边位置」
        //将求解第  行格子的顺序「从 0 到 c 」改为「从 c 到 0 」
        // (每次只改变后面 不影响前面继续求解)
        public int maxValue3(int N, int C, int[] v, int[] w) {
            int[] dp = new int[C + 1];
            for (int i = 0; i < N; i++) {
                for (int j = C; j >= v[i]; j--) {
                    // 不选该物品
                    int n = dp[j];
                    // 选择该物品
                    int y = dp[j-v[i]] + w[i];
                    dp[j] = Math.max(n, y);
                }
            }
            return dp[C];
        }
    }

    class Solution完全背包 {
        //物品是无限的  容量为C时的最大价值

        //朴素方法   套用01背包，枚举每个物品取的次数   容量是v 价值是w
        //时间复杂度： N*C*C
        public int maxValue1(int N, int C, int[] v, int[] w) {
            int[][] dp = new int[N][C + 1];

            // 先预处理第一件物品
            for (int j = 0; j <= C; j++) {
                // 显然当只有一件物品的时候，在容量允许的情况下，能选多少件就选多少件
                int maxK = j / v[0];
                dp[0][j] = maxK * w[0];
            }

            // 处理剩余物品
            for (int i = 1; i < N; i++) {
                for (int j = 0; j <= C; j++) {
                    // 不考虑第 i 件物品的情况（选择 0 件物品 i）
                    int n = dp[i - 1][j];
                    // 考虑第 i 件物品的情况
                    int y = 0;
                    for (int k = 1 ;; k++) {
                        if (j < v[i] * k) {
                            break;
                        }
                        y = Math.max(y, dp[i - 1][j - k * v[i]] + k * w[i]);
                    }
                    dp[i][j] = Math.max(n, y);
                }
            }
            return dp[N - 1][C];
        }

        //标准完全背包的解法
        //按朴素法的dp方程展开后 可以发现重复项   得到优化：
        //时间复杂度：N*C
        public int maxValue2(int N, int C, int[] v, int[] w) {
            int[] dp = new int[C + 1];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j <= C; j++) {
                    // 不考虑第 i 件物品的情况（选择 0 件物品 i）
                    int n = dp[j];
                    // 考虑第 i 件物品的情况
                    //前i个数 当前容量如果取 加在前i个数上一个容量上 在上一个容量基础上多取一个
                    int y = j - v[i] >= 0 ? dp[j - v[i]] + w[i] : 0;
                    dp[j] = Math.max(n, y);
                }
            }
            return dp[C];
        }

    }


}
