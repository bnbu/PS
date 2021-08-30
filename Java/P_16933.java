import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int y;
		int x;
		int k;
		int w;
		boolean sun;
		public Point(int y, int x, int k, int w, boolean s) {
			this.y = y;
			this.x = x;
			this.k = k;
			this.w = w;
			this.sun = s;
		}
	}
	public static int[][] map;
	public static int n, m, k, ans = Integer.MAX_VALUE;
	public static int[] dx = {0, 0, -1, 1}, dy = {1, -1, 0, 0};
	public static boolean[][][] visit;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		map = new int[n][m];
		visit = new boolean[n][m][k + 1];
		for (int i = 0; i < n; i++) {
			String s = br.readLine();
			for (int j = 0; j < m; j++) 
				map[i][j] = s.charAt(j) - '0';
		}
		bfs(0, 0);
		if (ans != Integer.MAX_VALUE)
			System.out.println(ans);
		else
			System.out.println(-1);
	}
	public static void bfs(int y, int x) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(y, x, 0, 1, true));
		visit[y][x][0] = true;
		while (!q.isEmpty()) {
			Point curr = q.poll();
			
			if (curr.y == n - 1 && curr.x == m - 1)
				ans = Math.min(ans, curr.w);
			
			for (int i = 0; i < 4; i++) {
				int next_y = curr.y + dy[i],
					next_x = curr.x + dx[i];
				boolean next_s = curr.sun ? false : true;
				
				if (next_y < 0 || next_x < 0 || next_y >= n || next_x >= m)
					continue;
				
				if (map[next_y][next_x] == 0) {
					if (!visit[next_y][next_x][curr.k]) {
					q.add(new Point(next_y, next_x, curr.k, curr.w + 1, next_s));
					visit[next_y][next_x][curr.k] = true;
					}
				}
				else {
					if (curr.k == k)
						continue;
					
					if (!visit[next_y][next_x][curr.k + 1]) {
						if (curr.sun && curr.k + 1 <= k) {
							q.add(new Point(next_y, next_x, curr.k + 1, curr.w + 1, next_s));
							visit[next_y][next_x][curr.k + 1] = true;
						}
						else if (!curr.sun) 
							q.add(new Point(curr.y, curr.x, curr.k, curr.w + 1, next_s));
					}
				} 
			}
		}
	}
}
// 2021-01-04 22:05 해결
// 이론 자체는 맞았는데, 제일 마지막에 가서, 도달할 수 없는 경우를 따로 체크를 안했었음
// 이론 :
// 벽을 k개 부쉈을때, y,x를 방문여무를 visit[y][x][k]라고 하고
// 이에 대하여 체크
// 매 방문마다 낮/밤을 번갈아가며 체크
// 다음 방문점이 0이라면, k에 변동없이 큐에 삽입
// 다음 방문점이 1이라면
// 1. 현재까지 벽을 몇개를 부쉈는지, k개를 부쉈다면 스킵
// 2. 다음 벽을 부쉈을 때, 이 점을 방문했는지, 즉 visit[next_y][next_x][curr.k + 1]을 확인
// 3. 만약 방문을 하지 않았을 경우, 지금이 낮인지, 낮이 아니라면, 해당 점을 다시 삽입한다.
