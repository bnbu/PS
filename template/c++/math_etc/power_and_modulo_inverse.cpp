#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
typedef long long ll;
typedef pair<int, int> pii;

pair<int, pii> extended_gcd(ll a, ll b) {
    if (b == 0) return {a, {1, 0}};
    pair<int, pii> ret = extended_gcd(b, a%b);
    int g, x, y;
    g = ret.first;
    x = ret.second.first;
    y = ret.second.second;
    return {g, {y, x-(a/b)*y}};
}
ll inverse(ll k, ll m) {
    pair<int, pii> res = extended_gcd(k, m);
    if (res.first > 1) return -1;
    return (res.second.first + m) % m; 
} // 확장 유클리드 호제법으로 역원 계산
ll p(ll x, ll n, ll m) {
    if (n == 1)
        return x % m;
    
    if (n % 2 == 0) {
        ll temp = p(x, n / 2, m);
        return (temp % m * temp % m) % m;
    }
    else 
        return (x % m * p(x, n - 1, m) % m) % m;
} // 분할정복으로 제곱 구하기
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int t;
    cin >> t;
    while (t--) {
        ll x, n, m;
        cin >> x >> n >> m;
        ll r = p(x, n+1, m);
        r = (r + (-x % m)) % m;
        ll i = inverse(x - 1, m);
        r = (r % m * i % m) % m;
    }
    return 0;
}