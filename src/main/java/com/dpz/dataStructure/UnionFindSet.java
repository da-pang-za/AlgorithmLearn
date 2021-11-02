package com.dpz.dataStructure;

import java.util.HashMap;

public class UnionFindSet {

    void init(int N) {
        f = new int[N];
        // 初始化
        for (int i = 0; i < N; i++)
            f[i] = i;

        //init by graph
    }

    //a所在的集合和b所在的集合
    void merge(int a, int b) {
        // 合并操作
        f[getFather(a)] = getFather(b);
    }

    boolean query(int a, int b) {
        // 查询操作
        return getFather(a) == getFather(b);
    }

    int[] f;//直观含义  ：一个同组的点   如果f(x)==x   此点是leader

    int getFather(int x) {
        // 查询所在团伙代表人
        return f[x] == x ? x : getFather(f[x]);
    }

    public int maxAreaOfIsland(int[][] grid) {
        //first merge with up
        //second merge with left
        int N = 100 * grid.length + grid[0].length;
        f = new int[N];
        HashMap<Integer, Integer> leader2Count = new HashMap<>();//leader count
        //第一行
        for (int i = 1; i < grid[0].length; i++) {
            f[100 * 0 + i] = grid[0][i - 1] == 1 ? getFather(100 * 0 + i - 1) : 100 * 0 + i;
        }
        //第一列
        for (int i = 1; i < grid.length; i++) {
            f[100 * i + 0] = grid[i - 1][0] == 1 ? getFather(100 * (i -1)+ 0) : 100 * i + 0;
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[i].length; j++) {
                if(grid[i ][j] ==0)continue;
                if (grid[i - 1][j] == 1) {
                    f[100 * (i) + j] = getFather(100 * (i - 1) + j);
                    //如果上左都是岛 连起来
                    if (grid[i][j - 1] == 1){
                        f[getFather(100 * (i) + j - 1)]=getFather(100 * (i - 1) + j);
                    }
                } else if (grid[i][j - 1] == 1) {
                    f[100 * (i) + j] = getFather(100 * (i) + j - 1);
                } else f[100 * (i) + j] = 100 * (i) + j;
            }

        }
        //statistics
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j]!=0){
                    int part=getFather(100 * (i) + j);
                    leader2Count.put(part,leader2Count.getOrDefault(part,0)+1);
                }
            }
        }
        int max=0;
        for(int count:leader2Count.values()){
            max=Math.max(max,count);
        }
        return max;
    }

    public static void main(String[] args) {
        UnionFindSet unionFindSet = new UnionFindSet();
        unionFindSet.maxAreaOfIsland(new int[][]{{0,0,1,0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,1,1,0,1,0,0,0,0,0,0,0,0},{0,1,0,0,1,1,0,0,1,0,1,0,0},{0,1,0,0,1,1,0,0,1,1,1,0,0},{0,0,0,0,0,0,0,0,0,0,1,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,0,0,0,0,0,0,1,1,0,0,0,0}});
    }


}


