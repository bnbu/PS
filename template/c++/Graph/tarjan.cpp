#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
using namespace std;

int v, e, cnt = 0;
vector<int> discover;
vector<bool> scc_chk;
stack<int> st;
vector<vector<int>> scc, adj;

int dfs(int n) {
    int ret = discover[n] = cnt++;
    st.push(n);
    for (int next : adj[n]) {
        if (discover[next] == -1)
            ret = min(ret, dfs(next));
        else if (!scc_chk[next])
            ret = min(ret, discover[next]);
    }

    if (ret == discover[n]) {
        vector<int> temp;
        while (true) {
            int t = st.top(); st.pop();
            scc_chk[t] = true;
            temp.push_back(t);
            if (t == n)
                break;
        }
        sort(temp.begin(), temp.end());
        scc.push_back(temp);
    }
    return ret;
}

void tarjan(int v) {
    for (int i = 1; i <= v; i++)
        if (discover[i] == -1)
            dfs(i);
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
    
    adj = vector<vector<int>>(v + 1);
    discover = vector<int>(v + 1);
    scc_chk = vector<bool>(v + 1);
    for (int i = 1; i <= v; i++) {
        discover[i] = -1;
        scc_chk[i] = false;
    }

    while (e--) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
    }

    tarjan(v);
    for (int i = 0; i < scc.size(); i++)
        sort(scc[i].begin(), scc[i].end());
    
    cout << scc.size() << '\n';
    for (vector<int> list : scc) {
        for (int i : list)
            cout << i << ' ';
        cout << "-1\n";
    }

    return 0;
}

// 타잔 알고리즘
// DFS 수행시 생성되는 DFS 트리의 간선 정보를 이용하며 모든 정점 DFS 한번으로 SCC를 구하는 방법
// 모든 정점 DFS를 수행하며 spanning tree를 만들 때 마다
// DFS의 호출 순서에 따라 정점을 stack에 push 시킨다.

// 이후 간선 분류를 통하여 먼저 호출되는 정점이 더 높은 위치(부모)를 가진다고 생각할 때
// 가장 높이 올라갈 수 있는 정점을 찾는다. (dfs의 리턴값으로 활용 가능)
// 이때 n->next 교차간선이지만, next가 아직 SCC가 형성되지 않았다면
// discover[next] 역시 고려해줘야 한다.

// DFS가 끝나기 전 ret와 discover[n]이 같다면
// 이는 사이클의 첫 시작 지점이라는 뜻이므로,
// stack에서 pop하면서 n이 나올때까지 모두 같은 SCC로 분류한다.

// 위상정렬을 이용한 방법으로 생성되는 SCC는 위상정렬의 역순으로 생성된다.

// 시간은 O(V+E)
// 하지만 역방향 그래프를 구현하지 않아도 되며
// 무엇보다 DFS 1번으로 가능하다.

// P_2150

// ** 마지막 scc 벡터 리스트간 정렬은 따로 구현하지 않았음
// => java코드 확인할것

// ** 이떄, 위상정렬 역순으로 만들어지는데,
// 타잔 알고리즘에서 dfs를 1에서 V번 정점까지 돌리면 모든 SCC를 만들게 되지만,
// dfs를 어느 특정 a번 정점에서만 실행하게 되면,
// a번 정점에서 출발하여 a번 정점보다 위상정렬 순서상 더 뒤에 있는 정점들 까지만 SCC를 만든다
// 즉 a번보다 위상정렬 순서상 앞서는 정점의 경우는 고려하지 않게 된다.