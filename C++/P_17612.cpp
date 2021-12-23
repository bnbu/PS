#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <tuple>
using namespace std;
typedef long long ll;
typedef tuple<ll, ll, ll> tlll;
typedef pair<ll, ll> pll;
struct comparer1 {
    bool operator()(tlll t1, tlll t2) {
        if (get<0>(t1) == get<0>(t2))
            return get<1>(t1) > get<1>(t2);
        else return get<0>(t1) > get<0>(t2);
    }
};
bool comparer2(tlll t1, tlll t2) {
        if (get<0>(t1) == get<0>(t2))
            return get<1>(t1) > get<1>(t2);
        else return get<0>(t1) < get<0>(t2);
    }

int main() {
    int n, k;
    cin >> n >> k;

    queue<pll> q;
    // <id, 물건수>
    for (int i = 0; i < n; i++) {
        ll id, w;
        cin >> id >> w;
        q.push({id, w});
    }

    priority_queue<tlll, vector<tlll>, comparer1> pq;
    // <누적계산 시간, 카운터번호, id>
    
    for (int i = 1; i <= k; i++) {
        if (!q.empty()) {
            pll cust = q.front(); q.pop();
            pq.push({cust.second, i, cust.first});
        }
    }


    vector<tlll> v;
    // <나올떄 누적시간, 카운터 번호, id>
    while (!pq.empty()) {
        tlll curr = pq.top(); pq.pop();
        v.push_back(curr);
        if (!q.empty()) {
            pll cust = q.front(); q.pop();
            pq.push({get<0>(curr) + cust.second, get<1>(curr), cust.first});
        }
    }
    sort(v.begin(), v.end(), comparer2);

    ll ans = 0;
    for (ll i = 0; i < n; i++) ans += (i+1)*get<2>(v[i]);
    cout << ans;

    return 0;
}
// long long 당함
// 계산 과정에 있는거도 long long으로