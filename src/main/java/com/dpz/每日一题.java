package com.company;

import java.util.*;

public class 每日一题 {
//1104. 二叉树寻路
    public List<Integer> pathInZigZagTree(int label) {
        List<Integer> list=new ArrayList<>();
        //n行最小 2^(n-1) 最大2^n-1
        //x在第 log2x +1层
        while(label>=1){
            list.add(label);
            int layer=(int)(Math.log(label)/Math.log(2))+1;
            label=(int)(Math.pow(2,layer-2)+Math.pow(2,layer-1)-1-(label/2));

        }
        //对称位置的和为 2^(n-1) +2^n-1
        //非之字顺序 父节点：x/2 向下取整
        Collections.reverse(list);
        return list;
    }
    //171. Excel 表列序号
    public int titleToNumber(String columnTitle) {
        HashMap<Character,Integer> map=new HashMap<>();
        int j=1;
        for(char i='A';i<='Z';i++,j++){
            map.put(i,j);
        }
        int res=0;
        for(char c:columnTitle.toCharArray()){
            res=26*res+map.get(c);
        }
        return res;


    }
}
