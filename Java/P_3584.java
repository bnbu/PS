import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent, depth;
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			int n = Integer.parseInt(br.readLine());
			parent = new int[n + 1];
			depth = new int[n + 1];
			visit = new boolean[n + 1];
			adj = new ArrayList[n + 1];
			for (int i = 1; i <= n; i++)
				adj[i] = new ArrayList<>();
			
			for (int i = 0; i < n - 1; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken());
				
				adj[a].add(b);
				parent[b] = a;
			}
			
			int root = -1;
			for (int i = 1; i <= n; i++)
				if (parent[i] == 0)
					root = i;
			dfs(root, 0);
			
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()),
				y = Integer.parseInt(st.nextToken());
			sb.append(lca(x, y) + "\n");
		}
		System.out.println(sb);
	}
	public static void dfs(int v, int d) {
		visit[v] = true;
		depth[v] = d;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (!visit[next])
				dfs(next, d + 1);
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
// 2021-01-13 17:25
// LCA 기초 ( O(N) 시간대 )
