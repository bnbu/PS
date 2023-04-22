#include <iostream>
#include <vector>
using namespace std;

int n, m, currNum;
bool* visit;
int* topology;
vector<int>* adj;

void topologicalDFS(int v) {
    visit[v] = true;
    for (int next : adj[v]) 
        if (!visit[next])  
            topologicalDFS(next);
    topology[v] = currNum--;
}

void topologicalSort() {
    currNum = n;
        for (int i = 1; i <= n; i++)
            if (!visit[i])
                topologicalDFS(i);
}

int main() {
    cin >> n >> m;

    adj = new vector<int>[n + 1];
    visit = new bool[n + 1];
    topology = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        visit[i] = false;
        topology[i] = 0;
    }
    
    while (m--) {
        int u, v;
        cin >> u >> v;
        adj[u].push_back(v);
    }

    topologicalSort();

    for (int i = 1; i <= n; i++)
        cout << topology[i] << ' ';
    cout << '\n';
}

// DFS로 들어가서 더 이상 다음으로 이어지지 않는 외부노드에 도달시
// 그 노드부터 넘버링, 넘버링은 큰 수부터 내려온다.