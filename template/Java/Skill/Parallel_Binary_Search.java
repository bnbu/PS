package D0612;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int s, d, w;
    public Edge(int s, int d, int w) {
        this.s = s;
        this.d = d;
        this.w = w;
    }
}
class Query {
    int u, v, s, e, ansC, ansV;
    public Query(int u, int v, int s, int e) {
        this.u = u;
        this.v = v;
        this.s = s;
        this.e = e;
        ansC = ansV = -1;
    }
}
public class Main {
    static int n, m;
    static int[] parent;
    static int[] size;
    static Edge[] edges;
    static void init() {
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

    }
    public static void union(int a, int b) {
        int x = find(a);
        int y = find(b);
        if (x != y) {
            parent[y] = x;
            size[x] += size[y];
        }
    }
    public static int find(int a) {
        if (a == parent[a])
            return a;
        else
            return parent[a] = find(parent[a]);
    }
    static int lowerBound(int[] arr, int target) {
        int begin = 0;
        int end = arr.length;
        while(begin < end) {
            int mid = (begin + end) / 2;
            if(arr[mid] >= target) end = mid;
            else begin = mid + 1;
        }
        return end;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        parent = new int[n + 1];
        size = new int[n + 1];
        edges = new Edge[m];

        int[] compress = new int[m + 1];
        compress[0] = -1;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()),
                d = Integer.parseInt(st.nextToken()),
                w = Integer.parseInt(st.nextToken());
            compress[i] = w;
            edges[i] = new Edge(s, d, w);
        }
        Arrays.sort(compress);
        for (int i = 0; i < m; i++) edges[i].w = lowerBound(compress, edges[i].w);

        Arrays.sort(edges, (e1, e2) -> {
            return Integer.compare(e1.w, e2.w);
        });

        int q = Integer.parseInt(br.readLine());
        Query[] queries = new Query[q];
        Queue<Query>[] queues = new Queue[compress.length];
        for (int i = 0; i < compress.length; i++) queues[i] = new LinkedList<>();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            queries[i] = new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0, compress.length);
        }

        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < queries.length; i++) {
                if (queries[i].s + 1 < queries[i].e) {
                    flag = true;
                    queues[(queries[i].s + queries[i].e) / 2].add(queries[i]);
                }
            }
            // 쿼리의 start, end 값을 보고 처리해야할 순서인 mid값에 포함시킴

            init();
            int queueIdx = 0;
            for (int i = 0; i < edges.length; i++) {
                if (find(edges[i].s) != find(edges[i].d))
                    union(edges[i].s, edges[i].d);

                while (queueIdx <= edges[i].w) {
                    Queue<Query> currQ = queues[queueIdx];
                    while (!currQ.isEmpty()) {
                        Query curr = currQ.poll();

                        if (find(curr.u) == find(curr.v)) {
                            curr.ansC = edges[i].w;
                            curr.ansV = size[find(curr.v)];
                            curr.e = queueIdx;
                        }
                        else curr.s = queueIdx;
                    }
                    queueIdx++;
                }
            }
            // 각 간선을 한개씩 보며 크루스칼 알고리즘을 진행
            // 이때, 지금의 간선과 mid값이 일치하는 쿼리가 있다면 그 쿼리를 처리해주고
            // 처리 결과에 따라 s, e 값을 갱신
            // 이를 모든 쿼리에 대해 진행 => 병렬로 이분탐색이 진행됨
        }

        for (Query query : queries)
            if (query.ansC == -1) sb.append(-1).append("\n");
            else sb.append(compress[query.ansC]).append(" ").append(query.ansV).append("\n");
        System.out.print(sb);
    }
}

// 병렬 이분탐색
// 어떤 문제(쿼리)에 대해 값이 특정 시점을 기준으로 T/F로 엇갈리게 되는 결정문제가 될 때
// 그 시점 중 최초가 되는 시점을 찾고자 할 때 사용할 수 있다.

// 각 쿼리의 s, e를 [0, length]로 두고
// 시점을 판별하는 알고리즘을 그대로 진행하되
// 각 쿼리의 mid가 되는 시점에서 각자 이분탐색을 갱신을 해준다

// 이 문제에서는 시점을 판별하는 알고리즘이 크루스칼 알고리즘이 되었다