import java.io.*;
import java.util.*;
public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int[][] map;
	public static boolean[][] visit;
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	public static Queue<Integer> posX = new LinkedList<>();
	public static Queue<Integer> posY = new LinkedList<>();
	public static Queue<Integer> tempX = new LinkedList<>();
	public static Queue<Integer> tempY = new LinkedList<>();
	public static int currX, currY, nextX, nextY;
	public static int cnt = -1;
	public static void main(String[] args) throws IOException {
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);
		map = new int[m][n];
		visit = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			s = br.readLine().split(" ");
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(s[j]);
		}
		
		boolean b = false;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] == 1) {
					tempX.add(j);
					tempY.add(i);
					visit[i][j] = true;
				}
				else if (map[i][j] == 0)
					b = true;
			}
		}
		if (b) {
			bfs(n, m);
			if (chk()) 
				System.out.println(cnt);
			else
				System.out.println(-1);
		}
		else 
			System.out.println(0);
	}
	public static void bfs(int n, int m) {	
		while (!tempX.isEmpty() && !tempY.isEmpty()) {
			cnt++;
			while (!tempX.isEmpty() && !tempY.isEmpty()) {
				posX.add(tempX.poll());
				posY.add(tempY.poll());
			}

			while (!posX.isEmpty() && !posY.isEmpty()) {
				currX = posX.poll();
				currY = posY.poll();

				for (int i = 0; i < 4; i++) {
					nextX = currX + dx[i];
					nextY = currY + dy[i];

					if (nextX < 0 || nextY < 0 || nextX > n - 1 || nextY > m - 1)
						continue;
					
					if (!visit[nextY][nextX] && map[nextY][nextX] == 0) {
						map[nextY][nextX] = 1;
						visit[nextY][nextX] = true;
						tempX.add(nextX);
						tempY.add(nextY);
					}
				}
			}
		}
	}
	public static boolean chk() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0)
					return false;
			}
		}
		return true;
	}
}
// 2020-10-08 23:37 해결
// bfs를 진행하는데 주의점이, 하루에 익는 토마토를 모두 먼저 처리 해야함
// 그리고 날짜 카운트 이후 다음 익을 목록(다음 탐색목록)들을 다시 익은거로 처리 후 bfs를 진행
