#include "import.h"

using namespace std;






using ll = long long;
int INF = 0x3f3f3f3f;


class Solution {
    int n;
    unordered_map<char, int> map;
public:
    int balancedString(string s) {
        vector<char> ab = {'Q', 'W', 'E', 'R'};
        n = s.size();
        for (int i = 0; i < n; i++) {
            map[s[i]] += 1;
        }
        int m = n / 4;
        if(map['Q'] <= m and map['W'] <= m
           and map['E'] <= m and map['R'] <= m)return 0;
        int ans = n;

        for (int l = 0, r = 0; r < n; r++) {
            map[s[r]]--;
            while (l <= r and
                   (map['Q'] <= m and map['W'] <= m
                    and map['E'] <= m and map['R'] <= m)) {
                map[s[l]]++;
                ans = min(ans, r - l+1);
                l++;
            }
        }
        return ans;
    }

};