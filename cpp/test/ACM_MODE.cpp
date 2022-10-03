#include "Solution.cpp"

#include "stl.h"

class CountIntervals {
    typedef pair<int, int> pii;

    int ans = 0;
    set<pii> st;

public:
    CountIntervals() {
    }

    void add(int left, int right) {
        int L = left, R = right;
        // 这里 (R1, L1) >= (R2, L2)，若 R1 > R2 或 R1 = R2 且 L1 >= L2
        auto it = st.lower_bound(pii(left - 1, -2e9));
        while (it != st.end()) {
            if (it->second > right + 1) break;
            L = min(L, it->second);
            R = max(R, it->first);
            ans -= it->first - it->second + 1;
            st.erase(it++);
        }
        ans += R - L + 1;
        st.insert(pii(R, L));
    }

    int count() {
        return ans;
    }
};


int main() {


    CountIntervals *opt = new CountIntervals();
    opt->add(1,3);
    opt->add(5,6);
    opt->add(3,5);
    cout<<endl;


}
