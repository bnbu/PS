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
		
		for (int i = 0; i < v - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u1 = Integer.parseInt(st.nextToken()),
				u2 = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[u1].add(new Point(u2, w));
			adj[u2].add(new Point(u1, w));
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
// 2020-12-23 03:03 해결
// 사실 이거랑 같은 문제를 풀었어서 그냥 그거 그대로 제출함
