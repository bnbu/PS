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
void makeFailureFunction(Trie* root) {
    queue<Trie*> q;
    root->fail = root;
    q.push(root);

    while (!q.empty()) {
        Trie* curr = q.front(); q.pop();
        for (pair<char, Trie*> p : curr->next) {
            Trie* _next = p.second;

            if (curr == root) _next->fail = root;
            else {
                Trie* temp = curr->fail;

                while (temp != root) {
                    bool chk = false;
                    for (pair<char, Trie*> p2 : temp->next)
                        if (p2.first == p.first) {
                            chk = true;
                            break;
                        }
                    if (chk) break;
                    else temp = temp->fail;
                }
                for (pair<char, Trie*> p2 : temp->next)
                    if (p2.first == p.first) {
                        temp = p2.second;
                        break;
                    }
                _next->fail = temp;
            }
            if (_next->fail->output) {
                _next->output = true;
                _next->length = max(_next->length, _next->fail->length);
            }
            q.push(_next);
        }
    }
}
int ahocorasik(string s, Trie* root) {
    vector<bool> b = vector<bool>(n, true);
    Trie* curr = root;
    for (int i = 0; i < s.length(); i++) {
        while (curr != root) {
            bool chk = false;
            for (pair<char, Trie*> p : curr->next) {
                if (p.first == s[i]) {
                    chk = true;
                    break;
                }
            }
            if (chk) break;
            else curr = curr->fail;
        }
        for (pair<char, Trie*> p : curr->next)
            if (p.first == s[i]) {
                curr = p.second;
                break;
            }
        if (curr->output)
            fill(b.begin() + i - curr->length + 1, b.begin() + i + 1, false);
    }
    int ret = 0;
    for (bool _b : b) ret += _b;
    return ret;
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    string t;
    Trie* root = new Trie();
    cin >> n >> t >> m;
    for (int i = 0; i < m; i++) {
        string s;
        cin >> s;
        root->insert(s.c_str(), s.length());
    }  

    makeFailureFunction(root);
    cout << ahocorasik(t, root);
    
    return 0;
}
// 2021-12-26 해결
// 기존처럼 알파벳 소문자 26문자를 사전에 모두 미리 생성해두면 메모리 초과가 발생
// => Trie에서 next를 vector로 둬서, 존재하는 문자만 자식으로 추가시키는 방법으로 메모리를 줄였다.

// 일단 자식이 사전에 모두 존재하지 않게 되었기 때문에
// 실패함수와 Aho-Corasik을 할때 약간 바뀌어야 하는 점이 있다

// 실패함수에서
// curr가 root가 아닐때
// 적절한 위치 temp를 찾는데 기존에는
// while (temp != root && !temp->next[i]) 였다면

// 이제는 존재하는 자식 노드에만 체크가 가능하고 또, 인덱스별로 매칭이 되는게 아니라
// 그냥 벡터에 추가되었기 때문에

// while (temp != root) {
//     bool chk = false;
//     for (pair<char, Trie*> p2 : temp->next)
//         if (p2.first == p.first) {
//             chk = true;
//             break;
//         }
//     if (chk) break;
//     else temp = temp->fail;
// }

// for (pair<char, Trie*> p2 : temp->next)
//     if (p2.first == p.first) {
//         temp = p2.second;
//         break;
//     }

// 위와 같이, temp != root를 먼저 검사
// 이후 현재 노드의 다음 자식노드와 같은 문자인 자식노드를 갖는지 확인해야한다.
// 위와같이 적절한 위치를 찾고 다음 노드가 존재하는 위치라면 그 노드로 바꾸는 작업을 통해
// 이전과 같은 작업을 할 수 있다.

// 아호코라식 에서도 마찬가지.
// while (curr != root) {
//     bool chk = false;
//     for (pair<char, Trie*> p : curr->next) {
//         if (p.first == s[i]) {
//             chk = true;
//             break;
//         }
//     }
//     if (chk) break;
//     else curr = curr->fail;
// }
// for (pair<char, Trie*> p : curr->next)
//     if (p.first == s[i]) {
//         curr = p.second;
//         break;
//     }

// 실패함수때와 비슷하다.

// 또는 map<char, Trie*> 로 해봐도 될거같다.