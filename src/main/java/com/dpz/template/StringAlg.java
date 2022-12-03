package com.dpz.template;

import java.util.*;

/**
 * 字符串算法
 */
public class StringAlg {

    /**
     * 字典树
     */
    static class Trie0 {

        Trie0[] nodes;
        int cnt;//当前结尾代表的单词的个数

        public Trie0() {
            nodes = new Trie0[26];
            cnt = 0;

        }

        public void insert(String word) {
            Trie0 cur = this;
            for (char c : word.toCharArray()) {
                if (cur.nodes[c - 'a'] == null) {
                    cur.nodes[c - 'a'] = new Trie0();
                }
                cur = cur.nodes[c - 'a'];
            }
            cur.cnt++;
        }

        public int search(String word) {
            Trie0 cur = this;

            for (char c : word.toCharArray()) {
                if (cur.nodes[c - 'a'] == null) return 0;
                cur = cur.nodes[c - 'a'];
            }
            return cur.cnt;

        }

        //判断前缀是否存在
        public boolean startsWith(String prefix) {
            Trie0 cur = this;

            for (char c : prefix.toCharArray()) {
                if (cur.nodes[c - 'a'] == null) return false;
                cur = cur.nodes[c - 'a'];
            }
            return true;
        }
    }

    //01字典树
    static class Trie01 {

        Trie01[] nodes;
        int cnt;//当前结尾代表的单词的个数

        public Trie01() {
            nodes = new Trie01[2];
            cnt = 0;
        }

        public void insert(int v) {
            Trie01 cur = this;
            for (int i = 31; i >= 0; i--) {
                int bit = (v >> i) & 1;
                if (cur.nodes[bit] == null) {
                    cur.nodes[bit] = new Trie01();
                }
                cur = cur.nodes[bit];
            }
            cur.cnt++;
        }

        public int search(int v) {
            Trie01 cur = this;
            for (int i = 31; i >= 0; i--) {
                int bit = (v >> i) & 1;
                if (cur.nodes[bit] == null) return 0;
                cur = cur.nodes[bit];
            }
            return cur.cnt;
        }

    }

    //数组实现的Trie
    public static class Trie1 {
        int[][] tr;
        int[] cnt;
        int idx = 0;

        Trie1(int size) {
            tr = new int[size][26];//值代表节点id
            cnt = new int[size];//字符串个数
        }

        int insert(String s) {
            int p = 0;
            for (int i = 0; i < s.length(); i++) {
                int u = s.charAt(i) - 'a';
                if (tr[p][u] == 0) tr[p][u] = ++idx;
                p = tr[p][u];
            }
            cnt[p]++;
            return p;
        }
    }


    /**
     * 表达式计算
     * 给定一个表达式，其中运算符仅包含 +,-,*,/（加 减 乘 整除）
     * 可能包含括号，请你求出表达式的最终值。
     * 详细条件见 https://www.acwing.com/problem/content/3305/
     */
    static class Compute {
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
     * AC自动机 k个字符串Len 是否是 某个字符串N的子串 复杂度O(N+k*len)
     * 对Trie建立next数组 每个点的next存的是「以当前点为结尾的后缀」 和 「Trie的所有前缀」
     * 的最长(非平凡)公共前后缀对应的前缀的尾端点
     * <p>
     * 题解 https://www.acwing.com/solution/content/18275/
     * <p>
     * 模板题 https://www.acwing.com/problem/content/1284/
     * cf:https://codeforces.com/contest/963/problem/D
     */
    static class AC_DFA {
        int N = 500010;
        Trie1 trie = new Trie1(N);
        int[] next = new int[N];
        int[] last = new int[N];//当前路径 上一个cnt>0的节点
        //        boolean[] vis = new boolean[N];//如果只需要判断子串是否出现，不需要找出位置。避免重复访问
        int[] sid = new int[N];//节点对应的单词编号,如果有重复单词会覆盖
        int n;//AC自动机单词个数

        AC_DFA(List<String> ss) {
            for (String s : ss) sid[trie.insert(s)] = n++;
            build();
        }

        void build() {
            Deque<Integer> deque = new ArrayDeque<>();
            //根据KMP next定义 根节点以及第一层结点都是指向根节点
            //从根节点的所有子节点开始搜
            for (int i = 0; i < 26; i++)
                if (trie.tr[0][i] > 0) deque.addLast(trie.tr[0][i]);
            while (!deque.isEmpty()) {
                int u = deque.pollFirst();//已匹配的
                for (int i = 0; i < 26; i++) {
                    int v = trie.tr[u][i];
                    //朴素写法
//                    if (p == 0) continue;
//                    int j = next[t];
//                    while (j > 0 && trie.tr[j][i] == 0) j = next[j];
//                    if (trie.tr[j][i] > 0) j = trie.tr[j][i];//匹配到了字母i
//                    next[p] = j;

                    //优化写法
                    if (v == 0) {
                        trie.tr[u][i] = trie.tr[next[u]][i];//简历Trie图 匹配的时候间接使用了next
                        continue;
                    } else {
                        next[v] = trie.tr[next[u]][i];
                        last[v] = trie.cnt[next[v]] > 0 ? next[v] : last[next[v]];
                    }

                    deque.addLast(v);//长度为len的位置计算之前,所有的长度<len的都已经计算过了
                }
            }
        }

        List<List<Integer>> getEndPos(String s) {
            List<List<Integer>> ans = new ArrayList<>();
            for (int i = 0; i < n; i++) ans.add(new ArrayList<>());
            int j = 0;
            for (int i = 0; i < s.length(); i++) {
                int t = s.charAt(i) - 'a';
                /*
                朴素写法
                while (j > 0 && trie.tr[j][t] == 0) j = next[j];
                if (trie.tr[j][t] > 0) j = trie.tr[j][t];
                **/

                //优化写法
                j = trie.tr[j][t];
                int p = trie.cnt[j] > 0 ? j : last[j];
                /*
                 while (!vis[p] && p > 0) {
                 vis[p] = true;//记录某个单词有没有出现，出现过就不用再往回跳了
                 ans += trie.cnt[p];
                 trie.cnt[p] = 0;
                 p = next[p];
                 }
                 **/

                while (p > 0) {
                    ans.get(sid[p]).add(i);
                    p = last[p];
                }
            }
            return ans;
        }
    }

    /**
     * Z-func   在 O(∣a∣+∣b∣) 求a的所有后缀 与 模式串 b 的 LCP 最长公共前缀
     * 模板题 https://www.luogu.com.cn/problem/P5410
     * 讲解 https://www.luogu.com.cn/blog/ericyan2008/Solution-P5410
     */
    int zfunc() {
        return 0;//todo
    }

}