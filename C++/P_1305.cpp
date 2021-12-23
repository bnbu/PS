#include <iostream>
#include <vector>
using namespace std;
int main() {
    int l;
    string s;
    cin >> l >> s;

    int j = 0;
    vector<int> fail = vector<int>(s.length());
    for (int i = 1; i < s.length(); i++) {
        while (j > 0 && s[i] != s[j]) j = fail[j - 1];
        if (s[i] == s[j]) fail[i] = ++j;
    }

    cout << s.length() - fail[s.length() - 1];
    return 0;
}
// 2021-12-20 해결
// KMP의 실패함수를 응용하는 문제
// 실패함수- fail[i]에서, 0~i 번째까지의 문자열에 대해
// prefix-suffix 간 최장 일치 길이에 대해
// 해당 영역에서, 이 최장 일치 길이를 빼면 가장 짧은 주기의 반복 문자열을 알 수 있다.

// 위의 성질을 이용해서 fail[n - 1]로 문자열 전체에 대한 prefix-suffix의 최장 일치 길이를 계산
// 문자열의 전체 길이에서 이 길이만큼 빼서 가장 짧은 주기의 길이를 알아낸다.

// 광고 방식을 보면, 특정 문자열이 주기를 가져 반복된다고 볼 수 있기 때문.