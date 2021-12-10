//
// Created by 大胖子 on 2021/12/10.
//

#ifndef LEETCODECPP_NEXTNODE_H
#define LEETCODECPP_NEXTNODE_H

#define NULL nullptr
//二叉树中序遍历的下一个节点
/**
 * 当前节点有右子树  返回右子树的最左子树
 *
 * 当前节点无右子树：
 *  1.当前节点是父节点的左子树 返回父节点
 *  2.当前节点是父节点的右子树 (最不好处理的情况):
 *      沿着父节点向上 找到节点node node满足：node是node.parent的左子树(即1.的条件)  找不到返回null
 */
struct TreeLinkNode {
    int val;
    struct TreeLinkNode *left;
    struct TreeLinkNode *right;
    struct TreeLinkNode *parent; //父节点
    TreeLinkNode(int x) :val(x), left(NULL), right(NULL), parent(NULL) {

    }
};

class Solution {
public:
    TreeLinkNode* GetNext(TreeLinkNode* pNode)
    {
        if(pNode->right) { //如果有右子树，则找右子树的最左节点
            TreeLinkNode *p = pNode->right;
            while(p->left) p = p->left;
            return p;
        }
        TreeLinkNode *p = pNode;
        while(p->parent) { //没右子树，则找第一个当前节点是父节点左孩子的节点
            if(p->parent->left == p) return p->parent;
            p = p->parent;
        }
        return nullptr; //退到了根节点仍没找到，则返回null
    }
};


#endif //LEETCODECPP_NEXTNODE_H
