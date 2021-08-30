#include <iostream>
#include <vector>
#include <limits>
using namespace std;

int n, p, start;
string s;
vector<vector<int>> cost;
vector<int> memo;

int getBitCount(int bit) {
    int ret = 0;
    for (int i = 0; i < n; i++)
        if (bit & (1 << i)) ret++;
    
    return ret;
}
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    cin >> n;
    cost = vector<vector<int>>(n);
    memo = vector<int>(1 << n, INT32_MAX);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int a;
            cin >> a;
            cost[i].push_back(a);
        }
    }
    start = 0;
    cin >> s;
    for (int i = 0; i < s.size(); i++)
        if (s[i] == 'Y')
            start = start | 1 << i;

    memo[start] = 0;
    for (int i = start; i < (1 << n) - 1; i++) {
        if (memo[i] == INT32_MAX) continue;
        for (int j = 0; j < n; j++) {
            if (!(i & 1 << j)) continue;
            for (int k = 0; k < n; k++) {
                if (i & 1 << k) continue;
                memo[i | 1 << k] = min(memo[i | 1 << k], memo[i] + cost[j][k]);
            }
        }
    }

    cin >> p;
    int ans = INT32_MAX;
    for (int i = 0; i < 1 << n; i++)
        if (getBitCount(i) >= p) 
            ans = min(ans, memo[i]);
        
    
    if (ans == INT32_MAX) cout << -1;
    else cout << ans;

    return 0;
}
// 2021-08-11 해결

// 기존의 비트마스킹을 이용한 최솟값 찾기 DP 문제와 비슷하지만,

// 1311번 문제의 경우는, 다음의 할 일 순서가 있기 때문에,
// 비트 상태에 따라 다음 순서를 알 수 있으므로, 다음 할 일에 대한 각 사람별 비용만 보면 됬고

// 2098번 문제의 경우는, 현재 정점 위치를 알아둬야 다음 정점까지의 거리를 알 수 있었으므로
// dp배열을 2차원으로 둬서 memo[i][j], 비트마스킹 상태 i일때, 마지막 위치가 j번 정점일 경우의 최소 비용
// memo[i | 1 << j][j] => j번째 정점을 방문했을때 비트마스킹은 i | 1 << j가 될 것이며
// i | 1 << j 번째의 경우의 수에서 마지막 방문 정점이 j번일 경우의 최솟값을 구하려면
// 우리가 필요한것은 먼저 i | 1 << j 번째가 되기 이전의 상태인 i번째의 경우의 수에서
// 마지막 방문정점이 k일 경우인 memo[i][k]에 대해 생각해볼때,
// 이 경우 i | 1 << j 번째 경우의 수로 간다면, 방문 정점은 k -> j가 된다
// 따라서 계산할 값은 memo[i][k] + w[k][j]가 되고
// 이때 w[k][j] == 0(길이 없거나)이거나, 
// memo[i][k]가 계산된 적이 없다(방문되어진 적이 없는 경우)면 건너뛴다


// 이 문제의 경우는 일단 켜진 발전기에 한해서는, 다른 꺼져있는 발전기는 킬 수 있다.
// memo[i] => 비트마스킹 상태 i일때 발전기를 키는 최소비용이 된다
// 특이하게도, 먼저 켜져있는 발전기 상태를 받기 때문에
// 시작되는 비트마스킹 상태는 0으로 고정이 아닌, 입력받은 특정값에 따라 시작한다.

// start한 부분부터, (1 << n) - 2 까지 순회하는데,
// 지금의 비트상태에 대한 최솟값이 존재하지 않는다면, 불가능한 케이스이므로 건너뛰고
// j는 키는 곳. 키는 곳이기 때문에, 먼저 켜져있어야 한다
// 따라서 i & 1 << j가 0이라면 시도가 불가능하므로 continue
// 키는 곳이 일단 결정되었다면,
// k는 켜지는 곳. 켜지는 곳이기 때문에, 켜져있으면 안된다
// 따라서 i & 1 << k가 1이라면 시도가 불가능하므로 continue
// 이렇게 켜지는곳까지 결정되었다면
// 다음번에 켜지는 곳은 k번째 발전기가 될것이며, 키는곳은 j번 이므로
// 다음번 비트마스킹 상태는 i | 1 << k가 될 것이고
// j번에서 k번을 켰기 때문에, 계산되어지는 값은 memo[i] + cost[j][k]
// => memo[i | 1 << k] = min(memo[i | 1 << k], memo[i] + cost[j][k]);

// 이후 최소 p개가 켜져있어야 하기 때문에
// 계산 된 이후 모든 경우의 수중, 비트마스킹 상태 i에 대해 비트 수가 p개 이상인 경우 중 최솟값을 구한다.
// 조건에 맞는 것 중 최솟값이 계산되어진게 한개도 존재하지 않는다면, 불가능하다는 뜻.