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
	public static long[][] costs;
	public static boolean[] visit;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		
		depth = new int[n + 1];
		parent = new int[n + 1][21];
		costs = new long[n + 1][21];
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
			int q = Integer.parseInt(st.nextToken());
			if (q == 1) {
				int x = Integer.parseInt(st.nextToken()),
					y = Integer.parseInt(st.nextToken());
				sb.append(func1(x, y) + "\n");
			}
			else {
				int x = Integer.parseInt(st.nextToken()),
					y = Integer.parseInt(st.nextToken()),
					k = Integer.parseInt(st.nextToken());
				sb.append(func2(x, y, k) + "\n");
			}
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
				costs[next.d][0] = next.w;
				dfs(next.d, d + 1);
			}
		}
	}
	public static void makeSparseTable(int n) {
		for (int j = 1; j <= 20; j++) {
			for (int i = 1; i <= n; i++) {
				costs[i][j] = costs[i][j - 1] + costs[parent[i][j - 1]][j - 1];
				parent[i][j] = parent[parent[i][j - 1]][j - 1];
			}
		}
	}
	public static long func1(int x, int y) {
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
				ret += costs[b][i];
				b = parent[b][i];
			}
		}
		if (a == b)
			return ret;
		
		for (int i = 20; i >= 0; i--) {
			if (parent[a][i] != parent[b][i]) {
				ret += costs[a][i] + costs[b][i];
				a = parent[a][i];
				b = parent[b][i];
			}
		}
		ret += costs[a][0] + costs[b][0];
		return ret;
	}
	public static int func2(int x, int y, int k) {
		int ret = -1;
		int lcaNum = lca(x, y);
		int leftPathLength = depth[x] - depth[lcaNum];
		int rightPathLength = depth[y] - depth[lcaNum];
		
		k--;
		if (leftPathLength == k)
			ret = lcaNum;
		else if (leftPathLength > k) {
			int diff = k;
			for (int i = 20; i >= 0; i--) 
				if (diff >= 1 << i) {
					x = parent[x][i];
					diff -= 1 << i;
				}
			
			ret = x;
		}
		else {
			int diff = rightPathLength - (k - leftPathLength);
			for (int i = 20; i >= 0; i--)
				if (diff >= 1 << i) {
					y = parent[y][i];
					diff -= 1 << i;
				}
			ret = y;
		}
		return ret;
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
// 2021-01-14 21:04
// 트리와 쿼리2
// LCA를 활용해야하는 문제, 첫 플3 문제
// 쿼리문 1번 => LCA 경로에 있는 비용들의 합
// => 비용 역시 costs[v][i] : 2^i번째 위의 정점까지 도달시의 총 비용으로 희소배열 정의

// 쿼리문 2번 => x번 정점 -> y번 정점의 경로 중 k번째의 정점 번호
// LCA 정점 기준 k번째가 LCA를 넘어서 존재하는지, 이전에 존재하는지 먼저 확인
// LCA를 넘지 않으면, k번째이므로, 이동해야하는 경로는 k-1개
// 반대로 LCA를 넘어선다면, 
// 넘어서는 부분에서부터 역으로 올라와야 한다.
// 따라서 넘어서는 쪽의 끝부분으부터 이동해야 하는 경로는 
// rightPathLength - (k - 1 - leftPathLength)개.

