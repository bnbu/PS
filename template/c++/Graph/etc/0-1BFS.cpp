#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;

int n, m;
int* d;
vector<pair<int, int>>* adj;

void bfs(int v) {
    deque<pair<int, int>> dq;
    dq.push_back(make_pair(v, 0));
    d[v] = 0;
    while (!dq.empty()) {
        pair<int, int> curr = dq.front(); dq.pop_front();
        int curr_v = curr.first;
        int curr_w = curr.second;
        for (pair<int, int> next : adj[curr_v]) {
            int next_v = next.first;
            int next_w = next.second;
            if (d[next_v] > d[curr_v] + next_w) {
                d[next_v] = d[curr_v] + next_w;
                if (next_w == 0)
                    dq.push_front(make_pair(next_v, d[next_v]));
                else
                    dq.push_back(make_pair(next_v, d[next_v]));
            }
        }

    }
    return;
}

int main() {
    cin >> n >> m;
    adj = new vector<pair<int, int>>[n + 1];
    d = new int[n + 1];
    for (int i = 1; i <= n; i++)
        d[i] = INT32_MAX;
    
    while (m--) {
        int u, v, w;
        cin >> u >> v >> w;
        
        adj[u].push_back(make_pair(v, w));
    }

}