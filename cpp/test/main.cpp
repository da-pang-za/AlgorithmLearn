
//#include "Solution.cpp"

//#include "dataStructure/SegmentTree-test.h"

//#include <bits/stdc++.h>
//
//
//#include <bits/stdc++.h>
//
//
//using namespace std;
//
//
//using ll = long long;
//constexpr ll MOD = 1e9 + 7;
//
////meguine
//class Solution {
//public:
//    int totalStrength(vector<int> &arr) {
//        int n = arr.size();
//        ll ans = 0;
//        vector<ll> pf(n + 1), pf2(n + 1), pf3(n + 1);
//        for (int i = 0; i < n; ++i) {
//            pf[i + 1] = (pf[i] + arr[i]) % MOD;
//            pf2[i + 1] = (pf2[i] + 1ll * arr[i] * (n - i)) % MOD;
//            pf3[i + 1] = (pf3[i] + 1ll * arr[i] * (i + 1)) % MOD;
//        }
//        stack<int> stack;
//        arr.push_back(0);
//        stack.push(-1);
//        for (int i = 0; i <= n; ++i) {
//            while (stack.size() > 1 && arr[stack.top()] > arr[i]) {
//                // cout << "-------\n";
//                // cout << i << ':' << ans << endl;
//                int k = stack.top();
//                stack.pop();
//                ans += 1ll * arr[k] * arr[k] * (k - stack.top()) % MOD * (i - k) % MOD;
//                // cout  << arr[k] << '!' << ans << endl;
//                // cout << pf2[i] - pf2[k + 1] << ',' << (pf[i] - pf[k + 1]) * (n - i) << endl;
//                ans += (pf2[i] - pf2[k + 1] - 1ll * (pf[i] - pf[k + 1]) * (n - i) % MOD) % MOD * (k - stack.top()) % MOD *
//                       arr[k] % MOD;
//                if (k - stack.top() > 1) {
//                    // cout << pf3[k] - pf3[stack.top() + 1] << ';' << 1ll * (pf[k] - pf[stack.top() + 1]) * (stack.top() + 1) % MOD << endl;
//                    ans += (pf3[k] - pf3[stack.top() + 1] - 1ll * (pf[k] - pf[stack.top() + 1]) * (stack.top() + 1) % MOD) %
//                           MOD * (i - k) % MOD * arr[k] % MOD;
//
//                }
//                ans %= MOD;
//                // cout << ans << endl;
//            }
//            stack.push(i);
//        }
//        if (ans < 0) ans += MOD;
//        return ans;
//    }
//};
//
////wifii
//class Solution {
//public:
//    typedef long long ll;
//    static const int maxn = 100005;
//    int stk[maxn], val[maxn], ls[maxn], rs[maxn], tp = 0, tot = 0;
//
//    void insert(int x) {
//        int k = tp;
//        val[++tot] = x;
//        while (k && val[stk[k]] > val[tot]) --k;
//        if (k) rs[stk[k]] = tot;
//        if (k < tp) ls[tot] = stk[k + 1];
//        stk[++k] = tot;
//        tp = k;
//    }
//
//    int totalStrength(vector<int> &a) {
//        for (int i: a) insert(i);
//        int rt = stk[1], n = a.size();
//        vector<long long> s(n + 1), dp(n + 1), dp2(n + 1);
//        vector<int> sz(n + 1);
//        const int mod = 1e9 + 7;
//        long long ans = 0;
//        function<void(int)> dfs = [&](int u) {
//            sz[u] = 1;
//            s[u] = val[u];
//            if (ls[u]) dfs(ls[u]), s[u] += s[ls[u]], sz[u] += sz[ls[u]];
//            if (rs[u]) dfs(rs[u]), s[u] += s[rs[u]], sz[u] += sz[rs[u]];
//            dp[u] = dp[ls[u]] + 1ll * (sz[ls[u]] + 1) * (val[u] + s[rs[u]]) % mod + dp[rs[u]];
//            dp2[u] = dp2[ls[u]] + dp2[rs[u]] + 1ll * (sz[rs[u]] + 1) * (val[u] + s[ls[u]]) % mod;
//            dp[u] %= mod;
//            dp2[u] %= mod;
//            s[u] %= mod;
//            ans += (dp[ls[u]] * (1 + sz[rs[u]]) % mod + dp2[rs[u]] * (1 + sz[ls[u]]) % mod) % mod * val[u] % mod;
//            ans += 1ll * val[u] * val[u] % mod * (1 + sz[ls[u]]) % mod * (1 + sz[rs[u]]) % mod;
//        };
//        dfs(rt);
//        ans %= mod;
//        return ans;
//    }
//};

#include <iostream>
#include <algorithm>
#include <string.h>

using namespace std;

const int N = 100010;

int h[N], ip[N], pi[N], cnt;

void heap_swap(int a, int b)
{
    swap(ip[pi[a]], ip[pi[b]]);
    swap(pi[a], pi[b]);
    swap(h[a], h[b]);
}

void down(int u)
{
    int t = u;
    if (u * 2 <= cnt && h[u * 2] < h[t]) t = u * 2;
    if (u * 2 + 1 <= cnt && h[u * 2 + 1] < h[t]) t = u * 2 + 1;
    if (u != t)
    {
        heap_swap(u, t);
        down(t);
    }
}

void up(int u)
{
    while (u / 2 && h[u] < h[u / 2])
    {
        heap_swap(u, u / 2);
        u >>= 1;
    }
}

int main()
{
    int n, m = 0;
    scanf("%d", &n);
    while (n -- )
    {
        char op[5];
        int k, x;
        scanf("%s", op);
        if (!strcmp(op, "I"))
        {
            scanf("%d", &x);
            cnt ++ ;
            m ++ ;
            ip[m] = cnt, pi[cnt] = m;//ip:第i个数的位置  pi:位置i是第几个数
            h[cnt] = x;
            up(cnt);
        }
        else if (!strcmp(op, "PM")) printf("%d\n", h[1]);
        else if (!strcmp(op, "DM"))
        {
            heap_swap(1, cnt);
            cnt -- ;
            down(1);
        }
        else if (!strcmp(op, "D"))
        {
            scanf("%d", &k);
            k = ip[k];//第k个插入的数的索引
            heap_swap(k, cnt);
            cnt -- ;
            up(k);
            down(k);
        }
        else
        {
            scanf("%d%d", &k, &x);
            k = ip[k];
            h[k] = x;
            up(k);
            down(k);
        }
    }

    return 0;
}

// 作者：yxc
// 链接：https://www.acwing.com/activity/content/code/content/45305/
// 来源：AcWing
// 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
