#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

class Node {
    int v;
    Node* left;
    Node* right;

    public:
    Node(int v) {
        this->v = v;
        this->left = NULL;
        this->right = NULL;
    }
    
    friend class Tree;
};
class Tree {
    public:
    Node* root;
    vector<Node*> nodeList;
    Tree() {
        root = NULL;
    }
    void printPreOrder(Node* node) {
        cout << node->v << ' ';
        if (node->left != NULL)
            this->printPreOrder(node->left);
        if (node->right != NULL)
            this->printPreOrder(node->right);
    }
    void addLeft(int parent, int child) {
        Node* p_node = this->findNode(parent);
        p_node->left = new Node(child);
        this->nodeList.push_back(p_node->left);
    }
    void addRight(int parent, int child) {
        Node* p_node = this->findNode(parent);
        p_node->right = new Node(child);
        this->nodeList.push_back(p_node->right);
    }
    Node* findNode(int v) {
        for (Node* n : this->nodeList) 
            if (n->v == v) 
                return n;
    }
};

Tree tree;
void makeTree(vector<int> in, vector<int> post) {
    int root = post[post.size() - 1];
    if (tree.root == NULL) {
        tree.root = new Node(root);
        tree.nodeList.push_back(tree.root);
    }
    
    int idx = find(in.begin(), in.end(), root) - in.begin();
    vector<int> i_left = vector<int>(in.begin(), in.begin() + idx);
    vector<int> i_right = vector<int>(in.begin() + idx + 1, in.end());

    vector<int> p_left = vector<int>(post.begin(), post.begin() + i_left.size());
    vector<int> p_right = vector<int>(post.begin() + i_left.size(), post.end() - 1);

    int left = p_left[p_left.size() - 1];
    int right = p_right.size() > 0 ? p_right[p_right.size() - 1] : 0;

    if (left && right) {
        tree.addLeft(root, left);
        tree.addRight(root, right);
    }   
    else if (right == 0) {
        if (left < root)
            tree.addLeft(root, left);
        else   
            tree.addRight(root, left);
    }

    if (i_left.size() > 1)
        makeTree(i_left, p_left);
    if (i_right.size() > 1)
        makeTree(i_right, p_right);
}

int main() {
    int n;
    cin >> n;
    vector<int> inOrder = vector<int>(n);
    vector<int> postOrder = vector<int>(n);
    for (int i = 0; i < n; i++)
        cin >> inOrder[i];
    for (int i = 0; i < n; i++)
        cin >> postOrder[i];

    makeTree(inOrder, postOrder);
    tree.printPreOrder(tree.root);

    return 0;
}