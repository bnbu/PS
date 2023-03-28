#include <iostream>
#include <vector>
using namespace std;

int n, m;
vector<int> depth;
vector<vector<int>> parent;
vector<bool> visit;
vector<int>* adj;

void dfs(int v, int d) {
    visit[v] = true;
    depth[v] = d;
    for (int next : adj[v]) {
        if (visit[next]) continue;

        parent[next][0] = v;
        dfs(next, d + 1);
    }
}

void calcParent() {
    for (int j = 1; j < 21; j++)
		for (int i = 1; i <= n; i++)
			parent[i][j] = parent[parent[i][j - 1]][j - 1];
}

int lca(int x, int y) {
    int a, b;
    if (depth[x] > depth[y]) {
        a = y;
        b = x;
    }
    else {
        a = x;
        b = y;
    }

    for (int i = 20; i >= 0; i--) {
        if (depth[b] - depth[a] >= (1 << i))
            b = parent[b][i];
    }
    // 1 << i : 1을 i만큼 left-shift 연산, 즉, 2^i 이다.
	// 높이 차가 2^i보다 작아지는 순간의 깊이가 깊은 정점을 parent[b][i]로 갱신
	// 새롭게 갱신된, 즉 2^i만큼 올라온 위치에서 다시 높이를 조절한다.
	// 이러면 2^i씩 거슬러 올라갈 수 있다. (1,2,4,8.. 칸씩 올라오므로)
	// 선형적인 구조보다 더 빠르다.

    if (a == b) return a;
    // => 같은 높이까지 올라온 b와 a가 서로 같음, 즉 LCA가 a일때.

    for (int i = 20; i >= 0; i--) {
        if (parent[a][i] != parent[b][i]) {
            a = parent[a][i];
            b = parent[b][i];
        }
    }
    // 서로 같지 않다면, 같은 부모가 나올때까지 찾으러 간다.
	// a와 b의 2^i번째 조상이 서로 같지 않다면,
	// 그 위치에서 다시 2^i번째 조상을 거슬러 찾으러 간다.
	// 이때 2^20번째부터 찾으며, 트리가 작아도
	// 2^20번째의 조상은 0 (루트의 위)로 판단하므로 상관없음.
	// 이렇게 마지막까지 찾고 난 다음에 a와 b의 조상이 같은 위치까지 오게 되면,
	// 찾은 노드는 공통조상의 바로 자식의 노드이게 된다.

    return parent[a][0];
}

int main() {
    cin >> n;
    parent = vector<vector<int>>(n + 1);
    adj = new vector<int>[n + 1];
    depth = vector<int>(n + 1);
    visit = vector<bool>(n + 1);

    for (int i = 0; i <= n; i++) {
        parent[i] = vector<int>(21);
        for (int j = 0; j < 21; j++)
            parent[i][j] = 0;
        visit[i] = false;
    }

    for (int i = 0; i < n - 1; i++) {
        int u, v;
        cin >> u >> v;
        
        adj[u].push_back(v);
        adj[v].push_back(u);
    }

    dfs(1, 0);
    calcParent();

    int m;
    cin >> m;
    while (m--) {
        int a, b;
        cin >> a >> b;
        cout << lca(a, b) << '\n';
    }

    return 0;
}



// LCA (Lowest Common Ancestor) 최소 공통 조상

// 두 정점에서 (자신을 포함한) 조상으로 올라갈 때 처음으로 공통되게 만나는 정점
// => LCA를 구하는 두 정점의 높이(깊이)를 동등하게 만든 후
// 조상으로의 탐색을 진행하여 LCA를 구할 수 있다.
// 일반적 구현으로는  선형구조가 되기 때문에 O(N)

// 약간의 전처리를 통하여 O(logN)으로 LCA를 구할 수 있다
// 일단 DFS를 수행하여 각 정점의 깊이와 부모를 저장한다.
// 이때 par[x][i] (정점번호가 x인 정점의 2^i 번째 조상) 을 선언
// par[x][0]에 부모를 채워준다.
// 이후 par[x][i] = par[par[x][i-1]][i-1] 으로 2^i번째 조상을 채워나갈 수 있다.
// ** 이때 parent[0][0]~parent[0][20]까지 다 사용하므로 초기화 할 것.

// 전처리(calcParent)에서 j를 20까지만 계산하는 이유는,
// N이 10만이라서, 2^20은 100만 이상으로, 조건에 충분함

// 여기서 2^i번째 조상을 구해놨기 때문에, 2^i씩 높이를 올려가며 조상을 탐색할 수 있다
// 2^i씩 탐색하므로 O(logN) 시간대를 갖는다.

// p_11438