#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

int maxValue = 0;
vector<int> w, v;
vector<vector<int>> memo;
int n, k;
int knapsack(int i, int left) {
    if (i > n) return 0;
    if (memo[i][left] != -1) return memo[i][left];
    
    if (left < w[i]) return memo[i][left] = knapsack(i + 1, left);
    else return memo[i][left] = max(knapsack(i + 1, left), knapsack(i + 1, left - w[i]) + v[i]);

    // i번째 물건을 넣을 수 없는 경우 -> 물건을 넣지 않고 가는것으로 생각
    // i번재 물건을 넣을 수 있는 경우 -> 물건을 넣지 않고 간 경우 vs 물건을 넣고 간 경우 중 큰거로.
}
int main() {
    cin >> n >> k;
    w = vector<int>(n + 1);
    v = vector<int>(n + 1);
    memo = vector<vector<int>>(n + 1);
    for (int i = 0; i <= n; i++)
        memo[i] = vector<int>(k + 1, -1);

    for (int i = 1; i <= n; i++) 
        cin >> w[i] >> v[i];

    cout << knapsack(1, k);
    return 0;
}
// 0-1 knapsack을 DFS 방식을 차용해서 품

// 구현은 간단한데
// 일반 방식보다 느린거 같다.