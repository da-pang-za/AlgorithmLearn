package com.dpz.dataStructure;


public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {

    }

    public TreeNode(int x) {
        val = x;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return ""+val;
    }
    //中序遍历
    static public class Codec {
       static final String NULL="null";
       static final String split=",";
        // Encodes a tree to a single string.
       static public String serialize(TreeNode root) {
            if(root==null)return "null";
            StringBuilder sb=new StringBuilder();
            dfs1(root,sb);
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }
       static void dfs1(TreeNode root,StringBuilder sb){
            if(root==null)return;
            sb.append(root.val).append(split);
            if(root.left==null)sb.append(NULL).append(split);
            dfs1(root.left,sb);
            if(root.right==null)sb.append(NULL).append(split);
            dfs1(root.right,sb);
        }

        // Decodes your encoded data to tree.
       static int index=0;
       static public TreeNode deserialize(String data) {
            String[] nodes = data.split(split);
            index=0;
            return dfs2(nodes);
        }

       static TreeNode dfs2(String[] nodes){
            if(index>=nodes.length)return null;
            if(nodes[index].equals(NULL)){
                index++;
                return null;
            }
            TreeNode root=new TreeNode(Integer.parseInt(nodes[index]));
            index++;
            root.left=dfs2(nodes);
            root.right=dfs2(nodes);
            return root;
        }
    }

    public static void main(String[] args) {
        TreeNode root=TreeNode.Codec.deserialize("1,2,null,null,3,4,null,null,5,null,null");

        System.out.println(TreeNode.Codec.serialize(root));
    }
}
