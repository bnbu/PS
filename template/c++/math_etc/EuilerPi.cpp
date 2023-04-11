#include <iostream>
#include <vector>
#include <algorithm>
#include <hash_set>
using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    hash_set<long> prime = hash_set<long>();
    long n; cin >> n;
    double d = sqrt(n);

    long temp = n, k = 2;
    while (k <= d && temp != 1) {
        if (temp % k == 0) {
            prime.insert(k);
            temp /= k;
        }
        else k++;
    }

    if (temp > 1) prime.insert(temp);

    for (long l : prime)
        n = n / l * (l - 1);
    
    cout << n;
    
    return 0;
}

// n = p1^k1 + p2^k2 + p3^k3 + ...
// 꼴로 소인수 분해가 될 떄,
// Pi(n) = n * (1 - 1/p1) * (1 - 1/p2) * ... 꼴로 계산

// 소인수 분해를 일단은 O(sqrt(n)) 시간대로 사용했음