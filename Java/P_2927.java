import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair {
	int dest, idx;
	public Pair(int dest, int idx) {
		this.dest = dest;
		this.idx = idx;
	}
}
class Query {
	String ans;
	int type, idx, a, b;
	// type
	// 0 : bridge a b
	// 1 : penguins a b -> ans 딱히 필요 x
	// 2 : excursion a b
	public Query(int type, int idx, int a, int b) {
		this.ans = null;
		this.type = type;
		this.idx = idx;
		this.a = a;
		this.b = b;
	}
}
public class Main {
	static int n, cnt;
	static int[] penguinSegTree;
	static int[] size, depth, head, chainParent, hld;
	static int[] penguins, parent;
	static boolean[] visit;
	static ArrayList<Integer>[] adj;
	static ArrayList<Query> bridges;
	static Query[] queries;
	
	// 정점 v에 대해 세그먼트 트리에서의 노드 번호
	static int findNode(int start, int end, int node, int idx) {
		if (start == end) return node;
		int mid = (start + end) / 2;
		if (idx <= mid) return findNode(start, mid, 2*node, idx);
		return findNode(mid + 1, end, 2*node + 1, idx);
	}
	
	// 펭귄 수 합 세그먼트트리
	static int makePenguinSegTree(int start, int end, int node) {
		if (start == end) return penguinSegTree[node];
		int mid = (start + end) / 2;
		return penguinSegTree[node] = makePenguinSegTree(start, mid, 2*node) + makePenguinSegTree(mid + 1, end, 2*node + 1);
	}
	static void penguinUpdate(int start, int end, int node, int idx, int k) {
		if (idx < start || end < idx) return;
		if (start == end) {
			penguinSegTree[node] = k;
			return;
		}
		int mid = (start + end) / 2;
		penguinUpdate(start, mid, 2*node, idx, k);
		penguinUpdate(mid + 1, end, 2*node + 1, idx, k);
		penguinSegTree[node] = penguinSegTree[2*node] + penguinSegTree[2*node + 1];
	}
	static int penguinGet(int start, int end, int node, int left, int right) {
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return penguinSegTree[node];
		int mid = (start + end) / 2;
		return penguinGet(start, mid, 2*node, left, right) + penguinGet(mid + 1, end, 2*node + 1, left, right);
	}
	
	// 임의의 두 정점 u, v가 같은 트리에 속하는지 판단을 위한 유니온파인드
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x == y) return;
		parent[x] = y;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return parent[a] = find(parent[a]);
	}
	
	// Heavy-Light Decomposition
	static void getSize(int curr, int parent) {
		size[curr] = 1;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			getSize(next, curr);
			size[curr] += size[next];
		}
	}
	static void dfs(int curr, int parent, int num) {
		hld[curr] = ++cnt;
		depth[curr] = num;
		
		boolean isFirst = true;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			if (isFirst) {
				head[next] = head[curr];
				chainParent[next] = chainParent[curr];
				dfs(next, curr, num);
				isFirst = false;
			}
			else {
				head[next] = next;
				chainParent[next] = curr;
				dfs(next, curr, num + 1);
			}
		}
	}
	
	// HLD 한거로 각 세그먼트 트리에 쿼리
	static int getPenguinPath(int u, int v) {
		int ret = 0;
		while (head[u] != head[v]) {
			if (depth[u] > depth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			ret += penguinGet(1, n, 1, hld[head[v]], hld[v]);
			v = chainParent[v];
		}
		ret += penguinGet(1, n, 1, Math.min(hld[u], hld[v]), Math.max(hld[u], hld[v]));
		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		
		penguinSegTree = new int[4*n];
		size = new int[n + 1];
		head = new int[n + 1];
		depth = new int[n + 1];
		chainParent = new int[n + 1];
		parent = new int[n + 1];
		penguins = new int[n + 1];
		hld = new int[n + 1];
		adj = new ArrayList[n + 1];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			penguins[i] = Integer.parseInt(st.nextToken());
			adj[i] = new ArrayList<>();
			parent[i] = i;
		}
		
		int q = Integer.parseInt(br.readLine());
		queries = new Query[q + 1];
		bridges = new ArrayList<>();
		
		for (int i = 1; i <= q; i++) {
			st = new StringTokenizer(br.readLine());
			String s = st.nextToken();
			int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
			queries[i] = new Query(-1, i, a, b);
			switch (s) {
				case "bridge":
					queries[i].type = 0;
					bridges.add(queries[i]);
					break;
				case "penguins":
					queries[i].type = 1;
					break;
				case "excursion":
					queries[i].type = 2;
						break;
			}
		}
		
		for (Query query : bridges) {
			if (find(query.a) != find(query.b)) {
				adj[query.a].add(query.b);
				adj[query.b].add(query.a);
				union(query.a, query.b);
				query.ans = "yes";
			}
			else query.ans = "no";
		}
		
		for (int i = 1; i <= n; i++) {
			if (size[i] == 0) getSize(i, -1);
			parent[i] = i;
		}
		
		for (int i = 1; i <= n; i++)
			Collections.sort(adj[i], (i1, i2) -> {
				return -Integer.compare(size[i1], size[i2]);
			});
		
		for (int i = 1; i <= n; i++)
			if (head[i] == 0) {
				head[i] = i;
				dfs(i, -1, 1);
			}
		
		for (int i = 1; i <= n; i++)
			penguinSegTree[findNode(1, n, 1, hld[i])] = penguins[i];
		makePenguinSegTree(1, n, 1);
		
		for (int i = 1; i <= q; i++) {
			switch (queries[i].type) {
				case 0:
					union(queries[i].a, queries[i].b);
					break;
				case 1:
					penguinUpdate(1, n, 1, hld[queries[i].a], queries[i].b);
					break;
				case 2:
					if (queries[i].a == queries[i].b || (find(queries[i].a) == find(queries[i].b))) {
						queries[i].ans = Integer.toString(getPenguinPath(queries[i].a, queries[i].b));
					}
					else queries[i].ans = "impossible";
					break;
			}
		}
		
		for (int i = 1; i <= q; i++)
			if (queries[i].type != 1) sb.append(queries[i].ans).append("\n");
		System.out.println(sb);
	}
}

/*
입력 받은 n으로 먼저 노드 n개짜리 그래프를 생성
입력 받은 펭귄 수로 세그먼트트리 구축
쿼리 입력받음 (bridge와 excursion, penguins 이렇게 두개의 큐를 둬서 따로 사용할 것)
오프라인 쿼리로 bridge를 먼저 수행, 입력받은 순서대로 진행하면서 같은 트리에 속하지 않는 두 정점간의 bridge만 실제 그래프에 간선을 포함시키며 동시에 yes, no를 먼저 계산
만든 그래프로 HLD를 진행한다 (서브트리 크기 구하고 HLD 번호 찾고 등등)
이후 쿼리를 순차적으로 진행
    0. bridge는 두 정점을 union 시켜버린다
    1. excursion은 먼저 UF로 같은 트리인지 검사 후 같은 트리이면(이거는 bridge 순으로 union 시키므로 보장이 됌) 펭귄 수 세그먼트 트리에서 쿼리를 통해 값을 출력할 것
    2. penguins 쿼리는 그냥 point update 쿼리 수행시킬 것
정답 출력
*/