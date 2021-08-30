import java.util.*;
import java.io.*;
public class Main {
	public static class Point { 
		int y;
		int x;
		public Point(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
	public static int[][] map;
	public static boolean[][] visit;
	public static int n, limit, max = 0, cnt = 0;
	public static int[] dx = {0, 0, -1, 1}, dy = {1, -1, 0, 0};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		visit = new boolean[n + 1][n + 1];
		map = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				max = Math.max(max, map[i][j]);
			}
		}
		
		int ans = 0;
		limit = max;
		while (max-- >= 0) {
			cnt = 0;
			visitInitialize();
			bfs();
			ans = Math.max(ans, cnt);
			limit--;
		}
		System.out.println(ans);
	}
	public static void visitInitialize() {
		for (int i = 1; i <= n; i++)
			Arrays.fill(visit[i], false);
	}
	
	public static void bfs() {
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++) {
				if (!visit[i][j]) {
					bfs_y_x(i, j);
					if (map[i][j] > limit)
						cnt++;
				}
			}
	}
	
	public static void bfs_y_x (int y, int x) {
		if (map[y][x] > limit) {
			Queue<Point> q = new LinkedList<>();
			q.add(new Point(y, x));
			visit[y][x] = true;
			while (!q.isEmpty()) {
				Point curr = q.poll();
				for (int i = 0; i < 4; i++) {
					int next_y = curr.y + dy[i], 
						next_x = curr.x + dx[i];
					
					if (next_y < 1 || next_x < 1 || next_y > n || next_x > n)
						continue;
					
					if (map[next_y][next_x] > limit && !visit[next_y][next_x]) {
						q.add(new Point(next_y, next_x));
						visit[next_y][next_x] = true;
					}
				}
			}
		}
	}
}

// 2021-01-04 20:07 해결
// 잠기지 않는 부위의 조건을 추가하여 이부분만 bfs를 진행
// connected component의 수를 체크
// 이때, 비가 하나도 오지 않는 경우까지 포함시켜야 한다
