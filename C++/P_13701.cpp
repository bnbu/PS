#include <iostream>
#include <vector>
using namespace std;

vector<int> bits;
vector<int> dup;

int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    bits = vector<int>(1048577);
    int n;
    while (true) {
        cin >> n;
        if (cin.eof()) break;
        int num, th;
        num = n / 32;
        th = n % 32;
        if (!(bits[num] & (1 << th))) {
            bits[num] = bits[num] | (1 << th);
            cout << n << ' ';
        }
    }
    return 0;
}

// 2021-08-09 해결
// 비트마스킹을 이용한 비트 집합을 사용

// 입력된 순 -> 입력받았을때, 해당 비트가 0. 즉 이전에 입력된 적이 없는 경우
// 해당 부분의 비트를 1로 변경시킨 후, 해당 값을 바로 출력
