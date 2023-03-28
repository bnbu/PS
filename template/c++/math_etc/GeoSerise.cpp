#include <iostream>
#include <vector>
using namespace std;
typedef long long ll;

ll x, n, m;
ll p(ll a, ll k) {
    if (k == 0)
        return 1;
    else if (k % 2 == 0)
        return p((a * a) % m, k / 2) % m;
    else
        return (a * p(a, k - 1)) % m;
}
ll s(ll st, ll ed) {
    ll len = ed - st + 1;
    ll mid = st + len / 2;
    if (len == 1)
        return (x * p(x, st)) % m;
    else if (len % 2 == 0) 
        return (s(st, mid - 1) * (1 + p(x, len / 2))) % m;
    else 
        return (s(ed, ed) + s(st, mid - 1) * (1 + p(x, len / 2))) % m;
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int t;
    cin >> t;
    while (t--) {
        cin >> x >> n >> m;
        cout << s(0, n - 1) << '\n';
    }
    return 0;
}
// 이전에는 등비수열의 합을 그냥 구해서 거기에 모듈러연산을 적용해보려 함
// 근데 문제가 m의 조건이 1 <= m <= 1000000 사이의 모든 정수라서
// x(x^n - 1) / x-1 의 모듈러 연산 과정중
// m과 x-1이 서로소가 아니면 역원을 구할 수 없어서 모듈러 연산을 할 수가 없음
// => O(k) 불가능 (*k는 상수)


// 그래서 다르게 접근한게
// 예를들어서, 10항까지 있다고 해보자
// r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9 + r10
// 반씩 나눠보면,
// r1 + r2 + r3 + r4 + r5 + (r1 + r2 + r3 + r4 + r5)*r5
// (r1 + r2 + r3 + r4 + r5)(1 + r5)
// 다시, r1~r4를 나눠보면
// r1 + r2 + (r1 + r2)*r2 => (r1 + r2)(1 + r2)
// 즉 ((r1~r2)(1 + r2) + r5)(1 + r5)

// 이를 일반화 시키면 1~n까지가 있다면 이를 쪼개면
// (r1 ~ r(n/2)) + (r(n/2 + 1) ~ rn)
// => (r1 ~ r(n/2))(1 + r(n/2)) 로 바꿀 수 있고
// 다시 첫째항을 쪼갤 수 있다.
// 이때, 짝수개의 항이라면, 그대로 쪼개면 되지만
// 홀수개의 항이 된다면, 제일 마지막항은 계산하고 나머지 짝수개의 항으로만 진행하면 된다.

// 여기서 rn의 값은 마찬가지로 분할정복을 사용한 곱셈을 사용

// 따라서 log n번 발생하는 각 곱셈에서 log n번의 계산이 발생
// => O((log n)^2) 로도 풀 수 있는 문제가 된다.