#include <iostream>
#include <unordered_map>
using namespace std;
int n, m;
int main() {
    ios_base::sync_with_stdio(false);
    cout.tie(NULL);
    cin.tie(NULL);

    cin >> n >> m;
    unordered_map<string, int> map;
    int ans = 0;

    while (n--) {
        string s;
        cin >> s;
        map[s] = 1;
    }
    while (m--) {
        string s;
        cin >> s;
        ans += map[s];
    }
    cout << ans;
    return 0;
}
// 맵으로 푼거