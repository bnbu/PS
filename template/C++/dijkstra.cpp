#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;

int* d;
vector<pair<int, int>>* adj;
// pair 순서대로 w, v로 해야 우선순위 큐에서 재대로 동작함

void dijkstra(int start) {
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    // 이렇게 써줘야 작은 순서대로 정렬함
    pq.push(make_pair(0, start));
    d[start] = 0;

    while (!pq.empty()) {
        pair<int, int> curr = pq.top(); pq.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;
        if (d[curr_v] != curr_w) continue;

        for (pair<int, int> next : adj[curr_v]) {
            int next_w = next.first;
            int next_v = next.second;
            if (d[next_v] > d[curr_v] + next_w) {
                d[next_v] = d[curr_v] + next_w;
                pq.push(make_pair(d[next_v], next_v));
            }
        }
    }
}

int main() {
    int n, e, k;
    cin >> n >> e >> k;

    adj = new vector<pair<int, int>>[n + 1];
    d = new int[n + 1];
    for (int i = 1; i <= n; i++)
        d[i] = INT32_MAX;
    
    while (e--) {
        int u, v, w;
        cin >> u >> v >> w;
        adj[u].push_back(make_pair(w, v));
        //adj[v].push_back(make_pair(w, u)); 양방향 그래프일 경우 반대의 경우도 추가
    }
    
    dijkstra(k);

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