package com.company;

import java.util.*;


public class Sol1 {
    public static void main(String[] args) {
        Sol1 sol1 = new Sol1();
        sol1.largestRectangleArea(new int[]{2,4});


    }

    HashMap<List<String>, Double> map = new HashMap<>();
    HashMap<String, List<String>> link = new HashMap<>();

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] ret = new double[queries.size()];
        int i = 0;
        for (List<String> l : equations) {
            map.put(l, values[i]);
            List<String> r = new ArrayList<>(l);
            Collections.reverse(r);
            map.put(r, 1 / values[i]);
            List<String> l1 = link.getOrDefault(l.get(0), new ArrayList<>());
            l1.add(l.get(1));
            List<String> l2 = link.getOrDefault(l.get(1), new ArrayList<>());
            l2.add(l.get(0));
            link.put(l.get(0), l1);
            link.put(l.get(1), l2);
            i++;
        }
        i = 0;
        for (List<String> l : queries) {
            //判断能否找到 找到则返回路径
            List<List<String>> path = find(l);
            if (path.size() == 0) ret[i++] = -1;
            else {
                if (path.size() == 1 && !map.containsKey(l)) {
                    ret[i++] = 1;
                    continue;
                }
                double v = 1;
                for (List<String> t : path) {
                    v *= map.get(t);
                }
                ret[i++] = v;
            }


        }

        return ret;
    }

    List<List<String>> list;

    //从l0 到 l1
    List<List<String>> find(List<String> l) {
        list = new ArrayList<>();
        if (l.get(0).equals(l.get(1))) {
            if (link.containsKey(l.get(0))) ;
            {
                list.add(l);
            }
            return list;

        }
        if (!dfs(l.get(0), l.get(1))) return new ArrayList<>();
        return list;

    }

    boolean dfs(String start, String end) {
        if (start.equals(end)) {
            return true;
        }
        if (!link.containsKey(start)) return false;

        for (String s : link.get(start)) {
            List<String> t = new ArrayList();
            t.add(start);
            t.add(s);
            list.add(t);
            if (dfs(s, end)) return true;
            list.remove(list.size() - 1);
        }
        return false;
    }

    public String minWindow(String s, String t) {
        HashMap<Character, Integer> smap = new HashMap<>();
        HashMap<Character, Integer> tmap = new HashMap<>();
        int l=-1,r=s.length()-1,min=Integer.MAX_VALUE;
        //t的所有字符
        for (int i = 0; i < t.length(); i++) {
            tmap.put(t.charAt(i),tmap.getOrDefault(t.charAt(i), 0) + 1);
        }
        int last;
        Deque<Integer>queue=new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {

                if(tmap.containsKey(s.charAt(i))){
                    queue.add(i);
                    smap.put(s.charAt(i),smap.getOrDefault(s.charAt(i), 0) + 1);
                    //比较
                    while(!queue.isEmpty()&&check(smap,tmap)){
                        //可能的答案
                        //长度
                        last=queue.remove();
                        smap.put(s.charAt(last),smap.get(s.charAt(last))-1);
                        int len=i-last+1;
                        if(len<min){
                            min=len;
                            l=last;
                            r=i;
                        }

                    }
                }

        }
        return l==-1?"":s.substring(l,r+1);

    }
    boolean check(Map<Character, Integer> smap,Map<Character, Integer> tmap){
        boolean flag = true;
        for (Map.Entry<Character, Integer> e : tmap.entrySet()) {
            if (smap.getOrDefault(e.getKey(),0)<e.getValue()){
                flag=false;
                break;
            }

        }
        return flag;
    }

    public int largestRectangleArea(int[] heights) {
        if(heights.length==0)return 0;
        int n=heights.length;
        int[]left=new int[n];
        int[]right=new int[n];
        //left
        //左侧小于当前位置的最近index    计算i-j *height，max
        //压栈的时候 左侧比当前值大的出栈

        //right 同理
        Deque<Integer>stack=new LinkedList<>();


        stack.offerLast(-1);
        stack.offerLast(0);

        left[0]=-1;

        for(int i=1;i<n;i++){

            while(!stack.isEmpty()&&stack.peekLast()!=-1&&heights[stack.peekLast()]>heights[i]){
                stack.pollLast();
            }
            if(stack.isEmpty()){
                left[i]=-1;
            }
            else{
                left[i]=stack.peekLast();
            }
            stack.offerLast(i);
        }
        stack=new LinkedList<>();
        stack.offerLast(n);
        stack.offerLast(n-1);
        right[n-1]=n;
        for(int i=n-2;i>=0;i--){
            while(!stack.isEmpty()&&stack.peekLast()!=n&&heights[stack.peekLast()]>heights[i]){
                stack.pollLast();
            }
            if(stack.isEmpty()){
                right[i]=n;
            }
            else{
                right[i]=stack.peekLast();
            }
            stack.offerLast(i);

        }
        int max=0;
        for(int i=0;i<n;i++){
            max=Math.max(heights[i]*(right[i]-left[i]-1),max);
        }
        return max;


    }

    public int maxEnvelopes(int[][] envelopes) {
        //排序
        //向前找能装下的&& 最大的  n^2
        //装完和内层信封就没关系了
        Arrays.sort(envelopes, (o1, o2) -> {
            if(o1[0]==o2[0]){
                return o1[1]-o2[1];
            }
            return o1[0]-o2[0];
        });
        int []dp=new int[envelopes.length];
        for (int i = 0; i <envelopes.length ; i++) {
            dp[i]=1;
        }
        int max=1;
        for (int i = 1; i < envelopes.length ; i++) {
            int w=envelopes[i][0];
            int h=envelopes[i][1];
            for(int j=0;j<i;j++){
                if(envelopes[j][0]<w&&envelopes[j][1]<h){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            max=Math.max(max,dp[i]);

        }
        return max;

    }
}
