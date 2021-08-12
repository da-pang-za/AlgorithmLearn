package com.dpz.leetcode;

import com.dpz.dataStructure.TreeNode;

import java.util.*;

/**
 * 重点题目
 */
public class Solution0725 {
    public static void main(String[] args) {
        Solution0725 solution=new Solution0725();
    }

    //42.接雨水
    public int trap(int[] height) {
        int n = height.length;
        int ans = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && height[i] > height[deque.peekLast()]) {
                int cur = deque.pollLast();
                if (deque.isEmpty()) continue;
                int l = deque.peekLast();
                int r = i;
                int w = r - l - 1;
                int h = Math.min(height[l], height[r]) - height[cur];
                ans += w * h;
            }
            deque.addLast(i);

        }
        return ans;
    }
    //264. 丑数 II

    //173. 二叉搜索树迭代器
    class BSTIterator {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur;

        public BSTIterator(TreeNode root) {
            if (root == null) {
                cur = null;
            }
            while (root != null) {
                stack.addLast(root);
                root = root.left;
            }
            cur = stack.pollLast();
        }

        public int next() {
            TreeNode res = cur;
            if (cur.right != null) {
                cur = cur.right;
                while (cur != null) {
                    stack.addLast(cur);
                    cur = cur.left;
                }
                cur = stack.pollLast();
            } else {
                if (stack.isEmpty()) {
                    cur = null;
                } else {
                    cur = stack.pollLast();
                }
            }

            return res.val;


        }

        public boolean hasNext() {
            return cur != null;

        }
    }

    //56.合并区间
    public int[][] merge(int[][] intervals) {
        //左端点排序

//        确定初始左右边界 下一个的左端点如果在里面 更新右边界 如果不在 则输出
//                并且这个不在的是新的边界
        Arrays.sort(intervals,
                (a, b) -> {
                    if (a[0] == b[0]) return a[1] - b[1];
                    return a[0] - b[0];
                });
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < intervals.length; ) {
            int l = intervals[i][0];
            int r = intervals[i][1];
            i++;
            for (; i < intervals.length; ) {
                if (intervals[i][0] <= r) {
                    r = Math.max(intervals[i][1], r);
                    i++;
                } else {
                    break;
                }
            }
            list.add(new int[]{l, r});
        }
        return list.toArray(new int[list.size()][]);


    }

}
