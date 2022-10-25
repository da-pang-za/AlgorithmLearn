package com.dpz.template;

import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * 贪心
 */
public class Greedy {
    //推公式
    //刷杂技的牛 https://www.acwing.com/problem/content/127/
    //考虑交换相邻两个的影响  https://www.acwing.com/video/122/
    static int acwing125(int n, int[] w, int[] s) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        //大的放下/右
        Arrays.sort(arr, (a, b) -> Integer.compare(w[a] + s[a], w[b] + s[b]));
        int W = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, W - s[arr[i]]);
            W += w[arr[i]];
        }
        return max;
    }

    //哈夫曼问题 哈夫曼编码   https://www.acwing.com/problem/content/150/
    //两个最小值一定在最下面   考虑交换后的情况可以证明
    //最优子结构
    int Huffman(int n, int[] nums) {
        var pq = new PriorityQueue<Integer>(Integer::compare);
        for (int i = 0; i < n; i++) pq.add(nums[i]);
        int tot = 0;
        while (pq.size() > 1) {
            int v = pq.poll() + pq.poll();
            tot += v;
            pq.add(v);
        }
        return tot;
    }
}