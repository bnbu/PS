#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <cstring>
using namespace std;
typedef pair<int, int> pii;

int* limit;
vector<pii>* adj;

void dijkstra(int v) {
    priority_queue<pii> pq;
    pq.push({INT32_MAX, v});
    limit[v] = INT32_MAX;
    while (!pq.empty()) {
        pii curr = pq.top(); pq.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;

        if (limit[curr_v] != curr_w)
            continue;

        for (pii next : adj[curr_v]) {
            int next_w = next.first;
            int next_v = next.second;
            
            if (limit[next_v] < min(next_w, curr_w)) {
                limit[next_v] = min(next_w, curr_w);
                pq.push({limit[next_v], next_v});
            }
        }
    }
}

int main() {
    int n, m;
    cin >> n >> m;
    adj = new vector<pii>[n + 1];
    limit = new int[n + 1];
    memset(limit, INT32_MAX, sizeof(int) * (n + 1));
    while (m--) {
        int a, b, c;
        cin >> a >> b >> c;

        adj[a].push_back({c, b});
        adj[b].push_back({c, a});
    }

    int s, e;
    cin >> s >> e;
    dijkstra(s);

    cout << limit[e];

    return 0;
}