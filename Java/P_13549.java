import java.util.*;
import java.io.*;
public class Main {
	public static class Point implements Comparable<Point> {
		int v;
		int t;
		public Point(int v, int t) {
			this.v = v;
			this.t = t;
		}
		public int compareTo(Point p) {
			return this.t - p.t;
		}
	}
	public static int[] dist = new int[200001];
	public static int n, m, t = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		dijkstra(n);
		System.out.println(dist[m]);
	}
	public static void dijkstra(int v) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		dist[v] = 0;
		pq.add(new Point(v, 0));
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			
			int next1 = curr.v + 1,
				next2 = curr.v - 1,
				next3 = curr.v * 2;
			
			if (next1 < 200001 && dist[next1] > dist[curr.v] + 1) {
				dist[next1] = dist[curr.v] + 1;
				pq.add(new Point(next1, dist[next1]));
			}
			if (next2 >= 0 && dist[next2] > dist[curr.v] + 1) {
				dist[next2] = dist[curr.v] + 1;
				pq.add(new Point(next2, dist[next2]));
			}
			if (next3 < 200001 && dist[next3] > dist[curr.v]) {
				dist[next3] = dist[curr.v];
				pq.add(new Point(next3, dist[next3]));
			}
		}
	}
}
// 2021-01-04 23:13 해결
// 걍 빡쳐서 다익스트라로 돌림
// 0-1 BFS를 할줄 알면 그거로 했는데
