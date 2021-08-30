#include <iostream>
#include <vector>
#include <stack>
using namespace std;
typedef pair<int, int> pii;

int n, m;
int dx[4] = {0, 1, 0, -1};
int dy[4] = {-1, 0, 1, 0};
vector<vector<char>> map;
vector<vector<int>> chk;

int dfs(int y, int x) {
    if (y < 1 || x < 1 || y > n || x > m) return 2;

    if (chk[y][x] == -1) {
        chk[y][x] = 0;
        int direct = 0;
        switch (map[y][x]) {
            case 'U':
                direct = 0;
                break;
            case 'R':
                direct = 1;
                break;
            case 'D':
                direct = 2;
                break;
            case 'L':
                direct = 3; 
                break;
        }
        int next_y = y + dy[direct];
        int next_x = x + dx[direct];
        return chk[y][x] = dfs(next_y, next_x);
    }
    else if (chk[y][x] == 0) return 1;
    else if (chk[y][x] == 1) return 1;
    else if (chk[y][x] == 2) return 2;
}
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    cin >> n >> m;
    map = vector<vector<char>>(n + 1);
    chk = vector<vector<int>>(n + 1);
    for (int i = 1; i <= n; i++) {
        chk[i] = vector<int>(m + 1, -1);
        map[i] = vector<char>(m + 1);
        for (int j = 1; j <= m; j++)
            cin >> map[i][j];
    }

    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= m; j++)
            if (chk[i][j] == -1)
                dfs(i, j);

    int cnt = 0;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= m; j++)
            if (chk[i][j] == 2) cnt++;

    cout << cnt;
    return 0;
}