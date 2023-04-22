#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
typedef pair<int, int> pii; // <cost, weight>
vector<pii> item;
vector<int> bound;
// vector<vector<int>> memo;
vector<int> memo;
int n, m;
// int calc(int idx, int left) {
//     if (idx >= n) return 0;
//     if (memo[idx][left] != -1) return memo[idx][left];
//     memo[idx][left] = calc(idx + 1, left);
//     for (int i = 1; i <= bound[idx]; i++)
//         if (i * item[idx].second <= left)
//             memo[idx][left] = max(memo[idx][left], calc(idx + 1, left - i*item[idx].second) + i*item[idx].first);

//     return memo[idx][left];
// }
int calc(int idx, int left) {
    if (idx >= n) return 0;
    if (memo[left] != -1) return memo[left];
    memo[left] = calc(idx + 1, left);
    for (int i = 1; i <= bound[idx]; i++)
        if (i * item[idx].second <= left)
            memo[left] = max(memo[left], calc(idx + 1, left - i*item[idx].second) + i*item[idx].first);

    return memo[left];
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> m;
    item = vector<pii>();
    bound = vector<int>();
    // memo = vector<vector<int>>(n + 1);
    memo = vector<int>(m + 1, -1);
    for (int i = 0; i < n; i++) {
        // memo[i] = vector<int>(m + 1, -1);
        int c, w, b; cin >> c >> w >> b;
        item.push_back({c, w});
        bound.push_back(b);
    }
    cout << calc(0, m);
    return 0;
}
// Bounded Knapsack
// 각 물건의 개수의 제한이 걸려 있는 경우
// 동일한 물건을 최대 k개 까지만 넣을 수 있는 경우로
// 이때 각 물건의 제한값이 모두 1이라면 0-1 Knapsack과 동일한 문제가 된다.

// dfs 방식으로는 어떻게 할지 고민하다가
// 0-1 Knapsack때 처럼
// 현재 물건을 한개도 가져가지 않을때
// 현재 물건을 1개 ~ 최대개수 를 가져갈 때

// 이렇게 나눠서 dfs를 진행
// 근데 이거를 1차원 dp로 해도 될지 (capacity로만)
// 2차원 dp로 해야할지 (idx / capacity) 모르겠네