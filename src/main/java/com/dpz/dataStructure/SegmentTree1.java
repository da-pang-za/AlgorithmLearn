package com.dpz.dataStructure;

/**
 * 线段树-动态开点
 * 例题：https://leetcode-cn.com/problems/my-calendar-iii/
 */
public
class SegmentTree1 {
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
    int[] nums;
    int n;
    int mode;
    M m;
    MN mn;
    A a;
    Node root;

    public SegmentTree1(int[] _nums, int start, int _n, A add, M merge, MN mergeN, int mode) {
        this.m = merge;
        this.mn = mergeN;
        this.a = add;
        this.mode = mode;
        n = MAXN = _n;
        nums = _nums;
        root = new Node(start, n);

        if (_nums != null)
            build(root);
    }

    static class Node {
        int l, r;
        Node left, right;
        long val = 0;
        long lazy;
        boolean hasLazy;

        Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    void build(Node p) {
        int l = p.l, r = p.r;
        if (l == r) {
            p.val = nums[l - 1];
            return;
        }

        build(left(p));
        build(right(p));
        push_up(p);//
    }

    public long query(int l, int r) {
        return query(l, r, root);
    }

    long query(int l, int r, Node p) {
        int nl = p.l, nr = p.r;
        if (l <= nl && nr <= r) return p.val;

        push_down(p);
        long ans = 0;
        int mid = (nl + nr) >> 1;
        if (mid >= l) ans = m.merge(ans, query(l, r, left(p)));
        if (mid < r) ans = m.merge(ans, query(l, r, right(p)));
        return ans;
    }


    public void update(int l, int r, long val) {
        update(l, r, val, root);
    }

    void update(int l, int r, long d, Node p) {
        int nl = p.l, nr = p.r;
//        System.out.println(l+" "+r+" "+nl+" "+nr);
        if (l <= nl && nr <= r) {
            if (mode == ADD) {
                p.val = a.add(p.val, mn.mergeN(d, nr - nl + 1));
                p.lazy = a.add(p.lazy, d);
            } else if (mode == ASSIGN) {
                p.val = mn.mergeN(d, nr - nl + 1);
                p.lazy = d;
                p.hasLazy = true;
            } else System.err.println("[SegmentTree]:unknown mode");

            return;
        }

        push_down(p);

        int mid = (nl + nr) >> 1;
        if (mid >= l) update(l, r, d, left(p));
        if (mid < r) update(l, r, d, right(p));
        push_up(p);
    }

    //利用左右点 更新当前点
    void push_up(Node p) {
        p.val = m.merge(p.left.val, p.right.val);
    }

    void push_down(Node p) {
        int l = p.l, r = p.r;
        if (l >= r) return;
        int mid = (l + r) >> 1;
        if (mode == ADD) {
            left(p).lazy = a.add(p.left.lazy, p.lazy);
            right(p).lazy = a.add(p.right.lazy, p.lazy);

            p.left.val = a.add(p.left.val, mn.mergeN(p.lazy, mid - l + 1));
            p.right.val = a.add(p.right.val, mn.mergeN(p.lazy, r - mid));
        } else if (mode == ASSIGN) {
            if (!p.hasLazy) return;
            left(p).lazy = p.lazy;//此处如果当前节点没有标记  会把子节点的标记给擦除  但当前节点也可能有值为0的标记
            right(p).lazy = p.lazy;
            p.left.hasLazy = true;
            p.right.hasLazy = true;
            p.left.val = mn.mergeN(p.lazy, mid - l + 1);
            p.right.val = mn.mergeN(p.lazy, r - mid);
            p.hasLazy = false;
        } else System.err.println("[SegmentTree]:unknown mode");

        p.lazy = 0;
    }

    Node right(Node p) {
        if (p.right != null) return p.right;
        int l = p.l, r = p.r;
        int mid = (l + r) >> 1;
        p.right = new Node(mid + 1, r);
        return p.right;
    }

    Node left(Node p) {
        if (p.left != null) return p.left;
        int l = p.l, r = p.r;
        int mid = (l + r) >> 1;
        p.left = new Node(l, mid);
        return p.left;
    }

}
