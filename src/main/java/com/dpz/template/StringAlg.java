package com.dpz.template;

import java.util.*;

/**
 * 字符串算法
 */
public class StringAlg {
    /**
     * 表达式计算
     * 给定一个表达式，其中运算符仅包含 +,-,*,/（加 减 乘 整除）
     * 可能包含括号，请你求出表达式的最终值。
     * 详细条件见 https://www.acwing.com/problem/content/3305/
     */
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
    int zfunc() {
        return 0;//todo
    }

    /**
     * AC自动机 todo
     */


}