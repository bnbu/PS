#include <iostream>
#include <algorithm>
using namespace std;
#define NEXT_MAX 26 // 알파벳 26개를 의미
class Trie {
public:
    Trie* next[NEXT_MAX]; // 트라이의 노드마다 최대 포인터 개수
    bool output; // 해당 노드에서 완성되는 문자열이 있는지


    Trie() {
        output = false; // 출력, 자식노드 는 없다고 체크
        for (int i = 0; i < NEXT_MAX; i++) next[i] = NULL;
    }
    ~Trie() {
        for (int i = 0; i < 26; i++)
            if (next[i]) delete next[i];
    }
    
    void insert(const char* key) {
        // string s를 현재 노드부터 하나씩 삽입
        if (*key == '\0') output = true;    // 문자열의 끝
        else {
            int curr = *key - 'a';
            if (next[curr] == NULL) next[curr] = new Trie();
            next[curr]->insert(key + 1);
        }   // 아닐 경우, 다음 자식 노드에 이어서 문자열을 삽입
    }
    bool find(const char* str) {
        // if (*str == '\0') {
        //     if (output) return true;
        //     else return false;
        // }   // 문자열 탐색을 이어 나가다 끝에 와서 NULL까지 온 경우
        //     // 그리고 이 위치에서의 output이 있으면 true

        if (*str == '\0') return output;
        // 위에거보단 이게 더 확실하긴 하다.

        int curr = *str - 'a';
        if (next[curr] == NULL) return false;   // 다음으로 가야할 노드가 없으면 false
        return next[curr]->find(str + 1);       // 다음으로 가야할 노드로 탐색
    }
};
int main() {
    Trie* root = new Trie();
    int n, m;
    cin >> n >> m;
    while (n--) {
        string s;
        cin >> s;
        root->insert(s.c_str());
    }
    while (m--) {
        string s;
        cin >> s;
        cout << root->find(s.c_str()) << '\n';
    }

    return 0;
}

// 문자열의 집합을 트리로 나타낸 자료구조
// 빈 문자열을 의미하는 root부터 시작하여,
// 각 노드는 알파벳 **(또는 가능한 문자의 범위) 개수만큼의 자식 노드를 가진다.
// 자식노드는 초기에는 nullptr 이지만,
// insert 하는 문자열을 통해
// 해당 노드로의 간선이 생기고
// 해당 노드에서 만약 문자열이 끝난다('\0')면 이 노드에서 output이 있음을 알리면 된다.

// 삽입 -> 문자열 원소를 하나하나 추가하며 구성
// 탐색 -> 트라이를 탐색하며, 원하는 문자열 혹은 접두사를 검색

// 단점으로는 각 노드마다 가능한 문자 범위의 개수만큼 자식노드를 가지기 때문에
// 공간소모가 크긴 하다.