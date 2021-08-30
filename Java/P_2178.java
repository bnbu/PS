import java.util.*;
import java.io.*;
public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int map[][];
	public static boolean visit[][];
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {1, -1, 0, 0};
	public static Queue<Integer> posX = new LinkedList<>();
	public static Queue<Integer> posY = new LinkedList<>();
	public static Queue<Integer> tempX = new LinkedList<>();
	public static Queue<Integer> tempY = new LinkedList<>();
	public static int currX, currY, nextX, nextY;
	public static int cnt = 0;
	public static void main(String[] args) throws IOException {
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);
		map = new int[n][m];
		visit = new boolean[n][m];
		
		String input;
		for (int i = 0; i < n; i++) {
			input = br.readLine();
			for (int j = 0; j < m; j++)
				map[i][j] = input.charAt(j) - '0';
		}
		tempX.add(0);
		tempY.add(0);
		bfs(n, m);
		
		System.out.println(cnt);
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
				
				if (currX == m - 1 && currY == n - 1)
					return;
				
				for (int i = 0; i < 4; i++) {
					nextX = currX + dx[i];
					nextY = currY + dy[i];
					
					if (nextX < 0 || nextY < 0 || nextX > m - 1 || nextY > n - 1)
						continue;
					
					if (!visit[nextY][nextX] && map[nextY][nextX] == 1) {
						map[nextY][nextX] = 1;
						visit[nextY][nextX] = true;
						tempX.add(nextX);
						tempY.add(nextY);
					}
				}
			}
		}
	}
}
// 2020-10-09 00:40 해결
// 토마토 하다 이거하니까 왜캐 쉽냐 이거
