#include <iostream>
#include <vector>
#include <limits>
using namespace std;

vector<pair<int, int>>* adj;
int* d;
int n, m;
bool negativeCycle = false;

void print() {
    for (int i = 1; i <= n; i++) {
        if (d[i] == INT32_MAX)
            cout << "INF ";
        else
            cout << d[i] << ' ';
    }
    cout << '\n';
}

void bellmanford(int start) {
    d[start] = 0;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++)
            for (pair<int, int> p : adj[j]) {
                int next_v = p.first;
                int next_w = p.second;
                if (d[j] != INT32_MAX && d[next_v] > d[j] + next_w)
                    d[next_v] = d[j] + next_w;
            }
    }
    print();
    // 정점 N개에 대해  이어진 간선들을 모두 다 탐색.
    // for문 i => 정점 V개 만큼 반복
    // for문 j 와 adj=> 정점에 이어진 간선들을 판단, 즉 E개 만큼 반복

	// 이때 모두 탐색할 때의 최악의 케이스는 일렬로 되있는 경우
	// 즉 정점 N개일 때, N-1번의 갱신작업을 거치면 된다.
	// 이떄 출발점은 거리가 0으로 시작.
}

void bellmanford_negativeCycle(int start) {
    d[start] = 0;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++)
            for (pair<int, int> p : adj[j]) {
                int next_v = p.first;
                int next_w = p.second;
                if (d[j] != INT32_MAX && d[next_v] > d[j] + next_w) {
                    d[next_v] = d[j] + next_w;
                    if (i == n)
                        negativeCycle = true;
                }
            }
    }
    print();
    // 음의 사이클이 없는 경우,
	// 최악의 케이스일 때, N-1번 갱신작업 이후
	// 거리의 갱신이 이루어지면 안되는데,
	// 음의 사이클이 존재할 경우에는 이후에도 거리 갱신이 이루어진다
	// 이를 이용해 N-1번 이후, N번째 루프에서 갱신이 있을 시
	// 음의 사이클이 있다고 판단한다.
}

int main() {
    cin >> n >> m;
    d = new int[n + 1];
    adj = new vector<pair<int, int>>[n + 1];
    for (int i = 1; i <= n; i++)
        d[i] = INT32_MAX;
    
    while (m--) {
        int u, v, w;
        cin >> u >> v >> w;

        adj[u].push_back(make_pair(v, w));
    }

    bellmanford(1);
    cout << '\n';

    bellmanford_negativeCycle(1);
    cout << '\n';
    cout << negativeCycle << '\n';

    return 0;
}
// 시작점에서 먼저 이어진 간선들로 가는 정점들의 거리를 갱신
// 이후 각 정점들의 이어진 간선들로 다시 가는 정점들의 거리를 계산하며
// 최소 거리가 되는 경우 다시 갱신을 반복한다.
// (이때, 시작점-다음정점 으로 이어지는 경로로 보면 된다)
// 즉 모든 정점의 모든 간선을 다 확인하는 방법으로
// 모든 정점의 간선을 다 살피게 되므로, 다익스트라보다는 느린 O(VE)지만
// 모든 정점의 간선을 다 살피게 되므로, 음의 가중치를 포함한 거리의 최단거리도 계산이 가능.
// 최악의 경우에는 정점의 개수 - 1번까지 (모두 일렬로 연결된 경우)
// 갱신작업을 반복해야 모두 갱신이 된다.

// 그리고 만약, 음의 사이클 (가중치가 모두 음수로 이루어진 사이클)이 존재한다면,
// 이러한 벨만-포드 알고리즘을 1회 더 돌렸을 시,
// 갱신 될 리 없는 최단거리가
// 음의 사이클에 의해서 더 줄어드는, 즉 갱신되는 경우가 발생한다
// 이로 인해 음의 사이클의 여부를 판단할 수 있다!
// 이를 축약시키면, V개의 정점일때, 


// p_11657