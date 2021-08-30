#include <iostream>
#include <stack>
#include <vector>
#include <math.h>
#include <string>
using namespace std;

int* sccNum;
bool* visit;
stack<int> st;
vector<int>* adj;
vector<int>* adj_r;
vector<vector<int>> scc;

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
	for (int i = 1; i <= n; i++)
		visit[i] = false;

	for (int i = 1; i <= n; i++)
		if (!visit[i])
			dfs(i);

	for (int i = 1; i <= n; i++)
		visit[i] = false;
	
	int idx = 0;
	while (!st.empty()) {
		int curr = st.top();
		st.pop();
		if (!visit[curr]) {
			scc.push_back(vector<int>());
			dfs_r(curr, idx++);
		}
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int n, m;
	while (cin >> n) {
		cin >> m;

		sccNum = new int[2 * n + 1];
		visit = new bool[2 * n + 1];
		adj = new vector<int>[2 * n + 1];
		adj_r = new vector<int>[2 * n + 1];

		while (m-- > 0) {
			int i, j;
			cin >> i >> j;

			int u = i < 0 ? n + abs(i) : i;
			int v = j < 0 ? n + abs(j) : j;

			adj[doNot(u, n)].push_back(v);
			adj_r[v].push_back(doNot(u, n));

			adj[doNot(v, n)].push_back(u);
			adj_r[u].push_back(doNot(v, n));
		}

		kosaraju(2 * n);

		bool chk = true;
		for (int i = 1; i <= n; i++)
			if (sccNum[i] == sccNum[i + n]) {
				chk = false;
				break;
			}

		if (chk)
			cout << "1\n";
		else
			cout << "0\n";
	}
	return 0;
}