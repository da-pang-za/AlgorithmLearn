//引用:https://oi-wiki.org/lang/reference/
#include "import.h"
using namespace std;

using ll = long long;
//左值引用
//C++11引入右值引用，标准库都已经支持，所以返回vector对象或者是传入vector引用性能上没有什么差别？

using ll = long long;

class Solution_ZH {
public:
    int goShopping(vector<int> &priceA, vector<int> &priceB) {
        int n = priceA.size();
        vector<int> order(n);
        for (int i = 0; i < n; ++i)
            order[i] = i;
        sort(order.begin(), order.end(),
             [&](int i, int j) { return priceB[i] > priceB[j]; });

        ll ans = LLONG_MAX;

        vector<vector<ll>> dp1(4, vector<ll>(3, LLONG_MAX));
        dp1[0][0] = 0;
        for (int i : order) {
            ll pa = priceA[i], pb = priceB[i];
            vector<vector<ll>> ndp1(4, vector<ll>(3, LLONG_MAX));
            for (int j = 0; j < 4; ++j)
                for (int k = 0; k < 3; ++k) {
                    if (dp1[j][k] < LLONG_MAX) {
                        ndp1[min(j + 1, 3)][k] =
                                min(ndp1[min(j + 1, 3)][k], dp1[j][k] + pa * 7);
                        ndp1[j][(k + 1) % 3] =
                                min(ndp1[j][(k + 1) % 3], dp1[j][k] + (k == 2 ? 0LL : pb * 10));
                    }
                }
            dp1 = ndp1;
        }
        for (int k = 0; k < 3; ++k)
            ans = min(ans, dp1[3][k]);

        vector<vector<ll>> dp2(3, vector<ll>(3, LLONG_MAX));
        dp2[0][0] = 0;
        for (int i : order) {
            ll pa = priceA[i], pb = priceB[i];
            vector<vector<ll>> ndp2(3, vector<ll>(3, LLONG_MAX));
            for (int j = 0; j < 3; ++j)
                for (int k = 0; k < 3; ++k) {
                    if (dp2[j][k] < LLONG_MAX) {
                        if (j < 2)
                            ndp2[j + 1][k] = min(ndp2[j + 1][k], dp2[j][k] + pa * 10);
                        ndp2[j][(k + 1) % 3] =
                                min(ndp2[j][(k + 1) % 3], dp2[j][k] + (k == 2 ? 0LL : pb * 10));
                    }
                }
            dp2 = ndp2;
        }
        for (int j = 0; j < 3; ++j)
            for (int k = 0; k < 3; ++k)
                ans = min(ans, dp2[j][k]);

        return ans / 10;
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