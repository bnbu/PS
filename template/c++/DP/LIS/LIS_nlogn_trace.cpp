#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
using namespace std;
int main() {
    int n;
    cin >> n;
    vector<int> arr = vector<int>(n);   // 입력받은 배열
    vector<int> memo = vector<int>();   // lis 길이를 알아낼때 사용할 배열
    vector<int> v = vector<int>(n);     // arr의 각 값이 LIS구성시 들어간 idx
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

// LIS trace (nlogn)
// LIS가 뭔지 추적까지 하는 방법. 

// LIS를 만들때 일단, lower_bound로 마지막 값보다 작은 값의 적절한 위치를 찾아서
// 그 위치에 집어넣는 방식이기 때문에, memo배열 자체는 옳은 LIS가 아니긴 하다.

// 이를 올바른 LIS로 출력시키기 위해서는 따로 idx를 추적해줄 필요가 있다.
// 각 원소별로 LIS를 구성시킬때, 몇번 idx에 해당하는지를 추적한다.

// 그러고 나서, 배열의 뒤에서부터,
// idx를 하나씩 내리면서 해당하는 체크하면 되는데
// 이 상태면 역순이므로, stack을 통해 거꾸로 뒤집던 아무튼 반대 순서로 출력시키면 된다.

// [3, 5, 2, 6, 1] 배열은 다음과 같이 진행이 된다. 
//1. K = [3]            / [0, ?, ?, ?, ?]
//2. K = [3, 5]         / [0, 1, ?, ?, ?]  
//3. K = [2, 5]         / [0, 1, 0, ?, ?]
//4. K = [2, 5, 6]      / [0, 1, 0, 2, ?]
//5. K = [1, 5, 6]      / [0, 1, 0, 2, 1]
// 여기서, 추가적인 작업으로, 각 원소가 몇번째 index로 들어가는지까지 추적

// 최종적으로 idx = 2로 끝나는데
// 저 trace배열의 뒤에서부터, idx와 일치하면 그 위치에 해당하는 배열을 출력하고
// idx를 하나 내리는 식으로 진행을 하면
// 2-1-0 순으로 해당하는 값들을 얻을 수 있고
// 위의 경우라면, 6-5-3이 된다.
// 그리고 이를 역순으로 출력시키면 [3, 5, 6] 이라는 LIS를 얻을 수 있다