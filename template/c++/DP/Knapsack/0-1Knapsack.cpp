#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

int maxValue = 0;
vector<vector<int>> memo;

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> w = vector<int>(n + 1);
    vector<int> v = vector<int>(n + 1);
    memo = vector<vector<int>>(n + 1);
    for (int i = 0; i <= n; i++)
        memo[i] = vector<int>(k + 1, 0);

    for (int i = 1; i <= n; i++) 
        cin >> w[i] >> v[i];
    
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= k; j++) {
            if (w[i] <= j)
                memo[i][j] = max(v[i] + memo[i - 1][j - w[i]], memo[i - 1][j]);
            else
                memo[i][j] = memo[i - 1][j];
            
            maxValue = max(maxValue, memo[i][j]);
        }
    }
    // i번째 물건만 넣을 수 있는 상황이고, 무게 제한은 j라고 하자
	// i번째 물건의 무게 w[i]에 대해 w[i] <= j 면 넣을 수 있는 상황
	// 넣을 수 있으니, 물건을 넣고, 남은 무게 제한 j - w[i]에서
	// 이전 물건 무게 중 j - w[i] 내에 충족하는 최대 가치가 있다면
	// 그것과 더해서 같은 무게제한, 이전번째 물건일때의 최대치와 비교해서
	// 최댓값을 저장한다 (memo[i][j]에는 항상 최댓값만 들어감)
	
	// 무게 제한을 충족시키지 못한다면, 이전번째 같은 무게제한의 최대가치를 가져온다
	// **첫번째 (i == 1)일때는 0번째를 참조하고, 0번째는 알다시피 모두 0임.
	
	// 일련의 과정을 거쳐 n번째 물건, 무게제한이 k일때의 값까지 구한다.

    cout << maxValue;
    return 0;
}

// 0-1 knapsack 기본방식
// sliding window 방식으로 메모리적 부분에서 최적화가 가능