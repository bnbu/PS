import java.util.*;
import java.io.*;
public class Main {
	public static int[][] map;
	public static boolean[][] visitNormal;
	public static boolean[][] visitBroken;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	static class Position {
		int x;
		int y;
		int d;
		boolean b;
		public Position(int x, int y, int d, boolean b) {
			this.x = x;
			this.y = y;
			this.d = d;
			this.b = b;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		map = new int[n + 1][m + 1];
		visitNormal = new boolean[n + 1][m + 1];
		visitBroken = new boolean[n + 1][m + 1];
		for (int i = 1; i <= n; i++){
			String s = br.readLine();
			for (int j = 1; j <= m; j++)
				map[i][j] = s.charAt(j - 1) - '0';
		}
		
		Queue<Position> q = new LinkedList<>();
		q.add(new Position(1, 1, 1, true));
		visitNormal[1][1] = true;
		
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
				if (visitNormal[ny][nx] && curr.b)
					continue;
				if (visitBroken[ny][nx] && !curr.b) 
					continue;
				if (map[ny][nx] == 1) {
					if (curr.b) {
						visitBroken[ny][nx] = true;
						q.add(new Position(nx, ny, curr.d + 1, false));
					}
				}
				else {
					if (!curr.b)
						visitBroken[ny][nx] = true;
					else {
						visitBroken[ny][nx] = true;
						visitNormal[ny][nx] = true;
					}
					q.add(new Position(nx, ny, curr.d + 1, curr.b));
				}
			}
		}
		if (chk)
			System.out.println(min);
		else
			System.out.println(-1);
	}
}
// 2020-10-09 23:28 해결
// bfs로 탐색
// 이때, 현재 위치에서 벽을 부수기 전 상태일때 벽을 만났다면, 벽을 부수고 탐색, 당연 벽은 이제 못부숨
// 그렇지 않다면 그대로 진행
// 여기서 특별히 주의할 점은,
// 벽을 부수기 전까지는, 벽 부순 후와 벽을 부수기 전 방문 기록을 동시에 하다가,
// 벽을 부순 후부터는 벽을 부순 다음의 방문만 기록한다.
// 즉 벽 부순 후 / 벽 부수기 전 이 두가지 경우를 달리하여 방문기록.
