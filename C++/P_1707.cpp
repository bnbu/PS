#include <iostream>
#include <vector>
#include <queue>
using namespace std;

vector<int>* adj;
int* group;

bool bfs(int start) {
    queue<int> q;
    q.push(start);
    group[start] = 1;
    while (!q.empty()) {
        int curr = q.front(); q.pop();
        for (int next : adj[curr]) {
            if (group[next] != 0) {
                if (group[next] == group[curr])
                    return false;
                else
                    continue;
            }
            q.push(next);
            group[next] = group[curr] == 1 ? 2 : 1;
        }
    }
    return true;
}

bool chk(int v) {
    for (int i = 1; i <= v; i++) {
        if (group[i] == 0)
            if (!bfs(i))
                return false;
    }
    return true;
}

int main() {
    int t;
    cin >> t;
    while (t--) {
        int v, e;
        cin >> v >> e;
        adj = new vector<int>[v + 1];
        group = new int[v + 1];
        for (int i = 1; i <= v; i++) {
            adj[i] = vector<int>();
            group[i] = 0;
        }
            
        while (e--) {
            int t, s;
            cin >> t >> s;
            adj[t].push_back(s);
            adj[s].push_back(t);
        }
        if (chk(v)) cout << "YES" << '\n';
        else cout << "NO" << '\n';
    }
}