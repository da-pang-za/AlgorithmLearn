package com.dpz.dataStructure;

/**
 * 线段树-动态开点
 * 例题：https://leetcode-cn.com/problems/my-calendar-iii/
 */
public
class SegmentTree1 {
    @FunctionalInterface
    public interface M {
        long merge(long v1, long v2);
    }

    @FunctionalInterface
    public interface MD {
        long modifyLR(long l, long r, long d);
    }

    @FunctionalInterface
    public interface A {
        long add(long v1, long v2);
    }

    private int MODE, BASE;
    public static final int ADD = 0;//增量
    public static final int ASSIGN = 1;//赋值

    private int[] nums;

    private M mergeFunc;
    private MD modifyFunc;
    private A addFunc;

    private Node root;

    /**
     * @param nums      原数组,下标从0开始，但在线段树上是从1开始 使用数组初始化时  区间固定为[1,n]
     * @param start,end 区间开始结束[start,end]
     * @param add       增量 只有add模式用到 累加方式
     * @param merge     合并左右两个区间
     * @param modifyLR  区间每个位置增量/赋值d,区间[l,r],求区间整体增量、赋值
     * @param MODE      累加 or 赋值
     */
    public SegmentTree1(int[] nums, long start, long end, A add, M merge, MD modifyLR, int MODE) {
        this.mergeFunc = merge;
        this.modifyFunc = modifyLR;
        this.addFunc = add;
        this.MODE = MODE;
        this.nums = nums;
        if (this.nums != null) {
            start = 1;
            end = this.nums.length;
        }
        root = new Node(start, end);

        if (nums != null) build(root);
    }

    private long query(long l, long r, Node p) {
        long nl = p.l, nr = p.r;
        if (l <= nl && nr <= r) return p.val;

        push_down(p);
        long mid = (nl + nr) >> 1;
        if (mid >= r) return query(l, r, left(p));
        if (mid < l) return query(l, r, right(p));
        return mergeFunc.merge(query(l, r, left(p)), query(l, r, right(p)));
    }

    private void update(long l, long r, long d, Node p) {
        long nl = p.l, nr = p.r;
//        System.out.println(l+" "+r+" "+nl+" "+nr);
        if (l <= nl && nr <= r) {
            if (MODE == ADD) {
                p.val = addFunc.add(p.val, modifyFunc.modifyLR(nl, nr, d));
                p.lazy = addFunc.add(p.lazy, d);
            } else if (MODE == ASSIGN) {
                p.val = modifyFunc.modifyLR(nl, nr, d);
                p.lazy = d;
                p.hasLazy = true;
            } else System.err.println("[SegmentTree]:unknown mode");

            return;
        }

        push_down(p);

        long mid = (nl + nr) >> 1;
        if (mid >= l) update(l, r, d, left(p));
        if (mid < r) update(l, r, d, right(p));
        push_up(p);
    }

    //利用左右点 更新当前点
    private void push_up(Node p) {
        p.val = mergeFunc.merge(left(p).val, right(p).val);
    }

    private void push_down(Node p) {
        long l = p.l, r = p.r;
        if (l >= r) return;
        long mid = (l + r) >> 1;
        if (MODE == ADD) {
            left(p).lazy = addFunc.add(p.left.lazy, p.lazy);
            right(p).lazy = addFunc.add(p.right.lazy, p.lazy);

            p.left.val = addFunc.add(p.left.val, modifyFunc.modifyLR(l, mid, p.lazy));
            p.right.val = addFunc.add(p.right.val, modifyFunc.modifyLR(mid + 1, r, p.lazy));
        } else if (MODE == ASSIGN) {
            if (!p.hasLazy) return;
            left(p).lazy = p.lazy;//此处如果当前节点没有标记  会把子节点的标记给擦除  但当前节点也可能有值为0的标记
            right(p).lazy = p.lazy;
            p.left.hasLazy = true;
            p.right.hasLazy = true;
            p.left.val = modifyFunc.modifyLR(l, mid, p.lazy);
            p.right.val = modifyFunc.modifyLR(mid + 1, r, p.lazy);
            p.hasLazy = false;
        } else System.err.println("[SegmentTree]:unknown mode");

        p.lazy = 0;
    }

    public long query(long l, long r) {
        return query(l, r, root);
    }

    public void update(long l, long r, long val) {
        update(l, r, val, root);
    }

    private void build(Node p) {
        long l = p.l, r = p.r;
        if (l == r) {
            p.val = nums[(int) l - 1];
            return;
        }
        build(left(p));
        build(right(p));
        push_up(p);
    }

    static class Node {
        long l, r;
        Node left, right;
        long val = 0;
        long lazy;
        boolean hasLazy;

        Node(long l, long r) {
            this.l = l;
            this.r = r;
        }
    }

    private Node right(Node p) {
        if (p.right != null) return p.right;
        long l = p.l, r = p.r;
        long mid = (l + r) >> 1;
        p.right = new Node(mid + 1, r);
        return p.right;
    }

    private Node left(Node p) {
        if (p.left != null) return p.left;
        long l = p.l, r = p.r;
        long mid = (l + r) >> 1;
        p.left = new Node(l, mid);
        return p.left;
    }
}