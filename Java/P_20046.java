import java.util.*;
import java.io.*;
public class Main {
	public static int[][] d;
	public static int[][] map;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0}; 
	public static int m, n;
	static class Position implements Comparable<Position> {
		int x;
		int y;
		int w;
		public Position(int x, int y, int w) {
			this.x = x;
			this.y = y;
			this.w = w;
		}
		public int compareTo(Position p) {
			return this.w - p.w;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		
		map = new int[m + 1][n + 1];
		d = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++)
			Arrays.fill(d[i], Integer.MAX_VALUE);
		
		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}
		
		dijkstra(1, 1);
		
		System.out.println(d[m][n] == Integer.MAX_VALUE ? "-1" : d[m][n]);
	}
	
	public static void dijkstra(int x, int y) {
		PriorityQueue<Position> pq = new PriorityQueue<>();
		if (map[x][y] != -1) {
			pq.add(new Position(x, y, map[x][y]));
			d[x][y] = map[x][y];
		}
		
		while (!pq.isEmpty()) {
			Position curr = pq.poll();
			for (int i = 0; i < 4; i++) {
				int nextX = curr.x + dx[i],
					nextY = curr.y + dy[i];
				
				if (nextX < 1 || nextY < 1 || nextX > m || nextY > n) continue;
				if (map[nextX][nextY] == -1) continue;
				
				if (d[nextX][nextY] > curr.w + map[nextX][nextY]) {
					d[nextX][nextY] = curr.w + map[nextX][nextY];
					pq.add(new Position(nextX, nextY, d[nextX][nextY]));
				}
			}
		}
	}
}
// 2020-11-15 21:33 해결
