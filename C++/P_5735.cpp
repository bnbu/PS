#include <iostream>
#include <vector>
#include <string>
#include <queue>
#include <unordered_map>
using namespace std;
#define NODE_MAX 85
unordered_map<char, int> charidx;
int n, m, ans;
struct Trie {
    Trie* fail;
    Trie* next[NODE_MAX];
    bool output;

    Trie() {
        output = false;
        for (int i = 0; i < NODE_MAX; i++) next[i] = NULL;
    }
    ~Trie() {
        for (int i = 0; i < NODE_MAX; i++) delete next[i];
    }

    void insert(const char* s) {
        if (*s == '\0') output = true;
        else {
            if (!next[charidx[*s]]) next[charidx[*s]] = new Trie();
            next[charidx[*s]]->insert(s + 1);
        }
    }
};
void makeFailureFunction(Trie* root) {
    queue<Trie*> q;
    root->fail = root;
    q.push(root);

    while (!q.empty()) {
        Trie* curr = q.front(); q.pop();
        for (int i = 0; i < NODE_MAX; i++) {
            Trie* _next = curr->next[i];
            if (!_next) continue;

            if (curr == root) _next->fail = root;
            else {
                Trie* temp = curr->fail;
                while (temp != root && !temp->next[i]) temp = temp->fail;
                if (temp->next[i]) temp = temp->next[i];
                _next->fail = temp;
            }
            if (_next->fail->output) _next->output = true;
            q.push(_next);
        }
    }
}
void ahocorasik(string s, Trie* root) {
    Trie* curr = root;
    for (int i = 0; i < s.length(); i++) {
        int n = charidx[s[i]];
        if (n == -1) {
            curr = root;
            continue;
        }
        while (curr != root && !curr->next[n]) curr = curr->fail;
        if (curr->next[n]) curr = curr->next[n];
        if (curr->output) {
            ans++;
            curr = root;
        }
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int idx = 0;
    string s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?.,:;-_'#$%&/=*+(){}[]";
    for (char c : s) charidx[c] = idx++;
    charidx[' '] = -1;
    
    while (true) {
        cin >> n >> m;
        if (n == 0 && m == 0) break;
        ans = 0;

        Trie* root = new Trie();
        for (int i = 0; i < n; i++) {
            string s;
            cin >> s;
            root->insert(s.c_str());
        }
        makeFailureFunction(root);
        cin.ignore();
        for (int i = 0; i < m; i++) {
            string s;
            getline(cin, s, '\n');
            ahocorasik(s, root);
        }
        cout << ans << '\n';
        delete root;
    }
    return 0;
}
// 2021-12-22 해결
// 무려 노드가 85개짜리인 트라이를 구성
// 구성되는 이모티콘들을 트라이로 만들어서
// 그거로 아호코라식을 진행
// 이때, 텍스트를 탐색중, 공백 혹은 이모티콘이 완성되는 부분을 발견하면
// 공백인 경우는, 트라이 내에 포함시키지 않는 문자이므로, root부터 다시 탐색한다.
// 이모티콘이 완성되는 부분은, 이 끝자락을 공백으로 대체시킨 것으로 가정, root부터 다시 탐색한다.