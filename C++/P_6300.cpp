#include <iostream>
#include <vector>
#include <queue>
#include <tuple>
#include <limits>
#include <unordered_map>
using namespace std;
typedef pair<int, int> pii;
typedef tuple<int, int, int> tiii;
int dx[8] = {0, 1, 1, 1, 0, -1, -1, -1};
int dy[8] = {-1, -1, 0, 1, 1, 1, 0, -1};
int l, c, w;
vector<string> puzzle;
vector<int> length;
vector<tiii> ans;

class Trie {
    public:
    Trie* next[26];
    bool output;
    unordered_map<int, int> idx;
    Trie* fail;

    Trie() {
        output = false;
        for (int i = 0; i < 26; i++) next[i] = NULL;
    }

    void insert(const char* str, int w_idx) {
        if (*str == '\0') {
            output = true;
            idx[w_idx] = 1;
        }
        else {
            int curr = *str - 'A';
            if (next[curr] == NULL) next[curr] = new Trie();
            next[curr]->insert(str + 1, w_idx);
        }
    }
};
void makeFailure(Trie* root) {
    queue<Trie*> q;
    root->fail = root;
    q.push(root);
    
    while (!q.empty()) {
        Trie* curr = q.front(); q.pop();

        for (int i = 0; i < 26; i++) {
            Trie* _next = curr->next[i];
            if (!_next) continue;

            if (curr == root) _next->fail = root;
            else {
                Trie* t = curr->fail;
                while (t != root && !t->next[i])
                    t = t->fail;

                if (t->next[i]) t = t->next[i];
                _next->fail = t;
            }

            if (_next->fail->output) {
                _next->output = true;
                for (pii p : _next->fail->idx)
                    _next->idx[p.first] = 1;
            }
            q.push(_next);
        }
    }
}
vector<tiii> ahocorasik(string s, Trie* root, int dir, int k, int lc) {
    Trie* curr = root;
    vector<tiii> ret = vector<tiii>(w, {INT32_MAX, INT32_MAX, INT32_MAX});
    for (int i = 0; i < s.size(); i++) {
        int n = s[i] - 'A';

        while (curr != root && !curr->next[n])
            curr = curr->fail;
        
        if (curr->next[n])
            curr = curr->next[n];
        
        if (curr->output) {
            int curr_y = 0;
            int curr_x = 0;
            
            if (dir == 2) curr_y = k;
            else if (dir == 3) {
                if (lc == 0) curr_y = k;
                else curr_x = k;
            }
            else if (dir == 4) curr_x = k;
            else if (dir == 5) {
                if (lc == 0) {
                    curr_y = k;
                    curr_x = c - 1;
                }
                else curr_x = k;
            }
            else if (dir == 6) {
                curr_y = k;
                curr_x = c - 1;
            }
            else if (dir == 7) {
                if (lc == 0) {
                    curr_y = k;
                    curr_x = c - 1;
                }
                else {
                    curr_y = l - 1;
                    curr_x = k;
                }
            }
            else if (dir == 0) {
                curr_y = l - 1;
                curr_x = k;
            }
            else if (dir == 1) {
                if (lc == 0) curr_y = k;
                else {
                    curr_y = l - 1;
                    curr_x = k;
                }
            }
            
            curr_y += dy[dir] * i;
            curr_x += dx[dir] * i;
            for (pii p : curr->idx) {
                ret[p.first] = {curr_y - dy[dir] * (length[p.first] - 1) , curr_x - dx[dir] * (length[p.first] - 1), dir};
                // => dir 보고 k와 i값으로 단어 시작위치 계산해서
                // {y, x, dir} 순으로 튜플 저장할것
            }
        }
    }
    return ret;
}
void l_chk(int k, int flag, Trie* root) {
    string s = "";
    int dir = -1;
    if (flag == 0) {
        s += puzzle[k];
        dir = 2;
    }   // C 방향
    else if (flag == 1) {
        for (int i = 0; i < min(l - k, c); i++) s += puzzle[k + i][i];
        dir = 3;
    }   // D 방향
    else if (flag == 2) {
        for (int i = 0; i < min(l - k, c); i++) s += puzzle[k + i][c - 1 - i];
        dir = 5;
    }   // F 방향
    else if (flag == 3) {
        for (int i = c - 1; i >= 0; i--) s += puzzle[k][i];
        dir = 6;
    }   // G 방향
    else if (flag == 4) {
        for (int i = 0; i <= min(k, c - 1); i++) s += puzzle[k - i][c - 1 - i];
        dir = 7;
    }   // H 방향
    else if (flag == 5) {
        for (int i = 0; i <= min(k, c - 1); i++) s += puzzle[k - i][i];
        dir = 1;
    }   // B 방향

    vector<tiii> result = ahocorasik(s, root, dir, k, 0);
    for (int i = 0; i < w; i++) 
        ans[i] = min(ans[i], result[i]);
}
void c_chk(int k, int flag, Trie* root) {
    string s = "";
    int dir = -1;
    if (flag == 0) {
        for (int i = 0; i < min(l, c - k); i++) s += puzzle[i][k + i];
        dir = 3;
    }   // D 방향
    else if (flag == 1) {
        for (int i = 0; i < l; i++) s += puzzle[i][k];
        dir = 4;
    }   // E 방향
    else if (flag == 2) {
        for (int i = 0; i <= min(k, l - 1); i++) s += puzzle[i][k - i];
        dir = 5;
    }   // F 방향
    else if (flag == 3) {
        for (int i = 0; i <= min(k, l - 1); i++) s += puzzle[l - 1 - i][k - i];
        dir = 7;
    }   // H 방향
    else if (flag == 4) {
        for (int i = l - 1; i >= 0; i--) s += puzzle[i][k];
        dir = 0;
    }   // A 방향
    else if (flag == 5) {
        for (int i = 0; i <= min(c - 1 - k, l - 1); i++) s += puzzle[l - 1 - i][k + i];
        dir = 1;
    }   // B 방향

    vector<tiii> result = ahocorasik(s, root, dir, k, 1);
    for (int i = 0; i < w; i++) 
        ans[i] = min(ans[i], result[i]);
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    cin >> l >> c >> w;

    puzzle = vector<string>(l);
    length = vector<int>(w);
    ans = vector<tiii>(w, {INT32_MAX, INT32_MAX, INT32_MAX});
    Trie* root = new Trie();

    for (int i = 0; i < l; i++) 
        cin >> puzzle[i];

    for (int i = 0; i < w; i++) {
        string s;
        cin >> s;
        length[i] = s.size();
        root->insert(s.c_str(), i);
    }
    makeFailure(root);

    for (int i = 0; i < l; i++)
        for (int j = 0; j < 6; j++) l_chk(i, j, root);
    
    for (int i = 0; i < c; i++)
        for (int j = 0; j < 6; j++) c_chk(i, j, root);
        
    // 8방향으로 아호코라식 사용하여 체크하기 구현
    // 체크 끝나면 차례로 y x dir 순으로 출력, 이때 dir은 (char)(dir+'A')로 출력시키기
    for (int i = 0; i < w; i++)
        cout << get<0>(ans[i]) << ' ' << get<1>(ans[i]) << ' ' << (char)(get<2>(ans[i]) + 'A') << '\n';

    return 0;
}