#include <iostream>
#include <algorithm>
#include <vector>
#include <tuple>
#include <queue>
using namespace std;

typedef tuple<int, int, int> point;

int n, m, k;
vector<int> parent;
vector<bool> chk;
priority_queue<point, vector<point>, greater<point>> pq;

int find(int a) {
    if (a == parent[a]) return a;
    parent[a] = find(parent[a]);
    chk[a] = chk[parent[a]];
    return parent[a];
}
void merge(int a, int b) {
    int x = find(a);
    int y = find(b);
    if (x == y) return;

    if (chk[x] && !chk[y]) parent[y] = x;
    else if (!chk[x] && chk[y]) parent[x] = y;
    else parent[x] = y;
}
int main() {
    cin >> n >> m >> k;
    parent = vector<int>(n + 1);
    chk = vector<bool>(n + 1, false);
    for (int i = 1; i <= n; i++)
        parent[i] = i;

    while(k--) {
        int i;
        cin >> i;
        chk[i] = true;
    }

    while(m--) {
        int u, v, w;
        cin >> u >> v >> w;
        pq.push({w, u, v});
    }

    int cost = 0;
    while (!pq.empty()) {
        point curr = pq.top(); pq.pop();
        int curr_w = get<0>(curr);
        int curr_s = get<1>(curr);
        int curr_d = get<2>(curr);
        if (find(curr_s) == find(curr_d)) continue;
        if (chk[curr_s] && chk[curr_d]) continue;
        cost += curr_w;
        merge(curr_s, curr_d); 
    }
    cout << cost;
    return 0;
}
// 2021-08-03 해결
// MST를 구성시키되,
// 그래프 전체를 잇는 신장트리가 아닌
// 특정 몇개의 정점을 포함만 하고 있는 그래프면 되는것

/*
9 14 3
1 2 9
1 3 3
1 4 8
2 4 10
3 4 11
3 5 6
4 5 4
4 6 10
5 6 5
5 7 4
6 7 7
6 8 4
7 8 5
7 9 2
8 9 5
*/

// 의 예제의 경우,
// 1, 2, 9를 포함하기만 하면 된다
// 작은 순부터 연결하는 크루스칼 알고리즘 기준으로
// 7 9 2 연결 
// 1 3 3 연결
// 4 5 4 연결
// 5 7 4 연결
// 6 8 4 연결
// 5 6 5 연결 되는 순간,
// 1-3, 2, 4-5-6-7-8-9 가 연결되어서
// 필수로 포함되어야 하는 정점을 모두 포함한 그래프가 된다.