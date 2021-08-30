import java.util.*;
import java.io.*;
public class Main {
	public static ArrayList<Point> arr[];
	public static int[][] d;
	static class Point implements Comparable<Point> {
		int v;
		int c;
		int w;
		public Point(int v, int c, int w) {
			this.v = v;
			this.c = c;
			this.w = w;
		}
		public int compareTo(Point p) {
			return this.w - p.w;
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		while (T-- > 0) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken()),
				m = Integer.parseInt(st.nextToken()),
				k = Integer.parseInt(st.nextToken());
			
			d = new int[n + 1][m + 1];
			for (int i = 1; i < d.length; i++)
				Arrays.fill(d[i], Integer.MAX_VALUE);
			arr = new ArrayList[n + 1];
			for (int i = 1; i < arr.length; i++)
				arr[i] = new ArrayList<>();
			
			for (int i = 0; i < k; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken()),
					v = Integer.parseInt(st.nextToken()),
					c = Integer.parseInt(st.nextToken()),
					d = Integer.parseInt(st.nextToken());
				
				arr[u].add(new Point(v, c, d));
			}
			dijkstra(1, m);
			
			int result = Integer.MAX_VALUE;
			for (int i = 1; i < d[n].length; i++) 
				result = Math.min(result, d[n][i]);
			
			sb.append(result == Integer.MAX_VALUE ? "Poor KCM\n" : (result + "\n"));
		}
		System.out.println(sb);
	}
	public static void dijkstra(int start, int m) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(start, 0, 0));
		d[1][0] = 0;
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			
			if (curr.c > m || d[curr.v][curr.c] < curr.w)
				continue;
			
			for (int i = 0; i < arr[curr.v].size(); i++) {
				Point next = arr[curr.v].get(i);
				if (curr.c + next.c <= m && d[next.v][next.c + curr.c] > curr.w + next.w) {
					d[next.v][next.c + curr.c] = curr.w + next.w;
					pq.add(new Point(next.v, curr.c + next.c, curr.w + next.w));
				}
			}
		}
	}
}

// 2020-10-14 23:56 해결은 아닌거 같다
// 최단거리의 다익스트라를 실행하되,
// 현재까지의 비용에 따른 최단거리를 달리 저장해야 하는 문제.
// 이 문제를 기점으로, 다익스트라를 할 때, 출발점의 최단거리 0 -> 이후 반복문에서 최단거리 갱신
// 방식으로 바꿔야겠다.
// 기존에는 출발점 Point 큐에 넣고, 반복문 내에서 최단거리 갱신을 했는데. 이게 더 확실한 방법인 듯

// 먼저 문제에 대해 얘기해보자면,
// 최단거리에 따른 다익스트라를 하는데,
// 현재까지의 비용이 최대 비용을 초과하느냐 마느냐도 주의해야한다.
// (curr.c > m || d[curr.v][curr.c] < curr.w)
// => 즉, 현까지의 비용이 초과하거나, 또는 현재 정점과 지금까지의 비용에 해당하는 최단거리를 갱신하지 못할 경우 패스
// 만약 둘중 하나라도 해당된다면,
// (curr.c + next.c <= m && d[next.v][next.c + curr.c] > curr.w + next.w))
// => 현재 비용 + 다음 정점까지 비용이 최대 비용 이내이고,
// 다음 정점, 다음 정점까지의 비용에 해당하는 기존의 최단거리가
// 지금의 정점을 거쳐 가는 거리로 갱신이 된다면,
// 값을 갱신하고 그 다음 정점을 큐에 넣음으로써, 다음 정점으로의 탐색을 이어나가게 한다.
// 이를 반복하면, 마지막 도시에 도착했을 때,
// 비용에 따른 최단거리들이 있을것이고,
// 이중 최솟값을 출력하거나 모두 INF, 즉 도달할 수 없다면 Poor KCM을 출력하면 되는 것이다.
