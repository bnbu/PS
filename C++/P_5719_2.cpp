#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <tuple>
using namespace std;

typedef pair<int, int> pii;
typedef tuple<int, int, vector<int>> tp;

int n, m, s, d, u, v, p;
vector<int> dist;
vector<bool> visit;
vector<vector<pii>> adj;
vector<vector<int>> trace;
vector<vector<bool>> ban_list;

void dijkstra(int start) {
    priority_queue<pii, vector<pii>, greater<pii>> pq;
    pq.push({0, start});
    dist[start] = 0;
    while (!pq.empty()) {
        pii curr = pq.top(); pq.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;
        if (curr_w != dist[curr_v]) continue;

        for (pii next : adj[curr_v]) {
            int next_w = next.first;
            int next_v = next.second;
            if (dist[next_v] == curr_w + next_w)
                trace[next_v].push_back(curr_v);
            else if (dist[next_v] > curr_w + next_w) {
                dist[next_v] = curr_w + next_w;
                trace[next_v].clear();
                trace[next_v].push_back(curr_v);
                pq.push({dist[next_v], next_v});
            }
        }
    }
}

void add_ban(int v) {
    visit[v] = true;
    for (int next : trace[v]) {
        ban_list[next][v] = true;
        if (!visit[next])
            add_ban(next);
    }
}

void dijkstra_w_ban(int start) {
    priority_queue<pii, vector<pii>, greater<pii>> pq;
    pq.push({0, start});
    dist[start] = 0;
    while (!pq.empty()) {
        pii curr = pq.top(); pq.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;
        if (curr_w != dist[curr_v]) continue;
        for (pii next : adj[curr_v]) {
            int next_w = next.first;
            int next_v = next.second;
            if (ban_list[curr_v][next_v]) continue;
            if (dist[next_v] > curr_w + next_w) {
                dist[next_v] = curr_w + next_w;
                pq.push({dist[next_v], next_v});
            }
        }
    }
}

int main() {
    while (true) {
        cin >> n >> m;
        if (n == 0 && m == 0) break;

        adj = vector<vector<pii>>(n);
        dist = vector<int>(n, INT32_MAX);
        trace = vector<vector<int>>(n);
        visit = vector<bool>(n, false);
        ban_list = vector<vector<bool>>(n);
        for (int i = 0; i < n; i++)
            ban_list[i] = vector<bool>(n, false);
        cin >> s >> d;

        while (m--) {
            cin >> u >> v >> p;
            adj[u].push_back({p, v});
        }

        dijkstra(s);
        add_ban(d);

        dist = vector<int>(n, INT32_MAX);
        dijkstra_w_ban(s);
        if (dist[d] == INT32_MAX) cout << "-1\n";
        else cout << dist[d] << '\n';
    }
    return 0;
}