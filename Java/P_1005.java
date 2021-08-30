import java.util.*;
import java.io.*;
public class Main {
	public static class Point implements Comparable<Point> {
		int v;
		int d;
		int t;
		int lv;
		public Point(int v, int d) {
			this.v = v;
			this.d = d;
			t = -1;
			lv = -1;
		}
		public int compareTo(Point p) {
			return this.t - p.t;
		}
	}
	public static int[] ans;
	public static int[] indegree;
	public static Point[] p;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken()),
				k = Integer.parseInt(st.nextToken());
			
			ans = new int[n + 1];
			Arrays.fill(ans, -1);
			indegree = new int[n + 1];
			p = new Point[n + 1];
			adj = new ArrayList[n + 1];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= n; i++) {
				adj[i] = new ArrayList<>();
				p[i] = new Point(i, Integer.parseInt(st.nextToken()));
			}
			
			while (k-- > 0) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken());
				
				adj[a].add(b);
				indegree[b]++;
			}
			
			topologicalSort(n);
						
			int d = Integer.parseInt(br.readLine());
			
			sb.append(ans[d] + "\n");
		}
		System.out.println(sb);
	}
	public static void topologicalSort(int v) {
		Queue<Integer> q = new LinkedList<>();

		for (int i = 1; i <= v; i++)
			if (indegree[i] == 0) {
				q.add(i);
				ans[i] = p[i].d;
			}
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (int next : adj[curr]) {
				ans[next] = Math.max(ans[next], ans[curr] + p[next].d);
				if (--indegree[next] == 0) {		
					q.add(next);
				}
			}
		}
	}
}
// 2021-02-17 00:04
// 위상정렬 자체를 이용하는게 아닌
// 위상정렬 과정에서의 DP를 하는거였다.
