#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
using namespace std;
int n, m, match;
vector<int> a, b;
vector<bool> visit;
vector<vector<int>> adj;
bool dfs(int curr) {
    visit[curr] = true;
    for (int next : adj[curr]) {
        if (b[next] == -1 || (!visit[b[next]] && dfs(b[next]))) {
            a[curr] = next;
            b[next] = curr;
            return true;
        }
    }
    return false;
}
void bipartiteMatching() {
    match = 0;
    a = vector<int>(2*n + 1, -1);
    b = vector<int>(m + 1, -1);
    visit = vector<bool>(2*n + 1, false);
    for (int i = 1; i <= 2*n; i++) {
        if (a[i] == -1) {
            fill(visit.begin(), visit.end(), false);
            if (dfs(i)) match++;
        }
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n >> m;
    adj = vector<vector<int>>(2*n + 1);
    for (int i = 1; i <= n; i++) {
        adj[i] = vector<int>();
        adj[i + n] = vector<int>();
        int j; cin >> j;
        for (int k = 0; k < j; k++) {
            int v; cin >> v;
            adj[i].push_back(v);
            adj[i + n].push_back(v);
        }
    }
    bipartiteMatching();
    cout << match;
    return 0;
}
// 직원이 할 수 있는 일의 크기가 2
// => source - 직원 간 용량을 2로 놓고 플로우를 구하던가
// 이분매칭을 진행하되, 직원을 2명씩 놓고 하면 되는 문제
// 근데 N이 커서인제 너무 오래걸림
// 일단 이분매칭으로는 자바로 통과를 못했음

// C++은 통과는 하지만 3300ms 이상 나옴
// 이를 해결하려면 더 빠른 플로우 알고리즘인 디닉이나
// 더 빠른 이분매칭 알고리즘인 호프크로프트를 써야 할거 같음