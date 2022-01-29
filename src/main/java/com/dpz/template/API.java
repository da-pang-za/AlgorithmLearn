package com.dpz.template;

import java.util.Arrays;

public class API {

    //常量
    int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    int mod=(int)1e9+7;
    int INF=0x3f3f3f3f;

    //最大公约数
    int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    //最长上升子序列 最长递增子序列
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
//        int[] f = new int[n];  //以i为结尾的最长递增子序列长度
        int[] g = new int[n + 1];//长度为i的子序列 最后一个位置的最小值  单调递增
//        Arrays.fill(f,1);
        Arrays.fill(g, Integer.MAX_VALUE);
        int ans = 1;
        for (int i = 0; i < n; i++) {
            int l = 0, r = n;
            while (l < r) {
                int mid = l + r + 1 >> 1;
                if (g[mid] < nums[i]) {
                    l = mid;
                } else r = mid - 1;
            }
//            f[i]=l+1;
            g[l + 1] = Math.min(nums[i], g[l + 1]);
            ans = Math.max(ans, l + 1);
        }
        return ans;
    }

    //最长公共子序列
    public int longestCommonSubsequence(String text1, String text2) {
        char[] nums1 = text1.toCharArray(), nums2 = text2.toCharArray();
        int m = nums1.length;
        int n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        //dp[0][0]=0; dp[0][i]=0 dp[i][0]=0
        //保存最后一条线的 i  j   这个线可能有一个或者两个 或者没有
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1])
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                dp[i][j] = Math.max(Math.max(dp[i][j - 1], dp[i - 1][j]), dp[i][j]);
            }
        }

        return dp[m][n];
    }

    //最长回文子串   可以马拉车
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) return s;

        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[n][n];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();

        for (int len = 2; len <= n; len++) {
            for (int i = 0; len + i - 1 < n; i++) {
                int j = len + i - 1;
                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    dp[i][j] = len == 2 || dp[i + 1][j - 1];
                }

                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }
    //最长回文子序列
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            char c1 = s.charAt(i);
            for (int j = i + 1; j < n; j++) {
                char c2 = s.charAt(j);
                if (c1 == c2) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    //下一个排列 O(n)
    class Solution31 {
        public void nextPermutation(int[] nums) {
            int n=nums.length;
            int i=n-2;
            while(i>=0&&nums[i]>=nums[i+1])i--;
            //有较小数 nums[i]
            if(i>=0){
                //较大数
                int j=n-1;
                while(nums[j]<=nums[i])j--;
                swap(nums,j,i);
                reverse(nums,i+1,n-1);
            }
            else reverse(nums,0,n-1);
        }
        void swap(int[]nums,int i,int j){
            int t=nums[i];
            nums[i]=nums[j];
            nums[j]=t;
        }
        void reverse(int[]nums,int l,int r){
            while(l<r){
                swap(nums,l++,r--);
            }

        }
    }
    //快速幂 x^k mod p
    int pow(long x, int k, int p){
        long res = 1;
        while (k!=0){
            if((k&1)!=0) res = res * x % p;
            x = x * x % p;
            k >>= 1;
        }
        return (int)res;
    }


}
