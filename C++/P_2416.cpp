#include <iostream>
#include <stack>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

int cnt;
int sccIdx;
int* sccNum;
int* discover;
int* ans;
stack<int> st;
vector<int>* adj;

int doNot(int i, int n) {
	return i <= n ? i + n : i - n;
}

int dfs(int v, int m) {
	int ret = discover[v] = cnt++;
	st.push(v);
	for (int next : adj[v]) {
		if (discover[next] == -1)
			ret = min(ret, dfs(next, m));
		else if (sccNum[next] == -1)
			ret = min(ret, discover[next]);
	}

	if (ret == discover[v]) {
		while (true) {
			int i = st.top(); st.pop();
			sccNum[i] = sccIdx;
			int num = i <= m ? i : i - m;
			if (ans[num] == -1) {
				if (i <= m)
					ans[num] = 0;
				else
					ans[num] = 1;
			}
			if (i == v)
				break;
		}
		sccIdx++;
	}
	return ret;
}
void tarjan(int n) {
	cnt = 0;
	sccIdx = 0;
	fill(discover, discover + 2 * n + 1, -1);
	fill(sccNum, sccNum + 2 * n + 1, -1);
	fill(ans, ans + n + 1, -1);
	for (int i = 1; i <= 2*n; i++) {
		discover[i] = -1;
		sccNum[i] = -1;
	}

	for (int i = 1; i <= n; i++)
		if (discover[i] == -1)
			dfs(i, n);
}
int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int n, m;
	cin >> n >> m;
	ans = new int[m + 1];
	discover = new int[2 * m + 1];
	sccNum = new int[2 * m + 1];
	adj = new vector<int>[2 * m + 1];

	int a, sa, b, sb, idx_a, idx_b;
	for (int i = 0; i < n; i++) {
		cin >> a >> sa >> b >> sb;

		idx_a = sa == 1 ? a + m : a;
		idx_b = sb == 1 ? b + m : b;

		adj[doNot(idx_a, m)].push_back(idx_b);
		adj[doNot(idx_b, m)].push_back(idx_a);
	}
	tarjan(m);

	bool chk = true;
	for (int i = 1; i <= m; i++)
		if (sccNum[i] == sccNum[i + m]) {
			chk = false;
			break;
		}

	if (chk) {
		for (int i = 1; i <= m; i++)
			cout << ans[i] << "\n";
	}
	else
		cout << "IMPOSSIBLE\n";
	return 0;
}