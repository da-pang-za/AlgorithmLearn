package com.dpz.test;

//负数的补码 是  对应的正数  取反 +1    Integer.MAX+1 回到 Integer.MIN  循环  符合二进制加法
//-1的二进制表示是全1 再+1 舍弃掉进位的1  变为全0
public class binaryNum {
    public static void main(String[] args) {
        /**
         * 11111111111111111111111111111110
         * 11111111111111111111111111111111
         * 1111111111111111111111111111111
         * 10000000000000000000000000000000
         * 10000000000000000000000000000001
         * 10000000000000000000000000000010
         * 10000000000000000000000000000000
         */
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE + 1));
        System.out.println(Integer.toBinaryString(-Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(-Integer.MAX_VALUE + 1));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));

    }
}
