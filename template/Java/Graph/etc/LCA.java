import java.util.*;
import java.io.*;
public class LCA {
	public static int n, m;
	public static int[] depth;
	public static int[] parent;
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			n = Integer.parseInt(br.readLine());
			parent = new int[n + 1];
			adj = new ArrayList[n + 1];
			depth = new int[n + 1];
			visit = new boolean[n + 1];
			for (int i = 1; i <= n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 0; i < n - 1; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken()), 
					v = Integer.parseInt(st.nextToken());

				adj[u].add(v);
				adj[v].add(u);
			}
			dfs(1, 0);

			for (int i = 1; i <= n; i++)
				System.out.print(depth[i] + " ");
			System.out.println();
			
			for (int i = 1; i <= n; i++)
				System.out.print(parent[i] + " ");
			System.out.println();
				
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()), 
				b = Integer.parseInt(st.nextToken());

			sb.append(lca(a, b) + "\n");
		}
		System.out.println(sb);
	}
	public static void dfs(int v, int d) {
		visit[v] = true;
		depth[v] = d;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (visit[next])
				continue;
			
			parent[next] = v;
			dfs(next, d + 1);
		}
	}
	public static int lca (int x, int y) {
		int a, b;
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
		// a와 b의 깊이가 같을때까지 탐색
		
		if (a == b) return a;
		
		while (a != b) {
			a = parent[a];
			b = parent[b];
		}

		return a;
	}
}