#include <iostream>
#include <string> // 이거 안쓰니까 틀렸던데 ㅅㅂ;
using namespace std;
int n, m;
struct Trie {
    Trie* next[26];
    bool output;

    Trie() {
        output = false;
        for (int i = 0; i < 26; i++) next[i] = NULL;
    }
    void insert(const char* s) {
        if (*s == '\0') output = true;
        else {
            if (!next[*s - 'a']) next[*s - 'a'] = new Trie();
            next[*s - 'a']->insert(s + 1);
        }
    }
    bool find(const char* s) {
        if (*s == '\0') return output; // 이부분도 ;;
        else {
            if (!next[*s - 'a']) return false;
            else return next[*s - 'a']->find(s + 1);
        }
    }
};
int main() {
    ios_base::sync_with_stdio(false);
    cout.tie(NULL);
    cin.tie(NULL);

    cin >> n >> m;
    Trie* root = new Trie();
    int ans = 0;

    while (n--) {
        string s;
        cin >> s;
        root->insert(s.c_str());
    }
    while (m--) {
        string s;
        cin >> s;
        ans += root->find(s.c_str());
    }
    cout << ans;
    return 0;
}
// 트라이로 푼거