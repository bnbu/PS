import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int v;
		int w;
		Point(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
	public static ArrayList<Integer>[] adj;
	public static boolean[] visit;
	public static int i1, i2, ans = 0;
	public static boolean chk = false;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		i1 = Integer.parseInt(st.nextToken());
		i2 = Integer.parseInt(st.nextToken());
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		bfs(i1);
		
		if (chk)
			System.out.println(ans);
		else
			System.out.println(-1);
	}
	public static void bfs(int v) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(v, 0));
		visit[v] = true;
		while (!q.isEmpty()) {
			Point curr = q.poll();
			
			if (curr.v == i2) {
				ans = curr.w;
				chk = true;
			}
			
			for (int i = 0; i < adj[curr.v].size(); i++) {
				int next = adj[curr.v].get(i);
				if (!visit[next]) {
					q.add(new Point(next, curr.w + 1));
					visit[next] = true;
				}
			}
		}
	}
}
// 2021-01-04 19:41 해결
// 간단한 bfs문제.
