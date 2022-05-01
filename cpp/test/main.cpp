
//#include "Solution.cpp"

//#include "dataStructure/SegmentTree-test.h"

#include <bits/stdc++.h>


#include <bits/stdc++.h>


using namespace std;

const int N = 2e4 + 10;
int ne[N * 20][2], cnt[N * 15], idx;

class Solution {
public:
    // 在字典数中插入一个数
    void insert(int x) {
        int p = 0;
        for (int i = 15; i >= 0; i--) {
            int j = x >> i & 1;
            if (!ne[p][j]) ne[p][j] = ++idx;
            p = ne[p][j];
            // 注意这里cnt记录的是该节点向下总数字的个数
            cnt[p]++;
        }
    }

    int query(int x, int hi) {
        int p = 0;
        int res = 0;
        for (int i = 15; i >= 0; i--) {
            int j = x >> i & 1, k = hi >> i & 1;
            // hi的当前位为1，则下一步可以往异或结果为1也可以往为0的方向走，往0的方向剪枝，因为亦或后的结果最大不可能超过hi了
            if (k) {
                // 相同的值，异或结果为0，可以直接剪枝
                if (ne[p][j]) res += cnt[ne[p][j]];
                // 如果不同值不存在，则直接返回
                if (!ne[p][1 - j]) return res;
                // 继续向下
                p = ne[p][1 - j];
            } else {
                // k为0，只能往抑或结果为0的方向走，不存在的话直接返回
                if (!ne[p][j]) return res;
                p = ne[p][j];
            }
        }
        // 不要忘记加上最后叶节点的结果
        res += cnt[p];
        return res;
    }

    int countPairs(vector<int>& nums, int low, int high) {
        memset(ne, 0, sizeof ne);
        memset(cnt, 0, sizeof cnt);
        idx = 0;

        int n = nums.size();
        int res = 0;
        for (int i = 0; i < n; i++) {
            int a = nums[i];
            insert(a);
            res += query(a, high) - query(a, low - 1);
        }

        return res;
    }
};

