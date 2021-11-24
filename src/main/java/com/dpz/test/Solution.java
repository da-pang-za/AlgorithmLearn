package com.dpz.test;


import java.util.*;

class Solution {
    static class Node {
        int val;
        List<Node> children = new ArrayList<>();

        Node(int v) {
            val = v;
        }
    }


    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n3 = new Node(3);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        n1.children.add(n3);
        n3.children.add(n5);
        n3.children.add(n6);
        System.out.println(maxDepth(n1));

    }

    static int maxDepth = 1;

    static public int maxDepth(Node root) {
        if (root != null) {
            dfs(root, 0);
        } else {
            return 0;
        }
        return maxDepth;
    }

    static private int dfs(Node root, int depth) {
        depth++;
        for (Node node : root.children) {
//            int k = dfs(node, depth);
//            maxDepth = Math.max(maxDepth, k);
             maxDepth = Math.max(maxDepth,dfs(node,depth));
        }
        return depth;
    }

}