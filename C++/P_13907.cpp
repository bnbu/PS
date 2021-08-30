#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <limits>
#include <tuple>
using namespace std;

typedef pair<int, int> pii; // adj : {cost, vertex}
typedef tuple<int, int, int> tp; // dijkstra : {cost, count, vertex}

int n, m, k, s, d, a, b, w, p;
vector<vector<pii>> adj;
vector<vector<int>> dist;

void dijkstra(int start) {
    priority_queue<tp, vector<tp>, greater<tp>> pq;
    pq.push({0, 0, start});
    dist[start][0] = 0;
    while (!pq.empty()) {
        tp curr = pq.top(); pq.pop();
        int curr_w = get<0>(curr);
        int curr_c = get<1>(curr);
        int curr_v = get<2>(curr);
        if (curr_c >= n) continue;
        if (curr_w != dist[curr_v][curr_c]) continue;
        for (pii next : adj[curr_v]) {
            int next_w = next.first;
            int next_v = next.second;
            bool flag = true;
            for (int i = 0; i <= curr_c; i++) 
                if (dist[next_v][i] < curr_w + next_w) {
                    flag = false;
                    break;
                }
            if (flag && dist[next_v][curr_c + 1] > curr_w + next_w) {
                dist[next_v][curr_c + 1] = curr_w + next_w;
                pq.push({dist[next_v][curr_c + 1], curr_c + 1, next_v});
            } 
        }
    }
}
void printDist(int sum) {
    int ans = INT32_MAX;
    for (int i = 0; i <= n; i++) {
        if (dist[d][i] == INT32_MAX) continue;
        else ans = min(ans, dist[d][i] + sum * i);
    }
    cout << ans << '\n';
}
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    cin >> n >> m >> k;
    adj = vector<vector<pii>>(n + 1);
    dist = vector<vector<int>>(n + 1);
    for (int i = 0; i <= n; i++)
        dist[i] = vector<int>(n + 1, INT32_MAX);
    
    cin >> s >> d;
    
    for (int i = 1; i <= m; i++) {
        cin >> a >> b >> w;
        adj[a].push_back({w, b});
        adj[b].push_back({w, a});
    }
    dijkstra(s);

    int sum = 0;
    printDist(sum);
    while (k--) {
        cin >> p;
        sum += p;
        printDist(sum);
    }

    return 0;
}

// 2021-08-05 해결
// 존재할 수 있는 최단경로들에 대해, 각 간선의 비용을 늘릴때마다, 그에 맞춰 최단경로를 계산하는 문제

// => 다익스트라의 최단경로 비용 배열을 2차원 배열로 확장시킨다
// dist[i][j] => 출발지로부터 i번 정점까지 총 j개의 정점을 거쳐서 도착하는 최단경로 비용
// 이렇게 확장시켜서 생각해본다

// 우선, 늘어나는 간선 비용이라는 특성때문에, 후에 변화하는 최단경로들이 가장 큰 영향을 받는 것은
// 해당 최단 경로에 포함된 간선의 개수가 가장 큰 요인이 된다.
// 해당 특성에 따라, 거쳐가는 경로가 더 많은 경우,
// 이보다 더 적은 경로 갯수에 이보다 더 적은 비용을 갖는다면 경로가 존재한다면
// 해당 비용은 후에 늘어나는 비용이 계산되어도 절대 최단경로 후보에 들 수 없다.
// 다익스트라 중에 위의 경우만 따로 걸러내서 계산하면 된다

// 이후 계산된 배열중 우리가 필요한 부분은 s -> d 부분
// 따라서 d에 도달하기까지 모든 경로에 포함된 경로 수 만큼 상승한 비용을 계산
// 매번 비용이 상승할 때 마다 이렇게 계산한 비용들 중 최솟값을 출력하면 된다.

// 첫 시도에는 지금의 최단비용에 몇개의 간선이 존재하는지, 역추적을 사용하려 했는데,
// 이는 결국 매번 다익스트라를 요구하게 되므로 시간초과가 예상되어 시도하지 않았다.

// 여기서 입출력 제한을 해제하지 않고 하니까 시간이 엄청 오래 걸렸는데
// 하고 안하고가 차이가 큰걸 실감했다.
