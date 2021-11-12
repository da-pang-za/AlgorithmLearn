package com.dpz.test;


import java.util.*;

class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int n1= nums1.length;
        int n2=nums2.length;
        int max=0;
        int[][] dp =new int[n1+1][n2+1];
        for (int i = 1; i <=n1 ; i++) {
            for (int j = 1; j <=n2 ; j++) {
                dp[i][j]=(nums1[i-1]==nums2[j-1])?dp[i-1][j-1]+1:0;
                max=Math.max(max,dp[i][j]);
            }
        }
        return max;
    }
}