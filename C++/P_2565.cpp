#include <iostream>
#include <vector>
using namespace std;

int* increase_memo;
vector<int> line;
int arr[501];

int main() {
    int n;
    cin >> n;
    increase_memo = new int[n];

    for (int i = 0; i < n; i++) {
        int a, b;
        cin >> a >> b;
        arr[a] = b;
    }

    for (int i = 0; i < 501; i++) {
        if (arr[i] != 0)
            line.push_back(arr[i]);
    }

    increase_memo[0] = 1;

    for (int i = 1; i < n; i++) {
        increase_memo[i] = 1;
        for (int j = 0; j <= i; j++) {
            if (line[i] > line[j])
                increase_memo[i] = max(increase_memo[i], increase_memo[j] + 1);
        }
    }

    int total = 0;
    for (int i = 0; i < n; i++) 
        total = max(total, increase_memo[i]);

    cout << n - total;

    return 0;
}

// 2021-07-24 해결
// 전기줄을 서로 합선이 발생하지 않도록 만든다
// 좌측의 번호로 정렬시켰을 때,
// 좌측-우측 연결시, 우측의 번호가 모두 증가하는 방향 수열을 이뤄야한다
// 즉 LIS의 길이가 가장 긴 경우.
// 따라서 LIS를 구하고 가장 긴 경우의 수를 전체 전깃줄의 수에서 제외시켜주면 됨.