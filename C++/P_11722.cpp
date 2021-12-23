#include <iostream>
using namespace std;
int* arr;
int* memo;
int main() {
    int n;
    cin >> n;
    arr = new int[n];
    memo = new int[n];
    for (int i = 0; i < n; i++)
        cin >> arr[i];
    
    memo[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) {
        memo[i] = 1;
        for (int j = n - 1; j >= i; j--) 
            if(arr[i] > arr[j])
                memo[i] = max(memo[i], memo[j] + 1);
    }
    int ans = 0;
    for (int i = 0; i < n; i++)
        ans = max(memo[i], ans);
    cout << ans;
    
    return 0;
}
// 2021-07-22 해결
// 가장 긴 감소하는 수열
// => 뒤에서부터 가장 긴 증가하는 수열을 진행 시켰다
