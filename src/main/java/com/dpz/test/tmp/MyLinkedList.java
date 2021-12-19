package com.dpz.test.tmp;

import java.util.*;


class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        List<String> list = solution.maxNumOfSubstrings("ababcccd");//"abbaccd"
        System.out.println(list);
    }

    int[] first;
    int[] last;
    int n;

    public List<String> maxNumOfSubstrings(String s) {
        //可以不选取所有字符   选出不重叠的子串   怎么使子串最多   子串中每个字母的数目=全串
        //找到每个字母对应的唯一子串的范围  排序  总长度最小
        n = s.length();
        first = new int[26];
        Arrays.fill(first, n);
        last = new int[26];
        Arrays.fill(last, -1);
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            first[c - 'a'] = Math.min(first[c - 'a'], i);
            last[c - 'a'] = Math.max(last[c - 'a'], i);

        }
        //以每个字母开始的线段  start end   有的时候不能以某个字母开始abab  不能以b开始
        int[][] sub = new int[26][];
        for (int i = 0; i < 26; i++) {
            if (first[i] == n) sub[i] = new int[]{first[i], last[i]};
            else {
                int right = getLast(s, first[i]);
                if (right == -1)
                    sub[i] = new int[]{n, -1};
                else
                    sub[i] = new int[]{first[i], right};
            }
        }
        //有重叠的要合并
        Arrays.sort(sub, (a, b) -> a[0] - b[0]);
        int t = 0;
        while (t < 26 && sub[t][0] < n) t++;
        int[][] dp = new int[t][2];//选择某一个串的最大子串数  最小长度
//        dp[0][0]=1;
//        dp[0][1]=sub[0][1]-sub[0][0]+1;
        for (int i = 0; i < t; i++) {
            int len = sub[i][1] - sub[i][0] + 1;
            dp[i][0] = 1;
            dp[i][1] = len;
            //找前面不重叠的位置
            for (int j = i - 1; j >= 0; j--) {
                if (sub[j][1] < sub[i][0]) {
                    if (dp[j][0] + 1 == dp[i][0] && dp[j][1] + len < dp[i][1]) {
                        dp[i][1] = dp[j][1] + len;
                    } else if (dp[j][0] + 1 > dp[i][0]) {
                        dp[i][0] = dp[j][0] + 1;
                        dp[i][1] = dp[j][1] + len;
                    }
                }
            }

        }
        int max = 0;
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < t; i++) {
            if (dp[i][0] == max && dp[i][1] < minLen) {
                minLen = dp[i][1];
            } else if (dp[i][0] > max) {
                max = dp[i][0];
                minLen = dp[i][1];
            }
        }
        List<String> ans = new ArrayList<>();
        for (int i = t - 1; i >= 0; i--) {
            if (sub[i][0] == n) continue;
            if (max == dp[i][0] && minLen == dp[i][1]) {
                int len = sub[i][1] - sub[i][0] + 1;
                ans.add(s.substring(sub[i][0], sub[i][1] + 1));
                //找上一个位置
                for (int j = i - 1; j >= 0; ) {
                    if (max == dp[j][0] + 1 && minLen == dp[j][1] + len) {
                        ans.add(s.substring(sub[j][0], sub[j][1] + 1));
                        max = dp[j][0];
                        minLen = dp[j][1];
                        len = sub[j][1] - sub[j][0] + 1;
                        j = j - 1;
                    } else j--;

                }
                break;
            }

        }
        return ans;
    }

    private int getLast(String s, int i) {
        //先记录左侧出现过的字母
        boolean[] vis = new boolean[26];
        for (int j = 0; j < i; j++) {
            vis[s.charAt(j) - 'a'] = true;
        }
        char c = s.charAt(i++);
        int right = last[c - 'a'];
        while (i <= right) {
            c = s.charAt(i++);
            if (vis[c-'a']) return -1;
            right = Math.max(right, last[c - 'a']);
        }
        return i - 1;
    }
}