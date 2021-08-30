import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static int[] cnt;
	public static int[][] map;
	public static int[] dx = {-1, 1, 0, 0};
	public static int[] dy = {0, 0, 1, -1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		map = new int[n][m];
		for (int i = 0; i < n; i++)
			Arrays.fill(map[i], -1);
		parent = new int[n * m];
		cnt = new int[n * m];
		for (int i = 0; i < n * m; i++) {
			parent[i] = i;
			cnt[i] = 1;
		}
		
		for (int i = 0; i < n; i++) {
			String s = br.readLine();
			for (int j = 0; j < m; j++) 
				map[i][j] = s.charAt(j) - '0';
		}
				
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 0) {
					for (int k = 0; k < 4; k++) {
						int ny = i + dy[k];
						int nx = j + dx[k];
						
						if (nx < 0 || ny < 0 || nx >= m || ny >= n)
							continue;
						
						if (map[ny][nx] == 0) {
							union(i * m + j, ny * m + nx);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 1) {
					int sum = 1;
					HashSet<Integer> set = new HashSet<>();
					for (int k = 0; k < 4; k++) {
						int nx = j + dx[k], 
							ny = i + dy[k];
							
						if (nx < 0 || ny < 0 || nx >= m || ny >= n)
								continue;
						
						if (map[ny][nx] == 0) 
							set.add(find(ny * m + nx));
					}
					for (int e : set)
						sum += cnt[e];
					sb.append(sum % 10);
				}
				else
					sb.append(0);
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y) {
			parent[y] = x;
			cnt[x] += cnt[y];
		}
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else 
			return parent[a] = find(parent[a]);
	}
}
// 2020-12-31 04:55 해결
// 0인 부분들을 union 시켜놓고, 하위 개체들의 수를 계산해놓는다.
// 이후 1인 부분에서 인접한 부분들 중 0인 곳이 속한 집합의 최상위 개체로부터
// 집합 하위 개체들의 수를 얻어와서 모두 합함.
// 이때, 중복될 수 있으니 HashSet에 find한 최상위 개체의 값을 입력해둔다.
