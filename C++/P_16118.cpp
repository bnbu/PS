#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <tuple>
#include <algorithm>
using namespace std;
typedef long long ll;
typedef pair<ll, int> p;
typedef tuple<ll, int, int> t;

int n, m, a, b, d;
vector<ll>fox_d;
vector<vector<ll>>wolf_d;
vector<vector<p>> adj;

void dijkstra_f(int start) {
    priority_queue<p, vector<p>, greater<p>> pq;
    pq.push({0, start});
    fox_d[start] = 0;
    while (!pq.empty()) {
        p curr = pq.top(); pq.pop();
        ll curr_d = curr.first;
        int curr_v = curr.second;
        if (fox_d[curr_v] != curr_d) continue;
        for (p next : adj[curr_v]) {
            ll next_d = next.first;
            int next_v = next.second;
            if (fox_d[next_v] > curr_d + next_d * 2) {
                fox_d[next_v] = curr_d + next_d * 2;
                pq.push({fox_d[next_v], next_v});
            }
        }
    }
}
void dijkstra_w(int start) {
    priority_queue<t, vector<t>, greater<t>> pq;
    pq.push({0, start, 0});
    wolf_d[start][0] = 0;
    while (!pq.empty()) {
        t curr = pq.top(); pq.pop();
        ll curr_d = get<0>(curr);
        int curr_v = get<1>(curr);
        int curr_c = get<2>(curr);
        int chk = curr_c % 2;
        if (wolf_d[curr_v][chk] < curr_d) continue;
        for (p next : adj[curr_v]) {
            ll next_d = next.first;
            int next_v = next.second;
            if (chk == 0 && wolf_d[next_v][1] > curr_d + next_d) {
                wolf_d[next_v][1] = curr_d + next_d;
                pq.push({wolf_d[next_v][1], next_v, curr_c + 1});
            }
            else if (chk == 1 && wolf_d[next_v][0] > curr_d + next_d * 4) {
                wolf_d[next_v][0] = curr_d + next_d * 4;
                pq.push({wolf_d[next_v][0], next_v, curr_c + 1});
            }
        }
    }
}
int main() {
    cin >> n >> m;
    fox_d = vector<ll>(n + 1, INT64_MAX);
    wolf_d = vector<vector<ll>>(n + 1);
    for (int i = 1; i <= n; i++) wolf_d[i] = vector<ll>(2, INT64_MAX);
    adj = vector<vector<p>>(n + 1);

    while (m--) {
        cin >> a >> b >> d;
        adj[a].push_back({d, b});
        adj[b].push_back({d, a});
    }
    dijkstra_w(1);
    dijkstra_f(1);

    int ans = 0;
    for (int i = 1; i <= n; i++)
        if (min(wolf_d[i][0], wolf_d[i][1]) > fox_d[i]) ans++;
    
    cout << ans;

    return 0;
}
// 2021-08-12 해결
// 다익스트라의 응용편

// 여우는 같은 속도로 이동
// 늑대는 2배의 속도 - 0.5배의 속도 - 2배의 속도 - 0.5배의 속도.. 로 반복

// 비용을 이동시간이라고 하고, 여우의 이동속도를 1이라 가정시켰을 때,
// 늑대의 경우 2배 속도일때 시간이 소수점이 나온다, 그러나 double형을 사용하면 범위초과

// => long long 으로 둬서
// 여우는 기존 비용의 2배를 시켜버리고
// 늑대의 경우는 빠르게 가면 기존 비용의 1배, 느리게 가면 기존 비용의 4배를 적용

// 여우의 경우는 비용만 2배로 해서 평범한 다익스트라를 하면 된다
// 늑대의 경우는 좀 복잡해지는데
// 우선 지금 늑대가 몇번째(홀수, 짝수) 이동인지에 따라서, 다음 이동이 2배 속도인지, 0.5배속도인지가 결정

// 그리고 2배속->0.5배속 으로 온 거리보다
// 2배속->0.5배속->2배속 으로 온 거리가 더 빠른 경우도 존재한다.

// 따라서 wolf_d[i][0], wolf_d[i][1]로 둬서, 
// 각각 i번 정점까지 짝수번 이동으로 / 홀수번 이동으로 도착하기까지의 최소비용으로 정의.

// 이렇게 두 경우를 모두 구하고, 늑대의 경우에는 각 정점당 이 두가지 경우중 더 적은 비용을
// 늑대가 도착하기까지의 최소 비용이라 보면 된다.