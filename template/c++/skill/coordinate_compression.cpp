# pragma GCC optimize ("O3")
# pragma GCC optimize ("Ofast")
# pragma GCC optimize ("unroll-loops")
# pragma GCC target("sse,sse2,sse3,ssse3,sse4,avx,avx2")

#include <iostream>
#include <vector>
#include <algorithm>
#include <unordered_map>
#include <math.h>
using namespace std;
struct Query {
    int left, right, idx;
};
int n, sqrtN;
vector<int> arr, cnt, ans;
unordered_map<int, int> map;
bool compare(Query q1, Query q2) {
    if (q1.left / sqrtN == q2.left / sqrtN) return q1.right < q2.right;
    return q1.left / sqrtN < q2.left / sqrtN;
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);    
    cin >> n;
    sqrtN = sqrt(n);

    // 
    arr = vector<int>(n + 1);
    cnt = vector<int>(n + 1);
    for (int i = 1; i <= n; i++) cin >> arr[i];
    vector<int> temp = arr;
    sort(temp.begin() + 1, temp.end());
    temp.erase(unique(temp.begin() + 1, temp.end()), temp.end());
    for (int i = 1; i <= n; i++)
        arr[i] = lower_bound(temp.begin(), temp.end(), arr[i]) - temp.begin();
    
    // 이 부분이 좌표 압축 부분

    int m; cin >> m;
    vector<Query> query = vector<Query>(m);
    ans = vector<int>(m);
    for (int i = 0; i < m; i++) {
        int a, b; cin >> a >> b;
        query[i] = {a, b, i};
    }
    sort(query.begin(), query.end(), compare);

    int start = query[0].left,
        end = query[0].right,
        currValue = 0;
    for (int i = start; i <= end; i++) {
        int curr = arr[i];
        if (cnt[curr] == 0) currValue++;
        cnt[curr]++;
    }
    ans[query[0].idx] = currValue;

    for (int i = 1; i < m; i++) {
        int left = query[i].left,
            right = query[i].right;

        while (start < left) {
			int curr = arr[start];
            cnt[curr]--;
            if (cnt[curr] == 0) currValue--;
			start++;
		}
		while (start > left) {
			start--;
			int curr = arr[start];
            if (cnt[curr] == 0) currValue++;
            cnt[curr]++;
		}
		while (end < right) {
			end++;
			int curr = arr[end];
			if (cnt[curr] == 0) currValue++;
            cnt[curr]++;
		}
		while (end > right) {
			int curr = arr[end];
			cnt[curr]--;
            if (cnt[curr] == 0) currValue--;
			end--;
		}
		ans[query[i].idx] = currValue;
    }

    for (int i : ans) cout << i << "\n";
    return 0;
}