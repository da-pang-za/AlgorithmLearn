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
        //试除法判断质数
        static boolean isPrime(int s) {
            for (int v = 2; v <= s / v; v++) {
                if (s % v == 0) return false;
            }
            return s > 1;
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

    }

    //算法提高课
}






