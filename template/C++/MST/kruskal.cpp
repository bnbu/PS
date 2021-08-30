#include <iostream>
#include <algorithm>
#include <vector>
#include <tuple>
using namespace std;

int* parent;
int cost = 0;
vector<tuple<int, int, int>> edgeList;
// tuple 순서대로 cost / source / destination


int find(int a) {
    if (a == parent[a])
        return a;
    else
        return parent[a] = find(parent[a]);
}
void merge(int a, int b) {
    int x = find(a);
    int y = find(b);
    if (x != y)
        parent[y] = x;
}

int main() {
    int v, e;
    cin >> v >> e;

    parent = new int[v + 1];
    for (int i = 1; i <= v; i++)
        parent[i] = i;

    while (e--) {
        int s, d, w;
        cin >> s >> d >> w;
        edgeList.push_back(make_tuple(w, s, d));
    }
    sort(edgeList.begin(), edgeList.end());

    for (tuple<int, int, int> curr : edgeList) {
        int curr_w = get<0>(curr);
        int curr_s = get<1>(curr);
        int curr_d = get<2>(curr);
        if (find(curr_s) != find(curr_d)) {
            merge(curr_s, curr_d);
            cost += curr_w;
        }
    }
    cout << cost;
    return 0;
}

// 유니온-파인드를 기초로 하는 크루스칼 알고리즘, 간선을 중점으로 구하는 방법
// 간선(Edge)들을 가중치순으로 정렬
// 정렬시킨 간선들을 작은 값부터 연결시키기 시작.
// 이떄, 해당 간선의 두 정점이 이미 연결되어 있는 경우(즉, 사이클이 형성될 시)는 제외
// 이를 모두 반복하면 최소 스패닝 트리의 비용을 알 수 있다.

// 시간복잡도
// 간선 정렬 -> O(ElogE)
// 간선 순회 및 union-find -> O(1) * E -> O(E);

// => 결과적으로 O(ElogE + E)
// = O(ElogE + E) = O(ElogV^2) = O(2ElogV) = O(ElogV)
// p_1197