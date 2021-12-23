#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
using namespace std;

int t, n, m, cnt, idx;
vector<vector<int>> scc;
vector<vector<int>> adj;
vector<int> discover;
vector<int> scc_indegree; 
vector<int> scc_idx;
vector<bool> visit;
stack<int> st;
int dfs(int v) {
    int ret = discover[v] = cnt++;
    st.push(v);
    for (int next : adj[v]) {
        if (discover[next] == -1)
            ret = min(ret, dfs(next));
        else if (scc_idx[next] == -1)
            ret = min(ret, discover[next]);
    }

    if (ret == discover[v]) {
        vector<int> temp;
        while (true) {
            int t = st.top(); st.pop();
            scc_idx[t] = idx;
            temp.push_back(t);
            if (t == v) break;
        }
        scc.push_back(temp);
        idx++;
    }
    return ret;
}
void tarjan(int v) {
    for (int i = 1; i <= v; i++)
        if (discover[i] == -1)
            dfs(i);
}
void makeSccAdj(int i) {
    visit = vector<bool>(idx, false);
    visit[i] = true;
    for (int v : scc[i])
        for (int next : adj[v])
            if (scc_idx[next] != -1 && !visit[scc_idx[next]]) {
                visit[scc_idx[next]] = true;
                scc_indegree[scc_idx[next]]++;
            }
}
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    cin >> t;
    while (t--) {
        cin >> n >> m;
        cnt = idx = 0;
        scc = vector<vector<int>>();
        adj = vector<vector<int>>(n + 1);
        discover = vector<int>(n + 1, -1);
        scc_idx = vector<int>(n + 1, -1);

        while (m--) {
            int x, y;
            cin >> x >> y;
            adj[x].push_back(y);
        }

        tarjan(n);

        scc_indegree = vector<int>(idx, 0);

        for (int i = 0; i < idx; i++)
            makeSccAdj(i);

        int ans = 0;
        for (int i : scc_indegree)
            if (i == 0) ans++;
        cout << ans << '\n';
    }
    return 0;
}

// 2021-09-12
// SCC를 구성한 후, SCC의 진입차원을 계산하여
// SCC의 진입차원이 0인 값을 찾는 문제