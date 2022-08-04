package com.dpz.template;

import java.math.BigInteger;
import java.util.*;

//todo  ACWing 系统学习算法    算法基础课&提高课  https://www.acwing.com/blog/content/8368/
public class API {
    //最大字段和 todo

    static class Pair<A, B> {
        A a;
        B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    //List转数组
    int[] list2Num(List<Integer> list) {
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    //字符集
    String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lower = "abcdefghijklmnopqrstuvwxyz";
    String digit = "0123456789";

    //多个数比大小
    long min(long... nums) {
        long ans = Long.MAX_VALUE;
        for (var a : nums) ans = Math.min(ans, a);
        return ans;
    }

    long max(long... nums) {
        long ans = Long.MIN_VALUE;
        for (var a : nums) ans = Math.max(ans, a);
        return ans;
    }

    //字母表  counter   小写字母
    int[] counter(String s) {
        int[] ab = new int[26];
        for (char c : s.toCharArray()) {
            ab[c - 'a']++;
        }
        return ab;
    }

    //二分查找
    //第一个>=x的元素的idx(下标)
    public Integer ceiling(int[] nums, int start, int end, int x) {
        if (x > nums[end]) return null;
        int l = start, r = end;
        while (l < r) {
            int mid = l + r >> 1;
            if (nums[mid] >= x) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    //第一个<=x的元素
    public Integer floor(int[] nums, int start, int end, int x) {
        if (x < nums[start]) return null;
        int l = start, r = end;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] <= x) l = mid;
            else r = mid - 1;
        }
        return l;

    }

    //第一个>=x的元素的idx(下标)
    public Integer ceiling(List<Integer> nums, int start, int end, int x) {
        if (x > nums.get(end)) return null;
        int l = start, r = end;
        while (l < r) {
            int mid = l + r >> 1;
            if (nums.get(mid) >= x) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    //第一个<=x的元素
    public Integer floor(List<Integer> nums, int start, int end, int x) {
        if (x < nums.get(start)) return null;
        int l = start, r = end;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums.get(mid) <= x) l = mid;
            else r = mid - 1;
        }
        return l;

    }


    //最大公约数
    int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    //最长上升子序列 最长递增子序列 这里是严格递增的
    public int LIS(int[] nums) {
        int n = nums.length;
//        int[] f = new int[n];  //以i为结尾的最长递增子序列长度
        int[] g = new int[n + 1];//长度为i的递增子序列 最后一个位置的最小值  单调递增
//        Arrays.fill(f,1);
        Arrays.fill(g, Integer.MAX_VALUE);
        int ans = 1;
        for (int i = 0; i < n; i++) {
            int l = 0, r = n;
            while (l < r) {
                int mid = l + r + 1 >> 1;
                if (g[mid] < nums[i]) {//如果是非递减子序列 这里加个等于号
                    l = mid;
                } else r = mid - 1;
            }
//            f[i]=l+1;
            g[l + 1] = Math.min(nums[i], g[l + 1]);
            ans = Math.max(ans, l + 1);
        }
        return ans;
    }

    //最长公共子序列
    public int longestCommonSubsequence(String text1, String text2) {
        char[] nums1 = text1.toCharArray(), nums2 = text2.toCharArray();
        int m = nums1.length;
        int n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        //dp[0][0]=0; dp[0][i]=0 dp[i][0]=0
        //保存最后一条线的 i  j   这个线可能有一个或者两个 或者没有
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1])
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                dp[i][j] = Math.max(Math.max(dp[i][j - 1], dp[i - 1][j]), dp[i][j]);
            }
        }

        return dp[m][n];
    }

    //最长回文子串   可以马拉车
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) return s;

        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[n][n];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();

        for (int len = 2; len <= n; len++) {
            for (int i = 0; len + i - 1 < n; i++) {
                int j = len + i - 1;
                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    dp[i][j] = len == 2 || dp[i + 1][j - 1];
                }

                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    //最长回文子序列
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            char c1 = s.charAt(i);
            for (int j = i + 1; j < n; j++) {
                char c2 = s.charAt(j);
                if (c1 == c2) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    //下一个排列 O(n)
    class Solution31 {
        public void nextPermutation(int[] nums) {
            int n = nums.length;
            int i = n - 2;
            while (i >= 0 && nums[i] >= nums[i + 1]) i--;
            //有较小数 nums[i]
            if (i >= 0) {
                //较大数
                int j = n - 1;
                while (nums[j] <= nums[i]) j--;
                swap(nums, j, i);
                reverse(nums, i + 1, n - 1);
            } else reverse(nums, 0, n - 1);
        }

        void swap(int[] nums, int i, int j) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }

        void reverse(int[] nums, int l, int r) {
            while (l < r) {
                swap(nums, l++, r--);
            }
        }
    }

    //快速幂 x^k mod p
    long pow(long x, long k, long p) {
        long res = 1;
        while (k != 0) {
            if ((k & 1) != 0) res = res * x % p;
            x = x * x % p;
            k >>= 1;
        }
        return res;
    }

    //计算器   表达式问题   究极表达式问题
    /*
    | 从前往后做，对遍历到的字符做分情况讨论：
    |
    | 空格 : 跳过
    | ( : 直接加入 ops 中，等待与之匹配的 )
    | ) : 使用现有的 nums 和 ops 进行计算，直到遇到左边最近的一个左括号为止，计算结果放到 nums
    | 数字 : 从当前位置开始继续往后取，将整一个连续数字整体取出，加入 nums
    | + - | / ^ % : 需要将操作放入 ops 中。在放入之前先把栈内可以算的都算掉（只有「栈内运算符」
    | 比「当前运算符」优先级高/同等，才进行运算），使用现有的 nums 和 ops 进行计算，直到没有操作或者遇到左括号，计算结果放到 nums
    */
    class Solution227 {
        // 使用 map 维护一个运算符优先级
        // 这里的优先级划分按照「数学」进行划分即可
        Map<Character, Integer> map = new HashMap() {{
            put('-', 1);
            put('+', 1);
            put('*', 2);
            put('/', 2);
            put('%', 2);
            put('^', 3);//这里是指数运算
        }};

        public int calculate(String s) {
            // 将所有的空格去掉
            s = s.replaceAll(" ", "");
            char[] cs = s.toCharArray();
            int n = s.length();
            // 存放所有的数字
            Deque<Integer> nums = new ArrayDeque<>();
            // 为了防止第一个数为负数，先往 nums 加个 0
            nums.addLast(0);
            // 存放所有「非数字以外」的操作
            Deque<Character> ops = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                char c = cs[i];
                if (c == '(') {
                    ops.addLast(c);
                } else if (c == ')') {
                    // 计算到最近一个左括号为止
                    while (!ops.isEmpty()) {
                        if (ops.peekLast() != '(') {
                            calc(nums, ops);
                        } else {
                            ops.pollLast();
                            break;
                        }
                    }
                } else {
                    if (isNumber(c)) {
                        int u = 0;
                        int j = i;
                        // 将从 i 位置开始后面的连续数字整体取出，加入 nums
                        while (j < n && isNumber(cs[j])) u = u * 10 + (cs[j++] - '0');
                        nums.addLast(u);
                        i = j - 1;
                    } else {
                        //防止括号内首个字符为运算符   "(-" 或 "(+"
                        if (i > 0 && (cs[i - 1] == '(')) {
                            nums.addLast(0);
                        }
                        // 有一个新操作要入栈时，先把栈内可以算的(优先级高或相同)都算了
                        // 只有满足「栈内运算符」比「当前运算符」优先级高/同等，才进行运算
                        while (!ops.isEmpty() && ops.peekLast() != '(') {
                            char prev = ops.peekLast();
                            if (map.get(prev) >= map.get(c)) {
                                calc(nums, ops);
                            } else {
                                break;
                            }
                        }
                        ops.addLast(c);
                    }
                }
            }
            // 将剩余的计算完
            while (!ops.isEmpty()) calc(nums, ops);
            return nums.peekLast();
        }

        void calc(Deque<Integer> nums, Deque<Character> ops) {
            if (nums.size() < 2) return;
            if (ops.isEmpty()) return;
            int b = nums.pollLast(), a = nums.pollLast();
            char op = ops.pollLast();
            int ans = 0;
            if (op == '+') ans = a + b;
            else if (op == '-') ans = a - b;
            else if (op == '*') ans = a * b;
            else if (op == '/') ans = a / b;
            else if (op == '^') ans = (int) Math.pow(a, b);
            else if (op == '%') ans = a % b;
            nums.addLast(ans);
        }

        boolean isNumber(char c) {
            return Character.isDigit(c);
        }
    }

    //位运算  位操作
    class BitOperation {
        //交换两个数 不用tmp 注意这里int不能传出去
        void swapWithoutTmp(int a, int b) {
            a ^= b;//ab
            b ^= a;//bab=b
            a ^= b;//ab b
            System.out.println(a + "," + b);
        }

        //判断是否同号(0划分到正数)   符号位异或
        boolean isSameSign(int x, int y) {
            return (x ^ y) >= 0;
        }

        //判断一个数是不是2的幂  1个1 后面全是0
        boolean isFactorialOfTwo(int n) {
            //   10000 & 01111
            //   10100 & 10011 如果不全是0 影响不到最高位
            return n > 0 && (n & (n - 1)) == 0;

        }

        //2的次方对m取余 // m<n
        int mod2pow(int n, int m) {
            return m & (n - 1);
        }

        //从低位到高位,取n的第m位
        int getBit(int n, int m) {
            return (n >> (m - 1)) & 1;
        }

        //从低位到高位.将n的第m位置1
        int setBitToOne(int n, int m) {
            return n | (1 << (m - 1));
        }

        //从低位到高位,将n的第m位置0
        int setBitToZero(int n, int m) {
            return n & ~(1 << (m - 1));
        }

        //从低位到高位,将n的第m位置取反    //一位异或0保持不变  异或1取反
        int setBitNeg(int n, int m) {
            return n ^ (1 << (m - 1));
        }

        void api() {
            int x = 12643;
            int count = Integer.bitCount(x);//二进制1的个数
            String binaryString = Integer.toBinaryString(x);//转为二进制字符串

        }
    }

    class StringOperation {
        void api() {
            String s = "egherh";
            int idx = s.indexOf('e');//从左到右 第一次遇到某个字符的下标
            String trim = s.trim();
            String[] ss = s.split(" ");
            int cmp = s.compareTo("fhdfhj");//比较字典序
        }
    }

    //随机  [0,1)
    double random() {
        return Math.random();
    }


    //数学
    //阶乘
    public BigInteger fact(BigInteger n) {
        BigInteger ans = BigInteger.ONE;
        while (n.compareTo(BigInteger.ONE) > 0) {
            ans = ans.multiply(n);
            n = n.add(BigInteger.ONE.negate());
        }
        return ans;
    }

    //组合数递推公式： C(a,b)=C(a-b,b-1)+C(a-1,b)    //根据是否取当前位置划分
    //组合
    public BigInteger combin(BigInteger n, BigInteger m) {
        assert n.compareTo(m) >= 0;

        return fact(n).divide(
                fact(m).multiply(fact(n.add(m.negate()))));
    }

    //枚举子集 遍历子集  非空   复杂度2^(bit_count(state))
    //如果是遍历n个数的「子集的子集」：\sum_{i=0}^{n} C_{n}^{i} * 2^i  =3^n 二项式公式 (1+2)^n
    //https://oi-wiki.org/math/bit/#_14
    void subSet(int state) {
        for (int i = state; i != 0; i = (i - 1) & state) {
            //
        }
        //子集的子集
//        for (int state = 1; state < 1 << n; state++)
//            for (int u = state; u != 0; u = (u - 1) & state)

    }

    //大数   求幂
//            return (int) (numberOfGoodSubsets(1, 1, 2, count)
//                * BigInteger.TWO.modPow(BigInteger.valueOf(count[1]), BigInteger.valueOf(1000000007)).intValue()
//                % 1000000007);


    //合并区间
    public int[][] merge56(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> ans = new ArrayList<>();
        ans.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] last = ans.get(ans.size() - 1);
            if (intervals[i][0] <= last[1]) {
                last[1] = Math.max(last[1], intervals[i][1]);
            } else {
                ans.add(intervals[i]);
            }
        }
        int[][] r = new int[ans.size()][2];
        int i = 0;
        for (int[] v : ans) {
            r[i++] = v;
        }
        return r;

    }

    //合并区间   求区间长度
    long calIntervalLen(List<int[]> list) {
        list.sort((a, b) -> Integer.compare(a[0], b[0]));//用compare防止溢出
        //
        // Calculate query
        long query = 0;//求区间总长度
        int cur = -1;//不断移动当前右端点位置
        for (int[] xs : list) {
            cur = Math.max(cur, xs[0]);
            query += Math.max(xs[1] - cur, 0);
            cur = Math.max(cur, xs[1]);
        }
        return query;
    }

    //三角函数
    void getAngle() {
        //360内的角度   角度制
//        Double degree = Math.atan2(y - locationY, x - locationX);

    }

    void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    //排序

    public static int[] radixSort2(int[] a) {
        int n = a.length;
        int[] c0 = new int[0x101];
        int[] c1 = new int[0x101];
        int[] c2 = new int[0x101];
        int[] c3 = new int[0x101];
        for (int v : a) {
            c0[(v & 0xff) + 1]++;
            c1[(v >>> 8 & 0xff) + 1]++;
            c2[(v >>> 16 & 0xff) + 1]++;
            c3[(v >>> 24 ^ 0x80) + 1]++;
        }
        for (int i = 0; i < 0xff; i++) {
            c0[i + 1] += c0[i];
            c1[i + 1] += c1[i];
            c2[i + 1] += c2[i];
            c3[i + 1] += c3[i];
        }
        int[] t = new int[n];
        for (int v : a) t[c0[v & 0xff]++] = v;
        for (int v : t) a[c1[v >>> 8 & 0xff]++] = v;
        for (int v : a) t[c2[v >>> 16 & 0xff]++] = v;
        for (int v : t) a[c3[v >>> 24 ^ 0x80]++] = v;
        return a;
    }

    static void sortWithPQ(int[] a) {
        sortWithPQ(a, 0, a.length - 1);
    }

    static void sortWithPQ(int[] a, int start, int end) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = start; i <= end; i++) pq.add(a[i]);
        for (int i = start; i <= end; i++) a[i] = pq.poll();
    }
}
