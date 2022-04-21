package com.dpz.dataStructure;
//线段树讲解：https://zhuanlan.zhihu.com/p/106118909
//例题：https://leetcode-cn.com/problems/range-sum-query-mutable/
/*
 * todo 动态设置幺元
 * 线段树
 * 线段树的用处就是，对编号连续的一些点进行修改或者统计操作，修改和统计的复杂度都是O(log2(n)).
 * 线段树中，每个点代表一条线段（区间）
 * <p>
 * 用线段树统计的东西，必须符合区间加法
 * 符合区间加法的例子：
 * 数字之和——总数字之和 = 左区间数字之和 + 右区间数字之和
 * 最大公因数(GCD)——总GCD = gcd( 左区间GCD , 右区间GCD );
 * 最大值——总最大值=max(左区间最大值，右区间最大值)
 * 不符合区间加法的例子：
 * 众数——只知道左右区间的众数，没法求总区间的众数
 * <p>
 */

/**
 * 数组实现
 * https://zhuanlan.zhihu.com/p/106118909
 * https://leetcode-cn.com/problems/range-sum-query-mutable/solution/meng-xin-de-di-yi-ke-xian-duan-shu-by-98-c3es/
 */

public
class SegmentTree {
    public interface M {
        long merge(long a, long b);
    }

    public interface MN {
        long mergeN(long a, long count);
    }

    public interface A {
        long add(long a, long b);
    }

    public static final int ADD = 0;//增量
    public static final int ASSIGN = 1;//赋值
    int MAXN;
    long[] tree;
    long[] lazy;
    boolean[] hasLazy;
    int[] nums;//区间[1,n]的每个位置的值
    int n;
    int mode;
    A a;
    M m;
    MN mn;


    public SegmentTree(int[] _nums, int _n, A add, M merge, MN mergeN, int mode) {
        this.m = merge;
        this.mn = mergeN;
        this.a=add;
        this.mode = mode;
        n = MAXN = _n;
        nums = _nums;
        tree = new long[MAXN << 2];
        lazy = new long[MAXN << 2];
        if (mode == ASSIGN)
            hasLazy = new boolean[MAXN << 2];
        if (_nums != null)
            build(1, 1, n);
    }

    //p:节点idx
    void build(int p, int l, int r) {
        if (l == r) {
            tree[p] = nums[l - 1];
            return;
        }

        int mid = (l + r) >> 1;
        build(p << 1, l, mid);
        build(p << 1 | 1, mid + 1, r);
        push_up(p);//
    }

    public long query(int l, int r) {
        return query(l, r, 1, 1, n);
    }

    long query(int l, int r, int p, int nl, int nr) {
        if (l <= nl && nr <= r) return tree[p];
        int mid = (nl + nr) >> 1;
        push_down(p, nl, nr);
        long ans = 0;
        if (mid >= l) ans = m.merge(ans, query(l, r, p << 1, nl, mid));
        if (mid < r) ans = m.merge(ans, query(l, r, p << 1 | 1, mid + 1, nr));
        return ans;
    }


    public void update(int l, int r, long val) {
        update(l, r, val, 1, 1, n);
    }

    void update(int l, int r, long d, int p, int nl, int nr) {
        if (l <= nl && nr <= r) {
            if (mode == ADD) {
                tree[p] = a.add(tree[p], mn.mergeN(d, nr - nl + 1));
                lazy[p] = a.add(lazy[p], d);
            } else if (mode == ASSIGN) {
                tree[p] = mn.mergeN(d, nr - nl + 1);
                lazy[p] = d;
                hasLazy[p] = true;
            } else System.err.println("[SegmentTree]:unknown mode");

            return;
        }

        push_down(p, nl, nr);

        int mid = (nl + nr) >> 1;
        if (mid >= l) update(l, r, d, p << 1, nl, mid);
        if (mid < r) update(l, r, d, p << 1 | 1, mid + 1, nr);
        push_up(p);
    }

    //利用左右点 更新当前点
    void push_up(int p) {
        tree[p] = m.merge(tree[p << 1], tree[p << 1 | 1]);
    }

    void push_down(int p, int l, int r) {
        if (l >= r) return;
        int mid = (l + r) >> 1;
        if (mode == ADD) {
            lazy[p << 1] = a.add(lazy[p << 1], lazy[p]);
            lazy[p << 1 | 1] = a.add(lazy[p << 1 | 1], lazy[p]);

            tree[p << 1] = a.add(tree[p << 1], mn.mergeN(lazy[p], mid - l + 1));
            tree[p << 1 | 1] = a.add(tree[p << 1 | 1], mn.mergeN(lazy[p], r - mid));
        } else if (mode == ASSIGN) {
            if (!hasLazy[p]) return;
            lazy[p << 1] = lazy[p];//此处如果当前节点没有标记  会把子节点的标记给擦除  但当前节点也可能有值为0的标记
            lazy[p << 1 | 1] = lazy[p];
            hasLazy[p << 1] = true;
            hasLazy[p << 1 | 1] = true;
            tree[p << 1] = mn.mergeN(lazy[p], mid - l + 1);
            tree[p << 1 | 1] = mn.mergeN(lazy[p], r - mid);
            hasLazy[p] = false;
        } else System.err.println("[SegmentTree]:unknown mode");

        lazy[p] = 0;
    }
}
//demo:690.掉落的方块
//class Solution {
//    public List<Integer> fallingSquares(int[][] positions) {
//        HashMap<Integer, Integer> map = new HashMap<>();
//        int idx = 0;
//        TreeSet<Integer> set = new TreeSet<>();
//        for (int[] p : positions) {
//            set.add(p[0]);
//            set.add(p[1] + p[0] - 1);
//        }
//        for (Integer v : set) {
//            map.put(v, ++idx);
//        }
//        int n = idx;
//        SegmentTree segmentTree = new SegmentTree(null, n, (a, b) -> Math.max(a, b), (a, b) -> a, SegmentTree.ASSIGN);
//        List<Integer> ans = new ArrayList<>();
//        for (int[] p : positions) {
//            int l = map.get(p[0]);
//            int r = map.get(p[0] + p[1] - 1);
//
//            //当前范围最高的
//            int h = (int) segmentTree.query(l, r);
//            segmentTree.update(l, r, h + p[1]);
//            ans.add((int) segmentTree.query(1, n));
//        }
//        return ans;
//    }
//}







