package com.dpz.leetcode;

public class Solution0730其他题目 {
    public int swimInWater(int[][] grid) {
        int n=grid.length;
        int[][]min=new int[n][n];
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n ; j++) {
                min[i][j]=Integer.MAX_VALUE;

            }

        }
        min[n-1][n-1]=grid[n-1][n-1];
        //更新

        for (int i = n-1; i >=0 ; i--) {
            for (int j = n-1; j>=0 ; j--) {
                dfs(i,j,min,grid);

            }

        }

        //路径的最大高度

        //找最大高度 最小的 路径

        //dp[i][j] 当前位置到终点的  最大高度最小的路径 的 高度值
        //一个位置的值更改了，必须检查其相邻的位置
        return min[0][0];
    }

    void dfs(int i,int j, int[][] min, int[][]grid){

    }
}
