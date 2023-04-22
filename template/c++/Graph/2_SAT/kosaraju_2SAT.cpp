#include <iostream>
#include <vector>
#include <stack>
using namespace std;

int* sccNum;
bool* visit;
stack<int> st;
vector<int>* adj;
vector<int>* adj_r;
vector<vector<int>> scc;

int getNum(int i, int n) {
    return i < 0 ? abs(i) : n + abs(i);
}
int doNot(int i, int n) {
    return i <= n ? i + n : i - n;
}

void dfs(int v) {
    visit[v] = true;
    for (int next : adj[v])
        if (!visit[next])
            dfs(next);
    st.push(v);
}
void dfs_r(int v, int idx) {
    visit[v] = true;
    sccNum[v] = idx;
    scc[idx].push_back(v);
    for (int next : adj_r[v])
        if (!visit[next])
            dfs_r(next, idx);
}
void kosaraju(int n) {
    for (int i = 1; i <= 2*n; i++)
        if (!visit[i])
            dfs(i);
    
    for (int i = 1; i <= 2*n; i++)
        visit[i] = false;
    
    int idx = 0;
    while (!st.empty()) {
        int curr = st.top(); st.pop();
        if (!visit[curr]) {
            scc.push_back(vector<int>());
            dfs_r(curr, idx++);
        }
    }
}

int main() {
    int n, m;
    cin >> n >> m;

    sccNum = new int[2*n + 1];
    visit = new bool[2*n + 1];
    adj = new vector<int>[2*n + 1];
    adj_r = new vector<int>[2*n + 1];
    // 1, 2, ... , n 
	// -1, -2, ... -n -> n+1, n+2, n+3... n+n

    for (int i = 1; i <= 2*n; i++) {
        visit[i] = false;
        sccNum[i] = 0;
    }

    while (m--) {
        int i, j;
        cin >> i >> j;

        // i V j : -i->j || -j->i
        int u = i < 0 ? getNum(abs(i), n) : i;
        int v = j < 0 ? getNum(abs(j), n) : j;

        adj[doNot(u, n)].push_back(v);
        adj_r[v].push_back(doNot(u, n));

        adj[doNot(v, n)].push_back(u);
        adj_r[v].push_back(doNot(v, n));
    }

    kosaraju(n);

    for (vector<int> list : scc) {
        for (int i : list)
            cout << i << ' ';
        cout << '\n';
    }

    bool chk = true;
    for (int i = 1; i <= n; i++)
        if(sccNum[i] == sccNum[n + 1]) {
            chk = false;
            break;
        }

    cout << chk;

    return 0;
}

// 2-SAT
// 하나의 절 (x1 V x2)는
// 명제 p -> q 꼴로, ㄱx1->x2와 ㄱx2->x1으로 나타낼 수 있고
// 이를 그래프형식으로 확장시켜보면, SCC를 이루는 정점 중
// x1와 ㄱx1이 만약 같은 SCC를 형성한다면, true가 나올 수 없다.