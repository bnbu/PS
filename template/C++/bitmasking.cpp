#include <iostream>
using namespace std;
int m, x, bit;
string s;
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    bit = 0;
    cin >> m;
    while (m--) {
        cin >> s;
        if (s == "add") {
            cin >> x;
            bit = bit | (1 << x);
        }
        else if (s == "remove") {
            cin >> x;
            bit = bit & ~(1 << x);
        }
        else if (s == "check") {
            cin >> x;
            if (bit & (1 << x)) cout << 1 << '\n';
            else cout << 0 << '\n';
        }
        else if (s == "toggle") {
            cin >> x;
            bit = bit ^ (1 << x);
        }
        else if (s == "all") bit = (1 << 21) - 1;
        else if (s == "empty") bit = 0;
    }
    return 0;
}

// P_11723

// 메모리 제한을 매우 작게 해버렸을때,
// 11723번 문제같은 경우에는 겨우 항목이 20개뿐인 집합이지만,
// 이를 bool 배열로 체크하기에도 벅찬 메모리 제한이다
// 이러한 경우, 20칸짜리 배열을, 하나의 정수로 표현해버리면 가능하다
// 이렇게 표현하는 방법이 바로 비트마스킹 기법.

// 예시로, 5개의 항목이 있다고 하자. 각 항목은 true 아니면 false로 판단되어지는데
// 이를 평소같으면 길이가 5인 bool 배열을 둬서 chk[i] = false / true 로 판단했지만,
// 비트마스킹을 사용하면 2진수로 나타냈을 때,
// 00000(2) ~ 11111(2) 사이의 정수로 판단이 가능하다.

// 이를 일반화 시켜서 항목이 총 n개인 것에 대해 true 아니면 false로 판단을 해야할 때
// 000..00(2) ~ 111..11(2) 의 정수 하나로 판단해볼 수 있고, **(단, 32비트 혹은 64비트가 한계)
// 각각 2^0부터 2^(n-1)에 해당하는 자리를 1번째부터 n번째 비트라고 두면

// n번째를 true로 변경
// bit = bit | (1 << n);
// => 1을 leftshift 연산을 x-1번 시켜서 2^n, 즉 n번째 비트에 위치시키고 이를 or 연산

// n번째 비트를 false로 변경
// bit = bit & ~(1 << n);
// => 1을 leftshift 연산을 x-1번 시켜서 2^n, 즉 n번째 비트에 위치시킨 것을 not 연산으로 반전시켜서
// 이를 and 연산 시키면, n번째 자리면 false로 변경할 수 있다.

// 전체를 true로 변경
// bit = (1 << n + 1) - 1;
// => 2^n - 1 이면, 2^n까지 모든 자리가 다 1이게 되고, 이는 전부 true를 의미한다

// 전체를 false로 변경
// bit = 0;
// => 값 자체가 0이면, 모든 비트가 0. 이는 전부 false를 의미한다.

// => ** 0번째 비트 ~ n번째 비트라고 생각하자.