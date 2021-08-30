import java.util.*;
import java.io.*;
public class Main {
	static class Point implements Comparable<Point>{
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
	public static ArrayList<Point>[] adjList;
	public static PriorityQueue<Integer>[] dist;
	public static int[] d;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		
		adjList = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adjList[i] = new ArrayList<>();
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adjList[u].add(new Point(v, w));
		}
		
		d = new int[n + 1];
		dist = new PriorityQueue[n + 1];
		for (int i = 1; i <= n; i++) {
			dist[i] = new PriorityQueue<>(Collections.reverseOrder());
			d[i] = Integer.MAX_VALUE;
		}
		
		dijkstra(1, k);
		
		for (int i = 1; i <= n; i++) {
			if (dist[i].size() != k) {
				sb.append("-1\n");
			}
			else {
				sb.append(dist[i].peek() + "\n");
			}
		}
		System.out.println(sb);
	}
	
	public static void dijkstra(int start, int k) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(start, 0));
		d[start]  = 0;
		dist[start].add(0);
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			for (int i = 0; i < adjList[curr.v].size(); i++) {
				Point next = adjList[curr.v].get(i);
				if (dist[next.v].size() < k) {
					dist[next.v].add(curr.w + next.w);
					pq.add(new Point(next.v, curr.w + next.w));
				}
				else {
					if (dist[next.v].peek() > curr.w + next.w) {
						dist[next.v].poll();
						dist[next.v].add(curr.w + next.w);
						pq.add(new Point(next.v, curr.w + next.w));
					}
				}
			}
		}
	}
}
// 2020-12-18 23:42 해결
// K번째 최단경로..
// 기존의 최단거리를 배열에 저장하는게 아닌,
// 우선순위큐에 K개까지 저장한다.
// 이때 우선순위큐는 최대힙으로 구성시켜서, 바로 K번째 최단거리를 얻어올 수 있도록 준비.
// 우선순위큐에 K개 미만으로 있다면 우선순위큐에 삽입
// K개가 이미 있다면, K번째 최단거리와 현재의 갱신되는 거리를 비교하여 하나를 제거하고 다시 삽입한다.
