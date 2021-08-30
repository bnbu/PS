#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
#include <queue>
using namespace std;

typedef pair<int, int> pii;

int v, e, s, p, cnt, idx;
int* discover;
int* atm;
int* total;
int* scc_idx;
int* d;
bool* visit;
stack<int> st;
vector<int>* adj;
vector<int>* scc_adj;
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

void tarjan(int v) {
    cnt = 0;
    idx = 0;
    for (int i = 1; i <= v; i++)
        if (discover[i] == -1)
            dfs(i);
}

void calcTotal(int i) {
    for (int v : scc[i]) {
        total[i] += atm[v];
    }
}

void makeSccAdj(int i) {
    fill(visit, visit + idx, false);
    visit[i] = true;
    for (int v : scc[i])
        for (int n : adj[v])
            if (scc_idx[n] != i && !visit[scc_idx[n]]) {
                scc_adj[i].push_back(scc_idx[n]);
                visit[scc_idx[n]] = true;
            }
}

void dijkstra(int i) {
    priority_queue<pii> pq;
    pq.push({total[i], i});
    d[i] = total[i];
    while(!pq.empty()) {
        pii curr = pq.top(); pq.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;
        if (d[curr_v] != curr_w) continue;
        for (int next : scc_adj[curr_v]) {
            if (d[next] < curr_w + total[next]) {
                d[next] = curr_w + total[next];
                pq.push({d[next], next});
            }
        }
    }
}

int main() {
    cin >> v >> e;
    adj = new vector<int>[v + 1];
    atm = new int[v + 1];
    discover = new int[v + 1];
    scc_idx = new int[v + 1];
    fill(discover, discover + v + 1, -1);
    fill(scc_idx, scc_idx + v + 1, -1);

    while (e--) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
    }

    for (int i = 1; i <= v; i++)
        cin >> atm[i];

    cin >> s >> p;

    tarjan(v);

    scc_adj = new vector<int>[idx];
    total = new int[idx];
    d = new int[idx];
    visit = new bool[idx];
    fill(total, total + idx, 0);
    fill(d, d + idx, 0);
    for (int i = scc_idx[s]; i >= 0; i--) {
        calcTotal(i);
        makeSccAdj(i);
    }

    dijkstra(scc_idx[s]);

    int ans = 0;
    while(p--) {
        int a;
        cin >> a;
        ans = max(ans, d[scc_idx[a]]);
    }

    cout << ans;

    return 0;
}