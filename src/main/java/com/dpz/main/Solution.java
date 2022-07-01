package com.dpz.main;


import java.util.*;


class Solution {
    public static void main(String[] args) {
        int ans = 0;
        for (int i = 1; i <= 10; i++) {
            ans += (i - 1) * (10 - i);
        }
        System.out.println(ans);
    }
}