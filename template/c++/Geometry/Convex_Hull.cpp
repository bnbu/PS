#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
using namespace std;

typedef long long ll;

struct Point {
    int x, y, p, q;
    Point(int x, int y) {
        this->x = x;
        this->y = y;
        p = 0;
        q = 1;
    }
    bool operator<(const Point& p1) {
        if (1LL * q * p1.p != 1LL * p * p1.q) return 1LL * q * p1.p < 1LL * p * p1.q;
        if (y != p1.y) return y < p1.y;
        return x < p1.x;
    }
};
int n;
vector<Point> arr;
stack<Point> st;

ll ccw (Point p1, Point p2, Point p3) {
    return ((ll)p1.x * p2.y + p2.x * p3.y + p3.x * p1.y) 
                - ((ll)p1.y * p2.x + p2.y * p3.x + p3.y * p1.x);
}
void scan() {
    st.push(arr[0]);
    st.push(arr[1]);
    
    for (int i = 2; i < n; i++) {
        while (st.size() >= 2) {
            Point first = st.top(); st.pop();
            Point second = st.top();
            
            if (ccw(first, second, arr[i]) < 0) {
                st.push(first);
                break;
            }
        }
        st.push(arr[i]);
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n;
    arr = vector<Point>();
    st = stack<Point>();

    for (int i = 0; i < n; i++) {
        int x, y;
        cin >> x >> y;
        arr.push_back(Point(x, y));
    }

    sort(arr.begin(), arr.end());
    for (int i = 1; i < n; i++) {
        arr[i].p = arr[i].x - arr[0].x;
        arr[i].q = arr[i].y - arr[0].y;
    }
    sort(arr.begin() + 1, arr.end());
    
    scan();

    return 0;
}

// 점 하나를 선택 (보통 x 혹은 y 좌표가 가장 작은 점을 선택) => 이렇게 해야 가장 바깥쪽부터 돌아갈 수 있다
// 점 두개를 먼저 잇고 모든 점이 한쪽 방향에만 위치하도록
// 선을 이어나가게끔 선택해나가면 된다. (보통 반시계방향으로 돌아간다)

// 먼저 기준점으로부터 반시계방향이 되도록 정점들의 순서를 정렬
// 그냥 왼쪽부터 차례로 정렬시키면 된다 (X좌표가 큰 순서부터 내림차순 정렬)

// 먼저 이은 직선에 대해 다음 좌표가 대략적으로 좌측인지, 우측인지를 판단
// 이는 CCW를 통해 판단이 가능

// 반시계 방향으로 컨벡스헐을 이루려면
// 먼저 이은 직선에 대해 다음에 이을 좌표가 오른쪽에 존재해서는 안된다
// 오른쪽에 있다는건 더 바깥에 위치하는 점이 있다는 의미가 되기 때문

// 스택에 가장 마지막 두 점을 직선으로 이었을 때, 다음 정점이 나타나는 위치를 계산한다.
// 왼쪽에 점이 나타난다면, 이를 스택에 넣어서 컨벡스헐을 이루는 것이라 판단
// 오른쪽에 점이 나타난다면, 기존에 이었던 정점 중 가장 마지막 것을 내보내고
// 오른쪽에 나타난 점을 새로 스택에 포함시켜 진행 

// 단, 간혹 볼록다각형이 아닌 직선의 형태가 되는 경우도 있다 (CCW값 0)
