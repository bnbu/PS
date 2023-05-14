import java.util.*;
import java.io.*;
class Pair {
	int l, r;
}
public class Main {
	static int n, cnt;
	static int[] size, depth, head, chainParent;
	static long[] segTree, lazy;
	static Pair[] range;
	static ArrayList<Integer>[] adj;
	static void propagate(int start, int end, int node) {
		if (lazy[node] != 0) {
			if (start != end) {
				lazy[2*node] += lazy[node];
				lazy[2*node + 1] += lazy[node];
			}
			segTree[node] += lazy[node] * (end - start + 1);
			lazy[node] = 0;
		}
	}
	static void update(int start, int end, int node, int left, int right, int k) {
		propagate(start, end, node);
		if (right < start || end < left) return;
		if (left <= start && end <= right) {
			lazy[node] += k;
			propagate(start, end, node);
			return;
		}
		int mid = (start + end) / 2;
		update(start, mid, 2*node, left, right, k);
		update(mid + 1, end, 2*node + 1, left, right, k);
		segTree[node] = segTree[2*node] + segTree[2*node + 1];
	}
	static long get(int start, int end, int node, int left, int right) {
		propagate(start, end, node);
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) return segTree[node];
		int mid = (start + end) / 2;
		return get(start, mid, 2*node, left, right) + get(mid + 1, end, 2*node + 1, left, right);
	}
	
	static void getSize(int curr, int parent) {
		size[curr] = 1;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			getSize(next, curr);
			size[curr] += size[next];
		}
	}
	static void dfs(int curr, int parent, int d) {
		range[curr].l = ++cnt;
		depth[curr] = d;
		
		boolean isFirst = true;
		for (int next : adj[curr]) {
			if (next == parent) continue;
			if (isFirst) {
				head[next] = head[curr];
				chainParent[next] = chainParent[curr];
				dfs(next, curr, d);
				isFirst = false;
			}
			else {
				head[next] = next;
				chainParent[next] = curr;
				dfs(next, curr, d + 1);
			}
		}
	}
	
	// query
	static void queryUpdate(int u, int v) {
		while (head[u] != head[v]) {
			if (depth[u] > depth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			update(1, n, 1, range[head[v]].l, range[v].l, 1);
			v = chainParent[v];
		}
		update(1, n, 1, Math.min(range[u].l, range[v].l) + 1, Math.max(range[u].l, range[v].l), 1);
	}
	static long queryGet(int u, int v) {
		long ret = 0;
		
		while (head[u] != head[v]) {
			if (depth[u] > depth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			ret += get(1, n, 1, range[head[v]].l, range[v].l);
			v = chainParent[v];
		}
		
		ret += get(1, n, 1, Math.min(range[u].l, range[v].l) + 1, Math.max(range[u].l, range[v].l));
		return ret;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		
		segTree = new long[4*n];
		lazy = new long[4*n];
		size = new int[n + 1];
		depth = new int[n + 1];
		head = new int[n + 1];
		chainParent = new int[n + 1];
		range = new Pair[n + 1];
		adj = new ArrayList[n + 1];
		
		for (int i = 1; i <= n; i++) {
			range[i] = new Pair();
			adj[i] = new ArrayList<>();
		}
		
		for (int i = 1; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			adj[u].add(v);
			adj[v].add(u);
		}
		
		getSize(1, 0);
		for (int i = 1; i <= n; i++)
			Collections.sort(adj[i], (i1, i2) -> {
				return -Integer.compare(size[i1], size[i2]);
			});
		head[1] = 1;
		dfs(1, 0, 1);
		
		while (q-- > 0) {
			st = new StringTokenizer(br.readLine());
			String str = st.nextToken();
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			if (str.equals("P")) {
				queryUpdate(u, v);
			}
			else {
				sb.append(queryGet(u, v)).append("\n");
			}
		}
		System.out.print(sb);
	}
}
