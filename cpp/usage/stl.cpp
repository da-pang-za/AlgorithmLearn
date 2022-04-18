#include <iostream>
using namespace std;

using ll = long long;

//todo lambda表达式

class Solution2213 {
    using PII = pair<int, int>;
public:
    vector<int> longestRepeating(string s, string s2, vector<int>& q) {
        vector<PII> idx;
        int n = int(s.size()), k = int(s2.size());
        for (int i = 0; i < n; ++i) {
            if (idx.empty() || s[idx.back().first] != s[i]) {
                idx.emplace_back(i, 1);
            } else {
                idx.back().second ++;
            }
        }
        map<int, int> seg;
        multiset<int> st;
        for (auto &[x, y]: idx) seg[x] = y, st.emplace(y);
        auto Cut = [&](int i) -> void {
            if (i < 0 || i >= n) return;
            if (!seg.count(i)) {
                auto it = prev(seg.lower_bound(i));
                auto [x, y] = *it;
                st.extract(y);
                seg[x] = i - x;
                seg[i] = y - (i - x);
                st.emplace(i - x);
                st.emplace(y - (i - x));
            }
        };

        auto Union = [&](int j) -> void {
            if (j <= 0 || j >= n) return;
            auto it = seg.lower_bound(j);
            int i = prev(it)->first;
            if (s[i] == s[j]) {
                st.extract(seg[i]);
                st.extract(seg[j]);
                st.emplace(seg[i] + seg[j]);
                seg[i] += seg[j];
                seg.erase(j);
            }
        };

        vector<int> ans(k);
        for (int i = 0; i < k; ++i) {
            char a = s2[i];
            int j = q[i];
            if (s[j] == a) {
                ans[i] = *st.rbegin();
                continue;
            }
            s[j] = a;
            Cut(j), Cut(j + 1);
            Union(j), Union(j + 1);
            ans[i] = *st.rbegin();
        }
        return ans;
    }
};
//unordered_map vector
class Solution1477 {
    int INF = 0x3f3f3f3f;
public:
    int minSumOfLengths(vector<int> &arr, int target) {
        int n = arr.size();
        //枚举分割点
        //左侧和为target最短的
        vector<int> left(n, n);
        vector<int> right(n, n);
        unordered_map<int, int> map1;
        unordered_map<int, int> map2;
        map1[0] = -1;
        map2[0] = n;
        int sum = 0;
        //a-b=target
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            if (i > 0)left[i] = left[i - 1];
            if (map1.find(sum - target) != map1.end()) {
                int pre = map1.find(sum - target)->second;
                left[i] = min(left[i], i - pre);
            }
            map1[sum] = i;
        }
        sum = 0;
        for (int i = n - 1; i >= 0; i--) {
            sum += arr[i];
            if (i < n - 1)right[i] = right[i + 1];
            if (map2.find(sum - target) != map2.end()) {
                int pre = map2.find(sum - target)->second;
                right[i] = min(right[i], pre - i);
            }
            map2[sum] = i;
        }
        int ans = n + 1;
        for (int i = 0; i < n - 1; i++) {
            ans = min(ans, left[i] + right[i + 1]);
        }
        return ans > n ? -1 : ans;
    }
};

//静态变量
class Solution401 {
public:
    static unordered_map<int,vector<string>> mp;
    static int cnt;
    static int getCnt(int x) {
        int ans = 0;
        for (int i = x; i > 0; i -= lowbit(i)) ans++;
        return ans;
    }
    static int lowbit(int x) {
        return x & -x;
    }
    static void PreTable(){
        cout <<"test:" << cnt << endl; //这里可以看到只加载一次
        cnt++;
        for (int h = 0; h <= 11; h++) {
            for (int m = 0; m <= 59; m++) {
                int tot = getCnt(h) + getCnt(m);
                char buffer[6];
                sprintf(buffer,"%d:%02d",h,m);
                mp[tot].push_back(buffer);
            }
        }
    }

    vector<string> readBinaryWatch(int turnedOn) {
        if(cnt == 1) PreTable();
        return mp[turnedOn];
    }
};

//cpp类的非const静态变量要在类外初始化
int Solution401::cnt = 1;
unordered_map<int,vector<string>> Solution401::mp = {};


