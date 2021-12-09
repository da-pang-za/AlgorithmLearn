//
// Created by 大胖子 on 2021/12/9.
//
#include "../import.h"

class Solution {
public:
    void dfs(vector<vector<int> > &res, vector<int> &output, int first, int len) {
        // 所有数都填完了
        if (first == len) {
            res.__emplace_back(output);
            return;
        }
        for (int i = first; i < len; ++i) {
            // 动态维护数组
            swap(output[i], output[first]);
            // 继续递归填下一个数
            dfs(res, output, first + 1, len);
            // 撤销操作
            swap(output[i], output[first]);
        }
    }

    vector<vector<int> > permute(vector<int> &nums) {
        vector<vector<int> > res;
        dfs(res, nums, 0, (int) nums.size());
        return res;
    }
};

