#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
using namespace std;

int cnt = 0;
int* discover;
bool* scc_chk;
stack<int> st;
vector<int>* adj;
vector<vector<int>> scc;

int getNum(int i, int n) {
    return i < 0 ? abs(i) : n + abs(i);
}
int doNot(int i, int n) {
    return i <= n ? i + n : i - n;
}

int dfs(int n) {
    int ret = discover[n] = cnt++;
    st.push(n);
    for (int next : adj[n]) {
        if (discover[next] == -1)
            ret = min(ret, dfs(next));
        else if (!scc_chk[next])
            ret = min(ret, discover[next]);
    }

    if (ret == discover[n]) {
        vector<int> temp;
        while (true) {
            int t = st.top(); st.pop();
            scc_chk[t] = true;
            temp.push_back(t);
            if (t == n)
                break;
        }
        sort(temp.begin(), temp.end());
        scc.push_back(temp);
    }
    return ret;
}
void tarjan(int n) {
    for (int i = 1; i <= 2 * n; i++)
        if (discover[i] == -1)
            dfs(i);
}

int main() {
    int n, m;
    cin >> n >> m;
    adj = new vector<int>[2 * n + 1];
    discover = new int[2 * n + 1];
    scc_chk = new bool[2 * n + 1];
    for (int i = 1; i <= 2 * n; i++) {
        discover[i] = -1;
        scc_chk[i] = false;
    }

    while (m--) {
        int i, j;
        cin >> i >> j;

        // i V j : -i->j || -j->i
        int u = i < 0 ? getNum(abs(i), n) : i;
        int v = j < 0 ? getNum(abs(j), n) : j;

        adj[doNot(u, n)].push_back(v);
        adj[doNot(v, n)].push_back(u);
    }

    tarjan(n);

    for (vector<int> list : scc) {
        for (int i : list)
            cout << i << ' ';
        cout << '\n';
    }

    return 0;
}

// 2-SAT
// 하나의 절 (x1 V x2)는
// 명제 p -> q 꼴로, ㄱx1->x2와 ㄱx2->x1으로 나타낼 수 있고
// 이를 그래프형식으로 확장시켜보면, SCC를 이루는 정점 중
// x1와 ㄱx1이 만약 같은 SCC를 형성한다면, true가 나올 수 없다.