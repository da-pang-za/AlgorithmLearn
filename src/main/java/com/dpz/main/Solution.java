package com.dpz.main;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


class Solution {
    public long distinctNames(String[] ideas) {
        var group = new HashMap<String, Integer>();
        for (var s : ideas) {
            var t = s.substring(1);
            group.put(t, group.getOrDefault(t, 0) | 1 << (s.charAt(0) - 'a'));
        }
        var ans = 0L;
        var cnt = new int[26][26];
        for (var mask : group.values())
            for (var i = 0; i < 26; i++)
                if ((mask >> i & 1) == 0) {
                    for (var j = 0; j < 26; j++)
                        if ((mask >> j & 1) > 0) ++cnt[i][j];
                } else { //1
                    for (var j = 0; j < 26; j++)
                        if ((mask >> j & 1) == 0) ans += cnt[i][j];
                }
        return ans * 2;
    }
}

