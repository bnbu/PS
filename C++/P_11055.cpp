#include <iostream>
using namespace std;
int* memo;
int* arr;
int main() {
    int n;
    cin >> n;
    arr = new int[n];
    memo = new int[n];
    for (int i = 0; i < n; i++) 
        cin >> arr[i];
    
    int ans = 0;
    memo[0] = arr[0];
    for (int i = 1; i < n; i++) {
        memo[i] = arr[i];
        for (int j = 0; j <= i; j++)
            if (arr[i] > arr[j]) 
                memo[i] = max(memo[i], arr[i] + memo[j]);
    }  
    for (int i = 0; i < n; i++)
        ans = max(ans, memo[i]);
    cout << ans;
    return 0;
}
// 2021-07-22 해결
// 가장 큰 증가하는 수열,
// dp 점화식을 큰 값의 갯수가 아닌 합으로 변경