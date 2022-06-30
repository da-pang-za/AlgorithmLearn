package com.dpz.dataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * 手写优先队列  大根堆
 */
public class PriorityQueue {

    List<Integer> list = new ArrayList<>();

    PriorityQueue() {

    }

    Integer peek() {
        if (size() == 0) return null;
        return list.get(0);
    }

    Integer poll() {
        if (size() == 0) return null;
        int ans = list.get(0);
        list.set(0, list.get(size() - 1));//赋值为最后一个 然后去掉最后一个
        heapify(list, 0, list.size());
        list.remove(list.size() - 1);
        return ans;
    }

    /**
     * 堆调整/下沉   复用堆排序的代码
     *
     * @param len 堆的长度  超过的是排序好的 不动
     */
    private void heapify(List<Integer> nums, int index, int len) {

        while (index * 2 + 1 < len) { //首先判断是否有子节点  有子节点才能下沉
            int son = index * 2 + 1;
            //子节点较大的
            if (son + 1 < len && nums.get(son) < nums.get(son + 1)) son++;
            //交换 大的放上面
            if (nums.get(index) < nums.get(son)) {
                Collections.swap(nums, index, son);
                //继续下沉
                index = son;
            }
            //返回
            else break;
        }

    }

    void add(int v) {
        list.add(v);
        //上浮最后一个
        int i = size() - 1;
        while (parent(i) >= 0 && list.get(parent(i)) < v) {
            Collections.swap(list, i, parent(i));
            i = parent(i);
        }
    }

    int size() {
        return list.size();
    }

    //辅助函数
    int left(int i) {
        return 2 * i + 1;
    }

    int parent(int i) {
        return (i - 1) >> 1;
    }


    public static void main(String[] args) {
        //test
        PriorityQueue pq = new PriorityQueue();
        java.util.PriorityQueue<Integer> pq1 = new java.util.PriorityQueue<>((a, b) -> Integer.compare(b, a));

        int error = 0;
        for (int i = 0; i < 1000000; i++) {
            int v = new Random().nextInt();
            pq.add(v);
            pq1.add(v);
            if (!pq.peek().equals(pq1.peek()))
                error++;
        }
        System.out.println(error);
        while (!pq1.isEmpty()) {
            if (!pq.poll().equals(pq1.poll()))
                error++;
        }
        System.out.println(error);

    }

}

