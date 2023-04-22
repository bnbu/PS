#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
typedef pair<int, int> pii; // <cost, weight>
vector<pii> item;
vector<int> memo;
int n, m;
int calc(int left) {
    if (memo[left] != -1) return memo[left];

    memo[left] = 0;
    for (int i = 0; i < n; i++)
        if (item[i].second <= left)
            memo[left] = max(memo[left], calc(left - item[i].second) + item[i].first);
    return memo[left];
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> m;
    item = vector<pii>();
    memo = vector<int>(m + 1, -1);
    for (int i = 0; i < n; i++) {
        int c, w; cin >> c >> w;
        item.push_back({c, w});
    }
    cout << calc(m) << '\n';
    return 0;
}
// Unbounded Knapsack
// 물건의 개수를 제한없이 넣을 수 있는 경우
// 동일한 물건을 2개이상 넣을 수 있는 경우로, 0-1 Knapsack 과는 약간 다르게 진행된다.

// 남은 용량(비용)에 대해서
// item array를 돌면서, 해당 item을 추가할 수 있다면
// 몇번째 item을 마지막으로 추가하는게 이득인지 체크
// 결과적으로 추가할 수 있는 모든 경우를 체크하게 되는 격이다.

// item 수 n / 가능 비용 c
// 시간 : O(nc) 
// 공간 : O(c)
