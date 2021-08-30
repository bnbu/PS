import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int d;
		int w;
		public Point(int d, int w) {
			this.d = d;
			this.w = w;
		}
	}
	public static int[] depth;
	public static int[][] parent;
	public static long[][] length;
	public static boolean[] visit;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		
		depth = new int[n + 1];
		parent = new int[n + 1][21];
		length = new long[n + 1][21];
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[u].add(new Point(v, w));
			adj[v].add(new Point(u, w));
		}
		dfs(1, 0);
		makeSparseTable(n);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()),
				y = Integer.parseInt(st.nextToken());
			
			sb.append(getLength(x, y) + "\n");
		}
		System.out.print(sb);
	}
	public static void dfs(int v, int d) {
		visit[v] = true;
		depth[v] = d;
		for (int i = 0; i < adj[v].size(); i++) {
			Point next = adj[v].get(i);
			if (!visit[next.d]) {
				parent[next.d][0] = v;
				length[next.d][0] = next.w;
				dfs(next.d, d + 1);
			}
		}
	}
	public static void makeSparseTable(int n) {
		for (int j = 1; j <= 20; j++) 
			for (int i = 1; i <= n; i++) {
				length[i][j] = length[i][j - 1] + length[parent[i][j - 1]][j - 1];
				parent[i][j] = parent[parent[i][j - 1]][j - 1];
			}
	}
	public static long getLength(int x, int y) {
		long ret = 0;
		int a, b;
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
				ret += length[b][i];
				b = parent[b][i];
			}
		}
		if (a == b)
			return ret;
		
		for (int i = 20; i >= 0; i--) {
			if (parent[a][i] != parent[b][i]) {
				ret += length[a][i] + length[b][i];
				a = parent[a][i];
				b = parent[b][i];
			}
		}
		ret += length[a][0] + length[b][0];
		return ret;
	}
}
// 2021-01-16 00:22
// LCA의 간단 응용1
// 희소배열 생성시, 부모에 대한 배열뿐 아니라, 거리를 저장한 희소배열을 생성
// parnet[v][i] => v로부터 2^i번째 위의 부모 인 것처럼
// length[v][i] => v로부터 2^i번째까지의 거리
// 이를 사용하여 어느 두 점의 LCA를 구하는 과정에서 거리도 같은 방식으로 구하면 된다.
