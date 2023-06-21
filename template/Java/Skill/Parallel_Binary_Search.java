import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    int x, y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
class Query {
    int sx, sy, ex, ey, s, e, ans;
    public Query(int sy, int sx, int ey, int ex, int s, int e) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.s = s;
        this.e = e;
        this.ans = -1;
    }
}
public class Main {
    static final int[] dx = {1, -1, 0, 0},
                       dy = {0, 0, 1, -1};
    static int m, n;
    static int[] parent;
    static int[][] map;
    static Queue<Pair>[] coordinates;
    static int find(int v) {
        if (v == parent[v]) return v;
        return parent[v] = find(parent[v]);
    }
    static int findCoordinate(int x, int y) {
        return find((y - 1) * n + x);
    }
    static void union(int x1, int y1, int x2, int y2) {
        int u = findCoordinate(x1, y1),
            v = findCoordinate(x2, y2);

        if (u == v) return;
        parent[v] = u;
    }
    static void init() {
        for (int i = 1; i <= m * n; i++) parent[i] = i;
    }
    static void checkMap(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nextX = x + dx[i],
                nextY = y + dy[i];

            if (nextX <= 0 || nextX > n || nextY <= 0 || nextY > m) continue;;
            if (map[y][x] < map[nextY][nextX]) continue;
            union(x, y, nextX, nextY);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        map = new int[m + 1][n + 1];
        parent = new int[m * n + 1];

        HashSet<Integer> hs = new HashSet<>();
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                hs.add(map[i][j]);
            }
        }

        // ** Arrays.binarySearch 메서드로 좌표압축
        int[] compress = hs.stream().mapToInt(i -> i).toArray();
        Arrays.sort(compress);

        ArrayList<Pair>[] coordinates = new ArrayList[compress.length];
        Queue<Query>[] queues = new Queue[compress.length];
        for (int i = 0; i < compress.length; i++) {
            coordinates[i] = new ArrayList<>();
            queues[i] = new LinkedList<>();
        }

        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                map[i][j] = Arrays.binarySearch(compress, map[i][j]);
                coordinates[map[i][j]].add(new Pair(j, i));
            }

        Query[] queries = new Query[q];
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            queries[i] = new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0, compress.length);
        }

        // PBS 
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Query query : queries) {
                if (query.s < query.e) {
                    flag = true;
                    queues[(query.s + query.e) / 2].add(query);
                }
            }
            // 처리해야할 쿼리의 start, end 값으로부터 쿼리를 처리해야할 mid값 계산

            // 해결해야할 문제 (단, 1회 시행으로 모든 쿼리를 병렬적으로 진행할 수 있는 문제만 가능)
            init();
            for (int i = 0; i < compress.length; i++) {
                // 특정 시점에서의 진행
                for (Pair c : coordinates[i]) checkMap(c.x, c.y);

                // mid값이 그 시점인 쿼리에 대해 처리
                while (!queues[i].isEmpty()) {
                    Query curr = queues[i].poll();

                    if (map[curr.sy][curr.sx] <= i && findCoordinate(curr.sx, curr.sy) == findCoordinate(curr.ex, curr.ey)) {
                        curr.ans = i;
                        curr.e = i;
                        // 조건이 충족할 경우, 정답 갱신 및 이보다 더 최적의 값이 있는지 탐색
                    }
                    else
                        curr.s = i + 1;
                        // 조건이 충족하지 않을 경우, 최적의 값이 있는지 탐색
                }
            }
        }

        for (Query query : queries) sb.append(compress[query.ans]).append("\n");
        System.out.print(sb);
    }
}
// 병렬 이분탐색
// 어떤 문제(쿼리)에 대해 값이 특정 시점을 기준으로 T/F로 엇갈리게 되는 결정문제가 될 때
// 그 시점 중 최초가 되는 시점을 찾고자 할 때 사용할 수 있다.

// 각 쿼리의 s, e를 [0, (가능한 값의 범위)length]로 두고
// 시점을 판별하는 알고리즘을 그대로 진행하되
// 각 쿼리의 mid가 되는 시점에서 각자 이분탐색을 갱신을 해준다