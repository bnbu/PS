#include <iostream>
#include <algorithm>
#include <vector>
#include <limits>
using namespace std;
vector<vector<int>> memo;
vector<int> v;
vector<int> pSum;

int dp(int start, int end) {
    if (memo[start][end] != INT32_MAX) return memo[start][end];
    if (start == end) return memo[start][end] = 0;
    if (start + 1 == end) return memo[start][end] = v[start] + v[end];
    
    for (int i = start; i < end; i++) {
        int sum1 = dp(start, i);
        int sum2 = dp(i + 1, end);

        memo[start][end] = min(memo[start][end], sum1 + sum2);
    }
    return memo[start][end] += pSum[end] - pSum[start - 1];
}
int main() {
    int t;
    cin >> t;
    while (t--) {
        int k;
        cin >> k;
        
        memo = vector<vector<int>>(k + 1);
        v = vector<int>(k + 1, 0);
        pSum = vector<int>(k + 1, 0);
        for (int i = 0; i <= k; i++) memo[i] = vector<int>(k + 1, INT32_MAX);
        for (int i = 1; i <= k; i++) {
            int n;
            cin >> v[i];
            pSum[i] = pSum[i - 1] + v[i];
        }
        
        cout << dp(1, k) << '\n';
    }
    return 0;
}

