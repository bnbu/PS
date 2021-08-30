import java.util.*;
import java.io.*;
public class Main {
	public static class VisitedPoint {
		int l;
		int r;
		boolean visited; 
		public VisitedPoint(int l, int r) {
			this.l = l;
			this.r = r;
			visited = false;
		}
	}
	public static VisitedPoint[][] visit;
	public static int[][] map;
	public static int[] dx = {-1, 1};
	public static int cnt = 0;
	public static int n, m;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		int l = Integer.parseInt(st.nextToken()),
			r = Integer.parseInt(st.nextToken());
		
		int curr_y = 0, curr_x = 0;
		visit = new VisitedPoint[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				visit[i][j] = new VisitedPoint(0, 0);
		map = new int[n][m];
		for (int i = 0; i < n; i++) {
			String s = br.readLine();
			for (int j = 0; j < m; j++) {
				map[i][j] = s.charAt(j) - '0';
				if (map[i][j] == 2) {
					curr_y = i;
					curr_x = j;
				}
			}
		}
		
		bfs(curr_y, curr_x, l, r);

		System.out.println(cnt);
	}
	public static void bfs(int y, int x, int l, int r) {
		visit[y][x].l = l;
		visit[y][x].r = r;
		visit[y][x].visited = true;
		Queue<Integer> q_y = new LinkedList<>();
		Queue<Integer> q_x = new LinkedList<>();
		Queue<VisitedPoint> q_p = new LinkedList<>();
		q_y.add(y);
		q_x.add(x);
		q_p.add(visit[y][x]);
		cnt++;
		while (!q_p.isEmpty()) {
			VisitedPoint curr_p = q_p.poll();
			int curr_y = q_y.poll();
			int curr_x = q_x.poll();

			int next_y = curr_y;
			while (true) {
				if (next_y >= n)
					break;
				if (map[next_y][curr_x] == 1)
					break;
				
				if (!visit[next_y][curr_x].visited) {
					visit[next_y][curr_x].l = curr_p.l;
					visit[next_y][curr_x].r = curr_p.r;
					visit[next_y][curr_x].visited = true;
					q_y.add(next_y);
					q_x.add(curr_x);
					q_p.add(visit[next_y][curr_x]);
					cnt++;
				}
				next_y++;
			}
			next_y = curr_y;
			while (true) {
				if (next_y < 0)
					break;
				if (map[next_y][curr_x] == 1)
					break;
				
				if (!visit[next_y][curr_x].visited) {
					visit[next_y][curr_x].l = curr_p.l;
					visit[next_y][curr_x].r = curr_p.r;
					visit[next_y][curr_x].visited = true;
					q_y.add(next_y);
					q_x.add(curr_x);
					q_p.add(visit[next_y][curr_x]);
					cnt++;
				}
				next_y--;
			}
			
			for (int i = 0; i < 2; i++) {
				int next_x = curr_x + dx[i];
				
				if (next_x < 0 || next_x >= m)
					continue;
				if (map[curr_y][next_x] == 1)
					continue;
				
				if (!visit[curr_y][next_x].visited) {
					if (i == 0) {
						if (curr_p.l == 0)
							continue;
						visit[curr_y][next_x].l = curr_p.l - 1;
						visit[curr_y][next_x].r = curr_p.r;
						visit[curr_y][next_x].visited = true;
					}
					else if (i == 1) {
						if (curr_p.r == 0)
							continue;
						visit[curr_y][next_x].l = curr_p.l;
						visit[curr_y][next_x].r = curr_p.r - 1;
						visit[curr_y][next_x].visited = true;
					}
					q_y.add(curr_y);
					q_x.add(next_x);
					q_p.add(visit[curr_y][next_x]);
					cnt++;
				}
			}
		}
	}
}
// 2021-01-01 00:26 해결
// 새해 첫 문제 
// bfs를 사용, 단..
// 상-하-좌-우 한칸씩 탐색시 먼저 간 한칸으로 인해 도착할 수 없다고 판단하는 칸이 생김
// 따라서 좌-우로는 한칸씩 탐색하되, 상-하로는 이동할 수 있는 범위 끝까지 탐색시킨다.
