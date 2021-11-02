package com.dpz.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 线段树例题 https://leetcode-cn.com/problems/rectangle-area-ii/
 * 850. 矩形面积 II
 * 找出平面中所有矩形叠加覆盖后的总面积。 由于答案可能太大，请返回它对 10 ^ 9 + 7 取模的结果。
 * <p>
 * rectangle[i] = [x1, y1, x2, y2]
 */
public class SegmentTreeDemo {

    public int rectangleArea(int[][] rectangles) {
        int len = rectangles.length;
        //扫描线line: up or down,  y  ,start ,end
        int[][] lines = new int[len + len][4];
        int index = 0;
        HashSet<Integer> xSet = new HashSet<>();
        for (int[] rec : rectangles) {
            lines[index++] = new int[]{1, rec[1], rec[0], rec[2]};
            lines[index++] = new int[]{-1, rec[3], rec[0], rec[2]};
            //横坐标  区间划分
            xSet.add(rec[0]);
            xSet.add(rec[2]);
        }
        Integer[] xArray = xSet.toArray(new Integer[0]);
        //sort
        Arrays.sort(lines, (a, b) -> {
            return Integer.compare(a[1], b[1]);
        });
        Arrays.sort(xArray);

        //the whole  Interval
        SegmentTreeNode node = new SegmentTreeNode(0, xArray.length - 1, xArray);

        Map<Integer, Integer> xToIndex = new HashMap<>();
        for (int i = 0; i < xArray.length; i++) xToIndex.put(xArray[i], i);
        long sum = 0;
        int baseLineY = lines[0][1];
        long xlen = 0;
        //update   line by line
        for (int[] line : lines) {
            //line x transfer to interval index
            sum += xlen * (line[1] - baseLineY);
            xlen = node.update(xToIndex.get(line[2]), xToIndex.get(line[3]), line[0]);
            baseLineY = line[1];
        }

        return (int) (sum % 1_000_000_007);
    }


    static class SegmentTreeNode {
        //区间
        int start;
        int end;
        int count;//计算面积用
        Integer[] interval;//
        SegmentTreeNode left;
        SegmentTreeNode right;
        long total;

        SegmentTreeNode(int start, int end, Integer[] xArray) {
            this.start = start;
            this.end = end;
            this.interval = xArray;
        }

        //return interval len sum
        //int this method l ,r are index of interval[]
        //update total
        long update(int l, int r, int v) {
            if (l >= r) return 0;
            //cover
//            if(l==start&&end==r){
//                count+=v;
//            }
//            getLeft().update(l,Math.min(mid(),r),v);
//            getRight().update(Math.max(mid(),l),r,v);


            if (start == l && end == r) {
                count += v;//下线加1 上线减1
            } else {//无 else : stack overflow start == l && end == r的情况无限循环
                getLeft().update(l, Math.min(mid(), r), v);
                getRight().update(Math.max(mid(), l), r, v);
            }
            //push up
            if (count > 0) total = interval[end] - interval[start];//有完整的下线
            else total = getRight().total + getLeft().total;
            return total;
        }

        private int mid() {
            return start + end >> 1;
        }

        private SegmentTreeNode getLeft() {
            if (left == null) left = new SegmentTreeNode(start, mid(), interval);
            return left;
        }

        private SegmentTreeNode getRight() {
            if (right == null) right = new SegmentTreeNode(mid(), end, interval);
            return right;
        }
    }

    public static void main(String[] args) {
        SegmentTreeDemo segmentTreeDemo = new SegmentTreeDemo();
        int i = segmentTreeDemo.rectangleArea(new int[][]{{0, 0, 3, 3}, {1, 1, 4, 4}});
        System.out.println(i);
    }


}
//参考实现：
/**
 class Solution {
 public int rectangleArea(int[][] rectangles) {
 int n = rectangles.length;
 int[][] events = new int[2 * n][4];
 int m = 0;
 Set<Integer> xVals = new HashSet<>();
 for(int[] r:rectangles){
 xVals.add(r[0]);
 xVals.add(r[2]);
 events[m++] = new int[]{r[1],1,r[0],r[2]};
 events[m++] = new int[]{r[3],-1,r[0],r[2]};
 }

 Comparator<int[]> cmp = new Comparator<>(){
 public int compare(int[] a,int[] b){
 return a[0] - b[0];
 }
 };
 Arrays.sort(events,cmp);

 Integer[] x = xVals.toArray(new Integer[0]);
 Arrays.sort(x);

 Map<Integer,Integer> mp = new HashMap<>();
 for(int i = 0;i < x.length;i++){
 mp.put(x[i],i);
 }

 long res = 0;
 int prev = events[0][0];
 long total = 0;
 SegmentTree st = new SegmentTree(x);
 for(int[] e:events){

 int y = e[0],val = e[1],x1 = e[2],x2 = e[3];
 res +=  total * (y - prev);
 st.update(st.root,mp.get(x1),mp.get(x2),val);
 total = st.query(st.root,0,x.length-1);

 prev = y;
 }

 return (int) (res%1_000_000_007);
 }
 }

 class SegmentTree{
 Node root = null;
 Integer[] x;
 int n;
 public SegmentTree(Integer[]  x){
 this.n = x.length;
 this.x = x;
 root = new Node(0,n-1);
 }

 public void update(Node root,int start,int end,int val){

 if(start <= root.start && end >= root.end){
 root.count += val;
 return;
 }

 int mid = (root.start + root.end) / 2;

 if(start < mid){
 Node left = root.left;
 if(root.left == null){
 left = new Node(root.start,mid);
 root.left = left;
 }
 update(left,start,end,val);
 }

 if(mid < end){
 Node right = root.right;
 if(root.right == null){
 right = new Node(mid,root.end);
 root.right = right;
 }
 update(right,start,end,val);
 }

 }

 public long query(Node root,int start,int end){
 if(start == root.start && end == root.end){
 if(root.count > 0){
 return x[root.end] - x[root.start];
 }
 }
 long res = 0;
 int mid = (root.start + root.end) / 2;
 if(root.left != null){
 res += query(root.left,start,mid);
 }
 if(root.right != null){
 res += query(root.right,mid,end);
 }
 return  res;
 }
 }

 class Node{
 int start;
 int end;
 Node left;
 Node right;
 int count = 0;
 public Node(int start,int end){
 this.start = start;
 this.end = end;
 }
 }
 */

/*
 class Solution {
    public int rectangleArea(int[][] rectangles) {
    // 扫描线按照纵坐标递增排序
    int OPEN = 1, CLOSE = -1;
    int[][] lines = new int[rectangles.length * 2][];
    Set<Integer> Xvals = new HashSet();
    int t = 0;
    for (int[] rec : rectangles) {
      lines[t++] = new int[]{rec[1], OPEN, rec[0], rec[2]};
      lines[t++] = new int[]{rec[3], CLOSE, rec[0], rec[2]};
      Xvals.add(rec[0]);
      Xvals.add(rec[2]);
    }
    Arrays.sort(lines, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        return Integer.compare(o1[0], o2[0]);
      }
    });

    // 离散化x坐标
    Integer[] X = Xvals.toArray(new Integer[0]);
    Arrays.sort(X);
    Map<Integer, Integer> Xi = new HashMap();
    for (int i = 0; i < X.length; ++i) {
      Xi.put(X[i], i);
    }

    // 构建线段树
    SegTree segTree = new SegTree();
    SegNode root = segTree.build(0, X.length - 1, X);

    // 计算面积
    long ans = 0;
    long cur_x_sum = 0;
    int cur_y = lines[0][0];
    for (int[] line : lines) {
      int y = line[0], type = line[1], x1 = line[2], x2 = line[3];
      ans += cur_x_sum * (y - cur_y);
      segTree.update(root, Xi.get(x1), Xi.get(x2), type);
      cur_x_sum = root.len;
      cur_y = y;
    }

    ans %= 1_000_000_007;
    return (int) ans;
    }

      class SegTree {
    public void pushUp(SegNode node) {
      if (node.count > 0) { // 是一条完整的线段
        node.len = node.X[node.end] - node.X[node.start];
        return;
      }
      if (node.end == node.start) { // 不是线段
        node.len = 0;
      } else { // 线段由左右两部分组成
        node.len = node.lc.len + node.rc.len;
      }
    }

//    /**
//     * 构建线段树
//     * 此处的线段树区间和常规的线段树有点区别
//     * 通常的线段树区间是[left, mid], [mid + 1, right], 此处时是[left, mid], [mid, right]
//     * 因此构建线段树的终止条件要适当修改
//     * @param start
//     * @param end
//     * @param X
//     * @return
//     *
public SegNode build(int start, int end, Integer[] X) {
    SegNode node = new SegNode(start, end, X);
    SegNode lc, rc;
    if (start + 1 == end) {
        lc = new SegNode(start, start, X);
        rc = new SegNode(end, end, X);
    } else {
        int mid = node.getMid();
        lc = build(start, mid, X);
        rc = build(mid, end, X);
    }
    node.lc = lc;
    node.rc = rc;
    return node;
}

    public void update(SegNode node, int x1, int x2, int lineType) {
        if (x2 <= node.start || x1 >= node.end) { // 要更新的线段与当前区间无交集
            return;
        }

        if (x1 <= node.start && x2 >= node.end) { // 要更新的线段完全覆盖当前区间
            node.count += lineType;
            pushUp(node);
            return;
        }

        int mid = node.getMid();
        if (x2 <= mid) { // 只更新左孩子
            update(node.lc, x1, x2, lineType); // 更新左孩子
        } else if (x1 > mid) { // 只更新右孩子
            update(node.rc, x1, x2, lineType); // 更新右孩子
        } else {
            update(node.lc, x1, x2, lineType); // 更新左孩子
            update(node.rc, x1, x2, lineType); // 更新右孩子
        }
        pushUp(node); // 更新父节点的值
    }
}

class SegNode {
    int start, end; // 起始点
    SegNode lc, rc; // 左右子树
    int count; // 被查找寻区间覆盖次数
    long len; // 区间长度
    Integer[] X; // X实际坐标

    public SegNode(int start, int end, Integer[] X) {
        this.start = start;
        this.end = end;
        this.X = X;
        lc = null;
        rc = null;
        count = 0;
        len = 0;
    }

    public int getMid() {
        return (start + end) >> 1;
    }
}
}
 */