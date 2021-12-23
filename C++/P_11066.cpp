#include <iostream>
#include <algorithm>
#include <vector>
#include <limits>
using namespace std;
vector<vector<int>> memo;
vector<int> v;

int dp(int start, int end) {
    if (memo[start][end] != INT32_MAX) return memo[start][end];
    if (start == end) return memo[start][end] = 0;
    if (start + 1 == end) return memo[start][end] = v[end] - v[start - 1];
    
    for (int i = start; i < end; i++) {
        int sum1 = v[i] - v[start - 1];
        int sum2 = v[end] - v[i];

        memo[start][end] = min(memo[start][end], dp(start, i) + dp(i + 1, end) + sum1 + sum2);
    }
    return memo[start][end];
}
int main() {
    int t;
    cin >> t;
    while (t--) {
        int k;
        cin >> k;
        
        memo = vector<vector<int>>(k + 1);
        v = vector<int>(k + 1, 0);
        for (int i = 0; i <= k; i++) memo[i] = vector<int>(k + 1, INT32_MAX);
        for (int i = 1; i <= k; i++) {
            int n;
            cin >> n;
            v[i] = v[i - 1] + n;
        }
        
        cout << dp(1, k) << '\n';
    }
    return 0;
}

// 맨처음에 한 방식
// 40 30 30 50 의 4개가 있다고 하면
// 40 | 30 30 50 으로 쪼개고
// 40 30 | 30 50 으로 쪼개고
// 40 30 30 | 50 으로 쪼개서 다시 3개짜리를 1 | 2개로 쪼개서
// 작은 부분의 비용을 최솟값만 계산해서 올라오는 방식

// 풀이는 총 3가지 정도가 있는데
// http://melonicedlatte.com/algorithm/2018/03/22/051337.html 를 참고하자