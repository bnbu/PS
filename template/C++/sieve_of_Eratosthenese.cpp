#include <iostream>
#include <cstring>
using namespace std;
int main() {
    int l;
    cin >> l;
    int* primes = new int[l + 1];
    memset(primes, 0, l + 1);
    
    for (int i = 2; i <= l; i++)
        primes[i] = i;
    
    for (int i = 2; i <= l; i++) {
        if (primes[i] == 0)
            continue;
        for (int j = i + i; j <= l; j += i)
            primes[j] = 0;
    }

    for (int i = 0; i <= l; i++)
        if (primes[i] != 0)
            cout << primes[i] << ' ';
}