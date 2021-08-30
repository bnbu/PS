#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <limits>
#include <algorithm>
using namespace std;

typedef pair<int, int> pii;

int n, m, x, y, a, b;
int last;
vector<vector<int>> elv_adj;
vector<vector<int>> elv;
vector<vector<int>> flr;
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
            if (flr_num == b) {
                last = curr_elv;
                return;
            }
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

    int ans = cnt[last];
    if (ans != INT32_MAX) {
        cout << ans << '\n';
        int temp = last;
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
// => bfs 도중 지금 타는 엘리베이터가 서는 층에 도착층이 포함시 바로 bfs 종료 하는 방식