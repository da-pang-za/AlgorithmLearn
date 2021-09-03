package com.company.test;

public class testAPI {
    public static void main(String[] args) {
        test();
    }

    static void test() {
        String version1 = "1.2";
        String[] v1 = version1.split("\\.");
        for (String s : v1) {
            System.out.println(s);
        }

    }
}
