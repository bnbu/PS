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
// 2021-08-08 ??????

// N?????? ??????, M?????? ????????? (?????????)
// S??? ???????????? ?????? T??? ???????????? ??????, ??????->????????? ????????? ????????? ????????? ??? ??????????

// ????????? ???????????? ?????? ?????? ?????????????????? SCC??? ??????
// ????????? SCC??? ?????? SCC??? ?????? ???????????? ???????????? SCC??? ?????????????????? ??????
// ?????? ?????? ?????????????????? ????????? SCC??? ??????????????? ??????
// ?????????, ???????????? ?????? SCC?????? ??? ????????? ???????????? SCC??? ????????? ???????????? ?????????
// ??? ??????????????? ??? ?????? ????????? SCC??? ????????? ????????? ???????????? ?????????

// ?????? ????????? ??????????????? ????????????,
// ???????????? ?????? SCC -> ???????????? ?????? SCC ?????? ???????????? SCC??? ????????? ?????? ????????? ?????? ??????????????? ??????
// ??????, ???????????? ????????? ????????? SCC??? ????????? SCC?????? ??? ???????????? ?????????????????? ???????????? ??? ??? ??????

// ????????? ??????
// 1. ????????? ??????
// 2. ????????? ???????????? ?????? ??????????????? ?????? SCC ??????
// 3. ????????? SCC??? ?????? ???????????? ???????????? SCC????????? ?????????????????? ??????
// 4. ?????? ?????????????????? ????????? SCC??? ??????????????? ???????????? ??????
//  => ????????? SCC?????? ????????? SCC????????? ??????????????? ????????? ??????, ???????????? SCC??? ????????? ?????? ?????? ?????????
// ** ?????? ???????????? ???????????? ????????? ??? ?????????????????? ??????????????? ??????.