#include <iostream>
#include <vector>
#include <limits>
using namespace std;
typedef long long ll;
typedef pair<ll, ll> pll;
vector<pll> arr;
vector<vector<ll>> memo;
int n;
ll calc(int a, int b) {
    if (a == b) return memo[a][b];
    else if (memo[a][b] != INT64_MAX) return memo[a][b];
    for (int i = a; i < b; i++) {
        memo[a][b] = min(memo[a][b], calc(a, i) + calc(i + 1, b) + arr[a].first*arr[i].second*arr[b].second);
    }
    return memo[a][b];
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n;
    arr = vector<pll>(n);
    memo = vector<vector<ll>>(n);
    for (int i = 0; i < n; i++) {
        memo[i] = vector<ll>(n, INT64_MAX);
        int a, b;
        cin >> a >> b;
        arr[i] = {a, b};
        memo[i][i] = 0;
    }
    cout << calc(0, n - 1);
    return 0;
}
// 2021-12-22 해결
// 알고리즘 수업시간에 DP의 예시로 배운 그 문제다.

// memo[i][j] = i ~ j 번째 메트릭스간 곱의 최소값

// 이떄 memo[i][i] 들의 값은 항상 0이다. (이미 만들어져있으니까)
// memo[i][j] = min(calc(i, k) + calc(i + 1, k) + 행렬곱연선횟수)

// 이때 M x N과 N x K의 행렬을 곱하는데 연산 횟수는 MNK가 된다.
// 이를 통해 top-down 식으로 구성이 가능