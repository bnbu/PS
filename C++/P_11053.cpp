#include <iostream>
#include <vector>
using namespace std;
int main() {
    int n;
    cin >> n;
    int* arr = new int[n];
    int* memo = new int[n];
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