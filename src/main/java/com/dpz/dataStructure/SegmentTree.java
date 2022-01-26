package com.dpz.dataStructure;
//线段树讲解：https://zhuanlan.zhihu.com/p/106118909

/**
 * todo 动态开点  lazy标记  数组实现
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

class SegmentTree {
    int sum;
    int left;
    int right;
    SegmentTree lNode;
    SegmentTree rNode;

    SegmentTree(int l, int r, int[] nums) {
        left = l;
        right = r;

        if (l != r) {
            int mid = left + right >> 1;
            lNode = new SegmentTree(l, mid, nums);
            rNode = new SegmentTree(mid + 1, r, nums);
            sum = lNode.sum + rNode.sum;
        } else sum = nums[l];
    }

    void add(int l, int r, int val) {
        if (l > right || r < left) return;
        if (left == right) sum += val;
        else {
            lNode.add(l, r, val);
            rNode.add(l, r, val);
            sum = lNode.sum + rNode.sum;
        }
    }

    int query(int l, int r) {
        if (l > right || r < left) return 0;
        if (l <= left && right <= r) return sum;
        return lNode.query(l, r) + rNode.query(l, r);
    }

}

class SegmentTreeArr {
    int MAXN = (int) 1e5 + 5;
    int[] tree = new int[MAXN << 2], mark = new int[MAXN << 2], A = new int[MAXN];
    int n, m;

    void push_down(int p, int len) {
        if (len <= 1) return;
        tree[p << 1] += mark[p] * (len - len / 2);
        mark[p << 1] += mark[p];
        tree[p << 1 | 1] += mark[p] * (len / 2);
        mark[p << 1 | 1] += mark[p];
        mark[p] = 0;
    }

    void build(int p, int cl, int cr) {
        if (cl == cr) {
            tree[p] = A[cl];
            return;
        }
//      return void(tree[p] = A[cl]);
        int mid = (cl + cr) >> 1;
        build(p << 1, cl, mid);
        build(p << 1 | 1, mid + 1, cr);
        tree[p] = tree[p << 1] + tree[p << 1 | 1];
    }

    int query(int l, int r, int p, int cl, int cr) {
        if (cl >= l && cr <= r) return tree[p];
        push_down(p, cr - cl + 1);
        int mid = (cl + cr) >> 1, ans = 0;
        if (mid >= l) ans += query(l, r, p << 1, cl, mid);
        if (mid < r) ans += query(l, r, p << 1 | 1, mid + 1, cr);
        return ans;
    }

    void update(int l, int r, int d, int p, int cl, int cr) {
        if (cl >= l && cr <= r) {
            tree[p] += d * (cr - cl + 1);
            mark[p] += d;
        }
//      return void(tree[p] += d * (cr - cl + 1), mark[p] += d);
        push_down(p, cr - cl + 1);
        int mid = (cl + cr) >> 1;
        if (mid >= l) update(l, r, d, p << 1, cl, mid);
        if (mid < r) update(l, r, d, p << 1 | 1, mid + 1, cr);
        tree[p] = tree[p << 1] + tree[p << 1 | 1];
    }
}


