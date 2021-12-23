#include <iostream>
#include <queue>
#include <string>
using namespace std;

int main() {
    int t;
    cin >> t;
    while (t--) {
        int n, m, i, cnt = 0;
        priority_queue<int> maxpq;
        priority_queue<int, vector<int>, greater<int>> minpq;
        string s = "";
        cin >> n >> m;
        s += to_string(m) + " ";
        cnt++;
        n--;
        while (n--) {
            cin >> i;
            if (i < m) maxpq.push(i);
            else minpq.push(i);

            if (n % 2 == 0) {
                if (maxpq.size() > minpq.size()) {
                    minpq.push(m);
                    m = maxpq.top(); maxpq.pop();
                }
                else if (maxpq.size() < minpq.size()) {
                    maxpq.push(m);
                    m = minpq.top(); minpq.pop();
                }

                if (cnt % 10 == 0) {
                    cnt++;
                    s += "\n";
                    s += to_string(m) + " ";
                }
                else {
                    cnt++;
                    s += to_string(m) + " ";
                }
            }
        }
        cout << cnt << '\n' << s << '\n';
    }
    return 0;
}
// 2021-08-01 해결
// 최소힙-최대힙의 활용