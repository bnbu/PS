#include <iostream>
#include <algorithm>
#include <vector>
#include <map>
using namespace std;
int n, k;
struct Node {
    map<string, Node*> next;
    string s;
    bool isRoot;
    
    Node() {
        s = "";
        isRoot = false;
        next = map<string, Node*>();
    }
    void insert(vector<string> v, int currIdx) {
        if (v.size() == currIdx) return;
        else {
            if (!next[v[currIdx]]) next[v[currIdx]] = new Node();
            next[v[currIdx]]->s = v[currIdx];
            next[v[currIdx]]->insert(v, currIdx + 1);
        }
    }
    void printPreOrder(int depth) {
        if (!isRoot) cout << s << '\n';
        for (pair<string, Node*> p : next) {
            for (int i = 0; i < depth; i++) cout << "--";
            p.second->printPreOrder(depth + 1);
        }
    }
};
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n;
    Node* root = new Node();
    root->isRoot = true;
    while (n--) {
        cin >> k;
        vector<string> v;
        while (k--) {
            string s;
            cin >> s;
            v.push_back(s);
        }
        root->insert(v, 0);
    }
    root->printPreOrder(0);
    return 0;
}
// 2021-12-22 해결
// 흠.. 사실 트라이를 먼저 공부하고 이거를 봐서
// 어떻게 하란건지 고민좀 하긴 함

// 트라이가 사용 가능한 문자의 범위 개수만큼의 자식노드를 NULL로 가지고 있다가
// 그거에 해당하는 문자가 존재하면 그 자식노드를 생성시켜 이어가는 느낌이었는데

// 이거는 이제 각 노드가 문자가 아니라 문자열이라
// 그 부분을 맵으로 설정시켰고
// 또 자식노드들을 오름차순으로 순회해야 해서, unordered_map 대신 map을 사용함

// 그래서 각 노드는 일단 문자열-노드 로 구성된 맵을 보유하고
// 노드에 삽입 과정은 트라이와 동일하다.

// 입력이 최상층부터 최하층까지 이어지는 문자열들을 나열한 것 이므로
// 이 나열한 문자열들을 트라이에서의 문자열의 각 문자로 치환시킨 것으로 생각해서
// 트라이처럼 삽입을 만들면 해결.

// 그렇게 구성한 트리를 전위순회 (단, 자식 노드간 순서는 오름차순 -> map으로 자동 해결)