#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
using namespace std;
int n, m;
struct Trie {
    Trie* fail;
    vector<pair<char, Trie*>> next;
    bool output;
    int length;

    Trie() {
        fail = NULL;
        output = false;
        length = 0;
    }
    void insert(const char* s, int length) {
        if (*s == '\0') {
            output = true;
            this->length = max(this->length, length);
        }
        else {
            for (pair<char, Trie*> p : next) 
                if (p.first == *s) return p.second->insert(s + 1, length);
            next.push_back({*s, new Trie()});
            next.back().second->insert(s + 1, length);
        }
    }
};