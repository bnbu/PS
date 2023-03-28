#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
using namespace std;
#define NEXT_MAX 26 // 알파벳 26개를 의미
class Trie {
public:
    Trie* next[NEXT_MAX]; // 트라이의 노드마다 최대 포인터 개수
    bool output; // 해당 노드에서 완성되는 문자열이 있는지

    Trie* fail; // 아호-코라식 에서 사용할, 다음으로의 노드가 존재하지 않을 경우 가는 노드

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
        if (*str == '\0') {
            if (output) return true;
            else return false;
        }   // 문자열 탐색을 이어 나가다 끝에 와서 NULL까지 온 경우
            // 그리고 이 위치에서의 output이 있으면 true

        int curr = *str - 'a';
        if (next[curr] == NULL) return false;   // 다음으로 가야할 노드가 없으면 false
        return next[curr]->find(str + 1);       // 다음으로 가야할 노드로 탐색
    }
};

void makeFailureFun(Trie* root) {
    // BFS를 통해, 트라이 노드를 방문하며 fail 함수 링크를 만들어둔다.
    queue<Trie*> q;
    root->fail = root;
    q.push(root);
    // root부터 시작
    
    while (!q.empty()) {
        Trie* curr = q.front(); q.pop();
        
        // 다음 노드들을 탐색
        for (int i = 0; i < 26; i++) {
            Trie* next = curr->next[i];
            if (!next) continue;

            if (curr == root)
                next->fail = root; // root의 fail은 root이다.
            else {
                Trie* dest = curr->fail;
                // fail을 참조할 가장 가까운 조상을 찾는 과정
                while (dest != root && !dest->next[i])
                    dest = dest->fail; 
                    // fail(px) = next(fail(p), x) 이다.
                    // fail함수는 반드시 자기보다 깊이가 작은 노드를 가리킴.
                
                if (dest->next[i]) dest = dest->next[i];
                // dest에서 다음 자식노드와 같은 문자로의 자식노드가 존재시 그곳을 fail 함수 값으로
                next->fail = dest;
            }

            // fail(x) = y일 때, output(y) ⊂ output(x) 이다.
            if (next->fail->output) next->output = true;
            q.push(next);
        }
    }
    // 실패함수 만드는 과정
}
bool ahoCorasik(string s, Trie* root) {
    Trie* curr = root;
    bool ret = false;
    for (int i = 0; i < s.size(); i++) {
        int n = s[i] - 'a';
        while (curr != root && !curr->next[n])
            curr = curr->fail;
            // 현재 노드에서 갈 수 없으면, fail을 이어서 따라간다.
        
        if (curr->next[n]) 
            curr = curr->next[n];
            // next가 존재하면, 이를 따라 이동
            // 단, root면 이게 false일수도 있다.
        
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
    makeFailureFun(root);
    cin >> m;
    while (m--) {
        string s;
        cin >> s;
        cout << (ahoCorasik(s, root) ? "YES" : "NO") << '\n';
    }
    return 0;
}
// 아호-코라식
// 일대다 매칭 알고리즘


// 트라이를 만든다.
// 트라이에 패턴의 원소를 모두 집어 넣는다.
// fail 함수를 만든다 (BFS를 통해 트라이 노드를 방문) (이 과정에서 current->go[i] 의 fail 함수를 결정한다)
// BFS 시작하기 전에, root 를 먼저 queue 에 넣는다.
// root->fail = root;
// BFS (큐가 빌때까지 반복)
// 큐에서 현재 노드를 가져온다 (current = Q.front())
// input 에 대해 각각 반복을 돌린다 (a-z)
// next 가 없다면 continue
// current == root 이면, next->fail = root; 이다. (당연하다)
// current != root 이면, (dest=current->fail, dest 가 root 이거나, go[i] 가 있을때까지 반복한다)
// dest = current->fail;
// while (current != root && !dest->go[i]) dest = dest->fail; (실패함수를 계속 찾음)
// if (dest->go[i]) dest = dest->go[i]; (dest->go[i] 가 있으면, 그곳이 실패함수의 목적지)
// next->fail = dest; (current->go[i] 의 fail 함수를 목적지로 지정한다)
// next->fail->output == true 이면, next->output = true 이다.
// 각 문자열을 받아서 문제를 푼다.
// current = root, 트라이의 root에서 시작한다
// 문자열의 문자를 하나씩 읽으며 처리한다
// (while)루트가 아니고 다음으로 갈수 없다면, 실패함수로 이동한다.
// go 함수가 존재하면 이동한다.
// 현재 노드에 output 이 있다면 (finish), 찾은 것이다