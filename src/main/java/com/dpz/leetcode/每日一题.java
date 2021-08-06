package com.dpz.leetcode;

import java.util.*;

public class 每日一题 {
    //847. 访问所有节点的最短路径
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        int INF = 0x3f3f3f3f;
        //点，状态(路径)->距离
        int mask = 1 << n;
        int[][] dist = new int[n][mask];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        Deque<int[]> deque = new ArrayDeque<>();//点，状态
        //初始化
        for (int i = 0; i < n; i++) {
            dist[i][1 << i] = 0;
            deque.addLast(new int[]{i, 1 << i});
        }
        while (!deque.isEmpty()) {
            int[] poll = deque.pollFirst();
            int status = poll[1];
            int idx = poll[0];
            if (status == mask - 1) {
                return dist[idx][status];
            }
            //遍历下一个位置
            for (int next : graph[idx]) {
                if (dist[next][status | (1 << next)] == INF) {
                    dist[next][status | (1 << next)] = dist[idx][status] + 1;
                    deque.addLast(new int[]{next, status | (1 << next)});

                }
            }

        }
        return -1;//never
    }

    //802. 找到最终的安全状态
    public List<Integer> eventualSafeNodes(int[][] graph) {
        //遍历一遍找到终点 graph[i].length==0
        //能到达终点的且只能到终点的标记为终点  去掉到达终点的边
        //end -> start list
        //start->end list
        Set<Integer> res = new HashSet<>();
        int modify = 0;
        HashMap<Integer, HashSet<Integer>> endStart = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> startEnd = new HashMap<>();
        //初始化
        for (int i = 0; i < graph.length; i++) {

            if (graph[i].length == 0) {
                res.add(i);
                continue;
            }

            for (int end : graph[i]) {
                HashSet<Integer> startEndSet = startEnd.getOrDefault(i, new HashSet<>());
                startEndSet.add(end);
                startEnd.put(i, startEndSet);
                HashSet<Integer> endStartSet = endStart.getOrDefault(end, new HashSet<>());
                endStartSet.add(i);
                endStart.put(end, endStartSet);
            }
        }
        //
        while (modify > 0) {
            modify = 0;
            for (int safe : res) {
                //能到达安全点的
                for (int start : endStart.get(safe)) {
                    startEnd.get(start).remove(safe);

                    modify++;
                }

                endStart.remove(safe);
            }
            //更新res
            for (Map.Entry<Integer, HashSet<Integer>> entry : startEnd.entrySet()) {
                if (entry.getValue().size() == 0) {
                    res.add((entry.getKey()));
                }
            }

        }
        List<Integer> list = new ArrayList<>(res);
        Collections.sort(list);
        return list;


    }

    //1104. 二叉树寻路
    public List<Integer> pathInZigZagTree(int label) {
        List<Integer> list = new ArrayList<>();
        //n行最小 2^(n-1) 最大2^n-1
        //x在第 log2x +1层
        while (label >= 1) {
            list.add(label);
            int layer = (int) (Math.log(label) / Math.log(2)) + 1;
            label = (int) (Math.pow(2, layer - 2) + Math.pow(2, layer - 1) - 1 - (label / 2));

        }
        //对称位置的和为 2^(n-1) +2^n-1
        //非之字顺序 父节点：x/2 向下取整
        Collections.reverse(list);
        return list;
    }

    //171. Excel 表列序号
    public int titleToNumber(String columnTitle) {
        HashMap<Character, Integer> map = new HashMap<>();
        int j = 1;
        for (char i = 'A'; i <= 'Z'; i++, j++) {
            map.put(i, j);
        }
        int res = 0;
        for (char c : columnTitle.toCharArray()) {
            res = 26 * res + map.get(c);
        }
        return res;


    }
}
