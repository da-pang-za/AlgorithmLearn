package com.dpz.dataStructure;

/**
 * 线段树-动态开点
 * 例题：https://leetcode-cn.com/problems/my-calendar-iii/
 * todo 合并前的初始值ans不一定是0
 */
public
class SegmentTree1 {
    @FunctionalInterface
    public interface M {
        long merge(long a, long b);
    }

    @FunctionalInterface
    public interface MN {
        long mergeN(long a, long count);
    }

    @FunctionalInterface
    public interface A {
        long add(long a, long b);
    }

    private int MODE;
    public static final int ADD = 0;//增量
    public static final int ASSIGN = 1;//赋值

    private int[] nums;

    private M mergeFunc;
    private MN mergeNFunc;
    private A addFunc;

    private Node root;

    /**
     * @param nums      原数组,下标从0开始，但在线段树上是从1开始 使用数组初始化时  区间固定为[1,n]
     * @param start,end 区间开始结束[start,end]
     * @param add       增量 只有add模式用到 累加方式
     * @param merge     合并左右两个区间
     * @param mergeN    区间每个位置增量d,区间长度为len,求区间整体增量
     * @param MODE      累加 or 赋值
     */
    public SegmentTree1(int[] nums, int start, int end, A add, M merge, MN mergeN, int MODE) {
        this.mergeFunc = merge;
        this.mergeNFunc = mergeN;
        this.addFunc = add;
        this.MODE = MODE;
        this.nums = nums;
        if (this.nums != null) {
            start = 1;
            end = this.nums.length;
        }
        root = new Node(start, end);

        if (nums != null)
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

    private void build(Node p) {
        int l = p.l, r = p.r;
        if (l == r) {
            p.val = nums[l - 1];
            return;
        }

        build(left(p));
        build(right(p));
        push_up(p);
    }

    public long query(int l, int r) {
        return query(l, r, root);
    }

    private long query(int l, int r, Node p) {
        int nl = p.l, nr = p.r;
        if (l <= nl && nr <= r) return p.val;

        push_down(p);
        long ans = 0;
        int mid = (nl + nr) >> 1;
        if (mid >= l) ans = mergeFunc.merge(ans, query(l, r, left(p)));
        if (mid < r) ans = mergeFunc.merge(ans, query(l, r, right(p)));
        return ans;
    }

    public void update(int l, int r, long val) {
        update(l, r, val, root);
    }

    private void update(int l, int r, long d, Node p) {
        int nl = p.l, nr = p.r;
//        System.out.println(l+" "+r+" "+nl+" "+nr);
        if (l <= nl && nr <= r) {
            if (MODE == ADD) {
                p.val = addFunc.add(p.val, mergeNFunc.mergeN(d, nr - nl + 1));
                p.lazy = addFunc.add(p.lazy, d);
            } else if (MODE == ASSIGN) {
                p.val = mergeNFunc.mergeN(d, nr - nl + 1);
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
    private void push_up(Node p) {
        p.val = mergeFunc.merge(left(p).val, right(p).val);
    }

    private void push_down(Node p) {
        int l = p.l, r = p.r;
        if (l >= r) return;
        int mid = (l + r) >> 1;
        if (MODE == ADD) {
            left(p).lazy = addFunc.add(p.left.lazy, p.lazy);
            right(p).lazy = addFunc.add(p.right.lazy, p.lazy);

            p.left.val = addFunc.add(p.left.val, mergeNFunc.mergeN(p.lazy, mid - l + 1));
            p.right.val = addFunc.add(p.right.val, mergeNFunc.mergeN(p.lazy, r - mid));
        } else if (MODE == ASSIGN) {
            if (!p.hasLazy) return;
            left(p).lazy = p.lazy;//此处如果当前节点没有标记  会把子节点的标记给擦除  但当前节点也可能有值为0的标记
            right(p).lazy = p.lazy;
            p.left.hasLazy = true;
            p.right.hasLazy = true;
            p.left.val = mergeNFunc.mergeN(p.lazy, mid - l + 1);
            p.right.val = mergeNFunc.mergeN(p.lazy, r - mid);
            p.hasLazy = false;
        } else System.err.println("[SegmentTree]:unknown mode");

        p.lazy = 0;
    }

    private Node right(Node p) {
        if (p.right != null) return p.right;
        int l = p.l, r = p.r;
        int mid = (l + r) >> 1;
        p.right = new Node(mid + 1, r);
        return p.right;
    }

    private Node left(Node p) {
        if (p.left != null) return p.left;
        int l = p.l, r = p.r;
        int mid = (l + r) >> 1;
        p.left = new Node(l, mid);
        return p.left;
    }

}
