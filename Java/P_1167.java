import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int v;
		int w;
		public Point(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
	public static ArrayList<Point>[] adj;
	public static boolean[] visit;
	public static int[] dist;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int v = Integer.parseInt(st.nextToken());
		adj = new ArrayList[v + 1];
		visit = new boolean[v + 1];
		dist = new int[v + 1];
		for (int i = 1; i <= v; i++)
			adj[i] = new ArrayList<>();
		
		while (v-- > 0) {
			st = new StringTokenizer(br.readLine());
			int e = Integer.parseInt(st.nextToken());
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				if (s.equals("-1"))
					break;
				else {
					int u = Integer.parseInt(s),
						w = Integer.parseInt(st.nextToken());
					adj[e].add(new Point(u, w));
					adj[u].add(new Point(e, w));
				}
			}
		}
		
		dfs(1, 0);
		
		for (int i = 1; i < visit.length; i++)
			visit[i] = false;
		
		int start = 1;
		for (int i = 2; i < dist.length; i++) {
			if (dist[start] < dist[i])
				start = i;
		}
		dfs(start, 0);
		
		
		int ans = 0;
		for (int i = 1; i < dist.length; i++)
			ans = Math.max(ans, dist[i]);
		System.out.println(ans);
		
	}
	public static void dfs(int v, int distance) {
		visit[v] = true;
		for (int i = 0; i < adj[v].size(); i++) {
			Point next = adj[v].get(i);
			if (!visit[next.v]) {
				dfs(next.v, distance + next.w);
			}
		}
		dist[v] = distance;
	}
}
// 2020-12-23 02:57 해결
// 단순 dfs 2번으로
// 1회 dfs => 시작점에서 가장 거리가 먼 점을 2차시작점으로 하기 위해 탐색
// 2회 dfs => 여기부터 가장 먼 거리가 지름임
