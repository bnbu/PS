#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
using namespace std;

vector<bool> visit;
vector<vector<int>> scc, adj, reverse_adj;
stack<int> st;
int v, e;

void dfs(int n) {
    visit[n] = true;
    for (int next : adj[n])
        if (!visit[next])
            dfs(next);
    st.push(n);
}
void dfs_r(int n, int idx) {
    visit[n] = true;
    scc[idx].push_back(n);
    for (int next : reverse_adj[n])
        if(!visit[next])
            dfs_r(next, idx);
}
void kosaraju() {
    for (int i = 1; i <= v; i++)
        if (!visit[i])
            dfs(i);
    
    for (int i = 1; i <= v; i++)
        visit[i] = false;

    int idx = 0;
    while (!st.empty()) {
        int curr = st.top(); st.pop();
        if (!visit[curr]) {
            scc.push_back(vector<int>());
            dfs_r(curr, idx++);
        }
    }
}
// void makeSccAdj(int i) {
//     fill(visit, visit + idx, false);
//     visit[i] = true;
//     for (int v : scc[i])
//         for (int n : adj[v])
//             if (scc_idx[n] != i && !visit[scc_idx[n]]) {
//                 scc_adj[i].push_back(scc_idx[n]);
//                 visit[scc_idx[n]] = true;
//             }
// } SCC를 하나의 정점으로 쳐서 다시 그래프 생성
int main() {
    cin >> v >> e;

    visit = vector<bool>(v + 1);
    adj = vector<vector<int>>(v + 1);
    reverse_adj = vector<vector<int>>(v + 1);
    for (int i = 1; i <= v; i++)
        visit[i] = false;
    
    while (e--) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        reverse_adj[b].push_back(a);
        // 정방향 및 역방향 인접리스트 추가
    }

    kosaraju();

    for (int i = 0; i < scc.size(); i++) {
        sort(scc[i].begin(), scc[i].end());
    }

    cout << scc.size() << '\n';
    for (vector<int> list : scc) {
        for (int i : list)
            cout << i << ' ';
        cout << "-1\n";
    }

    return 0;
}
// 코사라주 알고리즘
// 주어지는 방향 그래프와 주어지는 방향 그래프의 방향을 모두 뒤집은 역방향 그래프를 준비한다.
// 방향그래프, 역방향그래프, 스택 이렇게 준비

// 정방향 그래프를 DFS 탐색하여 **수행이 끝나는 순서로 스택에 삽입**
// ** 이때 DFS는 모든 정점에 대해서 수행해야한다.
// 이후, 스택에서 pop하는 순서대로 역방향 그래프에서의 DFS 탐색을 진행한다.
// 이 한 정점에서 수행시 탐색되는 모든 정점들을 같은 SCC로 묶는다.

// DFS로 구현되어서 O(V+E)의 시간 복잡도

// 단, 타잔 알고리즘에 비해 
// 정방향 / 역방향 그래프 2개를 구현해야 하며
// DFS역시 2번을 해야한다.

// P_2150

// ** 마지막 scc 벡터 리스트간 정렬은 따로 구현하지 않았음
// => java코드 확인할것