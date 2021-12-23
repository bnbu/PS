#include <iostream>
#include <vector>
using namespace std;
vector<bool> a, b;
vector<int> fail;
int n;
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    bool ans = false;
    a = vector<bool>(360000, false);
    b = vector<bool>(360000, false);
    fail = vector<int>(360000, 0);

    cin >> n;
    for (int i = 0; i < n; i++) {
        int k;
        cin >> k;
        a[k] = true;
    }
    for (int i = 0; i < n; i++) {
        int k;
        cin >> k;
        b[k] = true;
    }

    int j = 0;
    for (int i = 1; i < 360000; i++) {
        while (j > 0 && a[i] != a[j]) j = fail[j - 1];
        if (a[i] == a[j]) fail[i] = ++j;
    }

    j = 0;
    for (int i = 0; i < 720000; i++) {
        while (j > 0 && b[i % 360000] != a[j]) j = fail[j - 1];
        if (b[i % 360000] == a[j]) {
            if (j == 359999) {
                ans = true;
                break;
            }
            else j++;
        }
    }

    if (ans) cout << "possible";
    else cout << "impossible";
    return 0;
}
// 2021-12-22 해결
// 놀랍게도 KMP를 이용해서 푸는 문제

// 시계바늘 각도 정보에 따라, 위치한 부분을 1, 시계바늘이 위치하지 않은 부분을 0
// 그러면 길이가 360000인 1과 0의 배열이 나온다.

// 입력받은 두 시계바늘의 각도들중 하나를 패턴으로 생각하고 a라 하자.
// 이 a로 fail함수를 작성시킨다.
// 그리고 나머지 한쪽을 b라고 두고 두번 이어붙여 길이가 720000인 배열을 만들었다 치자.

// 두 시계의 시간이 같다고 판단하려면, 어느 한쪽을 돌려서 둘이 일치하게 되야 한다.
// 다시말해서 두 시계의 이루는 각도가 일치해야 한다는 뜻.

// 따라서 두번 이어붙인다는 것은, 그 한쪽을 최대 한바퀴 돌려보겠단 뜻이 된다.
// 따라서 b를 2번 이어붙인 배열에서 a가 발견이 된다면
// 이는 a와 b의 시계바늘이 이루는 모양이 일치
// 즉 어느 한쪽을 돌리다 보면 같은 시간으로 볼 수 있게 된다는 뜻.