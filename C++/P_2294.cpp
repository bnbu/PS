#include <iostream>
#include <cstring>
#include <limits>
using namespace std;
int* memo;
int* arr;
int n, k;
// m원을 만드는데 드는 최소동전 수
// -1 아직 구하지 않음, -2 구할 수 없음 그 외에 양수 => 최소 동전수
int coin(int m) {
    if (m == 0)
        return 0;
    if (memo[m] != -1)
        return memo[m];

    memo[m] = INT32_MAX;
    int next_m, next_c;
    for (int i = 0; i < n; i++) {
        next_m = m - arr[i];
        if (next_m < 0) continue;
        next_c = coin(next_m);
        if (next_c < 0) continue;
        memo[m] = min(memo[m], next_c);
    }

    if (memo[m] == INT32_MAX) memo[m] = -2;
    else memo[m] += 1;

    return memo[m];
}

int main() {
    cin >> n >> k;
    arr = new int[n];
    memo = new int[k + 1];
    memset(memo, -1, sizeof(int) * (k + 1));
    for (int i = 0; i < n; i++)
        cin >> arr[i];

    coin(k);
    int ans = memo[k] < 0 ? -1 : memo[k];
    cout << ans;
    return 0;
}

// 2021-07-26 해결
// 탑다운 방식으로 했는데
// 구할 수 없는 경우에 대한 처리를 어떻게 해야할지 고민