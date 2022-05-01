package com.dpz.main;


import com.dpz.dataStructure.TreeNode;

import java.math.BigInteger;
import java.util.*;

class Solution {

    public long appealSum(String s) {
        Long index[] = new Long[26], sum = 0L;
        for (int i = 0; i < s.length(); i++) {
            index[s.charAt(i) - 'a'] = (long) i;
            for (int j = 0; j < 26; j++) {
                sum += index[j] == null ? 0 : 1 + index[j];
            }
        }
        return sum;
    }
}

