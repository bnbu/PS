#include <iostream>
using namespace std;

class Node {
    public:
    int id;
    string name;
    int capacity;
    int price;

    int color; // 0 - red / 1 - black
    Node* left; 
    Node* right;
    Node* parent;

    Node(int id, string name, int capacity, int price) {
        updateNode(id, name, capacity, price);
        color = 0; // 새로 생성되는 노드는 반드시 red
        left = NULL;
        right = NULL;
        parent = NULL;
    }

    void updateNode(int id, string name, int capacity, int price) {
        this->id = id;
        this->name = name;
        this->capacity = capacity;
        this->price = price;
    }

    void setDiscount(int p) {
        double d = (double)100 - (double)p;
        d /= 100;
        d *= (double)this->price;
        this->price = (int)d;
    }
};

class RedBlackTree {
    private:
    Node* root;

    public:
    RedBlackTree() {
        this->root = NULL;
    }

    void insert(int id, string name, int capacity, int price) {
        Node* location = NULL; // 노드를 삽입할 위치
        Node* node = new Node(id, name, capacity, price); // 새로 삽입할 노드
        
        if (root == NULL) {
            root = node;
            root->color = 1;
            // 새로 삽입할 노드가 root가 되는 경우
            // root는 항상 black
            cout << 0 << '\n';
            return; 
        }
        else {
            Node* curr = root;
            int depth = 0;
            while (curr != NULL) {
                location = curr;
                if (curr->id == id) {
                    cout << depth << '\n';
                    return;
                }
                else if (curr->id < id) curr = curr->right;
                else if (curr->id > id) curr = curr->left;
                depth++;
            }
            // 적절한 삽입 위치 탐색
        }

        node->parent = location;
        if (location->id < id) location->right = node;
        else location->left = node;
        // 노드를 탐색한 위치에 삽입

        isDoubleRed(node, node->parent);
        // double red가 발생했는지 체크
        
        pair<Node*, int> p = find(id);
        cout << p.second << '\n';
        // 깊이 출력
    }
    void isDoubleRed(Node* node, Node* par) {
        // 자신과 부모의 색이 모두 red일 경우 double red
        if (node->color == 0 && par->color == 0) {
            Node* grandparent = par->parent;
            Node* uncle = NULL;
            if (grandparent->right) 
                uncle = grandparent->right == par ? grandparent->left : grandparent->right;
            else if (grandparent->left)
                uncle = grandparent->left == par ? grandparent->right : grandparent->left;
            
            if (uncle == NULL || uncle->color == 1) restructuring(node);
            else recoloring(node);
            // uncle이 black(외부노드도 black으로 처리) 일 경우 restructuring
            // uncle이 red일 경우는 recoloring
        }
    }
    void recoloring(Node* node) {
        Node* curr = node;
        Node* grandpar = curr->parent->parent;

        grandpar->color = grandpar->color == 1 ? 0 : 1;
        grandpar->left->color = grandpar->left->color == 1 ? 0 : 1;
        grandpar->right->color = grandpar->right->color == 1 ? 0 : 1;
        // 노드로부터 parent, grandparent, uncle 노드의 색을 반전시킴

        if (grandpar == root) {
            grandpar->color = 1;
            return;
        }
        // root일 경우는 black으로 처리 후 종료

        isDoubleRed(grandpar, grandpar->parent);
        // root가 아니라면, 그 위치에서 다시 double red 발생을 체크
    }
    void restructuring(Node* node) {
        Node* par = node->parent;
        Node* grandpar = par->parent;

        // 자신, 부모, 조상 노드를 각각 x, y, z라고 했을때
        if (grandpar->id < par->id) {
            if (par->id < node->id) {
                rotateLeft(par);
                par->color = 1;
                node->color = 0;
                grandpar->color = 0;
                // z
                //  \
                //   y
                //    \
                //     x
                // 순으로 위치했을 경우

                //    y
                //   / \
                //  z   x
            }
            else {
                rotateRight(node);
                rotateLeft(node);
                node->color = 1;
                par->color = 0;
                grandpar->color = 0;
                //  z
                //   \
                //    y
                //   /
                // x
                // 순으로 위치했을 경우 

                //    x
                //   / \
                //  z   y
            }
        }
        else {
            if (par->id > node->id) {
                rotateRight(par);
                par->color = 1;
                node->color = 0;
                grandpar->color = 0;
                //         z
                //       /
                //     y
                //   /
                // x
                // 순으로 위치했을 경우

                //     y
                //    / \
                //  x    z
            }
            else {
                rotateLeft(node);
                rotateRight(node);
                node->color = 1;
                par->color = 0;
                grandpar->color = 0;
                //     z
                //    /
                //   y
                //   \
                //    x
                // 순으로 위치했을 경우

                //    x
                //   / \
                //  y   z
            }
        }
    }
    void rotateLeft(Node* node) {
        Node* par = node->parent;
        Node* grandpar = par->parent;
        Node* child = node->left;

        if (grandpar == NULL) {
            root = node;
            node->parent = NULL;
        }
        else {
            node->parent = par->parent;
            if (grandpar->left == par) grandpar->left = node;
            else grandpar->right = node;
        }
        node->left = par;
        par->parent = node;
        par->right = child;
        if (child)
            child->parent = par;
        
        // y를 rotateleft 한다고 하면,
        
        //    x
        //   / \
        //  t1  y
        //     / \
        //    t2 t3
        // 을

        //     y
        //    / \
        //   x  t3
        //  / \
        // t1 t2
        // 로 rotateleft

    }
    void rotateRight(Node* node) {
        Node* par = node->parent;
        Node* grandpar = par->parent;
        Node* child = node->right;

        if (grandpar == NULL) {
            root = node;
            node->parent = NULL;
        }
        else {
            node->parent = par->parent;
            if (grandpar->left == par) grandpar->left = node;
            else grandpar->right = node;
        }
        node->right = par;
        par->parent = node;
        par->left = child;
        if (child)
            child->parent = par;

        // y를 rotateright 한다고 하면,
        
        //     x
        //    / \
        //   y  t3
        //  / \
        // t1 t2
        // 을

        //     y
        //    / \
        //   t1  x
        //      / \
        //     t2 t3
        // 로
    }   

    pair<Node*, int> find(int id) {
        // 순서대로 <노드, 깊이>
        Node* current = root; // root부터 시작
        int depth = 0;
        while (current != NULL) {
            if (current->id == id) return {current, depth};
            else if (current->id < id) current = current->right;
            else if (current->id > id) current = current->left;
            depth++;
            // 찾고자 하는 id가 현재 노드 id보다 크면 오른쪽 자식노드로, 작으면 왼쪽 자식노드로
            // 같은 값 발견하면 return
        }
        return {NULL, -1}; // NULL(외부노드)에 도달했다는 것은, 값이 없다는 뜻
    }
    void update(int id, string name, int capacity, int price) {
        pair<Node*, int> p = find(id);
        if (p.first == NULL) cout << "NULL\n";
        else {
            p.first->updateNode(id, name, capacity, price);
            cout << p.second << '\n';
        }
    }

    void discount(int x, int y, int p) {
        preOrder(root, x, y, p);
    }
    void preOrder(Node* node, int x, int y, int p) {
        if (node != NULL) {
            if (node->id <= y && node->id >= x) node->setDiscount(p);
            preOrder(node->left, x, y, p);
            preOrder(node->right, x, y, p);
        }
    }
    // 트리를 순회하면서, ID가 범위 내에 포함될 경우 discount 적용
};

int main() {
    ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    RedBlackTree rbt;
    int t;
    cin >> t;
    while (t--) {
        int id, capacity, price, x, y, p;
        string s, name;
        cin >> s;
        
        if (s == "I") {
            cin >> id >> name >> capacity >> price;
            rbt.insert(id, name, capacity, price);
        }
        else if (s == "F") {
            cin >> id;
            pair<Node*, int> p = rbt.find(id);
            if (p.first == NULL) cout << "NULL\n";
            else cout << p.second << ' ' << p.first->name << ' ' << p.first->capacity << ' ' << p.first->price << '\n';
        }
        else if (s == "R") {
            cin >> id >> name >> capacity >> price;
            rbt.update(id, name, capacity, price);
        }
        else if (s == "D") {
            cin >> x >> y >> p;
            rbt.discount(x, y, p);
        }
    }
    return 0;
}