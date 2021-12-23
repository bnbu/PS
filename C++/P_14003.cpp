#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
using namespace std;
int main() {
    int n;
    cin >> n;
    vector<int> arr = vector<int>(n);
    vector<int> memo = vector<int>();
    vector<int> v = vector<int>(n);
    for (int i = 0; i < n; i++)
        cin >> arr[i];
    
    memo.push_back(arr[0]);
    v[0] = 0;
    int idx = 0;
    for (int i = 1; i < n; i++) {
        if (memo[idx] < arr[i]) {
            memo.push_back(arr[i]);
            idx++;
            v[i] = idx;
        }
        else {
            int temp = lower_bound(memo.begin(), memo.begin() + idx, arr[i]) - memo.begin();
            memo[temp] = arr[i];
            v[i] = temp;
        }
    }

    cout << idx + 1 << '\n';
    stack<int> st;
    for (int i = n - 1; i >= 0; i--) {
        if (v[i] == idx) {
            st.push(arr[i]);
            idx--;
        }
    }

    while (!st.empty()) {
        cout << st.top() << ' ';
        st.pop();
    }

    return 0;
}
// 2021-12-22 해결
// LIS (nlogn)의 추적 연습문제
