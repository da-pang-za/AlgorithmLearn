package com.dpz;

//你可以将以下二叉树：
//
    //        1
    //        / \
//        2   3
        //        / \
    //          4   5
//
//        序列化为 "[1,2,3,null,null,4,5]"


import java.util.Deque;
import java.util.LinkedList;

public class Codec {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root==null)return "[]";//不用加null
        int deep=getDeep(root);//从1开始
        StringBuilder sb=new StringBuilder();
        sb.append('[');
        //层次遍历     每层节点数(包括null)2^d      //深度d 从0开始
        int d=1;
        //队列
        Deque<TreeNode >layer=new LinkedList<>();
        layer.offerLast(root);
        while (d<=deep){
            Deque<TreeNode >layer1=new LinkedList<>();
            //遍历当前层  加到sb   子节点加到队列
            while(!layer.isEmpty()){
                TreeNode node=layer.pollFirst();
                if(node==null){
                    sb.append("null");
                    layer1.offerLast(null);
                    layer1.offerLast(null);
                }
                else{
                    sb.append(node.val);
                    layer1.offerLast(node.left);
                    layer1.offerLast(node.right);
                }
                sb.append(',');

            }
            layer=layer1;
            d++;

        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(']');
        return sb.toString();


    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[]nodes = data.split(", ");
        if(nodes.length==0)return null;

        nodes[0]=nodes[0].substring(1);
        nodes[nodes.length-1]=nodes[nodes.length-1].substring(0,nodes[nodes.length-1].length()-2);
        int deep=(int)(Math.log(nodes.length)/Math.log(2))+1;
        TreeNode root=new TreeNode(Integer.parseInt(nodes[0]));
        TreeNode node=root;
        Deque<TreeNode>lastLayer=new LinkedList<>();
        int index=0;
        lastLayer.offerLast(root);
        int d=2;
        while (d<=deep){
            while(!lastLayer.isEmpty()){
                node=lastLayer.pollFirst();
                if(node==null){
                    index++;
                }
                else{

                    String t=nodes[2*(index+1)];
                    if(t.equals("null"))
                        node.left=null;
                    else node.left=new TreeNode(Integer.parseInt(t));
                    t=nodes[2*(index+1)+1];
                    if(t.equals("null"))
                        node.right=null;
                    else node.right=new TreeNode(Integer.parseInt(t));
                    lastLayer.offerLast(node.left);
                    lastLayer.offerLast(node.right);
                    index++;

                }

            }


        }


return root;
    }

    int getDeep(TreeNode root){//从1开始
        if(root==null)return 0;
        return 1+Math.max(getDeep(root.left),getDeep(root.right));

    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
