#include <iostream>
using namespace std;
int gcd(int a, int b){
    int tmp, n;
    if(a < b) {
        tmp = a;
        a = b;
        b = tmp;
    }
    while(b != 0) {
        n = a%b;
        a = b;
        b = n;
    }
    return a;
}
int gcd_rec(int a, int b){
    if(b == 0) return a;
    else return gcd(b, a % b);
}
int lcm(int a, int b) {
    return a*b / gcd(a, b);
}

int main() {
    return 0;
}