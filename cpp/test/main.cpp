#include "Solution.h"

int main() {
    Solution solution = Solution();
    vector<string>list={"hot","dot","dog","lot","log","cog"};
    const vector<vector<string>> &ans = solution.findLadders("hit", "cog", list);
    for(auto&line:ans){
        for(auto&v:line){
            cout<<v<<"->";
        }
        cout<<endl;
    }


}