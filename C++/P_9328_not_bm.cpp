#include <iostream>
#include <vector>
#include <queue>
using namespace std;
typedef pair<int, int> pii;
vector<pii> start;
vector<vector<char>> map;
vector<vector<bool>> visit;
vector<bool> key, door;
int h, w, bit, ans;
int dx[4] = {0, 0, 1, -1};
int dy[4] = {1, -1, 0, 0};
void visit_init() {
    visit = vector<vector<bool>>(h + 2);
    for (int i = 0; i < h + 2; i++) visit[i] = vector<bool>(w + 2);
}
void bfs(int x, int y) {
    queue<pii> q;
    q.push({x, y});
    visit[y][x] = true;

    while (!q.empty()) {
        pii curr = q.front(); q.pop();
        int curr_x = curr.first;
        int curr_y = curr.second;
        if (map[curr_y][curr_x] == '$') {
            map[curr_y][curr_x] = '.';
            ans++;
        }

        for (int i = 0; i < 4; i++) {
            int next_x = curr_x + dx[i];
            int next_y = curr_y + dy[i];

            if (next_x < 0 || next_y < 0 || next_x >= w + 2 || next_y >= h + 2) continue;
            if (map[next_y][next_x] == '*') continue;
            if (map[next_y][next_x] >= 'A' && map[next_y][next_x] <= 'Z' && !door[map[next_y][next_x] - 'A']) continue;
            if (map[next_y][next_x] >= 'a' && map[next_y][next_x] <= 'z') {
                door[map[next_y][next_x] - 'a'] = true;
                map[next_y][next_x] = '.';
                visit_init();
                while(!q.empty()) q.pop();
            }

            if (!visit[next_y][next_x]) {
                visit[next_y][next_x] = true;
                q.push({next_x, next_y});
            }
        }
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int t; cin >> t;
    while (t--) {
        cin >> h >> w;
        start = vector<pii>();
        map = vector<vector<char>>(h + 2);
        map[0] = vector<char>(w + 2, '.');
        map[h + 1] = vector<char>(w + 2, '.');
        for (int i = 1; i <= h; i++) {
            map[i] = vector<char>(w + 2);
            map[i][0] = '.';
            map[i][w + 1] = '.';
            for (int j = 1; j <= w; j++) cin >> map[i][j];
        }
        
        door = vector<bool>(26);
        string s;
        cin >> s;
        if (s.compare("0") != 0) 
            for (char c : s)
                door[c - 'a'] = true;
        
        visit_init();
        ans = 0;
        bfs(0, 0);

        cout << ans << '\n';
    }
    return 0;
}
// 비트마스킹 안쓴 방법

// 문 26개에 대해 획득한 열쇠로 해당 문을 통과할 수 있게 배열을 수정해준다.
// 이때, 열쇠를 새로 획득할 때 마다 모든 경로를 다시 탐색해야하므로
// 방문 배열을 초기화 시켜버린다.
// 그리고 추가로 기존에 갈 곳도 모두 없애버린다.
// 즉, 지금 위치부터 다시 처음부터 방문한다고 생각하면 된다.

// 당연하겠지만, 비트마스킹 방법보다 메모리도 덜씀