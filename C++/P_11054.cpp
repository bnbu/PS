#include <iostream>
using namespace std;
int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    int n;
    cin >> n;
    int* arr = new int[n];
    int* maxMemo = new int[n];
    int* minMemo = new int[n];
    
    for (int i = 0; i < n; i++)
        cin >> arr[i];

    maxMemo[0] = 1;
    for (int i = 1; i < n; i++) {
        maxMemo[i] = 1;
        for (int j = 0; j <= i; j++) {
            if (arr[i] > arr[j])
                maxMemo[i] = maxMemo[i] < maxMemo[j] + 1 ? maxMemo[j] + 1 : maxMemo[i];
        }
    }
    minMemo[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) {
        minMemo[i] = 1;
        for (int j = n - 1; j >= i; j--) {
            if (arr[i] > arr[j])
                minMemo[i] = minMemo[i] < minMemo[j] + 1 ? minMemo[j] + 1 : minMemo[i];
        }
    }

    int max = 0;
    for (int i = 0; i < n; i++)
        max = max < maxMemo[i] + minMemo[i] - 1 ? maxMemo[i] + minMemo[i] - 1 : max;
    
    cout << max;

    return 0;
}

// 2021-07-21 해결
// 가장 긴 증가하는 수열 + 가장 긴 감소하는 수열