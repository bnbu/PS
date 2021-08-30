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
	public static int[][] parent, max, min;
	public static boolean[] visit;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		
		depth = new int[n + 1];
		parent = new int[n + 1][21];
		max = new int[n + 1][21];
		min = new int[n + 1][21];
		for (int i = 1; i <= n; i++) {
			Arrays.fill(max[i], 0);
			Arrays.fill(min[i], 1000001);
		}
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 0; i < n - 1; i++ ) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[a].add(new Point(b, w));
			adj[b].add(new Point(a, w));
		}
		dfs(1, 0);
		makeSparseTable(n);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()),
				y = Integer.parseInt(st.nextToken());
			
			int[] ans = getLength(x, y);
			sb.append(ans[0] + " " + ans[1] + "\n");
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
				max[next.d][0] = min[next.d][0] = next.w; 
				dfs(next.d, d + 1);
			}
		}
	}
	public static void makeSparseTable(int n) {
		for (int j = 1; j <= 20; j++) 
			for (int i = 1; i <= n; i++) {
				max[i][j] = Math.max(max[i][j - 1], max[parent[i][j - 1]][j - 1]);
				min[i][j] = Math.min(min[i][j - 1], min[parent[i][j - 1]][j - 1]);
				parent[i][j] = parent[parent[i][j - 1]][j - 1];
			}
	}
	public static int[] getLength(int x, int y) {
		int[] res = {1000001, 0};
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
			if (depth[b] - depth[a] >= (1 << i)) {
				res[0] = Math.min(res[0], min[b][i]);
				res[1] = Math.max(res[1], max[b][i]);
				b = parent[b][i];
			}
		}
	
		if (a == b)
			return res;

		for (int i = 20; i >= 0; i--) {
			if (parent[a][i] != parent[b][i]) {
				res[0] = Math.min(res[0], Math.min(min[a][i], min[b][i]));
				res[1] = Math.max(res[1], Math.max(max[a][i], max[b][i]));
				a = parent[a][i];
				b = parent[b][i];
			}
		}
		res[0] = Math.min(res[0], Math.min(min[a][0], min[b][0]));
		res[1] = Math.max(res[1], Math.max(max[a][0], max[b][0]));
		return res;
	}
}
// 2021-01-14 00:48 해결
// 입력받은 정점 및 가중치 정보로 트리를 구성
// 1을 루트로 생각하고 (어차피 LCA 구하는 과정중에 루트가 어디인지는 상관 없음)
// DFS를 1회 진행하며 다음을 계산해놓는다.
// 해당 정점으로부터 2^0번째의 부모
// 해당 정점으로부터 2^0번째의 부모까지 갔을떄의 최대/최소거리
// 및 깊이.

// parent[v][i] => v로부터 2^i번째 부모 
// max[v][i] / min[v][i] => v로부터 2^i번째까지 갔을 때 최대/최소 거리

// 일단 DFS를 진행하면서 parent[v][0]과 max[v][0], min[v][0]을 계산했다.
// 이제 이를 토대로 parent, max, min 희소배열을 완성시켜야 한다.

// parent[i][j] = parent[parent[i][j - 1]][j - 1]로 구하는게 기존 LCA이며
// 이렇게 i정점에서 2^j 위로의 부모를 구함과 동시에
// i정점에서 2^j 위로의 부모까지의 최대/최소 거리도 같이 계산해야 한다.
// max[i][j] = max[i][j - 1], max[parent[i][j - 1]][j - 1] 중 더 큰거
// min[i][j] = min[i][j - 1], min[parent[i][j - 1]][j - 1] 중 더 작은거

// max[i][j - 1]은 이전 지점으로부터 지금까지의 거리 (즉 2^(j-1) 까지 중 최대/최소)
// max[parent[i][j - 1]][j - 1] 은 정점 i의 위로 2^j번째의 부모까지 거리 중 최대 최소.
// ** parent[i][j - 1]로부터 2^(j-1) 이므로.
// 그림으로 표현하면 어느 한 경유지로부터 지금까지 왔던 경로 중 거리와 앞으로 갈 경로의 거리를 비교하는 것.

// 이렇게 희소배열을 완성했으면
// LCA를 찾는 과정을 찾는 김에 최대 최소도 같이 찾는 느낌으로 찾으면 된다.
