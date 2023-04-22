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
