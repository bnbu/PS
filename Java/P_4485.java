import java.util.*;
import java.io.*;
public class Main {
	public static int[][] map;
	public static int[][] d;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	public static int n;
	public static int cnt = 0;
	static class Point implements Comparable<Point> {
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
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		while (true) {
			cnt++;
			n = Integer.parseInt(br.readLine());
			if (n == 0)
				break;
			
			d = new int[n + 1][n + 1];
			for (int i = 1; i < d.length; i++)
				Arrays.fill(d[i], Integer.MAX_VALUE);
			map = new int[n + 1][n + 1];
			for (int i = 1; i < map.length; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 1; j < map[i].length; j++) 
					map[i][j] = Integer.parseInt(st.nextToken());
			}
			
			dijkstra(1, 1);
			
			sb.append("Problem " + cnt + ": " + d[n][n] + "\n");
		}
		System.out.println(sb);
	}
	public static void dijkstra(int x, int y) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(x, y, map[y][x]));
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			
			if (d[curr.x][curr.y] > curr.w) {
				d[curr.x][curr.y] = curr.w;
				for (int i = 0; i < 4; i++) {
					int nx = curr.x + dx[i],
						ny = curr.y + dy[i];
					if (nx < 1 || ny < 1 || nx > n || ny > n)
						continue;
					
					int nw = d[curr.x][curr.y] + map[nx][ny];
					pq.add(new Point(nx, ny, nw));
				}
			}
		}
	}
}
// 2020-10-14 00:02 해결
// 그냥 다익스트라. 1261번이랑 상당히 유사한데, 이제 출발지점의 가중치도 생각해야함.
