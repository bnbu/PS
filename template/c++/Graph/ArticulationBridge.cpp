#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

typedef pair<int, int> pii;

int v, e, cnt = 0;
vector<int> discover;
vector<pii> isCut;
vector<vector<int>> adj;

int dfs(int curr, int parent) { 
    int ret = discover[curr] = ++cnt;
    cout << "in: " << curr << " " << parent << " / " << ret << '\n';
    for (int next : adj[curr]) {
        if (next == parent) continue;

        if (discover[next] != -1) {
            ret = min(ret, discover[next]);
            continue;
        }

        int prev = dfs(next, curr);
        cout << curr << " " << next << " " << prev << " " << discover[curr] << '\n';
        if (prev > discover[curr])
            isCut.push_back({min(curr, next), max(curr, next)});

        ret = min(ret, prev);
    }
    cout << "out: " << curr << " " << parent << " / " << ret << '\n';
    return ret;
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    cin >> v >> e;
    discover = vector<int>(v + 1, -1);
    adj = vector<vector<int>>(v + 1);

    for (int i = 0; i < e; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    for (int i = 1; i <= v; i++)
        if (discover[i] == -1)
            dfs(i, 0);

    for (int i = 1; i <= v; i++) cout << discover[i] << " ";
    
    // sort(isCut.begin(), isCut.end());
    // cout << isCut.size() << '\n';
    // for (pii p : isCut)
    //     cout << p.first << ' ' << p.second << '\n';
    return 0;
}

// 단절선
// 단절점과 같이 DFS를 하면서
// 어느 한 정점에서 부모로 가는 간선이 아닌 간선 중
// 방문하지 않은 노드의 discover 번호가 현재 discover 번호보다 크다면
// 단절선이라 판정할 수 있다.

// 다시 말해서 curr 정점에 대해, prev (next에서 dfs시 도달 가능한 가장 빠른 정점)가
// discover[curr] (현재 방문중인 정점의 순서)보다 크다면
// 이는 자기보다 이전 정점으로 갈 수 없다는 의미가 되므로
// curr-next 간 사이의 정점을 제거하게 되면 component가 늘어나는 단절선이 되게 된다.

// 7 8
// 1 4
// 4 5
// 5 1
// 1 6
// 6 7
// 2 7
// 7 3
// 2 3

// 1에서 시작, 4를 거쳐 5에 도달한 후에는
// 출발했었던 1로 다시 가는 간선밖에 없다.
// 따라서 정점 4에 대해 next인 5에서 도달 가능한 가장 빠른 순서의 정점은 
// discover 순서가 1인 1번 정점이 된다.
// 그리고 4번 정점은 discover 순서가 2 이므로,
// 4 - 5 는 단절선이 아니다

// 이후 1에 대해 next인 4에서 도달 가능한 가장 빠른 순서의 정점은
// 이전의 return값을 그대로 받아오게 되므로 prev=1.
// 따라서 1 - 4 역시 단절선이 아니다.

// 이후, 1-6 6-7 7-2 2-3을 거쳐 3에서는 7이 연결되있으나 7은 이전에 방문했었다
// 따라서 더 앞선 순서가 되는 discover[7] = 5를 반환하고
// 2번 정점에서의 prev값은 5가 되며, discover[2] = 6과 비교시 더 작으므로
// 2 - 3 은 단절선이 아니고, 5를 리턴

// 이후 7에 대해 prev는 이전의 리턴인 5가 되며, 
// discover[7] = 5 이므로. 단절선이 아니고, 5를 리턴한다.

// 이후 6에 대해, prev는 이전의 리턴인 5가 되며,
// discover[6] = 4 이므로, prev가 더 크기 때문에
// 6 - 7 은 단절선이며, 4를 리턴한다.

// 이후 1에 대해, prev는 이전의 리턴인 4가 되고
// discover[1] = 1이므로, prev가 더 크기 때문에
// 1 - 6 은 단절선이고,
// 모든 정점을 다 탐색했으므로 종료되게 된다

// 이 결과에 따르면 1-6, 6-7 이렇게 2개가 단절선이다
