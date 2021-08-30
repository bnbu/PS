import java.util.*;
import java.io.*;
public class Main {
	public static int[] depth;
	public static int[][] parent, time;
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		
		depth = new int[n + 1];
		parent = new int[n + 1][21];
		time = new int[n + 1][21];
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
		int[] arr = new int[m];
		for (int i = 0; i < m; i++) 
			arr[i] = Integer.parseInt(br.readLine());
		
		int ans = 0;
		for (int i = 0; i < m - 1; i++) 
			ans += getTime(arr[i], arr[i + 1]);
		
		System.out.println(ans);
	}
	public static void dfs(int v, int d) {
		visit[v] = true;
		depth[v] = d;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (!visit[next]) {
				parent[next][0] = v;
				time[next][0] = 1;
				dfs(next, d + 1);
			}
		}
	}
	public static void makeSparseTable(int n) {
		for (int j = 1; j < 21; j++) {
			for (int i = 1; i <= n; i++) {
				time[i][j] = time[i][j - 1] + time[parent[i][j - 1]][j - 1];
				parent[i][j] = parent[parent[i][j - 1]][j - 1];
			}
		}
	}
	public static int getTime(int x, int y) {
		int a, b, ret = 0;
		if (depth[x] > depth[y]) {
			a = y;
			b = x;
		}
		else {
			a = x;
			b = y;
		}
		
		for (int i = 20; i >= 0; i--) {
			if (depth[b] - depth[a] >= 1 << i) {
				ret += time[b][i];
				b = parent[b][i];
			}
		}
		
		if (a == b)
			return ret;
		
		for (int i = 20; i >= 0; i--) {
			if (parent[a][i] != parent[b][i]) {
				ret += time[a][i] + time[b][i];
				a = parent[a][i];
				b = parent[b][i];
			}
		}
		ret += time[a][0] + time[b][0];
		return ret;
	}
}
// 2021-01-16 18:11
// LCA 거리문제 2
