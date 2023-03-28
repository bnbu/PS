#include <iostream>
#include <vector>
using namespace std;
int main() {
    int n;
    cin >> n;
    vector<int> arr = vector<int>(n);
    vector<int> memo = vector<int>(n);
    for (int i = 0; i < n; i++)
        cin >> arr[i];
    
    int max = 1;
    memo[0] = 1;
    for (int i = 1; i < n; i++) {
        memo[i] = 1;
        for (int j = 0; j <= i; j++) {
            if (arr[i] > arr[j])
                memo[i] = memo[i] < 1 + memo[j] ? 1 + memo[j] : memo[i];
        }
        max = max < memo[i] ? memo[i] : max;
    }
    cout << max;

    return 0;
}

// LIS (n^2)
// 간단하게 지금 내가 확인중인 값 이전에 위치한 값 뒤에 이 값을 넣어서 LIS를 구성시킬 수 있다면
// 그리고 그러한 경우중 가장 긴 경우를 사용하겠단 뜻이 된다.