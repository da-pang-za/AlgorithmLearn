#include "Solution.h"


#include <iostream>
#include <vector>

using namespace std;

//ACM mode
int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int n, q;
    cin >> n >> q;

    int dir[8][2] = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
    int r = 100;
    int w = r * 2 + 1;
    vector<vector<int> > map(w, vector<int>(w, 0));
    map[r][r] = n;

    bool f = true;
    while (f) {
        f = false;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < w; y++) {
                if (map[x][y] >= 8) {
                    f = true;
                    for (int i = 0; i < 8; i++) {
                        int nx = x + dir[i][0];
                        int ny = y + dir[i][1];
                        map[nx][ny] += map[x][y] / 8;
                    }
                    map[x][y] %= 8;
                }
            }
        }
    }

    vector<vector<int> > sum(w, vector<int>(w));
    for (int x = 0; x < w; x++) {
        for (int y = 0; y < w; y++) {
            if (x == 0 && y == 0) sum[x][y] = map[x][y];
            else if (x == 0) sum[x][y] = map[x][y] + sum[x][y - 1];
            else if (y == 0) sum[x][y] = map[x][y] + sum[x - 1][y];
            else sum[x][y] = map[x][y] + sum[x - 1][y] + sum[x][y - 1] - sum[x - 1][y - 1];
        }
    }

    while (q--) {
        int x1, y1, x2, y2;
        cin >> x1 >> y1 >> x2 >> y2;
        x1 = max(x1 + r, 1);
        y1 = max(y1 + r, 1);
        x2 = min(x2 + r, w - 1);
        y2 = min(y2 + r, w - 1);
        if (x1 > x2 || y1 > y2) {
            cout << 0 << endl;
        } else {
            cout << sum[x2][y2] - sum[x2][y1-1] - sum[x1-1][y2] + sum[x1-1][y1-1] << endl;
        }
    }

    return 0;
}

