#include <iostream>
#include <vector>
#include <algorithm>
#include <limits>

using namespace std;

int n;
vector<vector<int>> w;
vector<vector<int>> memo;

int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    cin >> n;
    w = vector<vector<int>>(n);
    memo = vector<vector<int>>(1 << n);
    for (int i = 0; i < (1 << n); i++) 
        memo[i] = vector<int>(n, INT32_MAX);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int a;
            cin >> a;
            w[i].push_back(a);
        }
    }

    memo[0][0] = 0;
    for (int i = 0; i < (1 << n) - 1; i++) {
        for (int j = 0; j < n; j++) {
            if (i & 1 << j) continue;
            for (int k = 0; k < n; k++) {
                if (memo[i][k] == INT32_MAX) continue;
                if (w[k][j] == 0) continue;
                memo[i | 1 << j][j] = min(memo[i | 1 << j][j], memo[i][k] + w[k][j]);
            }
            
        }
    }

    cout << memo[(1 << n) - 1][0];
    return 0;
}
// 2021-08-10 해결..?

// memo[i][j]
// => 현재 방문상태 비트마스킹 i에 대해 마지막 방문정점이 j일때의 비용 최솟값

// memo[0][0] => 기저케이스
// 비트마스킹 상태 i = 0인것은 아직 1개도 방문하지 않은 상황, 시작정점은 0번 정점으로 가정
// 순환시키면 결국은 그게 그거라 시작정점은 고정시켜도 무방하다 판단

// memo[i | 1 << j][j] => j번째 정점을 방문했을때 비트마스킹은 i | 1 << j가 될 것이며
// i | 1 << j 번째의 경우의 수에서 마지막 방문 정점이 j번일 경우의 최솟값을 구하려면

// 우리가 필요한것은 먼저 i | 1 << j 번째가 되기 이전의 상태인 i번째의 경우의 수에서
// 마지막 방문정점이 k일 경우인 memo[i][k]에 대해 생각해볼때,
// 이 경우 i | 1 << j 번째 경우의 수로 간다면, 방문 정점은 k -> j가 된다

// 따라서 계산할 값은 memo[i][k] + w[k][j]가 되고
// 이때 w[k][j] == 0(길이 없거나)이거나, 
// memo[i][k]가 계산된 적이 없다(방문되어진 적이 없는 경우)면 건너뛴다

// 여기서 시작정점이 0번 정점이었으니,
// 순환이기 떄문에, 우리가 생각해야할 것은 memo[(1 << n) - 1][0]
// 모든 정점을 다 거치고 다시 0번 정점으로 되돌아왔을때 뿐이다.
