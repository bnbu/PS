#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
typedef pair<int, int> pii; // <cost, weight>
vector<pii> item;
vector<int> bound, memo;
int n, m;
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> m;
    item = vector<pii>();
    bound = vector<int>();
    memo = vector<int>(m + 1);
    for (int i = 0; i < n; i++) {
        int c, w, b; cin >> c >> w >> b;
        item.push_back({c, w});
        bound.push_back(b);
    }
    
    for (int i = 0; i < n; i++) {
        for (int j = m; j >= item[i].second; j--) 
            for (int k = 1; k <= min(bound[i], j / item[i].second); k++)
                memo[j] = max(memo[j], memo[j - k*item[i].second] + k*item[i].first);
    }

    int ans = 0;
    for (int i = 1; i <= m; i++) ans = max(ans, memo[i]);
    cout << ans;
    
    return 0;
}
// Bounded Knapsack
// 각 물건의 개수의 제한이 걸려 있는 경우
// 동일한 물건을 최대 k개 까지만 넣을 수 있는 경우로
// 이때 각 물건의 제한값이 모두 1이라면 0-1 Knapsack과 동일한 문제가 된다.

