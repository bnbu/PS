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
        scc.push_back(temp);
        idx++;
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

// 2021-07-29 일단은 해결
// 구상한 방법은
// 1. 타잔 알고리즘으로 SCC를 구성 (타잔 알고리즘으로 생성된 SCC는 위상정렬의 역순)
// 2. 구성된 SCC들을 하나의 정점으로 생각하여 다시 SCC간의 인접리스트를 재생성 및 SCC의 총 비용 계산
// 3. 시작 지점으로부터 SCC 인접리스트를 통해 (위상정렬 순서) 정점을 이동해가며 비용 계산
// 4. 계산 된 비용중 도착지(레스토랑이 있는 곳)가 위치하는 SCC의 도달 최대 비용간 비교하며 최대비용 계산
// 5. 계산된 최대비용을 출력

// 여기서 포인트는, 출발점이 포함된 SCC보다 위상정렬 순서상으로 더 앞서서 있는,
// ** 다시말해 출발점이 포함된 SCC 보다 먼저 방문되는 SCC
// 그러한 SCC에는 방문할일이 없으므로 굳이 구성시키지 않아도 된다
// 따라서, 일반적인 타잔 알고리즘은, 각 정점에 대해서 dfs를 수행시키지만,
// 이 경우에는 해당 출발점이 포함된 SCC에서 방문이 가능한 SCC들만 파악하면 되기 때문에
// 각 정점이 아닌, 출발점에서만 dfs를 실행시켜 구성되는 SCC만 고려하면 된다.

// 이렇게 생성된 출발점이 포함된 SCC로부터 방문할 수 있는 SCC에 대해
// 각 SCC별 최대로 얻을 수 있는 비용 계산 및 SCC를 정점으로 다시 생각하여
// SCC간의 인접리스트를 생성 -> 이 인접리스트를 토대로 그래프 탐색을 진행하여 계산
// 이때 일반적 그래프 탐색이 아닌, 반드시 위상정렬 순으로 방문해야하기 때문에
// indegree를 사용하여 위상정렬 진행하듯 방문시킨다.

// 여기서 맨 처음, 최대비용 계산을 다익스트라로 해봤는데 당연 시간초과
// 이후 위상정렬 순서를 통하여 계산을 해봤는데
// 배열 초기화때 fill을 써서인지 시간초과가 계속 떴음
// 시간을 줄일 수 있는 방법을 한번 더 찾아볼것