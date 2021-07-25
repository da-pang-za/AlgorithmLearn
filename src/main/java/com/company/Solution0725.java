package com.company;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 重点题目
 */
public class Solution0725 {

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
}
