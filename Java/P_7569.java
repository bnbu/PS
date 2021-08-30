import java.util.*;
import java.io.*;
public class Main {
	public static int[][][] map;
	public static boolean[][][] visit;
	public static final int[] dx = {0, 0, 1, -1, 0, 0};
	public static final int[] dy = {1, -1, 0, 0, 0, 0};
	public static final int[] dz = {0, 0, 0, 0, 1, -1};
	static class Tomato {
		int x, y, z;
		int time;
		public Tomato(int x, int y, int z, int t) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.time = t;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken()),
			n = Integer.parseInt(st.nextToken()),
			h = Integer.parseInt(st.nextToken());
		
		map = new int[h + 1][n + 1][m + 1];
		visit = new boolean[h + 1][n + 1][m + 1];
		Queue<Tomato> q = new LinkedList<>();
		
		boolean chk = false;
		for (int i = 1; i <= h; i++)
			for (int j = 1; j <= n; j++) {
				st = new StringTokenizer(br.readLine());
				for (int k = 1; k <= m; k++) {
					map[i][j][k] = Integer.parseInt(st.nextToken());
					
					if (map[i][j][k] == 1) {
						q.add(new Tomato(k, j, i, 0));
						visit[i][j][k] = true;
					}
					else if (map[i][j][k] == 0)
						chk = true;
				} 
			}
		
		if (chk) 	{
			int maxT = 0;
			while(!q.isEmpty()) {
				Tomato curr = q.poll();
				maxT = Math.max(maxT, curr.time);
				
				for (int i = 0; i < 6; i++) {
					int nx = curr.x + dx[i];
					int ny = curr.y + dy[i];
					int nz = curr.z + dz[i];
					
					if (nx < 1 || ny < 1 || nz < 1)
						continue;
					if (nx > m || ny > n || nz > h)
						continue;
					if (visit[nz][ny][nx])
						continue;
					if (map[nz][ny][nx] == -1)
						continue;
					
					q.add(new Tomato(nx, ny, nz, curr.time + 1));
					visit[nz][ny][nx] = true;
					map[nz][ny][nx] = 1;
				}
			}
			
			if (check())
				System.out.println(maxT);
			else 
				System.out.println(-1);
		}
		else 
			System.out.println(0);
	}
	public static boolean check() {
		for (int i = 1; i < map.length; i++)
			for (int j = 1; j < map[i].length; j++)
				for (int k = 1; k < map[i][j].length; k++)
					if (map[i][j][k] == 0)
						return false;
		
		return true;
	}
}
// 2020-10-09 10:40 해결
// 이전의 그 토마토 문제를 3차원으로 확장시킨것,
// map과 visit 배열을 3차원 배열로 늘리고, bfs시 탐색 위치를 3차원축으로 상하 추가하여
// 총 6방향 탐색을 진행시키면 됨.
