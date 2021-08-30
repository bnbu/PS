#include <iostream>
#include <vector>
#include <stack>
#include <map>
#include <algorithm>
#include <cstring>
using namespace std;
typedef long long ll;

int n, k, num, idx, cnt;
string s;
vector<string> links;
map<string, vector<string>> adj;
map<string, int> mapping;
stack<string> st;
vector<vector<string>> scc;
vector<int> discover;
vector<ll> score;
vector<int> scc_idx;

int dfs(string s) {
    int ret = discover[mapping[s]] = cnt++;
    st.push(s);
    for (string next : adj[s]) {
        if (discover[mapping[next]] == -1) ret = min(ret, dfs(next));
        else if (scc_idx[mapping[next]] == -1) ret = min(ret, discover[mapping[next]]);
    }

    if (ret == discover[mapping[s]]) {
        vector<string> temp;
        while (true) {
            string curr = st.top(); st.pop();
            scc_idx[mapping[curr]] = idx;
            temp.push_back(curr);
            if (curr.compare(s) == 0) break;
        }
        idx++;
        scc.push_back(temp);   
    }
    
    return ret;
}
void tarjan() {
    cnt = idx = 0;
    for (string link : links)
        if (discover[mapping[link]] == -1)
            dfs(link);
}
void scoreCalc(string start) {
    for (string link : adj[start]) {
        if (scc_idx[mapping[start]] == scc_idx[mapping[link]]) continue;
        score[mapping[link]] += score[mapping[start]];
    }
}

int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);
        
    num = 1;
    cin >> n;
    while (n--) {
        string curr, from;
        cin >> curr;
        if (!mapping[curr]) {
            mapping[curr] = num++;
            links.push_back(curr);
        }
        cin >> k;
        while (k--) {
            cin >> from;
            if (!mapping[from]) {
                mapping[from] = num++;
                links.push_back(from);
            }
            adj[from].push_back(curr);
        }
    }
    score = vector<ll>(num, 1);
    scc_idx = vector<int>(num, -1);
    discover = vector<int>(num, -1);

    tarjan();

    for (int i = idx - 1; i >= 0; i--) {
        for (string link : scc[i]) {
            scoreCalc(link);
        }
    }

    cin >> s;
    cout << score[mapping[s]];
    return 0;
}
// 2021-08-06 해결

// 웹사이트가 여럿 존재, 사이트간 링크는 단방향 간선으로 생각 가능
// A 3 B C D => B->A, C->A, D->A 링크가 존재한다는 뜻
// 이때, A의 점수는 A로의 링크가 존재하는 사이트의 점수를 모두 상속받음
// 단, 같은 SCC에 속하는 링크의 점수는 상속받지 않음
// 점수는 상속받는 특성에 따라, 위상정렬의 순서로 계산되어져야 한다.

// 링크간 정보가 입력되고, 인접리스트가 구현되면
// 타잔 알고리즘으로 위상정렬 역순의 SCC그룹을 생성
// 이 SCC 그룹들을 다시 정점으로 생각해서 SCC간 인접리스트를 생성
// SCC간 인접리스트로 위상정렬을 진행하며 각 링크의 점수를 계산
// => 위상정렬이므로 진입차원도 따로 계산해줄 필요가 있음.