#include <iostream>
#include <vector>
using namespace std;
typedef pair<int, int> pii;

int n, m, k;
int cost;
vector<int> parent;
vector<pii> edge;

int find(int a) {
    if (a == parent[a]) return a;
    else return parent[a] = find(parent[a]);
}
void merge(int a, int b) {
    int x = find(a);
    int y = find(b);
    if (x != y)
        parent[x] = y;
}
bool spanningChk() {
    for (int i = 1; i < n; i++)
        if (find(i) != find(i + 1))
            return false;

    return true;
}
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    cin >> n >> m >> k;
    parent = vector<int>(n + 1);
    edge.push_back({0, 0});
    for (int i = 1; i <= n; i++)
        parent[i] = i;
    for (int i = 0; i < m; i++) {
        int s, d;
        cin >> s >> d;
        edge.push_back({s, d});
    }
    
    int last = 1;
    for (int i = 1; i <= m; i++) {
        int s = edge[i].first;
        int d = edge[i].second;
        if (find(s) != find(d)) {
            merge(s, d);
            cost += i;
        }
    }

    if (spanningChk()) cout << cost << ' ';
    else cout << 0 << ' ';
    for (int i = 1; i < k; i++) {
        cost = 0;
        for (int j = 1; j <= n; j++)
            parent[j] = j;

        for (int j = i + 1; j <= m; j++) {
            int s_ = edge[j].first;
            int d_ = edge[j].second;
            if (find(s_) != find(d_)) {
                merge(s_, d_);
                cost += j;
            }
        }
        if (spanningChk()) cout << cost << ' ';
        else cout << 0 << ' ';
    }

    return 0;
}
// 2021-08-16 해결
// 별에 별 고민을 다 해봤는데
// 슬라이딩 윈도우 기법처럼 시작지점을 조금씩 밀어가보는 방법을 해봤는데

// 그냥 대충 나이브하게 일단 돌려볼까 했는데
// 이게 통과되어서 어이가 없었음