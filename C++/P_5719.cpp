#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <tuple>
using namespace std;

typedef pair<int, int> pii;
typedef tuple<int, int, vector<int>> tp;

int n, m, s, d, u, v, p;
vector<int> dist;
vector<bool> visit;
vector<vector<int>> adj;
vector<vector<int>> trace;

void dijkstra(int start) {
    priority_queue<pii, vector<pii>, greater<pii>> pq;
    pq.push({0, start});
    dist[start] = 0;
    while (!pq.empty()) {
        pii curr = pq.top(); pq.pop();
        int curr_w = curr.first;
        int curr_v = curr.second;
        if (curr_w != dist[curr_v]) continue;

        for (int i = 0; i < n; i++) {
            if (curr_v == i) continue;
            if (adj[curr_v][i] == -1) continue;
            int next_w = adj[curr_v][i];
            if (dist[i] == curr_w + next_w) 
                trace[i].push_back(curr_v);
            else if (dist[i] > curr_w + next_w) {
                dist[i] = curr_w + next_w;
                trace[i].clear();
                trace[i].push_back(curr_v);
                pq.push({dist[i], i});
            }
        }
    }
}

void add_ban(int v) {
    visit[v] = true;
    for (int next : trace[v]) {
        adj[next][v] = -1;
        if (!visit[next])
            add_ban(next);
    }
}

int main() {
    while (true) {
        cin >> n >> m;
        if (n == 0 && m == 0) break;

        adj = vector<vector<int>>(n);
        for (int i = 0; i < n; i++)
            adj[i] = vector<int>(n, -1);
        dist = vector<int>(n, INT32_MAX);
        trace = vector<vector<int>>(n);
        visit = vector<bool>(n, false);
        cin >> s >> d;

        while (m--) {
            cin >> u >> v >> p;
            adj[u][v] = p;
        }

        dijkstra(s);
        add_ban(d);

        dist = vector<int>(n, INT32_MAX);
        dijkstra(s);
        if (dist[d] == INT32_MAX) cout << "-1\n";
        else cout << dist[d] << '\n';
    }
    return 0;
}

// 2021-08-04 해결
// 다익스트라 1회 -> 최단경로를 역추적하여 해당 경로를 제거 -> 다시 다익스트라 1회

// 이때, 경로를 역추적하는 방법을 정하기가 상당히 힘들었다.

// 맨 처음
// => 나이브하게 첫 다익스트라 과정 중 거쳐오는 경로를 vector와 함께 보내서 경로를 push_back 시킴
// 당연하게도 메모리 초과가 날거라 생각했다.

// 2번째 시도
// 일전에 bfs 및 다익스트라간 최단경로의 역추적 문제에서 trace 배열을 따로 두어서 이동한 것이 생각,
// trace를 2차원 배열로 둬서, 각 정점간 최단경로의 역추적을 저장한다
// 이때, 2차원 배열로 함으로써, 경로가 여러개여도 추가로 저장할 수 있게 한다.
// 그리고 항상 최단경로의 역추적을 저장하기 위해서.
// 지금의 비용과 같은경우 (비용이 같을 수 있어도, 경로는 다른경우) => trace배열에 추가
// 지금의 비용이 갱신되는 경우, 새로운 최단경로이므로, 지금까지의 역추적 trace 배열을 모두 초기화
// 이후 새로운 경로를 다시 추가시켜야 한다.
// 예시로, 예제인 

/*
7 9
0 6
0 1 1
0 2 1
0 3 2
0 4 3
1 5 2
2 6 4
3 6 2
4 6 4
5 6 1
*/

// 이 경우, 목적지인 6번 정점으로 오는 최단경로가 2개,
// 각각 0->1->5->6 과 0->3->6 이다
// 따라서, trace[6] 에는 3, 5가 저장이 되어있는다

// 여기서 이 역추적 배열은 다시 살펴보면, 최단경로를 거꾸로 뒤집은 일종의 인접리스트가 된다
// 따라서, 이 배열로 bfs나 dfs를 통하여 목적지->출발지 간 이어지는 모든 경로를 막아버리면 된다

// 이렇게 막는 방법은 2가지를 떠올렸다
// 1번 -> 2차원 bool 배열, ban_list를 따로 두어서 이를 통해 막는다
// => 이렇게 하고 보니, 인접리스트 따로, ban_list는 결국 인접행렬이 되므로 추가적인 메모리 소모가 된다

// 2번 -> 애초에 인접리스트가 아닌 인접행렬로 두어서, 해당하는 adj[u][v]를 -1로 바꾸기
// => 메모리적 이득은 취할 수 있지만, 인접행렬이라 다익스트라가 O(V^2)가 된다.

// 맨 처음에는 1번을 시도했다가 메모리 초과가 나오길래
// 2번으로 고쳤는데 또 메모리초과가 떴다

// => 알고보니 경로를 제거하는 과정을 bfs로 하면 queue의 크기가 너무 커져서 발생하는 메모리 초과라 하더라

// 추후에 1번 방법으로 해봤는데, 아주 조금 더 빠르긴 한데 메모리를 이게 더 덜쓰는게 미스테리.