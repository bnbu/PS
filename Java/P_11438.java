import java.util.*;
import java.io.*;
public class Main {
	public static int[] depth;
	public static int[][] parent;
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		depth = new int[n + 1];
		parent = new int[n + 1][21];
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
		makeSparseTable(n);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			sb.append(lca(a, b) + "\n");
		}
		System.out.print(sb);
	}
	public static void makeSparseTable(int n) {
		for (int j = 1; j <= 20; j++) 
			for (int i = 1; i <= n; i++)
				parent[i][j] = parent[parent[i][j - 1]][j - 1];
	}
	public static void dfs(int v, int d) {
		visit[v] = true;
		depth[v] = d;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (!visit[next]) {
				parent[next][0] = v; 
				dfs(next, d + 1);
			}
		}
	}
	public static int lca(int x, int y) {
		int a, b;
		if (depth[x] > depth[y]) {
			a = y;
			b = x;
		}
		else {
			a = x;
			b = y;
		}
		
		for (int i = 20; i >= 0; i--)
			if (depth[b] - depth[a] >= (1 << i))
				b = parent[b][i];
		
		if (a == b)
			return a;
		
		for (int i = 20; i >= 0; i--) {
			if (parent[a][i] != parent[b][i]) {
				a = parent[a][i];
				b = parent[b][i];
			}
		}
		return parent[a][0];
	}
}
// 2021-01-13
// LCA 개선버전
// 희소배열로 2^i번째의 부모를 미리 계산해둠으로써
// LCA를 구하는데 O(log N)으로 줄였다.
