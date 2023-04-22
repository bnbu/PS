#include <iostream>
#include <vector>
using namespace std;
vector<int> boyerMoore(string t, string p) {
    vector<int> ret;
    vector<int> lastOccur = vector<int>(26, -1);

    for (int i = 0; i < p.size(); i++) lastOccur[p[i] - 'A'] = i;
    // 각 문자별 마지막 발생위치 **(알파벳 대문자로만 한정시켰음)

    int n = t.size();
    int m = p.size();

    int i = m - 1;
    int j = m - 1;
    while (i < n) {
        if (t[i] == p[j]) {
            if (j == 0) {
                ret.push_back(i);
                i = i + p.size();
                j = p.size() - 1;
            }
            else {
                i--;
                j--;
            }
        }
        else {
            int l = lastOccur[t[i] - 'A'];
            i = i + m - min(j, l + 1);
            j = m - 1;
        }
    }
    return ret;
}
int main() {
    string t, p;
    cin >> t >> p;

    vector<int> list = boyerMoore(t, p);
    for (int i : list) cout << i << ' ';
    return 0;
}

// Boyer-Moore
// 패턴의 뒷부분부터 비교를 시작
// mismatch 발생하면 text에서의 해당 지점의 문자가 패턴에서 마지막으로 발생한 index만큼 shift
// 즉 mismatch 이후, 다시 비교를 시작할 지점은
// i + m - (l + 1)이 되는데
// 여기서 l+1 이 mismatch가 발생한 패턴의 지점보다 작은 경우에는 무한루프가 생기므로
// 한칸 shift 할수 있게끔 해서
// i + m - min(j, l + 1) 이 된다.