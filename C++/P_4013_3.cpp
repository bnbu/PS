#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
#include <queue>
using namespace std;

int v, e, s, p, cnt, idx;
vector<int> discover;
vector<int> atm;
vector<int> total;
vector<int> scc_idx;
vector<int> scc_indegree;
vector<int> d;
vector<bool> visit;
stack<int> st;
vector<vector<int>> adj;
vector<vector<int>> scc_adj;
vector<vector<int>> scc;

int dfs(int n) {
    int ret = discover[n] = cnt++;
    st.push(n);
    for (int next : adj[n]) {
        if (discover[next] == -1)
            ret = min(ret, dfs(next));
        else if (scc_idx[next] == -1)
            ret = min(ret, discover[next]);
    }

    if (ret == discover[n]) {
        vector<int> temp;
        while (true) {
            int t = st.top(); st.pop();
            scc_idx[t] = idx;
            temp.push_back(t);
            if (t == n) break;
        }
        idx++;
        scc.push_back(temp);
    }
    return ret;
}

void calcTotal(int i) {
    for (int v : scc[i]) {
        total[i] += atm[v];
    }
}

void makeSccAdj(int i) {
    visit = vector<bool>(idx, false);
    visit[i] = true;
    for (int v : scc[i])
        for (int n : adj[v])
            if (scc_idx[n] != -1 && !visit[scc_idx[n]]) {
                scc_adj[i].push_back(scc_idx[n]);
                visit[scc_idx[n]] = true;
                scc_indegree[scc_idx[n]]++;
            }
}

void calc(int i) {
    queue<int> q;
    q.push(i);
    d[i] = total[i];
    while(!q.empty()) {
        int curr = q.front(); q.pop();
        for (int next : scc_adj[curr]) {
            d[next] = max(d[next], d[curr] + total[next]);
            if (--scc_indegree[next] == 0)
                q.push(next);
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);
    
    cin >> v >> e;
    adj.resize(v + 1);
    atm.resize(v + 1);
    discover = vector<int>(v + 1, -1);
    scc_idx = vector<int>(v + 1, -1);

    while (e--) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
    }

    for (int i = 1; i <= v; i++)
        cin >> atm[i];

    cin >> s >> p;

    cnt = 0;
    idx = 0;
    dfs(s);

    scc_adj.resize(idx);
    total = vector<int>(idx, 0);
    d = vector<int>(idx, 0);
    scc_indegree = vector<int>(idx, 0);
    
    for (int i = 0; i < idx; i++) {
        calcTotal(i);
        makeSccAdj(i);
    }

    calc(scc_idx[s]);

    int ans = 0;
    while (p--) {
        int a;
        cin >> a;
        if (scc_idx[a] != -1)
            ans = max(ans, d[scc_idx[a]]);
    }

    cout << ans;

    return 0;
}