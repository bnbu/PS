#include <iostream>
#include <tuple>
#include <queue>
using namespace std;

bool** visit;
int dx[8] = {1, 2, 2, 1, -1, -2, -2, -1};
int dy[8] = {2, 1, -1, -2, -2, -1, 1, 2};

int bfs(int y, int x, int l, int ey, int ex) {
    queue<tuple<int, int, int>> q;
    q.push(make_tuple(y, x, 0));
    visit[y][x] = true;
    while (!q.empty()) {
        tuple<int, int, int> curr = q.front(); q.pop();
        int curr_y = get<0>(curr);
        int curr_x = get<1>(curr);
        int curr_d = get<2>(curr);
        if (curr_y == ey && curr_x == ex) {
            return curr_d;
        }
        for (int i = 0; i < 8; i++) {
            int next_y = curr_y + dy[i];
            int next_x = curr_x + dx[i];
            
            if (next_y < 0 || next_x < 0 || next_y >= l || next_x >= l) continue;
            if (visit[next_y][next_x]) continue;

            q.push(make_tuple(next_y, next_x, curr_d + 1));
            visit[next_y][next_x] = true;
        }
    }
}

int main() {
    int t;
    cin >> t;
    while (t--) {
        int l;
        cin >> l;
        int x, y;
        cin >> x >> y;
        int ex, ey;
        cin >> ex >> ey;
        visit = new bool*[l];
        for (int i = 0; i < l; i++) {
            visit[i] = new bool[l];
            for (int j = 0; j < l; j++)
                visit[i][j] = false;
        }
        cout << bfs(y, x, l, ey, ex) << '\n';
    }

    return 0;
}