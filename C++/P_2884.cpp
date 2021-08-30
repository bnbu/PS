#include <iostream>
using namespace std;

int main() {
    int h, m, M;
    cin >> h >> m;
    if (h == 0 && m < 45) {
        h += 23;
        m = (m + 60) - 45;
        cout << h << " " << m << endl;
    }
    else {
        h *= 60;
        M = h + m;
        M -= 45;
        h = M / 60;
        m = (m + 60) - 45;
        cout << h << " " << m << endl;
    }
}