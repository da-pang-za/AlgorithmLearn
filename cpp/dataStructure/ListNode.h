//
// Created by 大胖子 on 2021/12/9.
//

#ifndef LEETCODECPP_LISTNODE_H
#define LEETCODECPP_LISTNODE_H


struct ListNode {
    int val;
    ListNode *next;

    ListNode() : val(0), next(nullptr) {}

    ListNode(int x) : val(x), next(nullptr) {}

};

#endif //LEETCODECPP_LISTNODE_H
