#include <iostream>
#include <cstring>
using namespace std;
int** memo;
int** arr;
int n;

// [y][x] 번째 스티커를 선택시 가능한 최대 점수
int stiker(int y, int x) {
    if (x >= n || y >= 2)
        return 0;
    
    if (memo[y][x] != -1)
        return memo[y][x];
    
    int next_y = y == 0 ? 1 : 0;
    memo[y][x] = arr[y][x] + max(stiker(next_y, x + 1), max(stiker(0, x + 2), stiker(1, x + 2)));
    return memo[y][x];
}

int main() {
    int t;
    cin >> t;
    while (t--) {
        cin >> n;
        memo = new int*[2];
        arr = new int*[2];
        for (int i = 0; i < 2; i++) {
            memo[i] = new int[n];
            arr[i] = new int[n];
            fill(memo[i], memo[i] + n, -1);
            for (int j = 0; j < n; j++)
                cin >> arr[i][j];
        }
        int ans = max(stiker(0, 0), stiker(1, 0));
        
        cout << ans << '\n';
    }
    return 0;
}

// 2021-07-26 해결
// dp 연습