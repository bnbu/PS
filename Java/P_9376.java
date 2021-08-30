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
	public static char[][] map;
	public static int h, w, first_y, first_x, second_y, second_x, ans;
	public static int[] dx = {0, 0, -1, 1}, dy = {1, -1, 0, 0};
	public static int[][][] num;
	public static Deque<Point> dq = new LinkedList<>();
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			st = new StringTokenizer(br.readLine());
			h = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			first_y = -1; first_x = -1; second_y = -1; second_x = -1;
			ans = Integer.MAX_VALUE;
			map = new char[h + 2][w + 2];
			num = new int[3][h + 2][w + 2];
			for (int i = 0; i < h + 2; i++) {
				Arrays.fill(map[i], '.');
				Arrays.fill(num[0][i], Integer.MAX_VALUE);
				Arrays.fill(num[1][i], Integer.MAX_VALUE);
				Arrays.fill(num[2][i], Integer.MAX_VALUE);
			}
			
			for (int i = 1; i <= h; i++) {
				String s = br.readLine();
				for (int j = 1; j <= w; j++) {
					map[i][j] = s.charAt(j - 1);
					
					if (map[i][j] == '$') {
						if (first_y == -1) {
							first_y = i;
							first_x = j;
						}
						else {
							second_y = i;
							second_x = j;
						}
					}
				}
			}
			
			bfs(0, 0, 0);
			bfs(1, first_y, first_x);
			bfs(2, second_y, second_x);
			
			for (int i = 0; i < h + 2; i++) {
				for (int j = 0; j < w + 2; j++) {
					if (map[i][j] == '*')
						continue;
					
					if (num[0][i][j] != Integer.MAX_VALUE) {
						int temp = 0;
						for (int k = 0; k < 3; k++)
							temp += num[k][i][j];
						if (map[i][j] == '#')
							temp -= 2;
						ans = Math.min(ans, temp);
					}
					else
						continue;
				}
			}
			sb.append(ans + "\n");
		}
		System.out.println(sb);
	}
	public static void bfs(int n, int y, int x) {
		dq.clear();
		dq.add(new Point(y, x));
		num[n][y][x] = 0;
		while (!dq.isEmpty()) {
			Point curr = dq.poll();
			for (int i = 0; i < 4; i++) {
				int next_y = curr.y + dy[i],
					next_x = curr.x + dx[i];
				
				if (next_y < 0 || next_x < 0 || next_y >= h + 2 || next_x >= w + 2)
					continue;
				
				if (num[n][next_y][next_x] != Integer.MAX_VALUE || map[next_y][next_x] == '*')
					continue;
				
				if (map[next_y][next_x] == '#') {
					if (num[n][next_y][next_x] > num[n][curr.y][curr.x] + 1) {
						num[n][next_y][next_x] = num[n][curr.y][curr.x] + 1;
						dq.addLast(new Point(next_y, next_x));
					}
				}
				else {
					if (num[n][next_y][next_x] > num[n][curr.y][curr.x]) {
						num[n][next_y][next_x] = num[n][curr.y][curr.x];
						dq.addFirst(new Point(next_y, next_x));
					}
				}
			}
		}
	}
}
// 2021-01-05 23:24 
// 맨 처음에 시도한 방법은
// 1번죄수 최단거리 탈출경로로부터 열어둔 문을 계산,
// 이를 토대로 다시 2번죄수 최단거리를 계산. 
// => 다만, 각각 둘의 최단거리 탈출경로를 합한 경우가 정답이 아닌경우에 실패
//
// 다음에 시도한 방법은
// 문을 열어둔 곳을 사용하므로, 둘의 탈출경로는 어느정도 공통된 점이 존재할 수 있다는점.
// 즉 둘은 어느 특정한 위치에서 만난다고 할 수 있다.
// (다만, 이 특정한 위치는 감옥 내 일수도 있고, 외부일 수도 있다)
// 즉 3가지의 경로를 알아봐야 한다.
// 1. 죄수 1 -> 특정 경로 (x, y)
// 2. 죄수 2 -> 특정 경로 (x, y)
// 3. 특정 경로(x, y) -> 외부 // 이는 반대로 외부->(x,y)로도 생각이 가능하다.
// 외부는 고정시켜서 (0, 0)이라 하자.
// 각 경로당 특정 지점에서의 문을 연 횟수를 모두 계산
// 이후 세 경로의 특정 지점에서의 문을 연 횟수를 모두 합친다.
// 이때, 문은 한번만 열면 되므로, 문이 있는 지점은 더한 값에서 2를 제외한다.
// 이 값들 중 가장 작은값이 최소값이 된다.

