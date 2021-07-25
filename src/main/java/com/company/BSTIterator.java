package com.company;

import java.util.Stack;

class BSTIterator {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    TreeNode pre=new TreeNode(0);
    TreeNode cur;//当前节点
    TreeNode root;
    Stack<TreeNode>pres=new Stack<>();
    public BSTIterator(TreeNode root) {

        if(root!=null)
        {
            //TreeNode pre=new TreeNode(0);
            //pres.push(new TreeNode(0));
            this.root=root;
            //pre.right=root;
            cur=root;
            while(cur.left!=null)
            {
                pres.push(cur);
                cur=cur.left;
            }
            pre.right=cur;
            cur=pre;
        }
        else
            cur=new TreeNode(0);
    }

    /** @return the next smallest number */
    public int next() {
        //有右子树：右子树的最左子树
        //没有右子树：pre
        if(cur.right==null)
        {
            cur=pres.pop();
            return cur.val;

        }
        else
        {

            cur=cur.right;
            while(cur.left!=null)
            {
                pres.push(cur);
                cur=cur.left;
            }
            return cur.val;
        }

    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return cur.right!=null||!pres.isEmpty();

    }
}
