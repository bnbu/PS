#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
int main() {
    int n;
    cin >> n;
    vector<int> arr = vector<int>(n);
    vector<int> memo = vector<int>();
    for (int i = 0; i < n; i++)
        cin >> arr[i];
    
    memo.push_back(arr[0]);
    int idx = 0;
    for (int i = 1; i < n; i++) {
        if (memo[idx] < arr[i]) {
            memo.push_back(arr[i]);
            idx++;
        }
        else memo[lower_bound(memo.begin(), memo.begin() + idx, arr[i]) - arr.begin()] = arr[i];
    }

    cout << idx << '\n';
    for (int i : memo) cout << i << ' ';
    cout << '\n';

    return 0;
}

// LIS (nlogn)
// 기본적으로 배열을 순회하면서, i번째 원소를 포함시킬 위치를 찾는 방법으로 진행한다.

// LIS를 만들기 위해서는, 만드는 과정에서 마지막 원소가 가능한 작을수록 더 길게 만들 수 있다.
// 따라서 원소가 들어올 때 현재 생성된 마지막 원소보다 더 작다면
// LIS에 들어갈 위치를 찾은 후 그것으로 교체하도록 한다. 
// 이때 이 위치를 찾는데 lower_bound를 사용한다.

// [10, 20, 10, 30, 20, 50] 이라고 하면
// i=0 [10] 
// i=1 [10, 20]
// i=2 [10, 20] (2번째 10이 1번째 10으로 들어감)
// i=3 [10, 20, 30]
// i=4 [10, 20, 30] (3번째 20이 2번째  20으로 들어감)
// i=5 [10, 20, 30, 50]

// [1, 2, 3, 7, 5, 6] 이라고 하면
// i=0 [1]
// i=1 [1, 2]
// i=2 [1, 2, 3]
// i=3 [1, 2, 3, 7]
// i=4 [1, 2, 3, 5] (4번째 5가, 7의 위치로 들어감)
// i=5 [1, 2, 3, 5, 6]

// 즉, 원소가 만약 지금까지 나온 원소중 가장 크다면 (마지막 삽입 원소보다 더 크다면)
// 그 원소는 LIS에 바로 삽입

// 반대로, 마짐가 삽입 원소보다는 작다면,
// 그 원소는 lower_bound를 통해 교체시킬 위치를 찾아서 그곳과 바꿔버린다.

// 이렇게 되면, idx는 마지막 삽입 idx. 즉 idx+1 은 LIS의 길이가 된다.
// 이 상태로면, 길이만 구할 수 있다.

// [3, 5, 2, 6, 1] 배열은 다음과 같이 진행이 된다. 
//1. K = [3]
//2. K = [3, 5]
//3. K = [2, 5]
//4. K = [2, 5, 6]
//5. K = [1, 5, 6]
// => 실재로 LIS는 [3, 5, 6] 이지만, 길이는 아무튼 구해지기 때문에
// 상관은 없다.