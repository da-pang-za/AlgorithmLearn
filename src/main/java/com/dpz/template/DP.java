package com.dpz.template;

import java.util.*;

import static com.dpz.template.Const.*;

public class DP {
    static int INF = 0x3f3f3f3f;

    //背包
    static class Backpack {
        /**
         * 01背包 物品只有一个  求不超过容量时的最大价值  //物品不能重复选择
         * 从前i个物品里面选 容量不超过j f[i][j]=max(f[i-1][j],f[i-1][j-v[i]]+w[i])
         * 二维费用的01背包：https://www.acwing.com/problem/content/8/
         * 求具体方案:根据答案倒推  如果需要输出字典序最小 可以倒序遍历物品 这样倒推就是正序了
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
             * 一维优化 注意内层循环是倒序的
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
         * 完全背包  物品无限   //物品可以重复选择
         * 从前i个物品里面选 容量不超过j f[i][j]=max(f[i-1][j],f[i][j-v[i]]+w[i])
         * 二维费用的完全背包: https://codeforces.com/contest/543/problem/A
         */
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


        /**
         * 多重背包 每个物品的数量有限  物品根据二进制拆分 转化为01背包问题  复杂度O(N*V*logS)
         * https://www.acwing.com/problem/content/5/
         * 从低到高补1    s=10001   补到1111  (剩10再加上)
         */
        //价值 容量 数量   m最大容量
        static int multiBp(int[] A, int[] B, int[] C, int m) {
            int N = 11010, M = 2010;
            int[] v = new int[N], w = new int[N], f = new int[M];
            int n = A.length;
            int idx = 0;//从1开始
            for (int i = 0; i < n; i++) {
                int a = A[i], b = B[i], c = C[i];
                int k = 1;
                while (k <= c) {
                    idx++;
                    v[idx] = a * k;
                    w[idx] = b * k;
                    c -= k;
                    k *= 2;
                }
                if (c > 0) {
                    idx++;
                    v[idx] = a * c;
                    w[idx] = b * c;
                }
            }
            n = idx;
            //01背包
            for (int i = 1; i <= n; i++)
                for (int j = m; j >= v[i]; j--)
                    f[j] = Math.max(f[j], f[j - v[i]] + w[i]);
            return f[m];
        }
        /**
         * 多重背包——单调队列优化 复杂度O(N*V) todo
         * https://www.acwing.com/solution/content/6500/
         */

        /**
         * 分组背包 背个物品属于一个组  每个组最多只能选择一个物品
         */
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

        /**
         * 混合背包   01多重完全结合  每步转移只和当前物品有关 根据类型找到对应的转移方程
         * https://www.acwing.com/video/390/
         */
        //价值 容量 数量   m最大容量
        int mixedBp(int[] A, int[] B, int[] C, int m) {
            int N = 11010, M = 1010;
            int[] v = new int[N], w = new int[N];
            boolean[] inf = new boolean[N];
            int[][] f = new int[N][M];
            int n = A.length;
            int idx = 0;//从1开始
            for (int i = 0; i < n; i++) {
                int a = A[i], b = B[i], c = C[i];
                if (c == 0) {
                    idx++;
                    v[idx] = a;
                    w[idx] = b;
                    inf[idx] = true;
                    continue;
                }
                if (c == -1) c = 1;
                int k = 1;
                while (k <= c) {
                    idx++;
                    v[idx] = a * k;
                    w[idx] = b * k;
                    c -= k;
                    k *= 2;
                }
                if (c > 0) {
                    idx++;
                    v[idx] = a * c;
                    w[idx] = b * c;
                }
            }
            n = idx;
            //根据物品是否无限 选择转移方程
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j < v[i] && j <= m; j++) f[i][j] = f[i - 1][j];
                for (int j = v[i]; j <= m; j++) {
                    if (inf[i]) f[i][j] = Math.max(f[i - 1][j], f[i][j - v[i]] + w[i]);
                    else f[i][j] = Math.max(f[i - 1][j], f[i - 1][j - v[i]] + w[i]);
                }
            }
            return f[n][m];
        }
    }

    //区间DP
    static class IntervalDP {
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

    /**
     * 记忆化搜索
     * 滑雪:https://www.acwing.com/problem/content/903/
     */
    static class MemSearch {
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
    }

    /**
     * 树形DP
     */
    static class TreeDP {
        /**
         * 树的中心: https://www.acwing.com/problem/content/1075/
         * 换根DP 分别求出父节点的贡献和子节点的贡献 一般需要2次dfs
         * todo 代码不够精简
         */
        void getTreeCenter() {
        }
    }

    //状压DP
    static class BitMask {
        /**
         * 棋盘类问题 蒙德里安的梦想
         * https://www.acwing.com/activity/content/problem/content/1010/
         */
        static class Board {
            void go() {
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

        /**
         * 集合类问题
         * 重复覆盖 从当前到之后的
         * 精确覆盖 从之前的到当前的  todo
         */
        static class Set {
            /**
             * 重复覆盖问题 愤怒的小鸟
             * https://www.acwing.com/problem/content/526/
             */
            static class CoverDuplicate {
                void go(int n, double[][] pigs) {
//                double[][] pigs = new double[n][];
//                for (int i = 0; i < n; i++) {
//                    pigs[i] = new double[]{nd(), nd()};
//                }
                    int[][] f = new int[n][n];//覆盖i j 的 覆盖了哪些
                    for (int i = 0; i < n; i++) {
                        f[i][i] = 1 << i;
                        for (int j = 0; j < i; j++) {
                            int s = 0;
                            double x1 = pigs[i][0], y1 = pigs[i][1];
                            double x2 = pigs[j][0], y2 = pigs[j][1];
                            if (eq(x1, x2)) continue;
                            double a = (y1 / x1 - y2 / x2) / (x1 - x2);
                            double b = y1 / x1 - a * x1;
                            if (Double.compare(a, 0) >= 0) continue;//开口要向下
                            for (int k = 0; k < n; k++) {
                                double x = pigs[k][0], y = pigs[k][1];
                                if (eq(a * x * x + b * x, y)) s |= 1 << k;
                            }
                            f[i][j] = s;
                        }
                    }

                    int[] dp = new int[1 << n];
                    Arrays.fill(dp, INF);
                    dp[0] = 0;
                    for (int i = 0; i + 1 < 1 << n; i++) {
                        int u = 0;
                        //找第一个0
                        for (int j = 0; j < n; j++)
                            if (0 == ((i >> j) & 1)) {
                                u = j;
                                break;
                            }

                        for (int j = u; j < n; j++)
                            dp[i | f[j][u]] = Math.min(dp[i] + 1, dp[i | f[j][u]]);
//                if ((i | f[u][j]) == i)//不一定当前抛物线所经过的点之前都没用过
//                    dp[i] = Math.min(dp[i], dp[i ^ (f[u][j])] + 1);
                    }
                    System.out.println(dp[(1 << n) - 1]);
                }

                boolean eq(double a, double b) {
                    return Math.abs(a - b) < 1e-8;
                }
            }
        }

    }

    /**
     * 数位DP
     * 构造树形结构  左侧为当前位的值ui<ai 右边为ui=ai 直到a0
     * <p>
     * 模板
     * https://leetcode.cn/problems/count-special-integers/
     * 参考@0x3F
     * 数位dp要点:
     * 1. 是否是最高位isHigh 最高位可以跳过
     * 2. 是否处理过的位都和n相同limit  则当前位不能超过num当前位h / 受限
     * 3. dp[i][j] 表示前i个位置已经处理过，状态为j(状态定义根据题目具体分析) //非最高位 非受限
     * 4. 考虑的是[1,n]    0 比较特殊
     */
    static class NumBitDP {
        //无重复数字的个数  https://leetcode.cn/submissions/detail/354119636/
        static class COUNT_SPECIAL_INTEGERS {
            int[] num;//0存的是低位
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
            int[] num;//0存的是低位
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
    }

    /**
     * 斜率优化DP
     * 任务安排系列:
     * 任务安排1(费用提前计算思想):https://www.acwing.com/solution/content/68062/
     * 任务安排2(斜率优化详解):https://www.acwing.com/solution/content/35208/
     * 任务安排3(标准模板题 结合二分):https://www.acwing.com/problem/content/description/304/
     */
    static class SlopeOptDP {
        void go() {
            int n = ni(), S = ni();
            long[] st = new long[n + 1], sc = new long[n + 1];
            for (int i = 1; i <= n; i++) {
                st[i] = st[i - 1] + ni();
                sc[i] = sc[i - 1] + ni();
            }
            // fi=sti×sci+S×scn+min(fj−S×scj−sti×scj)
            // fj - (S+sti)scj = b    k = S+sti
            // i ↑  任务安排2 k ↑  任务安排3 k 不一定
            // j ↑  scj ↑
            // 任务安排2  队头小于等于k的 可以去掉  任务安排3 用二分找到答案
            // 维护新加入的斜率 作为下凸壳 如果斜率小于等于当前队尾的斜率则替换

            int[] q = new int[n + 1];
            int hh = 0, tt = 0;
            long[] f = new long[n + 1];
            f[0] = 0;//sc0=0 f0=0
            for (int i = 1; i <= n; i++) {
                long k = S + st[i];
                int l = 0, r = tt;
                while (l < r) {
                    int mid = l + r + 1 >> 1;
                    if (f[q[mid]] - f[q[mid - 1]] <= k * (sc[q[mid]] - sc[q[mid - 1]])) l = mid;
                    else r = mid - 1;
                }
//            out.println(q[l]);
                f[i] = st[i] * sc[i] + S * sc[n] + (f[q[l]] - S * sc[q[l]] - st[i] * sc[q[l]]);
                //这里有爆long的问题  用double
                while (tt > hh && (double) (f[q[tt]] - f[q[tt - 1]]) * (sc[i] - sc[q[tt]])
                        >= (double) (f[i] - f[q[tt]]) * (sc[q[tt]] - sc[q[tt - 1]]))
                    tt--;
                q[++tt] = i;
            }
            out.println(f[n]);
        }
    }

    /**
     * 单调队列优化DP
     */
    static class MonoDequeOptDP {
        /**
         * 公式变型优化 修剪草坪 https://www.acwing.com/problem/content/1089/
         * 划分为多个连续的子段 子段长度不超过k
         * 从当前位置开始选连续的j个  i-j不取  j[0,k]
         * 前i合法的最大值 f[i]=max(f[i-j-1]+s[i]-s[i-j])=max(f[i-j-1]-s[i-j])+s[i]
         * g[i]=f[i-1]-s[i],g[0]为i==j时即前i个数取i个(全取) 全取时f[i]整体为s[i] 再减去s[i] g[0]=0
         * 注意数组下标要从1开始 (否则g[0]无法定义 i为0的时候j可取0,1 还需要g[0-1-1])
         */
        long AcWing1089(long[] nums, int k) {
            int n = nums.length - 1;//下标从1开始的数组
            long[] s = new long[n + 1];
            for (int i = 1; i <= n; i++) s[i] = s[i - 1] + nums[i];
            long[] f = new long[n + 1], g = new long[n + 1];
            Deque<Integer> deque = new ArrayDeque<>();
            deque.addLast(0);
            for (int i = 1; i <= n; i++) {
                g[i] = f[i - 1] - s[i];
                while (!deque.isEmpty() && g[i] >= g[deque.peekLast()]) deque.pollLast();
                deque.addLast(i);
                if (deque.peekFirst() < i - k) deque.pollFirst();
                f[i] = g[deque.peekFirst()] + s[i];
            }
            return f[n];
        }

        /**
         * 烽火传递 所有长度为k的区间范围内至少选一个 求最小和
         * https://www.acwing.com/problem/content/1091/
         */
        void AcWing1091() {
            //f[i]= j in [i-m,i-1] min(f[j])+w[i]
            //ans=min(f[j])  [n-m+1,n]
            int n = ni(), m = ni();
            int[] w = new int[n + 1];
            for (int i = 1; i <= n; i++) w[i] = ni();
            int[] f = new int[n + 1];
            f[0] = 0;
            Deque<Integer> deque = new ArrayDeque<>();
            deque.addLast(0);
            for (int i = 1; i <= n; i++) {
                while (!deque.isEmpty() && deque.peekFirst() < i - m) deque.pollFirst();
                f[i] = f[deque.peekFirst()] + w[i];
                while (!deque.isEmpty() && f[i] <= f[deque.peekLast()]) deque.pollLast();
                deque.addLast(i);
            }
            if (n - m + 1 > deque.peekFirst()) deque.pollFirst();
            out.println(f[deque.peekFirst()]);
        }

        //其他题目 lc.1687 rating 2610  https://leetcode.cn/problems/delivering-boxes-from-storage-to-ports/
    }

    /**
     * 状态机DP
     */
    static class DFA_DP {
        //结合KMP的DP  todo
        //设计密码:https://www.acwing.com/activity/content/problem/content/1290/
        //加强版：https://leetcode.cn/problems/find-all-good-strings/


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
    }

    /**
     * 前缀和优化DP、
     */
    static class Other {
        /**
         * 前缀和优化DP  https://www.acwing.com/problem/content/description/274/
         */
        static class SumOptDP {
            void go() {
                int n = ni();
                int[] a = new int[n + 1], b = new int[n + 1];
                for (int i = 1; i <= n; i++) a[i] = ni();
                for (int i = 1; i <= n; i++) b[i] = ni();
                int[][] dp = new int[n + 1][n + 1];// a pre i last element is b[j]
                int ans = 0;
                for (int i = 1; i <= n; i++) {
                    int preMax = 0;//dp[i - 1][k]
                    for (int j = 1; j <= n; j++) {
                        dp[i][j] = dp[i - 1][j];
                        if (a[i] == b[j]) dp[i][j] = Math.max(dp[i][j], preMax + 1);
                        if (a[i] > b[j]) preMax = Math.max(preMax, dp[i - 1][j]);
//                if (a[i] == b[j]) {
//                    for (int k = 1; k < j; k++)
//                        if (a[i] > b[k]) dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + 1);
//                }
                        ans = Math.max(ans, dp[i][j]);
                    }
                }
                out.println(ans);
            }
        }
    }

}





