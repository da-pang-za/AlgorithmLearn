package com.dpz.test.tmp;

import java.util.Random;

class Solution {
    public static void main(String[] args) {
        Solution solution=new Solution(new int[]{1,14,3,2});
    }
    int[]sum;
    Random random =new Random();
    public Solution(int[] w) {
        sum=w;
        for(int i=1;i<w.length;i++){
            sum[i]+=sum[i-1];
            // 1，3     1  4
            //当前位置范围：前一个sum-当前sum      (sum[i-1],sum[i]]
        }
        System.out.println("");

    }

    public int pickIndex() {
        int s=sum[sum.length-1];
        int k=(int)(Math.random()*s)+1;//1-s
        if(k<=sum[0])return 0;
        //二分
        int l=1;
        int r=sum.length-1;
        int mid=-1;
        while(l<r){
            mid=(r+l)/2;
            if(s>sum[mid-1]&&s<=sum[mid]){
                return mid;
            }
            else if(s>sum[mid]){
                l=mid+1;
            }
            else {
                r=mid-1;
            }


        }
        return l;

    }
}
