import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[][] arr;
    static long[][] segTree;
    static void makeSegTree(int startY, int endY, int nodeY) {
        if (startY == endY) {
            makeSegTreeX(1, n, 1, nodeY, startY);
            return;
        }
        int mid = (startY + endY) / 2;
        makeSegTree(startY, mid, 2*nodeY);
        makeSegTree(mid + 1, endY, 2*nodeY + 1);;
        for (int i = 1; i < 4*n; i++)
            segTree[nodeY][i] = segTree[2*nodeY][i] + segTree[2*nodeY + 1][i];
    }
    static void makeSegTreeX(int startX, int endX, int nodeX, int nodeY, int y) {
        if (startX == endX) {
            segTree[nodeY][nodeX] = arr[y][startX];
            return;
        }
        int mid = (startX + endX) / 2;
        makeSegTreeX(startX, mid, 2*nodeX, nodeY, y);
        makeSegTreeX(mid + 1, endX, 2*nodeX + 1, nodeY, y);
        segTree[nodeY][nodeX] = segTree[nodeY][2*nodeX] + segTree[nodeY][2*nodeX + 1];
    }
    static void update(int startY, int endY, int nodeY, int y, int x, int k) {
        if (y < startY || y > endY) return;

        updateX(1, n, 1, nodeY, x, k);
        if (startY == endY) return;

        int mid = (startY + endY) / 2;
        update(startY, mid, nodeY * 2, y, x, k);
        update(mid + 1, endY, nodeY * 2 + 1, y, x, k);
    }
    static void updateX(int startX, int endX, int nodeX, int nodeY, int x, int k) {
        if (x < startX || x > endX) return;

        segTree[nodeY][nodeX] += k;
        if (startX == endX) return;

        int mid = (startX + endX) / 2;
        updateX(startX, mid, 2*nodeX, nodeY, x, k);
        updateX(mid + 1, endX, 2*nodeX + 1, nodeY, x, k);
    }
    static long get(int startY, int endY, int nodeY, int ys, int xs, int ye, int xe) {
        if (ye < startY || endY < ys) return 0;
        if (ys <= startY && endY <= ye) return getX(1, n, 1, nodeY, xs, xe);

        int mid = (startY + endY) / 2;
        return get(startY, mid, 2*nodeY, ys, xs, ye, xe) + get(mid + 1, endY, 2*nodeY + 1,ys, xs, ye, xe);
    }
    static long getX(int startX, int endX, int nodeX, int nodeY, int xs, int xe) {
        if (xe < startX || endX < xs) return 0;
        if (xs <= startX && endX <= xe) return segTree[nodeY][nodeX];

        int mid = (startX + endX) / 2;
        return getX(startX, mid, 2*nodeX, nodeY, xs, xe) + getX(mid + 1, endX, 2*nodeX + 1, nodeY, xs, xe);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        arr = new int[n + 1][n + 1];
        segTree = new long[4*n][4*n];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++)
                arr[i][j] = Integer.parseInt(st.nextToken());
        }
        makeSegTree(1, n, 1);

        while (m-- > 0) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());
            if (i == 1) {
                int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken()),
                    c = Integer.parseInt(st.nextToken()), d = Integer.parseInt(st.nextToken());
                sb.append(get(1, n, 1, a, b, c, d)).append("\n");
            }
            else {
                int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken()),
                    c = Integer.parseInt(st.nextToken());
                update(1, n, 1, a, b, c - arr[a][b]);
                arr[a][b] = c;
            }
        }
        System.out.print(sb);
    }
}
// 2차원 세그먼트 트리
// y축으로 먼저 세그먼트 트리를 구축
// y축의 각 노드에는 그 y구간에 해당하는 x축의 세그먼트 트리가 존재한다

// 즉 4x4 경우면
//                  [y:1, 4]
//      [y:1, 2]                [y:3, 4]
// [y:1, 1]     [y:2, 2]    [y:3, 3]    [y:4, 4]
// 이런식으로 분포되어 있고
// 각 노드는 다시 

//                  [x:1, 4]
//      [x:1, 2]                [x:3, 4]
// [x:1, 1]     [x:2, 2]    [x:3, 3]    [x:4, 4]
// 를 이루고 있다

// 업데이트, 구간합 과정은 y축에 대해 먼저 쿼리를 진행
// 이후 기존에 업데이트 혹은 값을 반환하는 위치에서 다시 x축에 대한 쿼리를 진행하는 식으로 구현했다