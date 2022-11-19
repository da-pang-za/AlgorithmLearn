package com.dpz.template;

import java.util.*;

import static com.dpz.template.Const.*;

/**
 * 数学
 */
public class Math1 {
    //==============================质数相关===========================

    //快速幂 x^k mod p
    static long pow(long x, long k, long p) {
        long res = 1;
        while (k != 0) {
            if ((k & 1) != 0) res = res * x % p;
            x = x * x % p;
//            x = mul(x, x, p);//龟速乘
            k >>= 1;
        }
        return res;
    }

    //龟速乘 防止快速幂中间值爆long
    static long mul(long a, long b, long c) {
        long res = 0;
        while (b != 0) {
            if ((b & 1) == 1) res = (res + a) % c;
            a = (a + a) % c;
            b >>= 1;
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
         *
         * todo 欧拉定理    欧拉数&同余
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

        /**
         * 求最大公约数   欧几里得算法     辗转相除法
         * 正确性&复杂度证明:https://oi-wiki.org/math/number-theory/gcd/
         */

        static long gcd(long a, long b) {
            if (b == 0) return a;
            return gcd(b, a % b);
        }

        /**
         * 扩展欧几里得算法  求gcd 顺便求系数 x,y
         * https://www.acwing.com/problem/content/879/
         * 求 ax+by = gcd(a,b) 的解 (x,y)
         * <p>
         * 裴蜀定理  https://baike.baidu.com/item/%E8%A3%B4%E8%9C%80%E5%AE%9A%E7%90%86/5186593
         * 对于任意正整数a,b   一定存在整数x,y 使得 ax+by=gcd(a,b)      //可推广到多个数
         * gcd(a,b)是a,b能构造出的最小正整数
         * 方程ax+by=c 有解的充要条件是c为gcd(a,b)的倍数
         * </p>
         * 利用扩展欧几里得算法  构造(x,y)
         * 公式推导：https://www.acwing.com/solution/content;/1393/
         * 求 ax+by = gcd(a,b) 的系数   先求 bx + (a%b)y = gcd(a,b) = gcd(b,a%b)  再展开即可
         * 通解ax + by = g 通解  x= x0 + k (b/g) y = y0 - k (a/g)  证明 todo
         * note 如果右侧 ax + by = t*g  通解只有x需要乘倍数 不乘倍数带入后发现仍然是解
         * todo x0 y0的范围是多少 为什么不会爆long
         * todo 扩展欧几里得是否要求ab都是正整数 还是任意整数
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
    static class COMBINE {
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
        long[] fac(int n, long p) {
            long[] f = new long[n + 1];
            f[0] = f[1] = 1;
            for (int i = 2; i <= n; i++) {
                f[i] = f[i - 1] * i % p;
            }
            return f;
        }

        //facR  利用逆元求阶乘的倒数 复杂度O(NlogP)
        long[] facR(int n, long p) {
            long[] fr = new long[n + 1];
            fr[0] = fr[1] = 1;
            for (int i = 2; i <= n; i++) {
                fr[i] = fr[i - 1] * pow(i, p - 2, p) % p;
            }
            return fr;
        }

        int N = 10000;
        long[] fac = fac(N, mod);
        long[] facR = facR(N, mod);

        //公式法求组合数 O(NlogP)  结合逆元  求  C(a,b) mod p      a!/(b! (a-b)!)   1≤b≤a≤10^5
        long combine(int a, int b, int p) {
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
        long Catalan(int n) {
            int p = mod;
            return (combine(2 * n, n, p) - combine(2 * n, n - 1, p) + p) % p;
        }
    }

}