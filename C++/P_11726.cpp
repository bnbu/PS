#include <iostream>
#include <cstring>
using namespace std;

int* memo;
int p = 10007;

int tile(int n) {
    if (n == 1) return 1;
    if (n == 2) return 2;
    if (memo[n] != -1) return memo[n];
    return memo[n] = (tile(n - 1) % p + tile(n - 2) % p) % p;
}

int main() {
    int n;
    cin >> n;
    memo = new int[n + 1];
    memset(memo, -1, sizeof(int) * (n + 1));
    cout << tile(n);
    return 0;
}
// 2021-07-27 해결
// 간단한 점화식 찾기