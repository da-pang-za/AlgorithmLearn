package com.dpz.test;

import com.dpz.dataStructure.TreeNode;

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.verticalTraversal(new TreeNode(0));
    }
    int minleft=0;
    int maxright=0;
    Map<Integer,List<TreeNode>> map=new HashMap<>();//x-axis,node
    Map<TreeNode,Integer>nodeDepth=new HashMap<>();
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>>ans=new ArrayList<>();
        //node:depth val
        initMap(root,0,0);
        for(int i=minleft;i<=maxright;i++){
            List<TreeNode>xList=map.get(i);
            //Collections.sort(xList,(a, b)->{
            xList.sort((a, b) -> {
                int depthA = nodeDepth.get(a);
                int depthB = nodeDepth.get(b);
                //second val
                if (depthA == depthB) {
                    return a.val - b.val;
                }
                //first depth
                return depthA - depthB;
            });
            List<Integer> result=new ArrayList<>();
            for(TreeNode node:xList){
                result.add(node.val);
            }
            ans.add(result);
        }
        return ans;
    }
    void initMap(TreeNode root,int x,int depth){
        if(root==null)return ;
        nodeDepth.put(root,depth);
        map.getOrDefault(x,new ArrayList<>()).add(root);
        if(minleft>x)minleft=x;
        if(maxright<x)maxright=x;
        initMap(root.left,x-1,depth+1);
        initMap(root.right,x+1,depth+1);
    }
}