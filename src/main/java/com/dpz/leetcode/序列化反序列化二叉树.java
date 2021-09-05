package com.dpz.leetcode;

import com.dpz.dataStructure.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 剑指 Offer II 048. 序列化与反序列化二叉树
 * https://leetcode-cn.com/problems/h54YBf/
 * -1000 <= Node.val <= 1000
 */
public class 序列化反序列化二叉树 {
    private static String NULL = "null";
    private static TreeNode NULL_NODE = new TreeNode(9999);

    // 广度遍历 层次遍历
    public static String serialize(TreeNode root) {
        if(root==null)return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Deque<TreeNode> deque = new ArrayDeque<>();
        Deque<TreeNode> deque1 = new ArrayDeque<>();
        int count = 0;//下一层的非空节点数
        if (root != null) {
            deque.addLast(root);
            count++;
        }
        while (!deque.isEmpty() && count != 0) {
            count = 0;
            while (!deque.isEmpty()) {
                TreeNode node = deque.pollFirst();
                if (!isNull(node)) {
                    sb.append(node.val).append(',');
                    if (node.left != null) {
                        deque1.add(node.left);
                        count++;

                    } else {
                        deque1.add(NULL_NODE);
                    }
                    if (node.right != null) {
                        deque1.add(node.right);
                        count++;

                    } else {
                        deque1.add(NULL_NODE);
                    }
                } else {
                    sb.append(NULL).append(',');
                    deque1.add(NULL_NODE);
                    deque1.add(NULL_NODE);
                }

            }
            Deque<TreeNode> t = deque;
            deque = deque1;
            deque1 = t;
        }
        sb.deleteCharAt(sb.length()-1);//    删除,
        sb.append(']');
        return sb.toString();

    }

    private static boolean isNull(TreeNode root) {
        assert root != null;
        return root.val == NULL_NODE.val;

    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        if(data.length()==2)return null;
        String[] strs = data.substring(1, data.length() - 1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(strs[0]));
        Deque<TreeNode> deque = new ArrayDeque<>();
        Deque<TreeNode> deque1 = new ArrayDeque<>();
        int i = 1;
        deque.add(root);
        while (!deque.isEmpty() && i < strs.length) {
            while (!deque.isEmpty() && i < strs.length) {
                TreeNode node = deque.pollFirst();
                if (!strs[i++].equals(NULL)) {
                    node.left = new TreeNode(Integer.parseInt(strs[i-1]));
                } else {
                    node.left = new TreeNode(9999);
                }
                if (i < strs.length) {
                    if (!strs[i++].equals(NULL)) {
                        node.right = new TreeNode(Integer.parseInt(strs[i-1]));
                    } else {
                        node.right = new TreeNode(9999);
                    }

                }
                deque1.addLast(node.left);
                deque1.addLast(node.right);
            }
            Deque<TreeNode> t = deque;
            deque = deque1;
            deque1 = t;

        }
        //去除9999
        return deleteNull(root);

    }
    private  static TreeNode deleteNull(TreeNode root){
        if(root==null||root.val== NULL_NODE.val)return null;
        root.left=deleteNull(root.left);
        root.right=deleteNull(root.right);
        return root;

    }

    //test
    public static void main(String[] args) {
        System.out.println(Integer.parseInt("213]"));
        System.out.println(Integer.parseInt("[213"));

    }
}