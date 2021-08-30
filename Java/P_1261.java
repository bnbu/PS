import java.util.*;
import java.io.*;
public class Main {
	public static int n, m;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	public static int[][] map;
	public static int[][] d;
	static class Point implements Comparable<Point>{ 
		int x;
		int y;
		int w;
		public Point(int x, int y, int w) {
			this.x = x;
			this.y = y;
			this.w = w;
		}
		public int compareTo(Point p) {
			return this.w - p.w;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		map = new int[m + 1][n + 1];
		d = new int[m + 1][n + 1];
		for (int i = 1; i < d.length; i++)
			Arrays.fill(d[i], Integer.MAX_VALUE);
		
		for (int i = 1; i < map.length; i++) {
			String s = br.readLine();
			for (int j = 1; j < map[i].length; j++)
				map[i][j] = s.charAt(j - 1) - '0';
		}
		
		dijkstra(1, 1);
		
		System.out.println(d[m][n]);
	}
	public static void dijkstra(int x, int y) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(x, y, 0));
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			
			if (d[curr.y][curr.x] > curr.w) {
				d[curr.y][curr.x] = curr.w;
				for (int i = 0; i < 4; i++) {
					int nx = curr.x + dx[i],
						ny = curr.y + dy[i];
					
					if (nx < 1 || ny < 1 || nx > n || ny > m)
						continue;
					
					int nw = d[curr.y][curr.x] + map[ny][nx];
					pq.add(new Point(nx, ny, nw));
				}
			}
		}
 	}
}
// 2020-10-13 21:58 해결
// 평소 bfs로 m*n 타일을 탐색하던 방식을, 다익스트라로 바꿔 사용
// 마찬가지로 상/하/좌/우 탐색을 하되, visit로 판단하는게 아닌
// 다익스트라 방식으로 거리의 최솟값을 갱신하는지 마는지로 판단한다.
