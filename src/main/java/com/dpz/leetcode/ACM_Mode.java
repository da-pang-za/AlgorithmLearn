package com.dpz.leetcode;

import java.util.Scanner;

public class ACM_Mode {
   static class Solution {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            // 读取 n m
            int n = scanner.nextInt(), m = scanner.nextInt();
            scanner.nextLine();
            String cars = scanner.nextLine();
            String likes=scanner.nextLine();

            System.out.println(n+","+m+","+cars+","+likes);

        }
    }
}

