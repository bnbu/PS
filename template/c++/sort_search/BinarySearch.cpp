#include <iostream>
#include <vector>
using namespace std;
typedef long long ll;
int main() {
    vector<int> arr; // sorted array;

    int start = 0;
    int end = arr.size();
    int mid = (start + end) / 2;
    int key; // to find
    int idx;

    while (start <= end) {
        if (arr[mid] == key) idx = mid;
        else if (arr[mid] >= key) start = mid + 1;
        else start = mid - 1;
    }

    // parametric search는 위의 과정을 값을 찾는게 아닌
    // 활용하려는 매개변수를 이분탐색으로 찾으면서
    // 주어진 조건 (예를들면 특정 길이로 분할시, 최소 k개의 조각이 나오게끔)을
    // 만족하는 매개변수값을 이분탐색으로 찾는것.

    // 위처럼 index를 찾는게 아닌 값 자체를 찾는 것이기 때문에
    // 따라서 mid값이 매개변수가 되어서
    // 그 값으로 조건에 만족하는 k값을 찾는 과정을 거침

    // ex ) 1654 또는 2110번

    // ll start = 1, end = line.back(), mid;
    //    ll ans = 0;
    //    while (start <= end) {
    //        mid = (start + end) / 2;
    //        ll cnt = 0;
    //        for (ll i : line)
    //            cnt += i / mid;       <- mid 값으로 무언가 행동을 한다.
    //        if (cnt < n) end = mid - 1;
    //        else {
    //            ans = mid;
    //            start = mid + 1;
    //        }
    //    }
    return 0;
}