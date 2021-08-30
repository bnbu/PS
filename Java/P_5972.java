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
	public static int[] dist;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		dist = new int[n + 1];
		adj = new ArrayList[n + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[u].add(new Point(v, w));
			adj[v].add(new Point(u, w));
		}
		
		dijkstra(1);
		System.out.println(dist[n]);
	}
	public static void dijkstra(int v) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(v, 0));
		dist[v] = 0;
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			for (Point next : adj[curr.v]) 
				if (dist[next.v] > dist[curr.v] + next.w) {
					dist[next.v] = dist[curr.v] + next.w;
					pq.add(new Point(next.v, dist[next.v]));
				}
		}
	}
}
