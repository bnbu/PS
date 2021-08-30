import java.util.*;
import java.io.*;
public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static StringBuilder sb = new StringBuilder();
	public static int[][] map;
	public static boolean[][] visit;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	public static ArrayList<Integer> houseNum = new ArrayList<>();
	public static int cnt = 0;
	public static int houseCnt = 0;
	public static int currX = 0;
	public static int currY = 0;
	public static int nextX = 0;
	public static int nextY = 0;
	public static void main(String[] args) throws NumberFormatException, IOException {
		int n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		visit = new boolean[n][n];
		String s;
		for (int i = 0; i < n; i++) {
			s = br.readLine();
			for (int j = 0; j < n; j++)
				map[i][j] = s.charAt(j) - '0';
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!visit[i][j] && map[i][j] == 1) {
					cnt++;
					houseCnt = 0;
					dfs(j, i, n);
					houseNum.add(houseCnt);
				}
			}
		}
		
		Collections.sort(houseNum);
		sb.append(cnt + "\n");
		for (int i : houseNum)
			sb.append(i + "\n");
		System.out.println(sb);
	}
	public static void dfs(int x, int y, int n) {
		if (visit[y][x])
			return;
		
		visit[y][x] = true;
		houseCnt++;
		
		for (int i = 0; i < 4; i++) {
			nextX = x + dx[i];
			nextY = y + dy[i];
			if (nextX < 0 || nextY < 0 || nextX > n - 1 || nextY > n - 1)
				continue;
			else {
				if (!visit[nextY][nextX] && map[nextY][nextX] == 1)
					dfs(nextX, nextY, n);
			}
		}
	}
}
// 2020-10-08 00:03 해결
