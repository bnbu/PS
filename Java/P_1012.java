import java.util.*;
import java.io.*;
public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static StringBuilder sb = new StringBuilder();
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	public static int[][] map;
	public static boolean[][] visit;
	public static int cnt = 0;
	public static void main(String[] args) throws NumberFormatException, IOException {
		int t = Integer.parseInt(br.readLine());
		String[] s;
		while (t-- > 0) {
			cnt = 0;
			s = br.readLine().split(" ");
			int m = Integer.parseInt(s[0]), n = Integer.parseInt(s[1]),
					k = Integer.parseInt(s[2]);
			map = new int[n][m];
			visit = new boolean[n][m];
			
			while (k-- > 0) {
				s = br.readLine().split(" ");
				int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]);
				map[y][x] = 1;
			}
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (!visit[i][j] && map[i][j] == 1) {
						cnt++;
						dfs(j, i, m, n);
					}
				}
			}
			sb.append(cnt + "\n");
		}
		System.out.println(sb);
	}
	public static void dfs(int x, int y, int m, int n) {
		if (visit[y][x])
			return;
		
		visit[y][x] = true;
		
		for (int i = 0; i < 4; i++) {
			int nextX = x + dx[i];
			int nextY = y + dy[i];
			if (nextX < 0 || nextY < 0 || nextX > m - 1 || nextY > n - 1)
				continue;
			else {
				if (!visit[nextY][nextX] && map[nextY][nextX] == 1)
					dfs(nextX, nextY, m, n);
			}
		}
	}
}
