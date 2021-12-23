#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int n;
    cin >> n;
    vector<int> arr = vector<int>(n);
    vector<int> memo = vector<int>();

    for (int i = 0; i < n; i++) cin >> arr[i];
    memo.push_back(arr[0]);
    int idx = 0;

    for (int i = 1; i < n; i++) {
        if (memo[idx] < arr[i]) {
            memo.push_back(arr[i]);
            idx++;
        }
        else memo[lower_bound(memo.begin(), memo.begin() + idx, arr[i]) - memo.begin()] = arr[i];
    }

    cout << idx + 1 << '\n';
    return 0;
}
// 2021-12-22 해결
// LIS(nlogn) 연습문제