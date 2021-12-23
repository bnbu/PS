#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
using namespace std;
#define NEXT_MAX 26
class Trie {
public:
    Trie* next[NEXT_MAX];
    bool output;

    Trie* fail;

    Trie() {
        output = false;
        for (int i = 0; i < NEXT_MAX; i++) next[i] = NULL;
    }
    
    void insert(const char* key) {
        if (*key == '\0') output = true;
        else {
            int curr = *key - 'a';
            if (next[curr] == NULL) next[curr] = new Trie();
            next[curr]->insert(key + 1);
        }
    }
    bool find(const char* str) {
        if (*str == '\0') {
            if (output) return true;
            else return false;
        }
        int curr = *str - 'a';
        if (next[curr] == NULL) return false;
        return next[curr]->find(str + 1);
    }
};

void makeFailureLink(Trie* root) {
    queue<Trie*> q;
    root->fail = root;
    q.push(root);
    
    while (!q.empty()) {
        Trie* curr = q.front(); q.pop();
        
        for (int i = 0; i < 26; i++) {
            Trie* next = curr->next[i];
            if (!next) continue;

            if (curr == root)
                next->fail = root;
            else {
                Trie* dest = curr->fail;
                while (dest != root && !dest->next[i])
                    dest = dest->fail; 
                
                if (dest->next[i]) dest = dest->next[i];
                next->fail = dest;
            }

            if (next->fail->output) next->output = true;
            q.push(next);
        }
    }
}
bool ahoCorasik(string s, Trie* root) {
    Trie* curr = root;
    bool ret = false;
    for (int i = 0; i < s.size(); i++) {
        int n = s[i] - 'a';
        while (curr != root && !curr->next[n])
            curr = curr->fail;
        
        if (curr->next[n]) 
            curr = curr->next[n];
        
        if (curr->output) {
            ret = true;
            break;
        }
    }
    return ret;
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int n, m;
    cin >> n;
    Trie* root = new Trie();
    while (n--) {
        string s;
        cin >> s;
        root->insert(s.c_str());
    }
    makeFailureLink(root);
    cin >> m;
    while (m--) {
        string s;
        cin >> s;
        cout << (ahoCorasik(s, root) ? "YES" : "NO") << '\n';
    }
    return 0;
}