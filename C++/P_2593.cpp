#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <limits>
#include <algorithm>
using namespace std;

typedef pair<int, int> pii;

int n, m, x, y, a, b;
vector<vector<int>> elv_adj; // 엘리베이터간 연결 정보
vector<vector<int>> elv; // 엘리베이터가 멈추는 층 수
vector<vector<int>> flr; // 각 층당 서는 엘리베이터 번호
vector<int> trace;
vector<int> cnt;
vector<bool> chk;
vector<bool> visit;
stack<int> st;

void bfs() {
    queue<int> q;
    for (int elv_num : flr[a]) {
        q.push(elv_num);
        cnt[elv_num] = 1;
    }

    while (!q.empty()) {
        int curr_elv = q.front(); q.pop();
        for (int flr_num : elv[curr_elv]) {
            for (int next_elv : flr[flr_num]) {
                if (cnt[next_elv] > cnt[curr_elv] + 1) {
                    cnt[next_elv] = cnt[curr_elv] + 1;
                    trace[next_elv] = curr_elv;
                    q.push(next_elv);
                }
            }
        }
    }
}

int main() {
    cin >> n >> m;
    elv_adj = vector<vector<int>>(m + 1);
    elv = vector<vector<int>>(m + 1);
    flr = vector<vector<int>>(n + 1);
    cnt = vector<int>(m + 1, INT32_MAX);
    trace = vector<int>(m + 1, 0);
    visit = vector<bool>(m + 1);

    for (int i = 1; i <= m; i++) {
        cin >> x >> y;
        while (x <= n) {
            flr[x].push_back(i);
            elv[i].push_back(x);
            x += y;
        }
    }

    cin >> a >> b;
    bfs();
    
    int ans = INT32_MAX;
    int min_num;
    for (int elv_num : flr[b]) {
        if (ans > cnt[elv_num]) {
            ans = cnt[elv_num];
            min_num = elv_num;
        }
    }

    if (ans != INT32_MAX) {
        cout << ans << '\n';
        int temp = min_num;
        while (temp != 0) {
            st.push(temp);
            temp = trace[temp];
        }
        while (!st.empty()) {
            cout << st.top() << '\n';
            st.pop();
        }
    }
    else cout << -1;

    return 0;
}
// 2021-08-03 해결
// 각 층을 엘리베이터 단위로 재구성시켜 그래프간 연결로 bfs 혹은 다익스트라로 최단경로
// 이후 이 최단경로에 대해 역추적을 하는 문제.

// 그러나 시간이 너무 느린데 다시 볼것