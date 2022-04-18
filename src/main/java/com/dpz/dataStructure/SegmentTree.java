package com.dpz.dataStructure;
//线段树讲解：https://zhuanlan.zhihu.com/p/106118909
//例题：https://leetcode-cn.com/problems/range-sum-query-mutable/
/**
 * todo 动态开点
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
 */

/**
 * 线段树 基础版
 */
//class SegmentTree {
//    int sum;
//    int left;
//    int right;
//    SegmentTree lNode;
//    SegmentTree rNode;
//
//    SegmentTree(int l, int r, int[] nums) {
//        left = l;
//        right = r;
//
//        if (l != r) {
//            int mid = left + right >> 1;
//            lNode = new SegmentTree(l, mid, nums);
//            rNode = new SegmentTree(mid + 1, r, nums);
//            sum = lNode.sum + rNode.sum;
//        } else sum = nums[l];
//    }
//
//    void add(int l, int r, int val) {
//        if (l > right || r < left) return;
//        if (left == right) sum += val;
//        else {
//            lNode.add(l, r, val);
//            rNode.add(l, r, val);
//            sum = lNode.sum + rNode.sum;
//        }
//    }
//
//    int query(int l, int r) {
//        if (l > right || r < left) return 0;
//        if (l <= left && right <= r) return sum;
//        return lNode.query(l, r) + rNode.query(l, r);
//    }
//
//}


/**
 * 数组实现
 * https://zhuanlan.zhihu.com/p/106118909
 * https://leetcode-cn.com/problems/range-sum-query-mutable/solution/meng-xin-de-di-yi-ke-xian-duan-shu-by-98-c3es/
 */


class SegmentTree {
    int MAXN;
    int[] tree;
    int[] lazy;
    int[] nums;
    int n;

    SegmentTree(int[] A) {
        n=MAXN = A.length;
        nums = A;
        tree = new int[MAXN << 2];
        lazy = new int[MAXN << 2];
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

    long query(int l, int r) {
        return query(l, r, 1, 1, n);
    }

    long query(int l, int r, int p, int cl, int cr) {
        if (cl >= l && cr <= r) return tree[p];
        int mid = (cl + cr) >> 1;
        push_down(p, cl, cr, mid);
        long ans = 0;
        if (mid >= l) ans = merge(ans, query(l, r, p << 1, cl, mid));
        if (mid < r) ans = merge(ans, query(l, r, p << 1 | 1, mid + 1, cr));
        return ans;
    }

    void update(int l, int r, int val) {
        update(l, r, val, 1, 1, n);
    }

    //d 增量
    void update(int l, int r, int d, int p, int cl, int cr) {
        if (cl >= l && cr <= r) {
            tree[p] += d * (cr - cl + 1);
            lazy[p] += d;
            return;
        }
        int mid = (cl + cr) >> 1;
        push_down(p, cl, cr, mid);

        if (mid >= l) update(l, r, d, p << 1, cl, mid);
        if (mid < r) update(l, r, d, p << 1 | 1, mid + 1, cr);
        push_up(p);
    }
    //利用左右点 更新当前点
    void push_up(int p) {
        tree[p] = tree[p << 1] + tree[p << 1 | 1];
    }

    void push_down(int p, int l, int r, int mid) {
        if (lazy[p] == 0) return;
        lazy[p << 1] += lazy[p];
        lazy[p << 1 | 1] += lazy[p];
        tree[p << 1] += lazy[p] * (mid - l + 1);
        tree[p << 1 | 1] += lazy[p] * (r - mid);
        lazy[p] = 0;
    }

    long merge(long a, long b) {
        return a + b;
    }
}




