//
// Created by 大胖子 on 2021/12/9.
//

#ifndef LEETCODECPP_SOLUTION_H
#define LEETCODECPP_SOLUTION_H


#include "../import.h"

class Solution {
public:
    ListNode* reverseList(ListNode* head) {
        ListNode* prev = nullptr;
        ListNode* curr = head;
        while (curr) {
            ListNode* next = curr->next;
            curr->next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
};


#endif //LEETCODECPP_SOLUTION_H
