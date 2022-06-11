package com.dpz.template;

/**
 * ACWing算法模板
 */
public class ACWing {
    //==============================算法基础课===================================

    /**
     * 排序算法
     *
     * @see SortAgrm10
     */
    static class Sort {
    }

    //高精度加减乘除
    static String add(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int alen = a.length();
        int blen = b.length();
        int i = 0;
        for (; i < Math.min(alen, blen); i++) {
            int v = a.charAt(alen - i - 1) - '0' + b.charAt(blen - i - 1) - '0' + carry;
            carry = v / 10;
            sb.append(v % 10);
        }
        for (; i < alen; i++) {
            int v = a.charAt(alen - i - 1) - '0' + carry;
            carry = v / 10;
            sb.append(v % 10);
        }
        for (; i < blen; i++) {
            int v = b.charAt(blen - i - 1) - '0' + carry;
            carry = v / 10;
            sb.append(v % 10);
        }
        if (carry > 0) sb.append(carry);
        return sb.reverse().toString();
    }

    //算法提高课
}
