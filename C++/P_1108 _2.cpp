#include <iostream>
#include <vector>
#include <stack>
#include <map>
#include <algorithm>
#include <cstring>
using namespace std;

int n, k, num, idx, cnt;
string s;
vector<string> links;
map<string, vector<string>> adj;
map<string, int> mapping;
stack<string> st;
vector<vector<string>> scc;
vector<int> discover;
vector<int> score;
vector<int> scc_idx;
vector<vector<int>> scc_adj;
vector<bool> visit;
vector<int> scc_indegree;


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
void makeSccAdj(int i) {
    visit = vector<bool>(idx, false);
    visit[i] = true;
    for (string str1 : scc[i])
        for (string str2 : adj[str1])
            if (scc_idx[mapping[str2]] != -1 && !visit[scc_idx[mapping[str2]]]) {
                scc_adj[i].push_back(scc_idx[mapping[str2]]);
                visit[scc_idx[mapping[str2]]] = true;
                scc_indegree[scc_idx[mapping[str2]]]++;
            }
}

int main() {
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
    score = vector<int>(num, 1);
    scc_idx = vector<int>(num, -1);
    discover = vector<int>(num, -1);

    tarjan();
    
    scc_adj.resize(idx);
    scc_indegree = vector<int>(idx, 0);
    for (int i = 0; i < idx; i++)
        makeSccAdj(i);

    for (vector<string> list : scc) {
        for (string link : list)
            cout << link << ' ';
        cout << '\n';
    }

    for (vector<int> list : scc_adj) {
        for (int i : list)
            cout << i << ' ';
        cout << '\n';
    }

    cin >> s;
    cout << score[mapping[s]];
    return 0;
}

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