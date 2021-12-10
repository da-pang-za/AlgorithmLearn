package com.dpz.leetcode;


import com.dpz.dataStructure.ListNode;

import java.util.*;


class Solution {
    //生成过程 左括号>=右括号
    StringBuilder sb= new StringBuilder();
    List<String>ans=new ArrayList<>();
    int n;
    public List<String> generateParenthesis(int _n) {
        n=_n;
        dfs(0,0);
        return ans;
    }
    void dfs(int l,int r){
        if(l>n||r>l)return;
        if(l==n&&r==n){
            ans.add(sb.toString());
            return;
        }
        //
        sb.append('(');
        dfs(l+1,r);
        sb.deleteCharAt(sb.length()-1);
        sb.append(')');
        dfs(l,r+1);
        sb.deleteCharAt(sb.length()-1);

    }
}