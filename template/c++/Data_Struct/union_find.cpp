#include <iostream>
#include <vector>
using namespace std;

vector<int> parent;

int find(int a) {
    if (a == parent[a])
        return a; // a와 a의 부모(상위개체)가 서로 같다면, a 자체가 최상위 개체.
    else 
        return parent[a] = find(parent[a]);
    // a와 a의 상위개체가 다르다면, b(a의 상위개체의 상위개체)를 다시 a의 상위개체로
	// 이러한 작업을 수행하면, a가 속하는 최상위개체의 바로 하위개체로 존재할 수 있다.
	// 이를 계속 반복시키면 초기 O(n)작업 한번으로
	// 이후의 find를 모두 O(1)로 가능.
}
void merge(int a, int b) {
    int x = find(a);
    int y = find(b);
    if (x != y)
        parent[y] = x;
    
    // 두 집합의 최상위개체가 서로 다르다면, union 시킨다.
    // b의 최상위 개체를 a의 최상위 개체의 하위 개체로 변경
}
// ** union은 c++ 예약어라 못쓰고 merge로 바꿔 사용함
int find(int n) {
    return parent[n] = parent[n] < 0 ? n : find(parent[n]);
}
void merge(int a, int b) {
    a = find(a);
    b = find(b);
    if (a != b) parent[a] = b;
}
// -> 이게 더 효율적일듯. //** parent를 모두 -1로 초기화

int main() {
    int n, m;
    cin >> n >> m;
    parent = vector<int>(n + 1);
    for (int i = 1; i <= n; i++)
        parent[i] = i;
    
    while (m--) {
        int k, a, b;
        cin >> k;
        if (k == 0) {
            cin >> a >> b;
            merge(a, b);
        }
        else {
            cin >> a >> b;
            if (find(a) == find(b))
                cout << "YES\n";
            else 
                cout << "NO\n";
        }

        for (int i = 1; i <= n; i++)
            cout << find(i) << ' ';
        cout << '\n';
    }

    return 0;
}
	/*
	7 10
	0 7 6
	1 2 3 4 5 7 7 
	0 5 7
	1 2 3 4 5 7 5 
	0 4 5
	1 2 3 4 4 7 5 
	0 3 4
	1 2 3 3 4 7 5 
	0 2 3
	1 2 2 3 4 7 5 
	0 1 2
	1 1 2 3 4 7 5 
	1 1 6
	YES
	1 1 1 1 1 1 1 => 최하위에 존재하던 6을 find하는 순간 
	parent[a] = find(parent[a])에 의해 모두 1의 직계 하위 개체로 되는 모습이다.
	*/