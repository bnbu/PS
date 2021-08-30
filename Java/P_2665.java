import java.util.*;
import java.io.*;
public class Main {
	public static int n;
	public static int[][] map;
	public static int[][] d;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
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
		n = Integer.parseInt(br.readLine());
		map = new int[n + 1][n + 1];
		for (int i = 1; i < map.length; i++) {
			String s = br.readLine();
			for (int j = 1; j < map[i].length; j++) 
				map[i][j] = s.charAt(j - 1) - '0';
		}
		
		d = new int[n + 1][n + 1];
		for (int i = 1; i < d.length; i++)
			Arrays.fill(d[i], Integer.MAX_VALUE);
		
		dijkstra(1, 1);
		
		/*for (int i = 1; i < d.length; i++) {
			for (int j = 1; j < d[i].length; j++)
				System.out.print(d[i][j] + " ");
			System.out.println();
		}*/
		
		System.out.println(d[n][n]);
	}
	public static void dijkstra (int x, int y) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(x, y, 0));
		d[x][y] = 0;
		
		while (!pq.isEmpty()) {
			Point curr = pq.poll();
			
			for (int i = 0; i < 4; i++) {
				int nx = curr.x + dx[i],
					ny = curr.y + dy[i];
				
				if (nx < 1 || ny < 1 || nx > n || ny > n)
					continue;
				
				int nw = map[nx][ny] == 0 ? curr.w + 1 : curr.w;
				if (d[nx][ny] > nw) {
					d[nx][ny] = nw;
					pq.add(new Point(nx, ny, nw));
				}
			}
		}
	}
}
