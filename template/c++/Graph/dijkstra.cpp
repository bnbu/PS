#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;
typedef pair<int, int> pii;
vector<int> d;
vector<vector<pii>> adj;
// pair 순서대로 w, v로 해야 우선순위 큐에서 가중치 순으로 정렬

void dijkstra(int start) {
    priority_queue<pii, vector<pii>, greater<pii>> pq;
    pq.push({0, start});
    d[start] = 0;

    while (!pq.empty()) {
        pii curr = pq.top(); pq.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;
        if (d[curr_v] != curr_w) continue;

        for (pii next : adj[curr_v]) {
            int next_w = next.first;
            int next_v = next.second;
            if (d[next_v] > d[curr_v] + next_w) {
                d[next_v] = d[curr_v] + next_w;
                pq.push({d[next_v], next_v});
            }
        }
    }
}

int main() {
    int n, e, k;
    cin >> n >> e >> k;

    adj = vector<vector<pii>>(n + 1);
    d = vector<int>(n + 1, INT32_MAX);
    
    while (e--) {
        int u, v, w;
        cin >> u >> v >> w;
        adj[u].push_back({w, v});
        //adj[v].push_back(make_pair(w, u)); 양방향 그래프일 경우 반대의 경우도 추가
    }
    
    dijkstra(k);
    // 출발점 k로부터 도달 가능한 모든 정점까지로의 최단거리 계산

    for (int i = 1; i <= n; i++) {
        if (d[i] == INT32_MAX)
            cout << "INF ";
        else
            cout << d[i] << ' ';
    }
    cout << '\n';
    return 0;
}

// p_1753
// => O(ElogV)