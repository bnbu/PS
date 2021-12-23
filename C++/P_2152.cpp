#include <iostream>
#include <vector>
#include <stack>
#include <queue>
#include <algorithm>
using namespace std;

int n, m, s, t, a, b;
int idx, cnt, ans;
stack<int> st;
vector<int> discover;
vector<int> scc_idx;
vector<vector<int>> scc;
vector<vector<int>> adj;
vector<bool> visit;
vector<int> scc_indegree;
vector<int> city_cnt;
vector<vector<int>> scc_adj;


int dfs(int v) {
    int ret = discover[v] = cnt++;
    st.push(v);
    for (int next : adj[v]) {
        if (discover[next] == -1) ret = min(ret, dfs(next));
        else if (scc_idx[next] == -1) ret = min(ret, discover[next]);
    }

    if (ret == discover[v]) {
        vector<int> temp;
        while (true) {
            int curr = st.top(); st.pop();
            scc_idx[curr] = idx;
            temp.push_back(curr);
            if (curr == v) break;
        }
        scc.push_back(temp);
        idx++;
    }
    return ret;
}
void makeSccAdj(int i) {
    visit = vector<bool>(idx, false);
    visit[i] = true;
    for (int v : scc[i]) 
        for (int next : adj[v])
            if (scc_idx[next] != -1 && !visit[scc_idx[next]]) {
                scc_adj[i].push_back(scc_idx[next]);
                visit[scc_idx[next]] = true;
                scc_indegree[scc_idx[next]]++;
            }
}
void calc(int i) {
    queue<int> q;
    q.push(i);
    city_cnt[i] = scc[i].size();
    while (!q.empty()) {
        int curr = q.front(); q.pop();
        for (int next : scc_adj[curr]) {
            int next_cnt = city_cnt[curr] + scc[next].size();
            city_cnt[next] = max(city_cnt[next], next_cnt);
            if (--scc_indegree[next] == 0)
                q.push(next);
        }
    }
}
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    cin >> n >> m >> s >> t;
    cnt = idx = 0;
    adj = vector<vector<int>>(n + 1);
    scc_idx = vector<int>(n + 1, -1);
    discover = vector<int>(n + 1, -1);
    while (m--) {
        cin >> a >> b;
        adj[a].push_back(b);
    }
    dfs(s);

    scc_adj = vector<vector<int>>(idx);
    scc_indegree = vector<int>(idx, 0);
    for (int i = 0; i < idx; i++)
        makeSccAdj(i);

    ans = 0;
    if (scc_idx[t] != -1) {
        city_cnt = vector<int>(idx, 0);
        calc(scc_idx[s]);
        ans = city_cnt[scc_idx[t]];
    }

    cout << ans;

    return 0; 
}
// 2021-08-08 해결

// N개의 도시, M개의 비행로 (단방향)
// S번 도시에서 출발 T번 도시에서 종료, 출발->도착간 몇개의 도시를 방문할 수 있는가?

// 주어진 그래프에 대해 타잔 알고리즘으로 SCC를 구성
// 구성된 SCC에 대해 SCC를 다시 정점으로 판단하여 SCC간 인접리스트를 구성
// 이때 타잔 알고리즘으로 구성된 SCC는 위상정렬의 역순
// 따라서, 출발점이 속한 SCC보다 더 나중에 만들어진 SCC의 경우는 방문하지 않는다
// 즉 출발점보다 더 앞선 경우의 SCC는 구성할 필요가 없을지도 모른다

// 일단 전체를 구성한다는 가정하에,
// 출발점이 속한 SCC -> 도착점이 속한 SCC 까지 이동하는 SCC에 속하는 정점 개수를 모두 카운트하면 될듯
// 이때, 위상정렬 순서상 도착점 SCC가 출발점 SCC보다 더 앞선다면 불가능하다고 판단하면 될 듯 하다

// 대략적 순서
// 1. 그래프 구성
// 2. 구성된 그래프로 타잔 알고리즘을 통한 SCC 구성
// 3. 구성된 SCC를 다시 정점으로 생각해서 SCC들간의 인접리스트를 구성
// 4. 타잔 알고리즘으로 생성된 SCC는 위상정렬의 역순임을 이용
//  => 출발점 SCC에서 도착점 SCC으로의 위상정렬순 탐색을 진행, 거쳐가는 SCC에 속하는 도시 수를 카운트
// ** 이때 도착점이 위상정렬 순서상 더 앞서버린다면 불가능이라 판단.