#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
typedef long long ll;
ll k, n;
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> k >> n;
    vector<ll> line = vector<ll>();
    while (k--) {
        ll a; cin >> a;
        line.push_back(a);
    }
    sort(line.begin(), line.end());
    if (n == 1) cout << line.front();
    else {
        ll start = 1, end = line.back(), mid;
        ll ans = 0;
        while (start <= end) {
            mid = (start + end) / 2;
            ll cnt = 0;
            for (ll i : line)
                cnt += i / mid;

            if (cnt < n) end = mid - 1;
            else {
                ans = mid;
                start = mid + 1;
            }
        }
        cout << ans;
    }
    return 0;
}
// 2021-12-27 해결
// 파라메트릭 서치
// 적절한 랜선의 길이를 이분탐색으로
// 범위가 int32_max까지 가능 -> 이분탐색 과정에서 오버플로우 가능성 다분함
// long long 으로 바꿔서 해결함