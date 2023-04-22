#include <iostream>
#include <vector>
#include <string>
#include <queue>
#include <unordered_map>
using namespace std;
typedef pair<int, int> pii;
#define NEXT_MAX 26 
int n, m;
vector<string> map;
vector<bool> result;

class Trie {
public:
    Trie* next[NEXT_MAX];
    bool output;
    unordered_map<int, int> idx;

    Trie* fail;
    // 아호코라식 실패시 돌아갈 지점

    Trie() {
        output = false;
        for (int i = 0; i < NEXT_MAX; i++) next[i] = NULL;
    }

    void insert(const char* key, int wd_idx) {
        if (*key == '\0') {
            output = true;
            idx[wd_idx] = 1;
        }
        else {
            int curr = *key - 'A';
            if (next[curr] == NULL) next[curr] = new Trie();
            next[curr]->insert(key + 1, wd_idx);
        }
    }
    bool find(const char* str) {
        if (*str == '\0') {
            if (output) return true;
            else return false;
        }

        int curr = *str - 'A';
        if (next[curr] == NULL) return false;
        return next[curr]->find(str + 1);
    }
};

void ahoCorasik(Trie* root) {
    queue<Trie*> q;
    root->fail = root;
    q.push(root);

    while (!q.empty()) {
        Trie* curr = q.front(); q.pop();
        for (int i = 0; i < 26; i++) {
            Trie* n = curr->next[i];
            if (!n) continue;

            if (curr == root)
                n->fail = root;
            else {
                Trie* dest = curr->fail;
                while (dest != root && !dest->next[i])
                    dest = dest->fail;

                if (dest->next[i]) dest = dest->next[i];
                n->fail = dest;
            }

            if (n->fail->output) {
                n->output = true;
                for (pii p : n->fail->idx)
                    n->idx[p.first] = 1;
            }
            q.push(n);
        }
    }
}
vector<bool> ahoCorasikSearch(string s, Trie* root) {
    Trie* curr = root;
    vector<bool> ret = vector<bool>(m, false);
    for (int i = 0; i < s.size(); i++) {
        int n = s[i] - 'A';

        while (curr != root && !curr->next[n]) 
            curr = curr->fail;

        if (curr->next[n])
            curr = curr->next[n];

        if (curr->output) {
            for (pii p : curr->idx)
                ret[p.first] = true;
        }
    }
    return ret;
}
void chk(int k, int dir, Trie* root) {
    string s = "";
    if (dir == 0) {
        s += map[k];
    }   // 가로
    else if (dir == 1) {
        for (int i = 0; i < n; i++) s += map[i][k];
    }   // 세로
    else if (dir == 2) {
        for (int i = 0; i < n - k; i++) s += map[i][k + i];
    }   // 0 row 기준 대각선
    else {
        for (int i = 0; i < n - k; i++) s += map[k + i][i];
    }   // 0 column 기준 대각선

    vector<bool> list = ahoCorasikSearch(s, root); // 각 문자열마다 패턴매칭 시도
    for (int i = 0; i < m; i++) result[i] = result[i] || list[i];
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n >> m;
    map = vector<string>();
    result = vector<bool>(m, false);
    Trie* root = new Trie();

    for (int i = 0; i < n; i++) {
        string s;
        cin >> s;
        map.push_back(s);
    }
    for (int i = 0; i < m; i++) {
        string s;
        cin >> s;
        root->insert(s.c_str(), i);
        // 트라이에 삽입
    }

    ahoCorasik(root);
    // 아호코라식 실패함수 생성

    for (int i = 0; i < n; i++)
        for (int j = 0; j < 4; j++)
            chk(i, j, root);

    for (bool b : result) cout << b << '\n';
    return 0;
}

// 아호코라식 일대다 패턴 매칭에서
// 어떤 패턴이 매칭되는지에 대한거까지 탐색할 경우
