#include <iostream>
#include <vector>
using namespace std;
vector<int> fail;
string s;
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    while (true) {
        cin >> s;
        if (s.compare(".") == 0) break;

        fail = vector<int>(s.length());
        int j = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s[i] != s[j])  j = fail[j - 1];
            if (s[i] == s[j]) fail[i] = ++j;
        }

        if (s.length() % (s.length() - fail[s.length() - 1]) != 0) cout << 1 << '\n';
        else cout << s.length() / (s.length() - fail[s.length() - 1]) << '\n';
    }
    return 0;
}
// 2021-12-20 해결
// KMP의 실패함수를 응용하는 문제
// 실패함수- fail[i]에서, 0~i 번째까지의 문자열에 대해
// prefix-suffix 간 최장 일치 길이에 대해
// 해당 영역에서, 이 최장 일치 길이를 빼면 가장 짧은 주기의 반복 문자열을 알 수 있다.

// 위의 성질을 이용해서 fail[n - 1]로 문자열 전체에 대한 prefix-suffix의 최장 일치 길이를 계산
// 문자열의 전체 길이에서 이 길이만큼 빼서 가장 짧은 주기의 길이를 알아낸다.

// 이 가장짧은 주기로 문자열 전체를 쪼갤때, 딱 나누어 떨어진다면 총 몇개로 나누어 떨어지는지
// 그렇지 못하다면 그냥 1을 출력하면 된다.