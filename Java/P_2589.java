import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int y;
		int x;
		int w;
		public Point(int y, int x, int w) {
			this.y = y;
			this.x = x;
			this.w = w;
		}
	}
	public static char[][] map;
	public static boolean[][] visit;
	public static int[] dx = {0, 0, -1 ,1},
						dy = {1, -1, 0, 0};
	public static int y, x;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		
		map = new char[y][x];
		visit = new boolean[y][x];
		
		for (int i = 0; i < y; i++) {
			String s = br.readLine();
			for (int j = 0; j < x; j++)
				map[i][j] = s.charAt(j);
		}
		System.out.println(doBFS());
		
	}
	public static int doBFS() {
		int max = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (map[i][j] == 'L') {
					max = Math.max(max, bfs(i, j));
				}
			}
		}
		return max;
	}
	public static int bfs(int start_y, int start_x) {
		int max = 0;
		for (int i = 0; i < y; i++)
			for (int j = 0; j < x; j++)
				visit[i][j] = false;
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(start_y, start_x, 0));
		visit[start_y][start_x] = true;
		while (!q.isEmpty()) {
			Point curr = q.poll();
			max = Math.max(max, curr.w);
			for (int i = 0; i < 4; i++) {
				int next_y = curr.y + dy[i],
					next_x = curr.x + dx[i];
				
				if (next_y < 0 || next_y >= y || next_x < 0 || next_x >= x)
					continue;
				
				if (map[next_y][next_x] == 'W')
					continue;
				
				if (!visit[next_y][next_x]) {
					q.add(new Point(next_y, next_x, curr.w + 1));
					visit[next_y][next_x] = true;
				}
			}
		}
		return max;
	}
}
// 2021-01-11 22:53
// bfs + 브루트포스 문제
