#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
typedef unsigned int uint;
typedef pair<int, int> pii;
int n, cnt;
vector<int> subSize, nodeDepth, chainDepth, chainHead, chainParent;
vector<uint> segTree, plusLazy, mulLazy;
vector<pii> hldRange;
vector<vector<int>> adj;
// segment tree
void propagate(int start, int end, int node) {
    if (plusLazy[node] != 0 || mulLazy[node] != 1) {
        segTree[node] *= mulLazy[node];
        segTree[node] += plusLazy[node] * (end - start + 1);
        if (start != end) {
            mulLazy[2*node] *= mulLazy[node];
            mulLazy[2*node + 1] *= mulLazy[node];
            plusLazy[2*node] *= mulLazy[node];
            plusLazy[2*node + 1] *= mulLazy[node];

            plusLazy[2*node] += plusLazy[node];
            plusLazy[2*node + 1] += plusLazy[node];
        }
        mulLazy[node] = 1;
        plusLazy[node] = 0;
    }
}
void update(int start, int end, int node, int left, int right, uint plus, uint mul) {
    propagate(start, end, node);
    if (right < start || end < left) return;
    if (left <= start && end <= right) {
        mulLazy[node] *= mul;
        plusLazy[node] += plus;
        propagate(start, end, node);
        return;
    }
    int mid = (start + end) / 2;
    update(start, mid, 2*node, left, right, plus, mul);
    update(mid + 1, end, 2*node + 1, left, right, plus, mul);
    segTree[node] = segTree[2*node] + segTree[2*node + 1];
}
static uint get(int start, int end, int node, int left, int right) {
    propagate(start, end, node);
	if (right < start || end < left) return 0;
	if (left <= start && end <= right) return segTree[node];
	int mid = (start + end) / 2;
    return get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right);
}

// hld
bool sizeCompare(int i, int j) {
    return subSize[i] > subSize[j];
}
void getSize(int curr, int parent) {
    subSize[curr] = 1;
    for (int next : adj[curr]) {
        if (next == parent) continue;
        getSize(next, curr);
        subSize[curr] += subSize[next];
    }
}
void dfs(int curr, int parent, int depth, int cDepth) {
    hldRange[curr].first = ++cnt;
    nodeDepth[curr] = depth;
    chainDepth[curr] = cDepth;
    bool isFirst = true;
    for (int next : adj[curr]) {
        if (next == parent) continue;
        if (isFirst) {
            chainHead[next] = chainHead[curr];
            chainParent[next] = chainParent[curr];
            dfs(next, curr, depth + 1, cDepth);
            isFirst = false;
        }
        else {
            chainHead[next] = next;
            chainParent[next] = curr;
            dfs(next, curr, depth + 1, cDepth + 1);
        }
    }
    hldRange[curr].second = cnt;
}

//query 
void updateSubTree(int x, uint plus, uint mul) {
    update(1, n, 1, hldRange[x].first, hldRange[x].second, plus, mul);
}
uint getSubTree(int x) {
    return get(1, n, 1, hldRange[x].first, hldRange[x].second);
}
void updatePath(int u, int v, uint plus, uint mul) {
    while (chainHead[u] != chainHead[v]) {
        if (chainDepth[u] > chainDepth[v]) swap(u, v);
        update(1, n, 1, hldRange[chainHead[v]].first, hldRange[v].first, plus, mul);
        v = chainParent[v];
    }
    if (nodeDepth[u] > nodeDepth[v]) swap(u, v);
    update(1, n, 1, hldRange[u].first, hldRange[v].first, plus, mul);
}  
uint getPath(int u, int v) {
    uint ret = 0;
    while (chainHead[u] != chainHead[v]) {
        if (chainDepth[u] > chainDepth[v]) swap(u, v);
        ret += get(1, n, 1, hldRange[chainHead[v]].first, hldRange[v].first);
        v = chainParent[v];
    }
    if (nodeDepth[u] > nodeDepth[v]) swap(u, v);
    ret += get(1, n, 1, hldRange[u].first, hldRange[v].first);
    return ret;
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    int q; cin >> n >> q;

    segTree = vector<uint>(4*n);
    plusLazy = vector<uint>(4*n);
    mulLazy = vector<uint>(4*n, 1);
    subSize = vector<int>(n + 1);
    nodeDepth = vector<int>(n + 1);
    chainDepth = vector<int>(n + 1);
    chainHead = vector<int>(n + 1);
    chainParent = vector<int>(n + 1);
    hldRange = vector<pii>(n + 1);
    adj = vector<vector<int>>(n + 1);

    for (int i = 1; i < n; i++) {
        int u, v; cin >> u >> v;
        adj[u].push_back(v);
        adj[v].push_back(u);
    }
    getSize(1, 0);
    for (int i = 1; i <= n; i++)
        sort(adj[i].begin(), adj[i].end(), sizeCompare);
    cnt = 0;
    chainHead[1] = 1;
    dfs(1, 0, 1, 1);

    while (q--) {
        int a; cin >> a;
        if (a % 2 == 1) {
            int x; cin >> x;
            if (a == 5) {
                cout << getSubTree(x) << "\n";
                continue;
            }
            int v; cin >> v;
            if (a == 1) {
                updateSubTree(x, v, 1);
            }
            else {
                updateSubTree(x, 0, v);
            }
        }
        else {
            int x, y; cin >> x >> y;
            if (a == 6) {
                cout << getPath(x, y) << "\n";
                continue;
            }
            int v; cin >> v;
            if (a == 2) {
                updatePath(x, y, v, 1);
            }
            else {
                updatePath(x, y, 0, v);
            }
        }
    }
    return 0;
}
// 일단 논리는 맞으니까 푼거에 대해서 작성은 할건데
// Java는 unsigned 없어서 불가능한거면 이거 대체 어떻게 풀어야하지

// 문제 자체는 트리에 대해 6개의 쿼리를 실행한다
// 쿼리는 크게 2종류로 나눠볼 수 있다

// 1. 어떤 정점 x가 루트인 서브트리에 대한 쿼리
// 2. 어떤 두 정점 u, v를 잇는 단순 경로에 대한 쿼리

// 1의 경우는 Euler Tour Technique을 통해
// 2의 경우는 Heavy-Light Decomposition을 통해 
// 트리의 쿼리에 대해 Segment Tree를 사용할 수 있다.
// 1이건 2이건 구간에 대한 값의 갱신이 발생하므로 Lazy Propagation을 사용해야한다.

// 우선 HLD과정에서 HLD 방문 순서를 결정할 때, ETT를 하던떄처럼
// 이어진 노드의 방문이 끝나고 난 후 나올때의 HLD 방문 순서를 기록하는 것으로
// HLD를 하면서 동시에 ETT를 해낼 수 있다.

// 문제는 갱신하는 쿼리가 2종류다
// a. 어느 서브트리 혹은 경로에 v를 더한다
// b. 어느 서브트리 혹은 경로에 v를 곱한다

// 구간에 값을 더하는 갱신은 많이 해서 익숙하지만
// 구간에 값을 곱하는 갱신은 이것만 있으면 어려움이 없는데
// 더하는 갱신과 같이 발생해서 많이 고민됐다
// 더하는 갱신과 곱하는 갱신의 순서에 따라 값이 전혀 다르게 바뀌기 때문

// 이때 잘 생각해보면 덧샘에 대한 Lazy는 곱샘에 대해 어떠한 영향을 미치진 못한다
// 하지만 그 반대로 곱샘에 대한 Lazy는 덧샘에 대해 영향을 미칠 수 밖에 없다.

// 예시로, 덧샘에 대한 Lazy가 3이 있는 구간에
// 곱샘을 2를 한다고 치면
// (기존값 + lazy값) * 2 가 되어야 이치가 맞고

// 곱샘에 대한 Lazy가 2가 있는 구간에
// 덧샘을 3을 한다고 치면
// 기존값 * 3 + Lazy값 이 되므로

// 이 방식에 따라 propagate를 
// 덧샘에 대한 Lazy는 기존에 하던데로 덧셈에 똑같이 전파시키지만
// 곱샘에 대한 Lazy는 곱샘 뿐만 아니라, 덧샘에까지 곱샘을 통해 전파를 시켜야만 한다

// 이렇게 하면 문제없이 구간에 대한 덧샘, 곱샘 갱신을 할 수 있다.
