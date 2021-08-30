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

// p_11657