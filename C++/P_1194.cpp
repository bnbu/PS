#include <iostream>
#include <vector>
#include <queue>
#include <tuple>
#include <algorithm>
using namespace std;

typedef tuple<int, int, int, int> t;
// {x, y, cnt, bit}

int dx[4] = {0, 0, -1, 1};
int dy[4] = {1, -1, 0, 0};
int n, m, xs, ys;
vector<vector<char>> map;
vector<vector<vector<bool>>> visit;

int bfs(int y, int x) {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    queue<t> q;
    q.push({y, x, 0, 0});
    visit[y][x][0] = true;
    while (!q.empty()) {
        t curr = q.front(); q.pop();
        int curr_y = get<0>(curr);
        int curr_x = get<1>(curr);
        int curr_c = get<2>(curr);
        int curr_b = get<3>(curr);
        if (map[curr_y][curr_x] == '1') return curr_c;
        for (int i = 0; i < 4; i++) {
            int next_y = curr_y + dy[i];
            int next_x = curr_x + dx[i];
            if (next_y < 1 || next_x < 1 || next_y > n || next_x > m) continue;
            if (map[next_y][next_x] == '#') continue;

            if (map[next_y][next_x] >= 'A' && map[next_y][next_x] <= 'F') {
                int chk_num = map[next_y][next_x] - 'A';
                if (curr_b & 1 << chk_num && !visit[next_y][next_x][curr_b]) {
                    q.push({next_y, next_x, curr_c + 1, curr_b});
                    visit[next_y][next_x][curr_b] = true;
                }
                else continue;
            }
            else if (map[next_y][next_x] >= 'a' && map[next_y][next_x] <= 'f') {
                int chk_num = map[next_y][next_x] - 'a';
                int next_b = curr_b | 1 << chk_num;
                if (!visit[next_y][next_x][next_b]) {
                    q.push({next_y, next_x, curr_c + 1, next_b});
                    visit[next_y][next_x][next_b] = true;
                }
            }
            else {
                if (!visit[next_y][next_x][curr_b]) {
                    q.push({next_y, next_x, curr_c + 1, curr_b});
                    visit[next_y][next_x][curr_b] = true;
                }
            }
        }
    }

    return -1;
}
int main() {

    cin >> n >> m;
    map = vector<vector<char>>(n + 1);
    visit = vector<vector<vector<bool>>>(n + 1);
    for (int i = 1; i <= n; i++) {
        map[i] = vector<char>(m + 1);
        visit[i] = vector<vector<bool>>(m + 1);
        for (int j = 1; j <= m; j++) {
            visit[i][j] = vector<bool>(1 << 6, false);
            cin >> map[i][j];
            if (map[i][j] == '0') {
                ys = i;
                xs = j;
            }
        }
    }
    cout << bfs(ys, xs);
    return 0;
}
// 2021-08-12 해결
// bfs에 비트마스킹을 섞은 문제

// bfs간 필요한 값은 총 4개
// y좌표, x좌표, 이동횟수, 비트마스킹 상태값(어떤 열쇠를 소유중인지)

// 평범한 bfs대로 진행한다
// 경계션 밖 -> continue
// 벽 ('#') -> continue

// 문 ('A' ~ 'F')는 열쇠 ('a' ~ 'f')가 먼저 선행되어야 한다.
// 따라서 열쇠를 먹었는지 안먹었는지에 대한것을 체크해야 한다.
// 이를 체크하기 위해 비트마스킹을 사용, 000000(2) ~ 111111(2)

// 이떄, 방문 여부를 체크할때, 이미 지나온 길이여도
// 열쇠를 새로 보유중이라면 이전에 지나쳐온 문 중에 열쇠가 일치하는지를
// 다시 체크해봐야 하기 때문에 다시 가야할 필요가 있다.
// 따라서 방문여부를 판단할 배열을 다음과 같이 한다
// visit[y][x][b]
// => 열쇠를 비트마스킹 상태 b와 같이 보유하고 있을때, 좌표 y, x의 방문 여부

// 열쇠를 획득했다면, 현재 비트마스킹 상태를 변경시키고
// 문에 도달시, 현재 비트마스킹 상태로부터 이 문에 맞는 열쇠를 갖고 있는지 체크하여
// 없으면 지나갈 수 없고, 있으면 지나갈 수 있게 하면 된다.

// 문제를 맨 처음 A~F 를 A~Z로 잘못 봐가지고 
// 최대 50*50*2^26 배열이 만들어져서 메모리 초과가 났었다.