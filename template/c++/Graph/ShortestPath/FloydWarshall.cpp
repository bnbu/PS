#include <iostream>
#include <vector>
#include <limits>

using namespace std;

vector<vector<int>> d;
int n, m;

void print() {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (d[i][j] == INT32_MAX)
                cout << "INF ";
            else
                cout << d[i][j] << ' ';
        }
        cout << '\n';
    }
}

void floyd() {
    // k는 경유지, i와 j는 거리를 측정할 두 정점
    for (int k = 1; k <= n; k++) 
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                if (d[i][k] != INT32_MAX && d[k][j] != INT32_MAX)
                    d[i][j] = min(d[i][j], d[i][k] + d[k][j]);

    // d[i][j]는 경유지 k를 기점으로, 현재까지의 최단거리 d[i][j]와
    // k를 경유하는 d[i][k] + d[k][j] 중 더 작은값으로
}

int main() {
    cin >> n >> m;

    d = vector<vector<int>>(n + 1);
    for (int i = 1; i <= n; i++) {
        d[i] = vector<int>(n + 1);
        for (int j = 1; j <= n; j++)
            d[i][j] = i == j ? 0 : INT32_MAX;
    }

    while (m--) {
        int u, v, w;
        cin >> u >> v >> w;

        d[u][v] = d[u][v] < w ? d[u][v] : w;
    }
    
    cout << "진행 전\n";
    print();
    cout << '\n';

    floyd();

    cout << "진행 후\n";
    print();
    cout << '\n';

    
}

// 모든 정점 쌍 간 최단거리를 구하는 플로이드-와샬
// 정점 개수 V에 대해 O(V^3)의 복잡도를 가짐
// 정점 i와 j사이의 모든 경유지를 탐색하여, 최단 경로를 찾는 방법으로,
// 그래프의 정점이 1~V까지라고 한다면, 경유지는 k이다.
// 즉, 경유지 k를 거치는 정점 i와 j사이의 거리 중 최단거리를 갱신하는것.
// 다시 말해, d[i][j] 는 지금까지의 최단거리 d[i][j]와 k를 경유한 최단거리
// d[i][k] + d[k][j] 와 비교하게 되는 것이다.

// 별도의 pair 클래스 필요없이
// 2차원 배열로 인접행렬을 구성하여 한다.

// 1->2 / 1->3 / 1->4 / 2->3 / 2->4 / 3->4 의 간선을 가지는 그래프가 있다고 하자
// 우선 현재 연결된 간선의 정보로, 인접행렬을 먼저 구성한다
// 이후, K=1,2,3,4 일때, 즉 경유 정점 1,2,3,4일때의 거리를 모두 계산하여
// 기존 거리와 비교하여 최단거리를 갱신해준다.

// p_11404