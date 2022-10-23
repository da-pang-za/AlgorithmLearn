package com.dpz.template;

import java.util.*;

public class ACWing {
    static int mod = (int) 1e9 + 7;

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Basic.divide("777", "32")));
    }

    /**
     * 排序算法
     *
     * @see SortAgrm10
     */
    static class Sort {
    }

    /**
     * 基础算法
     */
    static class Basic {
        //todo  待整理：第k个数  浮点二分 区间合并 差分
        //高精度加减乘除
        //加
        static String add(String a, String b) {
            StringBuilder sb = new StringBuilder();
            int carry = 0;
            int alen = a.length();
            int blen = b.length();
            int i = 0;
            for (; i < Math.min(alen, blen); i++) {
                int v = a.charAt(alen - i - 1) - '0' + b.charAt(blen - i - 1) - '0' + carry;
                carry = v / 10;
                sb.append(v % 10);
            }
            for (; i < alen; i++) {
                int v = a.charAt(alen - i - 1) - '0' + carry;
                carry = v / 10;
                sb.append(v % 10);
            }
            for (; i < blen; i++) {
                int v = b.charAt(blen - i - 1) - '0' + carry;
                carry = v / 10;
                sb.append(v % 10);
            }
            if (carry > 0) sb.append(carry);
            return sb.reverse().toString();
        }

        //两个非负整数比大小
        private static int gt(String a, String b) {
            if (a.length() != b.length()) return a.length() - b.length();
            return a.compareTo(b);
        }

        //两个正整数 不含前导零
        //减
        static String minus(String a, String b) {
            String neg = "";
            int f = gt(a, b);
            if (f < 0) {
                neg = "-";
                String tmp = a;
                a = b;
                b = tmp;
            }
            if (f == 0) return "0";

            int alen = a.length(), blen = b.length();
            int[] A = new int[a.length()];
            int[] B = new int[b.length()];
            for (int i = 0; i < a.length(); i++) {
                A[i] = a.charAt(i) - '0';
            }
            for (int i = 0; i < b.length(); i++) {
                B[i] = b.charAt(i) - '0';
            }
            StringBuilder sb = new StringBuilder();
            int i = alen - 1, j = blen - 1;
            for (; j >= 0; i--, j--) {
                int x = A[i], y = B[j];
                int v = x - y;
                if (v < 0) {
                    v += 10;
                    A[i - 1]--;
                }
                if (v < 0) System.out.println(v);
                sb.append(v);
            }
            for (; i >= 0; i--) {
                int v = A[i];
                if (v < 0) {
                    v += 10;
                    A[i - 1]--;
                }
                sb.append(v);
            }
            i = sb.length() - 1;
            while (sb.charAt(i) == '0') {
                sb.deleteCharAt(i--);
            }
            return neg + sb.reverse();
        }

        //给定两个非负整数（不含前导 0） A 和 B，请你计算 A×B 的值。 1≤A的长度≤100000,0≤B≤10000
        //m位和n位相乘  结果可能为 m+n-1或m+n位
        static String multiply(String a, String b) {
            if (b.equals("0") || a.equals("0")) return "0";

            int n = a.length();
            int m = b.length();
            StringBuilder sb = new StringBuilder();
            int[] nums = new int[n + m];
            for (int j = 0; j < m; j++) {
                for (int i = 0; i < n; i++) {
                    int x = a.charAt(n - i - 1) - '0';
                    int y = b.charAt(m - j - 1) - '0';
                    int v = x * y;
                    nums[i + j] += v;
                }
            }
            for (int i = 0; i < m + n - 1; i++) {
                nums[i + 1] += nums[i] / 10;
                nums[i] %= 10;
            }
            if (nums[m + n - 1] > 0) sb.append(nums[m + n - 1]);

            for (int i = nums.length - 2; i >= 0; i--) {
                sb.append(nums[i]);
            }
            return sb.toString();
        }

        //给定两个非负整数（不含前导 0） A，B，请你计算 A/B 的商和余数。 1≤A的长度≤100000,0≤B≤10000
        static String[] divide(String a, String b) {
            return divide(a, Integer.parseInt(b));
        }

        static String[] divide(String a, int b) {
            StringBuilder sb = new StringBuilder();
            int n = a.length();
            int r = 0;
            for (int i = 0; i < n; i++) {
                r = r * 10 + (a.charAt(i) - '0');

                int v = r / b;
                if (v != 0 || sb.length() != 0)
                    sb.append(r / b);

                r = (r - v * b);
            }
            return new String[]{sb.length() == 0 ? "0" : sb.toString(), String.valueOf(r)};
        }
    }


    /**
     * 数学
     */
    static class Math1 {
        //==============================质数相关===========================

        //快速幂 x^k mod p
        static long pow(long x, long k, long p) {
            long res = 1;
            while (k != 0) {
                if ((k & 1) != 0) res = res * x % p;
                x = x * x % p;
                k >>= 1;
            }
            return res;
        }

        /**
         * 质数
         */
        static class Prime {
            //试除法判断质数
            static boolean isPrime(int s) {
                for (int v = 2; v <= s / v; v++) {
                    if (s % v == 0) return false;
                }
                return s > 1;
            }

            //埃氏筛求质数 O(nloglogn)
            static void prime(int n) {
                boolean[] prim = new boolean[n + 1];
                Arrays.fill(prim, true);
                int ans = 0;//质数的个数
                for (int v = 2; v <= n; v++) {
                    if (!prim[v]) continue;
                    ans++;
                    //注意这里是v*v
                    for (long k = (long) v * v; k <= n; k += v) prim[(int) k] = false;
                }
            }

            /*
             * 线性筛   每次筛只与质数相乘   每个合数只会被其最小质因子筛掉
             * 埃氏筛其实还是存在冗余的标记操作，比如对于 4545 这个数，它会同时被 3,53,5 两个数标记为合数，
             * 因此我们优化的目标是让每个合数只被标记一次，这样时间复杂度即能保证为 O(n)
             */
            static public int countPrimes(int n) {
                List<Integer> primes = new ArrayList<>();
                boolean[] isPrime = new boolean[n + 1];
                Arrays.fill(isPrime, true);
                for (int v = 2; v <= n; ++v) {
                    if (isPrime[v]) primes.add(v);
                    //不管质数还是合数 都与质数相乘
                    for (int j = 0; j < primes.size() && primes.get(j) <= n / v; ++j) {
                        isPrime[v * primes.get(j)] = false;
                        if (v % primes.get(j) == 0) break;//primes[j]一定是v的最小质因子
                    }
                }
                return primes.size();
            }

            //质因数分解  列表中每一项是<因数，因数的指数>
            static List<List<Integer>> primeFact(int s) {
                var list = new ArrayList<List<Integer>>();
                for (int v = 2; v <= s / v; v++) {
                    if (s % v != 0) continue;
                    int c = 0;
                    while (s % v == 0) {
                        s /= v;
                        c++;
                    }
                    list.add(List.of(v, c));
                }
                if (s > 1) list.add(List.of(s, 1));
                return list;
            }

            /**
             * 欧拉函数   [1,N]中和N互质的数的个数
             * 需要求出所有的质因子 公式见：https://www.acwing.com/problem/content/875/
             */
            static int EulerFun(int n) {
                var list = primeFact(n);
                long ans = n;
                for (var e : list) ans = ans / e.get(0) * (e.get(0) - 1);
                return (int) ans;
            }

            /**
             * 筛法求欧拉函数  求 1∼n 中每个数的欧拉函数之和  利用递推公式+线性筛
             * https://www.acwing.com/video/299/
             */
            static long EulerSum(int n) {
                List<Integer> primes = new ArrayList<>();
                boolean[] isPrime = new boolean[n + 1];
                int[] el = new int[n + 1];
                el[1] = 1;
                Arrays.fill(isPrime, true);
                for (int v = 2; v <= n; ++v) {
                    if (isPrime[v]) {
                        primes.add(v);
                        el[v] = v - 1;//质数
                    }
                    //不管质数还是合数 都与质数相乘
                    for (int j = 0; j < primes.size() && primes.get(j) <= n / v; ++j) {
                        isPrime[v * primes.get(j)] = false;
                        int ne = v * primes.get(j);
                        if (v % primes.get(j) == 0) {
                            //gcd(v,p[j])=p[j]  分子分母各消去一项
                            el[ne] = el[v] * primes.get(j);
                            break;//primes[j]一定是v的最小质因子
                        } else {//互质 gcd(v,p[j])=1
                            el[ne] = el[v] * (primes.get(j) - 1);
                        }
                    }
                }
                long ans = 0;
                for (int i = 1; i <= n; i++) {
                    ans += el[i];
                }
                return ans;
            }
        }


        //约数相关
        static class Divisor {
            /**
             * 试除法求约数 O(√n) 推论:约数个数不超过2√n
             * tips: 1e6以内 最多240个约数 2e9以内1536
             * 常用上界:N^(1/3) https://codeforces.com/blog/entry/14463
             */
            List<Integer> getDivisors(int n) {
                List<Integer> ans = new ArrayList<>();
                for (int v = 1; v <= n / v; v++) {
                    if (n % v == 0) {
                        ans.add(v);
                        if (n / v != v) ans.add(n / v);
                    }
                }
                ans.sort(Integer::compare);
                return ans;
            }

            /**
             * 约数个数   求数组a元素乘积的约数个数(无重复) 设乘积为N
             * 质因数分解部分为每个数O(√n)(没细分析)  乘积部分复杂度为质因数个数
             * 一个数可以表示为 N=p1^a1*p2^a2...*pk^ak   其中pi是质数
             * 每个质数取任意数量，都构成不同的约数,数量组合和约数一一对应
             * 因此 约数个数 (1+a1)*(1+a2)...*(1+a3)  每个质数[0,ai]个
             */
            long divisorCnt(int[] a) {
                long ans = 1;
                //统计所有质因子的数量
                HashMap<Integer, Integer> cnt = new HashMap<>();
                for (int v : a) {
                    for (int i = 2; i <= v / i; i++) {
                        while (v % i == 0) {
                            v /= i;
                            cnt.put(i, cnt.getOrDefault(i, 0) + 1);
                        }
                    }
                    if (v > 1) cnt.put(v, cnt.getOrDefault(v, 0) + 1);
                }
                for (int v : cnt.values()) ans = ans * (v + 1) % mod;
                return ans;
            }

            /**
             * 求数组a元素乘积的约数之和(无重复) 设乘积为N
             * 每个质因子取一定个数作为一个约数 复杂度O(logN+质因子个数)
             * 约数之和 (1+p1+p1^2+...p1^a1)*...*(1+pk+..+pk^ak) 展开后每一项对应一个约数
             */
            long divisorSum(int[] a) {
                long ans = 1;
                //统计所有质因子的数量
                HashMap<Integer, Integer> cnt = new HashMap<>();
                for (int v : a) {
                    for (int i = 2; i <= v / i; i++) {
                        while (v % i == 0) {
                            v /= i;
                            cnt.put(i, cnt.getOrDefault(i, 0) + 1);
                        }
                    }
                    if (v > 1) cnt.put(v, cnt.getOrDefault(v, 0) + 1);
                }
                for (var e : cnt.entrySet()) {
                    long v = e.getKey(), c = e.getValue();
                    long t = 1;
                    while (c-- > 0) t = (t * v + 1) % mod;//tips 秦九韶
                    ans = ans * t % mod;
                }
                return ans;
            }

            //求最大公约数   欧几里得算法     辗转相除法
            static long gcd(long a, long b) {
                if (b == 0) return a;
                return gcd(b, a % b);
            }

            /**
             * 扩展欧几里得算法  求gcd 顺便求系数 x,y
             * https://www.acwing.com/problem/content/879/
             * 求 ax+by = gcd(a,b) 的解 (x,y)
             * <p>
             * 裴蜀定理
             * 对于任意正整数a,b   一定存在整数x,y 使得 ax+by=gcd(a,b)
             * gcd(a,b)是a,b能构造出的最小正整数
             * 方程ax+by=c 有解的充要条件是c为gcd(a,b)的倍数
             * 利用扩展欧几里得算法  构造(x,y)
             * 公式推导：https://www.acwing.com/solution/content/1393/
             */
            static long exGcd(long a, long b, long[] xy) {
                if (b == 0) {
                    xy[0] = 1;
                    xy[1] = 0;
                    return a;
                }
                long ans = exGcd(b, a % b, xy);
                long x = xy[0], y = xy[1];
                xy[0] = y;
                xy[1] = x - a / b * y;
                return ans;
            }

        }


        //==============================博弈论 game===========================

        //Nim游戏  https://www.acwing.com/activity/content/problem/content/961/
        static boolean Nim(int[] stones) {
            int ans = 0;
            for (int v : stones) ans ^= v;
            return ans != 0;
        }

        //博弈论  集合-Nim游戏 https://www.acwing.com/activity/content/problem/content/963/
        //SG函数 https://www.acwing.com/solution/content/23435/
        //用图表示局面  结束局面SG值被定义为0  其他局面SG值为无法到达的最小自然数
        //0为必败态  非0为必胜态       一步 0只能到非0   非0一定可以到0
        //利用sg值状态转移 转化为经典Nim游戏
        static int NimMax = 10010;

        static boolean NimSet(int[] stones, int[] canUse) {
            int[] f = new int[NimMax];//   看每一堆石子是否可以操作
            Arrays.fill(f, -1);
            int ans = 0;
            for (int x : stones) ans ^= sg1(x, f, canUse);
            return ans != 0;
        }

        //重点理解sg定理
        private static int sg1(int x, int[] f, int[] canUse) {
            if (f[x] != -1) return f[x];
            HashSet<Integer> set = new HashSet<>();
            for (int v : canUse) {
                if (x >= v) set.add(sg1(x - v, f, canUse));
            }
            for (int i = 0; ; i++) {
                if (!set.contains(i)) return f[x] = i;
            }
        }

        //==============================博弈论 game===========================


        //==============================排列组合===========================
        //全排列  按字典序
        static class FullArray {
            static int n;

            static List<Integer> list = new ArrayList<>();
            static List<List<Integer>> ans = new ArrayList<>();

            static boolean[] used;

            FullArray(int n) {
                FullArray.n = n;
                dfs(0);
            }


            static void dfs(int cnt) {
                if (cnt == n) {
                    ans.add(new ArrayList<>(list));
                    return;
                }
                for (int i = 1; i <= n; i++) {
                    if (used[i]) continue;
                    used[i] = true;
                    list.add(i);
                    dfs(cnt + 1);
                    list.remove(list.size() - 1);
                    used[i] = false;
                }
            }
        }

        //逆元(b mod p 的逆元  p是质数)     用乘法代替除法
        //https://www.acwing.com/activity/content/problem/content/945/
        //利用费马小定理求逆元     b^(p-1) mod p = 1      b * b^(p-2) mod p= 1
        //因此b的逆元 b-1 = b^(p-2)
        static int inverse(int b, int p) {
            return (int) pow(b, p - 2, p);
        }

        /**
         * 组合数
         */
        //DP 求组合数   O(N^2)
        static int[][] comb = new int[2001][2001];

        static void dpComb() {
            comb[0][0] = 1;
            for (int i = 1; i <= 2000; i++) {
                comb[i][0] = 1;
                for (int j = 1; j <= i; j++) {
                    comb[i][j] = (comb[i - 1][j] + comb[i - 1][j - 1]) % mod;//是否选当前数
                }
            }
        }

        //递推求阶乘
        static long[] fac(int n, long p) {
            long[] f = new long[n + 1];
            f[0] = f[1] = 1;
            for (int i = 2; i <= n; i++) {
                f[i] = f[i - 1] * i % p;
            }
            return f;
        }

        //facR  利用逆元求阶乘的倒数 复杂度O(NlogP)
        static long[] facR(int n, long p) {
            long[] fr = new long[n + 1];
            fr[0] = fr[1] = 1;
            for (int i = 2; i <= n; i++) {
                fr[i] = fr[i - 1] * pow(i, p - 2, p) % p;
            }
            return fr;
        }

        static long[] fac = fac(10000, mod);
        static long[] facR = facR(10000, mod);

        //公式法求组合数 O(NlogP)  结合逆元  求  C(a,b) mod p      a!/(b! (a-b)!)   1≤b≤a≤10^5
        static long combine(int a, int b, int p) {
            //先预处理出阶乘   然后 每次 常数时间求解
            return (fac[a] * facR[b] % p) * facR[a - b] % p;
        }

        /**
         * 卢卡斯定理求组合数   C(a,b) 同余 C(a mod p , b mod p) * C(a/p ,b/p)    mod  p
         * C(a/p ,b/p) 这部分可以递归求解
         * https://www.acwing.com/problem/content/889/
         * 1≤b≤a≤10^18    1≤p≤105  todo
         */


        /**
         * 高精度求组合数   1≤b≤a≤5000    高精度得到实际结果  递推法
         * 先用质因数分解预处理 todo
         * 把组合数转化为质因子相乘的形式   然后用高精度乘法算
         * 分子的质因子倍数-分母的质因子倍数
         */


        /**
         * 卡特兰数
         * <p>
         * 括号序列问题https://www.acwing.com/problem/content/891/
         * 出栈序列问题https://www.acwing.com/video/66/
         * 从(0,0)走到(n,n) 且 路径上任意位置 (x,y) 都满足 x>=y  (在y=x直线之下)
         * 的路径方案数（只能向下或向右走）
         * 转化为不经过y=x+1这条直线(红线)的路径数
         * 每个经过红线到(n,n)的方案 都对应 一条到(n-1,n+1)的路径
         * 所以答案为C(2n,n)-C(2n,n-1)
         */
        static long Catalan(int n) {
            int p = mod;
            return (combine(2 * n, n, p) - combine(2 * n, n - 1, p) + p) % p;
        }
    }


    /**
     * 字符串算法
     */
    static class StringAlg {
        //给定一个表达式，其中运算符仅包含 +,-,*,/（加 减 乘 整除），
        // 可能包含括号，请你求出表达式的最终值。
        //详细条件见 https://www.acwing.com/problem/content/3305/
        static int eval(String expression) {
            HashMap<Character, Integer> priority = new HashMap<>();
            priority.put('+', 1);
            priority.put('-', 1);
            priority.put('*', 2);
            priority.put('/', 2);
            priority.put('(', 3);
            priority.put(')', 3);
            Deque<Integer> nums = new ArrayDeque<>();
            Deque<Character> opts = new ArrayDeque<>();
            for (int i = 0; i < expression.length(); ) {
                char c = expression.charAt(i);
                if (c == ')') {
                    while (opts.peekLast() != '(') {
                        cal(nums, opts.pollLast());
                    }
                    opts.pollLast();
                    i++;
                } else {
                    if (Character.isDigit(c)) {
                        int v = 0;
                        while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                            v = 10 * v + expression.charAt(i) - '0';
                            i++;
                        }
                        nums.addLast(v);
                    } else {
                        opts.addLast(c);
                        //先算高优先级的
                        Character opt = opts.pollLast();
                        Character pre;
                        while (!opts.isEmpty() && (pre = opts.peekLast()) != '(' &&
                                priority.get(pre) >= priority.get(opt)) {
                            cal(nums, opts.pollLast());
                        }
                        opts.addLast(opt);
                        i++;
                    }
                }

            }
            while (nums.size() > 1) {
                cal(nums, opts.pollLast());
            }
            return nums.pollLast();
        }

        private static void cal(Deque<Integer> nums, char opt) {
            int b = nums.pollLast(), a = nums.pollLast();
            if (opt == '+') nums.addLast(a + b);
            else if (opt == '-') nums.addLast(a - b);
            else if (opt == '*') nums.addLast(a * b);
            else if (opt == '/') nums.addLast(a / b);
        }

        //单调栈  左边第一个小于当前位置的数
        int[] MonoStack(int[] nums, int n) {
            Deque<Integer> dq = new ArrayDeque<>();
            int[] ans = new int[n];
            //维护递增栈
            for (int i = 0; i < n; i++) {
                int v = nums[i];
                while (!dq.isEmpty() && dq.peekLast() >= v) dq.pollLast();
                if (dq.isEmpty()) ans[i] = -1;
                else ans[i] = dq.peekLast();
                dq.addLast(v);
            }
            return ans;
        }

        //单调队列 滑动窗口最大值最小值   区间长度为k
        void MonoDeque(int n, int k, int[] nums) {
            Deque<Integer> deque1 = new ArrayDeque<>();//维护递减队列 求最大值
            int[] max = new int[n - k + 1];

            for (int i = 0; i < n; i++) {
                int v = nums[i];
                while (!deque1.isEmpty() && i - deque1.peekFirst() >= k) deque1.pollFirst();
                while (!deque1.isEmpty() && v > nums[deque1.peekLast()]) deque1.pollLast();
                deque1.addLast(i);
                if (i >= k - 1) max[i - k + 1] = nums[deque1.peekFirst()];
            }

            Deque<Integer> deque2 = new ArrayDeque<>();//维护递增队列 求最小值
            int[] min = new int[n - k + 1];
            for (int i = 0; i < n; i++) {
                int v = nums[i];
                while (!deque2.isEmpty() && i - deque2.peekFirst() >= k) deque2.pollFirst();
                while (!deque2.isEmpty() && v < nums[deque2.peekLast()]) deque2.pollLast();
                deque2.addLast(i);
                if (i >= k - 1) min[i - k + 1] = nums[deque2.peekFirst()];
            }
        }

        /**
         * 字符串哈希 rolling hash
         * 判断子串 [l1,r1]  [l2,r2]是否相同  note 下标从1开始
         */
        static class StringHash {
            //todo  base 和 mod 优化   返回long范围的hash值
            final int base1 = 1313131;
            final int base2 = 500007;
            final int mod = 1000_000_007;
            long[] hash1, p1, hash2, p2;

            public StringHash(String s) {
                int n = s.length();
                hash1 = new long[n + 1];
                p1 = new long[n + 1];
                p1[0] = 1;
                hash2 = new long[n + 1];
                p2 = new long[n + 1];
                p2[0] = 1;
                for (int i = 0; i < n; i++) {
                    hash1[i + 1] = (base1 * hash1[i] + s.charAt(i)) % mod;
                    p1[i + 1] = p1[i] * base1 % mod;
                    hash2[i + 1] = (base2 * hash2[i] + s.charAt(i)) % mod;
                    p2[i + 1] = p2[i] * base2 % mod;
                }
            }

            public long h1(int l, int r) {
                return hash(l, r, hash1, p1);
            }

            public long h2(int l, int r) {
                return hash(l, r, hash2, p2);
            }

            //判断两个子串是否相等 单哈希
            public boolean eq1(int l1, int r1, int l2, int r2) {
                return h1(l1, r1) == h1(l2, r2);
            }

            //判断两个子串是否相等 双哈希
            public boolean eq2(int l1, int r1, int l2, int r2) {
                return h1(l1, r1) == h1(l2, r2) && h2(l1, r1) == h2(l2, r2);
            }

            long hash(int l, int r, long[] h, long[] p) {
                return (h[r] - h[l - 1] * p[r - l + 1] % mod + mod) % mod;
            }
        }


        /**
         * KMP   https://www.zhihu.com/question/21923021/answer/37475572
         * s中是否有子串是p
         * s长度为M p长度为N   时间复杂度O(M+N)   note 证明：j最多增加n次,所以最多回退n次 整体为线性
         * 暴力做法：枚举s的每个位置作为开头 每次不匹配换下一个位置  最坏O(M*N)
         * 优化 匹配不成功后，p最少移动多少 note 当前正在匹配的位置之前都匹配了
         * next[i]=j  =>  p[0,j]=p[i-j+1,i]  note 最长(且长度小于N的)相同的前后缀 长度为j
         * 这样就可以直接从p[j]继续匹配
         * 字符串下标从0开始
         */
        List<Integer> KMP_ANS = new ArrayList<>();

        int KMP(String s, String p) {
            int m = s.length(), n = p.length();
            int[] next = new int[n];
            //求next  p和p自己匹配  从1开始
            for (int i = 1, j = 0; i < n; i++) {
                char c = p.charAt(i);
                while (j > 0 && c != p.charAt(j)) j = next[j - 1];
                if (c == p.charAt(j)) j++;
                next[i] = j;
            }
            //匹配
            for (int i = 0, j = 0; i < m; i++) {
                char c = s.charAt(i);
                //s[i]和p[j]进行匹配
                while (j > 0 && c != p.charAt(j)) j = next[j - 1];
                if (c == p.charAt(j)) j++;
                //返回从0开始的下标
                if (j == n) {
                    KMP_ANS.add(i - n + 1);//匹配所有位置
                    j = next[n - 1];//继续左移
                }
                //if (j == n) return i - n;//匹配最早位置
            }
            return -1;
        }

        /**
         * Z-func   在 O(∣a∣+∣b∣) 求a的所有后缀 与 模式串 b 的 LCP 最长公共前缀
         * 模板题 https://www.luogu.com.cn/problem/P5410
         * 讲解 https://www.luogu.com.cn/blog/ericyan2008/Solution-P5410
         */
        int zfunc(){
            return 0;//todo
        }

    }

    /**
     * 贪心
     */
    static class Greedy {
        //推公式
        //刷杂技的牛 https://www.acwing.com/problem/content/127/
        //考虑交换相邻两个的影响  https://www.acwing.com/video/122/
        static int acwing125(int n, int[] w, int[] s) {
            Integer[] arr = new Integer[n];
            for (int i = 0; i < n; i++) {
                arr[i] = i;
            }
            //大的放下/右
            Arrays.sort(arr, (a, b) -> Integer.compare(w[a] + s[a], w[b] + s[b]));
            int W = 0;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                max = Math.max(max, W - s[arr[i]]);
                W += w[arr[i]];
            }
            return max;
        }

        //哈夫曼问题 哈夫曼编码   https://www.acwing.com/problem/content/150/
        //两个最小值一定在最下面   考虑交换后的情况可以证明
        //最优子结构
        int Huffman(int n, int[] nums) {
            var pq = new PriorityQueue<Integer>(Integer::compare);
            for (int i = 0; i < n; i++) pq.add(nums[i]);
            int tot = 0;
            while (pq.size() > 1) {
                int v = pq.poll() + pq.poll();
                tot += v;
                pq.add(v);
            }
            return tot;
        }
    }

    /**
     * 动态规划 DP
     *
     * @see DP
     */
    static class DP1 {

    }

    /**
     * 图论
     *
     * @see Graph
     */
    static class G {
    }

}






