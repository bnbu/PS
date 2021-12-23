#include <iostream>
#include <cstring>
#include <queue>
using namespace std;
typedef pair<int, int> pii;
bool** visit;
int** map;
int n, m, k;
int ans = 0;
int dx[4] = {0, 0, -1, 1};
int dy[4] = {1, -1, 0, 0};
void bfs(int y, int x) {
    int cnt = 0;
    queue<pii> q;
    q.push({y, x});
    visit[y][x] = true;
    while(!q.empty()) {
        pii curr = q.front(); q.pop();
        int curr_y = curr.first;
        int curr_x = curr.second;
        cnt++;
        for (int i = 0; i < 4; i++) {
            int next_y = curr_y + dy[i];
            int next_x = curr_x + dx[i];
            if (next_y < 1 || next_x < 1 || next_y > n || next_x > m) continue;
            if (map[next_y][next_x] == 0) continue;
            if (visit[next_y][next_x]) continue; 
            q.push({next_y, next_x});
            visit[next_y][next_x] = true;
        }
    }
    ans = max(ans, cnt);
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
        int r, c;
        cin >> r >> c;
        map[r][c] = 1;
    }

    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= m; j++)
            if (!visit[i][j] && map[i][j] == 1)
                bfs(i, j);

    cout << ans;
    return 0;
}

// 2021-07-27 해결
// 간단한 bfs