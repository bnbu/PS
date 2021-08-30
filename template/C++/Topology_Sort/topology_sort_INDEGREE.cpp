#include <iostream>
#include <vector>
#include <queue>
using namespace std;

int* indegree;
int* topology;
vector<int>* adj;

void topologicalSort(int n) {
    queue<int> q;
    for (int i = 1; i <= n; i++)
        if (indegree[i] == 0)
            q.push(i);
    
    int cnt = 1;
    while (!q.empty()) {
        int curr = q.front(); q.pop();
        topology[curr] = cnt++;
        for (int next : adj[curr])
            if (--indegree[next] == 0)
                q.push(next);
    }
}

int main() {
    int n, m;
    cin >> n >> m;
    topology = new int[n + 1];
    indegree = new int[n + 1];
    adj = new vector<int>[n + 1];
    for (int i = 1; i <= n; i++) {
        indegree[i] = 0;
        topology[i] = -1;
    }

    while (m--) {
        int u, v;
        cin >> u >> v;
        adj[u].push_back(v);
        indegree[v]++;
        // v의 진입차원 1 증가.
    }

    for (int i = 1; i <= n; i++)
        cout << indegree[i] << ' ';
    cout << '\n';

    topologicalSort(n);

    for (int i = 1; i <= n; i++)
        cout << topology[i] << ' ';
    cout << '\n';
}

// 진입차원이 0인 노드부터 넘버링,
// 넘버링은 작은 수부터 올라가며, 넘버링 한 노드는 그래프에서 제거 한거로 취급한다.
// 이후 다시 진입차원이 0인 노드로 반복, 모든 노드가 넘버링 될때까지 반복한다.