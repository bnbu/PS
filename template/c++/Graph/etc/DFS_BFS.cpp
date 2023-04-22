#include <iostream>
#include <vector>
#include <stack>
#include <queue>
using namespace std;

vector<int>* adj;
bool* visit;

void dfs1(int x) {
    if (visit[x])
        return;
    else {
        visit[x] = true;
        cout << x << ' ';
        for (int next : adj[x])
            if (!visit[next])
                dfs1(next);
    }
    // 재귀를 이용한 방법
}

void dfs2(int x) {
    stack<int> st;
    st.push(x);
    visit[x] = true;
    while (!st.empty()) {
        int curr = st.top(); st.pop();
        cout << curr << ' ';
        for (int next : adj[curr]) 
            if (!visit[next]) {
                visit[next] = true;
                st.push(next);
            }
    }
    // 스택을 이용한 방법 -> 스택을 큐로 바꾸면 bfs
}

void bfs(int x) {
    queue<int> q;
    q.push(x);
    visit[x] = true;
    while (!q.empty()) {
        int curr = q.front(); q.pop();
        cout << curr << ' ';
        for (int next : adj[curr])
            if (!visit[next]) {
                visit[next] = true;
                q.push(next);
            }
    }
}

int main() {
    int n, m, k;
    cin >> n >> m >> k;

    adj = new vector<int>[n + 1];
    visit = new bool[n + 1];
    for (int i = 1; i <= n; i++)
        visit[i] = false;

    while (m--) {
        int u, v;
        cin >> u >> v;
        adj[u].push_back(v);
        adj[v].push_back(u);
        // 양방향일 경우 다음과 같이 양쪽 다 추가 (u -> v / v -> u)
    }


    return 0;
}