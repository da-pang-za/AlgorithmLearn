package com.dpz.test.tmp;

import java.util.*;

class Solution {

    public String licenseKeyFormatting(String s, int k) {
        StringBuilder sb = new StringBuilder();
        List<List<Character>> list = new ArrayList<>();
        for (int i = s.length() - 1; i >= 0; ) {

            List<Character> part = new ArrayList<>();
            while (i >= 0 && part.size() < k) {
                char c= s.charAt(i);
                if(c!='-'){
                    if(c>='a'&&c<='z')c+='A'-'a';
                    part.add(c);
                }
                i--;
            }
            if(part.size()>0)
            list.add(part);
            // if(part.size>=k)continue;
        }

        for (List<Character> part : list) {
            for (char c : part) {
                sb.append(c);
            }
            sb.append('-');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.reverse().toString();

    }
}
class SummaryRanges {
    Map<Integer,List<Integer>> mapS=new HashMap<>();
    Map<Integer,List<Integer>> mapE=new HashMap<>();
    Set<Integer> set=new HashSet<>();
    public SummaryRanges() {

    }
    public void addNum(int val) {
        //判断数是否在区间中 在集合中   或 数在区间边缘 i-1||i+1在集合中
        if(set.contains(val))return ;
        List<Integer> start=mapE.get(val-1);
        List<Integer> end=mapS.get(val+1);
        if(start!=null&&end!=null){
            start.set(1,end.get(1));
            end.set(0,start.get(0));//val开始
            //去除原来的
            mapS.remove(val+1);
            mapE.remove(val-1);
            //加入新的
            mapE.put(start.get(0),start);
            mapS.put(end.get(1),end);
        }
        else if(start!=null){
            start.set(1,val);
            //去除原来的
            mapE.remove(val-1);
            //加入新的
            mapE.put(val,start);
        }
        else if(end!=null){
            end.set(0,val);
            //去除原来的
            mapS.remove(val+1);
            //加入新的
            mapS.put(val,end);
        }
        else{ //都不包含
            List<Integer> list=
                    Arrays.asList(val,val);
            //加入新的
            mapE.put(val,list);
            mapS.put(val,list);
        }
        set.add(val);

    }

    public int[][] getIntervals() {
        //map <起点，list>  map<重点,list>
        int[][]ans=new int[mapS.size()][2];
        int index=0;
        for(List<Integer>list:mapS.values()){
            ans[index][0]=list.get(0);
            ans[index][1]=list.get(1);
            index++;
        }
        return ans;

    }
}

