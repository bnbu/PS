#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>
using namespace std;
typedef pair<int, int> pii;
vector<int> ans;
int n;
struct Trie {
    Trie* next[26];
    bool output;
    int childNum;
    int pressCnt;
    unordered_map<int, int> idx;

    Trie() {
        output = false;
        childNum = 0;
        pressCnt = 0;
        for (int i = 0; i < 26; i++) next[i] = NULL;
    }
    ~Trie() {
        for (int i = 0; i < 26; i++) delete next[i];
    }

    void insert(const char* s, int idx) {
        if (*s == '\0') {
            output = true;
            this->idx[idx] = 1;
        }
        else {
            if (!next[*s - 'a']) {
                next[*s - 'a'] = new Trie();
                this->childNum++;
            }
            next[*s - 'a']->insert(s + 1, idx);
        }
    }
};
void chk(Trie* root) {
    queue<Trie*> q;
    q.push(root);

    while(!q.empty()) {
        Trie* curr = q.front(); q.pop();
        if (curr->output) {
            for (pii p : curr->idx)
                ans[p.first] = curr->pressCnt;
        }
        for (int i = 0; i < 26; i++) {
            Trie* _next = curr->next[i];
            if (!_next) continue;
            
            if (curr == root) _next->pressCnt = curr->pressCnt + 1;
            else {
                if (curr->childNum > 1) _next->pressCnt = curr->pressCnt + 1;
                else {
                    if (curr->output) _next->pressCnt = curr->pressCnt + 1;
                    else _next->pressCnt = curr->pressCnt;
                }
            }
            q.push(_next);
        }
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    cout << fixed;
    cout.precision(2);

    while (cin >> n) {
        Trie* root = new Trie();
        ans = vector<int>(n);
        for (int i = 0; i < n; i++) {
            string s;
            cin >> s;
            root->insert(s.c_str(), i);
        }
        chk(root);

        double d = 0;
        for (int i : ans) d += i;
        cout << d / n << '\n';
        delete root;
    }
    return 0;
}
// 2021-12-22 해결
// 트라이를 구성시킨 다음
// 각 노드까지 도달하는데 눌러야 하는 버튼 수를 계산하면 된다.
// 단, 루트의 다음 노드들은 반드시 1번 눌러야하고
// 자식노드가 1개뿐인 노드의 다음 노드는 굳이 누를필요가 없다.

// 또한, 자식노드가 1개뿐이지만, 현재 노드에서 단어가 완성된다면
// 다음 노드에 해당하는 문자를 눌려야 하는 조건이 있어서 카운트 해줘야한다.