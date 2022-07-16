package com.dpz.template;

import java.util.*;

/**
 * ACWing算法模板
 */
public class ACWing {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(Basic.divide("777", "32")));
    }
    //==============================算法基础课===================================

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

        //试除法判断质数
        static boolean isPrime(int s) {
            for (int v = 2; v <= s / v; v++) {
                if (s % v == 0) return false;
            }
            return s > 1;
        }

        //埃氏筛求质数 O(nloglogn)
        void prime(int n) {
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

        //埃氏筛其实还是存在冗余的标记操作，比如对于 4545 这个数，它会同时被 3,53,5 两个数标记为合数，
        // 因此我们优化的目标是让每个合数只被标记一次，这样时间复杂度即能保证为 O(n)
        //线性筛   每次筛只与质数相乘   每个合数只会被其最小质因子筛掉
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

        //欧拉函数   [1,N]中和N互质的数的个数
        //需要求出所有的质因子 公式见：https://www.acwing.com/problem/content/875/
        static int EulerFun(int n) {
            var list = primeFact(n);
            long ans = n;
            for (var e : list) ans = ans / e.get(0) * (e.get(0) - 1);
            return (int) ans;
        }

        //筛法求欧拉函数    利用递推公式+线性筛
        //https://www.acwing.com/video/299/
        //求 1∼n 中每个数的欧拉函数之和
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


        //逆元(b mod p 的逆元  p是质数)     用乘法代替除法

        //利用费马小定理求逆元     b^(p-1) mod p = 1      b * b^(p-2) mod p= 1
        //因此b的逆元 b-1 = b^(p-2)
        static int inverse(int b, int p) {
            return (int)pow(b, p - 2, p);
        }

    }


    /**
     * 数据结构
     */
    static class DataStructure {
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

        //字符串哈希   判断子串 [l1,r1]  [l2,r2]是否相同  下标从1开始!!!
        //单哈希
        void StringHash1(int n, int m, String s, List<int[]> questions) {
            int base1 = 1313131;
            int mod = (int) 1e9 + 7;
            long[] hash1 = new long[n + 1];
            long[] p1 = new long[n + 1];
            p1[0] = 1;
            for (int i = 0; i < n; i++) {
                hash1[i + 1] = (base1 * hash1[i] + s.charAt(i)) % mod;
                p1[i + 1] = p1[i] * base1 % mod;
            }

            for (var q : questions) {
                int l1 = q[0], r1 = q[1], l2 = q[2], r2 = q[3];

                long h1 = (hash1[r1] - hash1[l1 - 1] * p1[r1 - l1 + 1] % mod + mod) % mod;
                long h2 = (hash1[r2] - hash1[l2 - 1] * p1[r2 - l2 + 1] % mod + mod) % mod;
                if (h1 == h2) System.out.println("Yes");
                else System.out.println("No");
            }
        }

        void StringHash2(int n, int m, String s, List<int[]> questions) {
            int base1 = 1313131;
            int base2 = 500007;
            int mod = (int) 1e9 + 7;
            long[] hash1 = new long[n + 1];
            long[] p1 = new long[n + 1];
            p1[0] = 1;
            long[] hash2 = new long[n + 1];
            long[] p2 = new long[n + 1];
            p2[0] = 1;
            for (int i = 0; i < n; i++) {
                hash1[i + 1] = (base1 * hash1[i] + s.charAt(i)) % mod;
                p1[i + 1] = p1[i] * base1 % mod;
                hash2[i + 1] = (base2 * hash2[i] + s.charAt(i)) % mod;
                p2[i + 1] = p2[i] * base2 % mod;
            }
            for (var q : questions) {
                int l1 = q[0], r1 = q[1], l2 = q[2], r2 = q[3];

                long h1 = (hash1[r1] - hash1[l1 - 1] * p1[r1 - l1 + 1] % mod + mod) % mod;
                long h2 = (hash1[r2] - hash1[l2 - 1] * p1[r2 - l2 + 1] % mod + mod) % mod;
                if (h1 == h2) {
                    h1 = (hash2[r1] - hash2[l1 - 1] * p2[r1 - l1 + 1] % mod + mod) % mod;
                    h2 = (hash2[r2] - hash2[l2 - 1] * p2[r2 - l2 + 1] % mod + mod) % mod;

                    if (h1 == h2)
                        System.out.println("Yes");
                    else System.out.println("No");
                } else System.out.println("No");
            }
        }
    }

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

    //算法提高课
}






