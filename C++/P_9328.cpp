#include <iostream>
#include <vector>
#include <queue>
#include <tuple>
#include <unordered_map>
using namespace std;
typedef pair<int, int> pii;
vector<pii> start;
vector<vector<char>> map;
vector<vector<unordered_map<int, bool>>> visit;
int h, w, bit, ans;
int dx[4] = {0, 0, 1, -1};
int dy[4] = {1, -1, 0, 0};
void bfs(int x, int y) {
    queue<pii> q;
    q.push({x, y});
    visit[y][x][bit] = true;

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
            if (map[next_y][next_x] >= 'A' && map[next_y][next_x] <= 'Z' && !(bit & (1 << (map[next_y][next_x] - 'A')))) continue;
            if (map[next_y][next_x] >= 'a' && map[next_y][next_x] <= 'z') {
                bit = bit | (1 << (map[next_y][next_x] - 'a'));
                map[next_y][next_x] = '.';
            } 

            if (!visit[next_y][next_x][bit]) {
                visit[next_y][next_x][bit] = true;
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
        
        bit = 0;
        string s;
        cin >> s;
        if (s.compare("0") != 0) 
            for (char c : s)
                bit = bit | (1 << (c - 'a'));
        
        visit = vector<vector<unordered_map<int, bool>>>(h + 2);
        for (int i = 0; i < h + 2; i++) visit[i] = vector<unordered_map<int, bool>>(w + 2);
        
        ans = 0;
        bfs(0, 0);

        cout << ans << '\n';
    }
    return 0;
}
// 2021-12-22 해결
// 기본적으로 발상은 P_1939번과 동일했다.

// 일단 1가지를 놓치고 있었다.
// 건물의 가장자리의 벽이 아닌부분으로 오갈 수 있다
// 이를 조금 이상하게 생각하고 코드를 짜서 일단 틀림과 시간초과로 해매버렸다.

// 위의 말을 다시 잘 해석해보면
// 건물의 가장자리를 모두 벽이 아닌 빈칸으로 감싸버리면 되는 부분이다.
// 그렇게 감싸버리고 그냥 (0, 0) 부터 bfs를 시도하면 얘기한대로 가장자리를 통해 오가는 꼴이 된다.

// 문제는 이제 열쇠가 26개나 존재할 수 있고
// 문도 26가지 모두 존재할 수 있는 상황인데

// 이부분을 비트마스킹으로 하면 2^26=6700만 정도라 이 모든 경우를 각 좌표에 대해 전부 bool벡터를 만드려니
// 그건 좀 오바인거 같았다. 실제로도 1939번에서는 열쇠가 a~f 까지라 2^6으로 충분히 커버가 가능했지만
// 여기서는 어떻게할지 고민좀 해보다가

// 일단 임시로 사용한 방법은 visit를 벡터가 아닌 unordered_map으로 만들어서
// 확인할 비트에 해당하는 경우만 bit-visit 쌍을 생성시켜 저장하게 했다.

// 근데 이거도 결국은 모든 열쇠에 대해 확인을 해버리면 아무튼 메모리 소모는 커질것이다.

// 다른 방법으로는 열쇠 벡터 26개 / 문 벡터 26개 를 만들어서
// 열쇠를 획득시, 열쇠 벡터를 갱신
// 문을 만날시, 열쇠 벡터로부터 이 문을 지나칠 수 있는지 없는지를 확인

// 위의 방법으로 가능할 것 같다. 다만, 이렇게 되면 비트마스킹이 아니게 되버리긴 한다.
// 즉, 비트마스킹이 아니어도 풀수 있다는 뜻이다.