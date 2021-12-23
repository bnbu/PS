#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
using namespace std;

int n, m;
int cnt, idx;
vector<int> sccIdx;
vector<int> discover;
//vector<int> ans;
stack<int> st;
vector<vector<int>> adj;

int getNum(string s) {
    int ret = stoi(s);
    if (s[s.size() - 1] == 'w') return ret;
    else return ret + n;
}
int doNot(int i) {
    return i < n ? i + n : i - n;
}
int dfs(int v) {
    int ret = discover[v] = cnt++;
    st.push(v);
    for (int next : adj[v]) {
        if (discover[next] == -1) ret = min(ret, dfs(next));
        else if (sccIdx[next] == -1) ret = min(ret, discover[next]);
    }

    if (ret == discover[v]) {
        while (true) {
            int curr = st.top(); st.pop();
            sccIdx[curr] = idx;
            int num = curr < n ? curr : curr - n;
            // if (ans[num] == -1) {
            //     if (curr < n) ans[num] = 0; 
            //     else ans[num] = 1;
            // } 
            // 먼저 방문된 것을 false로 둔다
            // x가 먼저 방문 (curr < n) => x는 false, ㄱx는 true
            // ㄱx가 먼저 방문 (curr >= n) => x는 true, ㄱx는 false
            if (curr == v) break;
        }
        idx++;
    }
    return ret;
}
void tarjan() {
    cnt = idx = 0;
    sccIdx = vector<int>(2*n + 1, -1);
    discover = vector<int>(2*n + 1, -1);
//    ans = vector<int>(n + 1, -1);
    for (int i = 0; i < 2*n; i++)
        if (discover[i] == -1)
            dfs(i);
}
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    while (true) {
        cin >> n >> m;
        if (n == 0 && m == 0) break;

        cnt = idx = 0;
        adj = vector<vector<int>>(2*n + 1);
        st = stack<int>();

        while (m--) {
            int a, b;
            string s1, s2;
            cin >> s1 >> s2;
            a = getNum(s1);
            b = getNum(s2);

            adj[doNot(a)].push_back(b);
            adj[doNot(b)].push_back(a);
        }
        adj[doNot(0)].push_back(0);
        /// **보람이의 반대편에는 항상 철승이가 존재한다
        tarjan();

        bool chk = true;
        for (int i = 0; i < n; i++)
            if (sccIdx[i] == sccIdx[i + n]) {
                chk = false;
                break;
            }
        
        if (chk) {
            for (int i = 1; i < n; i++)
                cout << i << (sccIdx[i] < sccIdx[i + n] ? 'w' : 'h') << ' ';
        }
        else cout << "bad luck";
        cout << '\n';
    }
    return 0;
}
// 2021-08-13 해결
// 2-SAT 4와 거의 비슷한 선상에 위치한 문제 (P_11281)

// 문제를 요약하자면
// 불륜관계에 있는 두 사람이 모두 보림이의 반대편에 앉지 않게 배치할 수 있는지
// 그리고 그러한 배치가 존재한다면, 어떠한 배치인지 아무거나 가능한 배치를 출력하는 문제.

// 일단 불륜관계의 두 사람이 모두 보람이의 반대편에 있지 않아야 하기 때문에
// 둘중 하나라도 보람이쪽에 있게 하는 경우 생각해보면 결국은 2-SAT문제로 바뀐다.

// 보람이쪽을 True, 철승이쪽을 False로 정의하면
// 보람이쪽에는 불륜관계인 두 사람이 둘중 한명이라도 존재하면 되는 문제가 되는 것이다.
// 즉 불륜관계인 사람 a와 b에 대해서 (a v b)로 되고
// 모든 불륜관계에 대해 성립해야하므로
// (a v b)&&(c v d)&&(e v f)&& ... => True가 되게 해야하는 것이다

// 여기서 중요한게, 보람이의 반대편에는 항상 철승이가 존재한다
// 즉 보람이 (0w)는 항상 True라는 것이다.
// 따라서 SAT절에 반드시 (0 v 0)이 추가가 되어야하므로
// 0 v 0 => ㄱ0 -> 0 가 포함되어야 한다.
// ** 이거 안해서 계속틀림

// 따라서 입력받는 불륜관계에 대해 2-SAT 식으로 그래프를 구성하여
// 만들어지는 SCC에 대해서

// 2-SAT 특성상 서로 NOT 관계에 있는 두 정점이 같은 SCC에 포함된다면
// 이는 절대 충족시킬 수 없는 문제 (bad luck 출력)

// 아니라면, 가능한 경우를 출력시키면 된다
// 계산 방식은 2-SAT를 SCC로 바꾸어 보았을때
// SCC단위로 위상정렬 시킨 순서로 방문시 먼저 방문되는 변수를 false로 설정한다
// x가 먼저 방문되면 x는 false로 (0)
// ㄱx가 먼저 방문되면 x는 true로 (1) 
// ==> 먼저 방문되는 여부로만 판단할 수 있다면
//      굳이 ans배열을 따로 두지 않고, scc_idx로 판단이 가능
//      타잔 알고리즘이니까, 위상정렬 역순으로 scc가 생성되기 때문에
//      scc_idx가 더 크다 == 위상정렬 상 더 앞에 존재