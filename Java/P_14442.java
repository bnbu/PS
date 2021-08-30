import java.util.*;
import java.io.*;
public class Main {
	public static int[][] map;
	public static boolean[][][] visit;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	static class Position {
		int x;
		int y;
		int d;
		int left;
		public Position(int x, int y, int d, int l) {
			this.x = x;
			this.y = y;
			this.d = d;
			this.left = l;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		
		map = new int[n + 1][m + 1];
		visit = new boolean[k + 1][n + 1][m + 1];
		for (int i = 1; i <= n; i++){
			String s = br.readLine();
			for (int j = 1; j <= m; j++)
				map[i][j] = s.charAt(j - 1) - '0';
		}
		
		Queue<Position> q = new LinkedList<>();
		q.add(new Position(1, 1, 1, k));
		for (int i = k; i >= 0; i--)
			visit[i][1][1] = true;
		
		boolean chk = false;
		int min = Integer.MAX_VALUE;
		while (!q.isEmpty()) {
			Position curr = q.poll();
			
			if (curr.x == m && curr.y == n) {
				chk = true;
				min = Math.min(min, curr.d);
			}
			
			for (int i = 0; i < 4; i++) {
				int nx = curr.x + dx[i];
				int ny = curr.y + dy[i];
				
				if (nx < 1 || ny < 1 || nx > m || ny > n)
					continue;
				if (curr.left > 0 && visit[curr.left][ny][nx])
					continue;
				if (curr.left == 0 && visit[0][ny][nx]) 
					continue;
				if (map[ny][nx] == 1) {
					if (curr.left > 0) {
						for (int j = curr.left; j >= 0; j--)
							visit[j][ny][nx] = true;
						q.add(new Position(nx, ny, curr.d + 1, curr.left - 1));
					}
				}
				else {
					for (int j = curr.left; j >= 0; j--)
						visit[j][ny][nx] = true;
					q.add(new Position(nx, ny, curr.d + 1, curr.left));
				}
			}
		}
		if (chk)
			System.out.println(min);
		else
			System.out.println(-1);
	}
}
// 2020-10-11 01:47 해결
// 그 벽 부수고 이동의 강화버젼
// 벽을 k번 부수고 지나갈 수 있는데,
// 이전의 문제에서는 벽을 부쉈을 때와 안부쉈을때를 나눠서 방문을 기록했다
// 이 부분을 가져와서, 벽을 부술기회가 k번 남았을때로 구분지어서 기록한다
// k번 기회가 남아있다면, k번보다 덜 남았을 모든 부분에 방문했다고 기록한다.
// 이렇게 하면, 부수고 나서와 부수기 전의 경계를 확실하게 구분지어서 bfs를 할 수 있다.
