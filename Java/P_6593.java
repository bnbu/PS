import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int z;
		int y;
		int x;
		int t;
		public Point(int z, int y, int x, int t) {
			this.z = z;
			this.y = y;
			this.x = x;
			this.t = t;
		}
	}
	public static char[][][] map;
	public static boolean[][][] visit;
	public static int[] dx = {1, -1, 0, 0, 0, 0},
						dy = {0, 0, -1, 1, 0, 0},
						dz = {0, 0, 0, 0, 1, -1};
	public static int l, r, c;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		while (true) {
			st = new StringTokenizer(br.readLine());
			l = Integer.parseInt(st.nextToken());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			
			if (l == 0 && r == 0 && c == 0)
				break;

			int s_l = 0, s_r = 0, s_c = 0;
			map = new char[l][r][c];
			visit = new boolean[l][r][c];
			for (int i = 0; i < l; i++) {
				for (int j = 0; j < r; j++) {
					String s = br.readLine();
					for (int k = 0; k < c; k++) {
						map[i][j][k] = s.charAt(k);
						if (map[i][j][k] == 'S') {
							s_l = i;
							s_r = j;
							s_c = k;
						}
					}
				}
				br.readLine();
			}
			
			int t = bfs(s_l, s_r, s_c);
			if (t != -1)
				sb.append("Escaped in " + t + " minute(s).\n");
			else
				sb.append("Trapped!\n");
		}
		System.out.println(sb);
		
	}
	public static int bfs(int z, int y, int x) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(z, y, x, 0));
		visit[z][y][x] = true;
		while (!q.isEmpty()) {
			Point curr = q.poll();
			
			if (map[curr.z][curr.y][curr.x] == 'E')
				return curr.t;
			
			for (int i = 0; i < 6; i++) {
				int next_z = curr.z + dz[i],
					next_y = curr.y + dy[i],
					next_x = curr.x + dx[i];
				
				if (next_z < 0 || next_y < 0 || next_x < 0 ||
						next_z >= l || next_y >= r || next_x >= c)
					continue;
				
				if (map[next_z][next_y][next_x] == '#')
					continue;

				if (!visit[next_z][next_y][next_x]) {
					q.add(new Point(next_z, next_y, next_x, curr.t + 1));
					visit[next_z][next_y][next_x] = true;
				}
			}
		}
		return -1;
	}
}
// 2021-01-11 22:36
// 간단한 3차원 bfs문제.
