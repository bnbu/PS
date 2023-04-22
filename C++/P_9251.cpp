#include <iostream>
using namespace std;
string s, t;
int** memo;
int main() {
    cin >> s >> t;
    int n = s.size();
    int m = t.size();    
    memo = new int*[n + 1];
    for (int i = 0; i <= n; i++) 
        memo[i] = new int[m + 1];

    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
            if (i == 0 || j == 0)
                memo[i][j] = 0;
            else if (s[i - 1] == t[j - 1]) 
                memo[i][j] = memo[i - 1][j - 1] + 1;
            else   
                memo[i][j] = max(memo[i - 1][j], memo[i][j - 1]);
        }
    }

    cout << memo[n][m];
    return 0;
}

// 2021-07-29 공부
// LCS (Longest Common Subsequence 또는 String..)
// LCS **subsequence를 이해하기 위해선 먼저 Longest Common String을 이해하면 쉽다.

// 최장 공통 문자열의 경우는
// 해당 문자까지 연속적으로 일치해야 문자열이다

// 따라서, 길이가 n인 문자열에 대해 길이가 m인 문자열과 비교를 한다고 하면
// 0 ~ n-1 인덱스까지 각 문자를 문자열 m과 비교를 하는데
// 이때, 서로 일치하는 문자가 있다면, 이 앞의 문자와 비교를 해서 연속적으로 매치가 되는지 확인해야한다.
// 이를 DP를 활용시켜서, 이 앞전까지 일치하는 최장 문자 길이에 +1을 시키면 된다

// ex)
// 이때, 2차원 배열은 다음과 같이 구성
// - - ABCDEF
// - 0 000000
// G 0 ------
// B 0 ------
// C 0 ------
// D 0 ------
// F 0 ------
// E 0 ------
// ** -는 아직 계산 안한 부분

// GBCDFE와 ABCDEF를 비교한다고 하자
// 1. G를 ABCDEF와 비교 => 일치하는게 없음 
// - - ABCDEF
// - 0 000000
// G 0 000000
// B 0 ------
// C 0 ------
// D 0 ------
// F 0 ------
// E 0 ------

// 2. B를 ABCDEF와 비교 => 두번째 위치와 일치, 즉 [2][2]가 지금 일치하는 위치이므로,
// 이전 문자인 G와 연속해서 일치하는지를 알기위해 [1][1]을 참조하여 +1 시켜 기록, 다르면 0
// - - ABCDEF
// - 0 000000
// G 0 000000
// B 0 010000
// C 0 ------
// D 0 ------
// F 0 ------
// E 0 ------

// 3. C를 ABCDEF와 비교 => 3번째 위치와 일치, [3][3]이 일치하는 위치이므로,
// 이전 문자인 B와 연속해서 일치하는지 알기위해 [2][2]를 참조
// - - ABCDEF
// - 0 000000
// G 0 000000
// B 0 010000
// C 0 002000
// D 0 ------
// F 0 ------
// E 0 ------

// 4. D를 ABCDEF와 비교 => 4번째 위치와 일치..
// - - ABCDEF
// - 0 000000
// G 0 000000
// B 0 010000
// C 0 002000
// D 0 000300
// F 0 ------
// E 0 ------

// 5. F와 비교 => 6번째 위치와 일치, [5][6]이 일치하는 위치이므로 [4][5]를 참조
// - - ABCDEF
// - 0 000000
// G 0 000000
// B 0 010000
// C 0 002000
// D 0 000300
// F 0 000001
// E 0 ------

// 6. E와 비교, => 5번째 위치와 일치, [6][5]가 일치하는 위치이므로, [5][6]을 참조
// - - ABCDEF
// - 0 000000
// G 0 000000
// B 0 010000
// C 0 002000
// D 0 000300
// F 0 000001
// E 0 000010

// 여기서 최장공통부분수열, subsequence는 문자열과 달리 반드시 연속할 필요가 없다.
// 따라서, 같은 경우에는 [i - 1][j - 1]을 참조하여 +1 시키는게 맞지만,
// 다른 경우에는 0으로 하는게 아닌, 이 앞전 부분 수열중 더 긴 것으로 하면 된다

// 앞전 부분 수열의 길이를 의미하는 것은 [i - 1][j] 와 [i][j - 1]이므로
// 이 두개 중 더 큰 값을 [i][j]의 값으로 사용하면 된다.
// 즉, GBCDEF중 C와 ABCDEF중 B를 비교 중일때,
// GBC, AB이므로, 우리가 봐야 할 부분은 GBC와 A의 최장 GB와 AB의 최장 부분수열만 비교해보면 된다
// 어차피 C와 일치하는건 없으므로, 증가하지 않아서 앞전거중 더 큰 것이 되기 때문.