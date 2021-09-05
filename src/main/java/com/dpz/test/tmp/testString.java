package com.dpz.test.tmp;

public class testString {

    public static void main(String[] args) {
        testSplit(",1,1,");
    }

    /**
     * 结论：如果 出现  ", ..."  第一个字符时空   如果 出现  "...  ,"  则正常 无空字符
     * @param s
     */
    private static void testSplit(String s) {
        for(String str:s.split(","))
        System.out.println("val:"+str);
    }
}
