#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
using namespace std;
int n, m, t, cnt, idx;
vector<int> discover, sccIdx, sccIndegree;
vector<bool> visit;
stack<int> st;
vector<vector<int>> adj, sccAdj, scc;
int dfs(int v) {
    int ret = discover[v] = cnt++;
    st.push(v);
    for (int next : adj[v]) {
        if (discover[next] == -1) ret = min(ret, dfs(next));
        else if (sccIdx[next] == -1) ret = min(ret, discover[next]);
    }

    if (ret == discover[v]) {
        vector<int> temp = vector<int>();
        while (true) {
            int curr = st.top(); st.pop();
            sccIdx[curr] = idx;
            temp.push_back(curr);
            if (curr == v) break;
        }
        idx++;
        scc.push_back(temp);
    }
    return ret;
}
void tarjan() {
    for (int i = 1; i <= n; i++)
        if (discover[i] == -1)
            dfs(i);
}
void calcSccIndgree(int v) {
    visit = vector<bool>(idx, false);
    visit[v] = true;
    for (int curr : scc[v]) {
        for (int next : adj[curr])
            if (sccIdx[next] != -1 && !visit[sccIdx[next]]) {
                visit[sccIdx[next]] = true;
                sccIndegree[sccIdx[next]]++;
                cnt--;
            }
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n >> m;

    adj = vector<vector<int>>(n + 1);
    discover = vector<int>(n + 1, -1);
    sccIdx = vector<int>(n + 1, -1);
    st = stack<int>();
    scc = vector<vector<int>>();
    cnt = 0;

    while (m--) {
        int u, v;
        cin >> u >> v;
        adj[u].push_back(v);
    }
    tarjan();

    sccIndegree = vector<int>(idx);
    for (int i = 0; i < idx; i++) calcSccIndgree(i);
    cnt = 0;
    for (int i = 0; i < idx; i++)
        if (sccIndegree[i] == 0) cnt++;

    int t;
    cin >> t;
    visit = vector<bool>(idx);
    int ans = 0;
    for (int i = 0; i < t; i++) {
        int j;
        cin >> j;
        j = sccIdx[j];
        if (!visit[j] && sccIndegree[j] == 0) {
            ans++;
            visit[j] = true;
        }
    }

    if (ans == cnt) cout << ans;
    else cout << -1;
    return 0;
}
// 자바당했음
// 같은 방식을 C++로 바꿔서 해봤는데 통과함

// 함수 실행 관계를 그래프로 변환
// 입력한 그래프를 토대로 SCC를 추출
// SCC 단위로 다시 노드로 취급하여 그래프로 보고 indegree를 계산

// 입력받은 테스트케이스 번호가 속한 SCC가 만약 indegree가 0이면
// 일단 그 scc에 연결된 다른 부분에는 전부 방문이 가능함

// 따라서 요점은 입력받은 케이스들 내에 indgree가 0인 SCC가 모두 포함되어 있다면
// indegree가 0인 scc의 개수를 출력하면 되고
// 그게 아니면 -1 출력하면 됨