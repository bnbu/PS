#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;
typedef pair<int, int> pii;
int v, e;
vector<bool> selected;
vector<vector<pii>> adj;
// pair<int, int> 순서대로 w, d
vector<int> trace;
// 어떤 정점순으로 추가됐나 추적

int prim(int start) {
    priority_queue<pii, vector<pii>, greater<pii>> pq;
    pq.push({0, start}); // 시작점
    int cost = 0;
    for (int i = 1; i <= v; i++) {
        pii curr;
        int min = INT32_MAX;

        while (!pq.empty()) {
            curr = pq.top(); pq.pop();
            if (!selected[curr.second]) {
                trace.push_back(curr.second);   // 추가될 정점 순으로 벡터에 삽입
                min = curr.first;
                break;
            }
        } // 연결되지 않은 정점 중 가중치가 최소인 점 탐색

        if (min == INT32_MAX) return INT32_MAX;
        // mst를 만들 수 없음
        
        cost += min;
        selected[curr.second] = true; // tree set에 추가
        for (pii next : adj[curr.second]) pq.push(next);
        // 방금 추가시킨 정점과 연결된 정점을 다시 pq(fringe)에 추가
    }
    return cost;
}

int main() {
    cin >> v >> e;

    selected = vector<bool>(v + 1, false);
    adj = vector<vector<pii>>(v + 1);

    while (e--) {
        int s, d, w;
        cin >> s >> d >> w;
        adj[s].push_back({w, d});
        adj[d].push_back({w, s});
    }

    int start;
    cin >> start;
    trace = vector<int>();
    cout << prim(start) << '\n';
    for (int i : trace) cout << i << ' ';
    cout << '\n';

    return 0;
}