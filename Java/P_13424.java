import java.util.*;
import java.io.*;
public class Main {
	public static class Point implements Comparable<Point> {
		int v;
		int w;
		public Point(int v, int w) {
			this.v = v;
			this.w = w;
		}
		public int compareTo(Point p) {
			return this.w - p.w;
		}
	}
	public static int[][] dist;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken()),
				m = Integer.parseInt(st.nextToken());
			
			adj = new ArrayList[n + 1];
			for (int i = 1; i <= n; i++)
				adj[i] = new ArrayList<>();
			
			while (m-- > 0) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken()),
					c = Integer.parseInt(st.nextToken());
				
				adj[a].add(new Point(b, c));
				adj[b].add(new Point(a, c));
			}
			
			int k = Integer.parseInt(br.readLine());
			dist = new int[k][n + 1];
			for (int i = 0; i < k; i++)
				Arrays.fill(dist[i], Integer.MAX_VALUE);
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < k; i++) {
				int num = Integer.parseInt(st.nextToken());
				dijkstra(num, i);
			}
			
			int min = Integer.MAX_VALUE;
			int ans = 0;
			for (int i = 1; i <= n; i++) {
				int sum = 0;
				for (int j = 0; j < k; j++) 
					sum += dist[j][i];
				
				if (min > sum) {
					min = sum;
					ans = i;
				}
			}
			sb.append(ans + "\n");
		}
		System.out.print(sb);
	}
	public static void dijkstra(int start, int k) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(start, 0));
		dist[k][start] = 0;
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			for (Point next : adj[curr.v]) {
				if (dist[k][next.v] > dist[k][curr.v] + next.w) {
					dist[k][next.v] = dist[k][curr.v] + next.w;
					pq.add(new Point(next.v, dist[k][next.v]));
				}
			}
		}
	}
}
// 2021-01-29
