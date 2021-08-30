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
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		
		dfs(1, 0);
		makeSparseTable(n);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken()),
				u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			int lca1 = lca(r, u), 
				lca2 = lca(r, v),
				lca3 = lca(u, v),
				ans = -1;

			ans = depth[lca1] > depth[lca2] ? lca1 : lca2;
			ans = depth[ans] > depth[lca3] ? ans : lca3;
			sb.append(ans + "\n");
		}
		System.out.print(sb);
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
	public static void makeSparseTable(int n) {
		for (int j = 1; j <= 20; j++) 
			for (int i = 1; i <= n; i++) 
				parent[i][j] = parent[parent[i][j - 1]][j - 1];
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
			if (depth[b] - depth[a] >= 1 << i)
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
// 2021-01-16 18:49
// 루트가 r일때 u와 v의 LCA를 구하는 문제 (첫 플래2)
// 당연하지만, 매 r마다 희소배열을 재계산하여 구하면 시간초과가 난다.

// 루트를 아무거나(대충 1로) 잡고 트리를 그려보자.
// 여기서 r, u, v를 잡고 루트가 r일때 기준으로 LCA를 찾는다면,
// r,u / r,v / u,v 의 LCA중 가장 깊이가 깊은 LCA가 
// 루트가 r일때 u,v의 LCA가 된다.
