#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
#define MAX 1000000000
int n, cnt;
vector<int> arr, p, subSize, pos, serialized;
vector<vector<int>> adj;
void dfs(int curr, int depth) {
    subSize[curr] = 1;
    serialized.push_back(MAX + depth);
    pos[curr] = ++cnt;
    serialized.push_back(arr[curr]);
    cnt++;
    for (int next : adj[curr]) {
        dfs(next, depth + 1);
        subSize[curr] += subSize[next];
    }
    serialized.push_back(MAX + depth + 1);
    serialized.push_back(arr[curr]);
    cnt += 2;
}
void manachers() {
    p = vector<int>(serialized.size());
    int j = -1, k = -1;
    for (int i = 0; i < serialized.size(); i++) {
        if (i <= j) p[i] = min(j - i, p[2*k - i]);
        while (i - p[i] - 1 >= 0 && i + p[i] + 1 < serialized.size() && serialized[i - p[i] - 1] == serialized[i + p[i] + 1])
            p[i]++;
        
        if (j < i + p[i]) {
            j = i + p[i];
            k = i;
        }
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n;
    arr = vector<int>(n + 1);
    subSize = vector<int>(n + 1);
    pos = vector<int>(n + 1);
    adj = vector<vector<int>>(n + 1);
    
    for (int i = 1; i <= n; i++) cin >> arr[i];

    for (int i = 1; i < n; i++) {
        int u, v; cin >> u >> v;
        adj[u].push_back(v);
    }
    serialized = vector<int>();
    for (int i = 1; i <= n; i++)
        sort(adj[i].begin(), adj[i].end());
    dfs(1, 1);
    serialized.push_back(MAX + 1);
    manachers();

    vector<int> ans = vector<int>();
    for (int i = 1; i <= n; i++) 
        if (p[pos[i] + 2*subSize[i] - 1] >= 2*subSize[i])
            ans.push_back(i);

    cout << ans.size() << "\n";
    for (int i : ans) cout << i << " ";
    return 0;
}

// ETT로 트리를 일자로 폈을떄 배열을 구한다
// Manacher's를 통해 팰린드롬 길이를 구함
// 각 노드를 끝으로 하는 팰린드롬의 중심은 일자로 폈을때의 배열에서 해당 노드의 위치 + 해당노드를 루트로 하는 서브트리 크기 - 1이 된다 (더미문자열 은 따로 고려해서 계산해야함)
// 이를 통해 팰린드롬 1이상인 노드를 모두 찾으면 된다

// 이때, 2번 예제처럼 숫자가 다 같은데, 모양이 다른 경우는 각 사이에 넣는 더미값을 깊이에 따라 다르게 넣게 해서 Manacher's를 통한 대칭을 판단

// 자바는 시간초과 걸림
