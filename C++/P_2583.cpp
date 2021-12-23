#include <iostream>
#include <algorithm>
#include <cstring>
#include <queue>
using namespace std;
typedef pair<int, int> pii;
bool** visit;
int** map;
int n, m, k;
int dx[4] = {0, 0, -1, 1};
int dy[4] = {1, -1, 0, 0};
int bfs(int y, int x) {
    int ret = 0;
    queue<pii> q;
    q.push({y, x});
    visit[y][x] = true;
    while (!q.empty()) {
        pii curr = q.front(); q.pop();
        int curr_y = curr.first;
        int curr_x = curr.second;
        ret++;
        for (int i = 0; i < 4; i++) {
            int next_y = curr_y + dy[i];
            int next_x = curr_x + dx[i];
            if (next_y < 1 || next_x < 1 || next_y > n || next_x > m) continue;
            if (map[next_y][next_x] == 1) continue;
            if (visit[next_y][next_x]) continue;
            q.push({next_y, next_x});
            visit[next_y][next_x] = true;
        }
    }
    return ret;
}
int main() {
    cin >> n >> m >> k;
    visit = new bool*[n + 1];
    map = new int*[n + 1];
    for (int i = 1; i <= n; i++) {
        visit[i] = new bool[m + 1];
        map[i] = new int[m + 1];
        memset(visit[i], false, sizeof(bool) * (m + 1));
        memset(map[i], 0, sizeof(int) * (m + 1));
    }

    while (k--) {
        int xs, ys, xe, ye;
        cin >> xs >> ys >> xe >> ye;
        for (int i = ys + 1; i <= ye; i++)
            for (int j = xs + 1; j <= xe; j++)
                map[i][j] = 1;
    }

    vector<int> ans;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= m; j++)
            if (map[i][j] == 0 && !visit[i][j])
                ans.push_back(bfs(i, j));

    sort(ans.begin(), ans.end());
    cout << ans.size() << '\n';
    for (int i : ans)
        cout << i << ' ';
    return 0;
}
// 2021-07-21 해결
// 간단한 bfs