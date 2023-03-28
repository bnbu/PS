#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
using namespace std;
vector<int> chk;
struct Trie {
    map<int, Trie*> next;
    bool output;

    Trie() {
        output = false;
        next.clear();
    }
    void insert(const char* s) {
        if (!*s) output = true;
        else {
            if (next.find(*s - 'a') == next.end()) next[*s - 'a'] = new Trie();
            next[*s - 'a']->insert(s + 1);
        }
    }
    void find(const char* s, int idx, bool type) {
        if (output) chk[idx]++;
        if (!*s) return;
        if (next.find(*s - 'a') == next.end()) return;
        if (type) next[*s - 'a']->find(s + 1, idx + 1, type);
        else next[*s - 'a']->find(s + 1, idx - 1, type);
    }
};