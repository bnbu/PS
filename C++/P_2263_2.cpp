#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

int n;
vector<int> in;
vector<int> post;

void printPreOrder(int in_s, int in_e, int post_s, int post_e) {
    int root = post[post_e];
    cout << root << ' ';

    int idx = find(in.begin() + in_s, in.begin() + in_e, root) - in.begin();
    /*
        for (int i = in_s; i <= in_e; i++)
        if (in[i] == root)
            idx = i;
    */
   // 위에 find 함수로 찾는게 더 빠름;
    
    int i_left_s = in_s;
    int i_left_e = idx - 1;
    int i_right_s = idx + 1;
    int i_right_e = in_e;

    int p_left_s = post_s;
    int p_left_e = post_s + idx - in_s - 1;
    int p_right_s = post_s + idx - in_s;
    int p_right_e = post_e - 1;
    
    if (i_left_e - i_left_s + 1 > 0) 
        printPreOrder(i_left_s, i_left_e, p_left_s, p_left_e);
    if (i_right_e - i_right_s + 1 > 0) 
        printPreOrder(i_right_s, i_right_e, p_right_s, p_right_e);
}

void printPreOrder2(vector<int> in, vector<int> post) {
    int root = post[post.size() - 1];
    cout << root << ' ';
    
    int idx = find(in.begin(), in.end(), root) - in.begin();
    vector<int> i_left = vector<int>(in.begin(), in.begin() + idx);
    vector<int> i_right = vector<int>(in.begin() + idx + 1, in.end());

    vector<int> p_left = vector<int>(post.begin(), post.begin() + i_left.size());
    vector<int> p_right = vector<int>(post.begin() + i_left.size(), post.end() - 1);

    if (i_left.size() > 0)
        printPreOrder2(i_left, p_left);
    if (i_right.size() > 0)
        printPreOrder2(i_right, p_right);
} 
// 기존 벡터떄문에 메모리 초과를 받은 코드
// 이를 토대로 벡터 -> 인덱스로 변환한 것이 위의 코드
// 이때 보면 알겠지만,
// p_left_e는 post_s + "i_left의 크기" 이다.
// 크기는 i_left_e - i_left_s 이므로, idx - in_s - 1이 될것.
// 따라서 이것만 고쳐주면 됬었다.

int main() {
    cin >> n;
    in = vector<int>(n);
    post = vector<int>(n);
    for (int i = 0; i < n; i++)
        cin >> in[i];
    for (int i = 0; i < n; i++)
        cin >> post[i];
    
    printPreOrder(0, n - 1, 0, n - 1);
    return 0;
}