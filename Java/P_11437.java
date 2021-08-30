import java.util.*;
import java.io.*;
public class Main {
	public static int[] depth, parent;
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		depth = new int[n + 1];
		parent = new int[n + 1];
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
			adj[b].add(a);
		}
		dfs(1, 0);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()),
				y = Integer.parseInt(st.nextToken());
			
			sb.append(lca(x, y) + "\n");
		}
		
		System.out.print(sb);
	}
	public static void dfs(int v, int d) {
		visit[v] = true;
		depth[v] = d;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (!visit[next]) {
				parent[next] = v;
				dfs(next, d + 1);
			}
		}
	}
	public static int lca(int x, int y) {
		int a, b; // b가 더 깊은 깊이의 노드
		if (depth[x] > depth[y]) {
			a = y;
			b = x;
		}
		else {
			a = x;
			b = y;
		}
		
		while (depth[a] != depth[b])
			b = parent[b];
		
		if (a == b)
			return a;
		
		while (a != b) {
			a = parent[a];
			b = parent[b];
		}
		
		return a;
	}
}
// LCA 기초 2
// 마찬가지로 O(N)이다.
