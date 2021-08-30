import java.util.*;
import java.io.*;

public class Main {
	public static class Point implements Comparable<Point> {
		int v;
		long w;
		int k;

		public Point(int v, long w, int k) {
			this.v = v;
			this.w = w;
			this.k = k;
		}

		public int compareTo(Point p) {
			if (this.w > p.w)
				return 1;
			else if (this.w == p.w)
				return 0;
			else
				return -1;
		}
	}

	public static long[][] dist;
	public static ArrayList<Point>[] adj;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		
		dist = new long[n + 1][k + 1];
		for (int i = 1; i < n + 1; i++) 
			for (int j = 0; j < k + 1; j++)
				dist[i][j] = Long.MAX_VALUE;
		adj = new ArrayList[n + 1];
		for (int i = 1; i < n + 1; i++)
			adj[i] = new ArrayList<>();
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[u].add(new Point(v, w, 0));
			adj[v].add(new Point(u, w, 0));
		}
		
		dijkstra(1, k);
		
		long ans = Long.MAX_VALUE;
		for (int i = 0; i < k + 1; i++)
			ans = Math.min(ans, dist[n][i]);
		
		System.out.println(ans);
	}

	public static void dijkstra(int v, int k) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(v, 0, 0));
		dist[v][0] = 0;
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			if (curr.w > dist[curr.v][curr.k])
				continue;
			
			for (int i = 0; i < adj[curr.v].size(); i++) {
				Point next = adj[curr.v].get(i);
				
				if (curr.k < k && curr.w < dist[next.v][curr.k + 1]) {
					dist[next.v][curr.k + 1] = curr.w;
					pq.add(new Point(next.v, curr.w, curr.k + 1));
				}
				
				if (curr.w + next.w < dist[next.v][curr.k]) {
					dist[next.v][curr.k] = curr.w + next.w;
					pq.add(new Point(next.v, curr.w + next.w, curr.k));
				}
			}
		}
	}
}

// 2020-12-22 해결
// K번째 최단거리처럼 거리를 단순 배열이 아닌 2차원 배열로 선언하여
// 각 정점당, 도로포장을 0~k번 했을때의 최단거리로 따로 저장하는 dp기법을 사용
// 즉 다익스트라 + dp 응용
