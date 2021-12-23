#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <limits>
using namespace std;

int n, m, k, s, p, q;
bool* danger_chk;
bool* visit;
long long* cost;
long long* d;
vector<int> danger;
vector<int>* adj;

void bfs() {
    queue<pair<int, int>> que; // 순서대로 w, v
    for (int d : danger) {
        que.push(make_pair(0, d));
        visit[d] = true;
    }

    while (!que.empty()) {
        pair<int, int> curr = que.front(); que.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;
        cost[curr_v] = q;
        for (int next : adj[curr_v]) {
            if (!visit[next] && curr_w + 1 <= s) {
                visit[next] = true;
                que.push(make_pair(curr_w + 1, next));
            }
        }
    }
}

void dijkstra(int v) {
    priority_queue<pair<long long, int>, vector<pair<long long, int>>, greater<pair<long long, int>>> pq;
    pq.push(make_pair(0, v));
    d[v] = 0;
    while (!pq.empty()) {
        pair<long long, int> curr = pq.top(); pq.pop();
        long long curr_w = curr.first;
        int curr_v = curr.second;
        if (d[curr_v] != curr_w) continue;
        for (int next : adj[curr_v]) {
            if (danger_chk[curr_v]) continue;
            if (d[next] > curr_w + cost[next]) {
                d[next] = curr_w + cost[next];
                pq.push(make_pair(d[next], next));
            }
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);
    cin >> n >> m >> k >> s >> p >> q;
    adj = new vector<int>[n + 1];
    visit = new bool[n + 1];
    danger_chk = new bool[n + 1];
    cost = new long long[n + 1];
    d = new long long[n + 1];
    fill(visit, visit + n + 1, false);
    fill(danger_chk, danger_chk + n + 1, false);
    fill(cost, cost + n + 1, p);
    fill(d, d + n + 1, INT64_MAX);

    for (int i = 0; i < k; i++) {
        int a;
        cin >> a;
        danger_chk[a] = true;
        danger.push_back(a);
    }

    while (m--) {
        int u, v;
        cin >> u >> v;

        adj[u].push_back(v);
        adj[v].push_back(u);
    }

    bfs();
    dijkstra(1);

    cout << d[n] - cost[n];

    return 0;
}

// 2021-07-22 해결
// 점령당한 도시로부터 bfs를 통하여, 범위 내 위험군 도시를 파악하여 cost 배열을 갱신해놓고
// 이 갱신한 배열로 다익스트라를 진행
// ** 최단거리 계산값이 int 값을 넘어서기에 long long으로 고침

// 그 외에는 bfs를 여러번 잘못 돌린 이슈나
// 여러 요소로 시간초과가 떠버린 적이 있다.
// 불필요한 반복을 잘못하는 실수를 줄여야한다.