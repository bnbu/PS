import java.util.*;
import java.io.*;
public class Main {
	public static int[][] d;
	public static int n, m;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());
		
		d = new int[n + 1][n + 1];
		for (int i = 1; i < d.length; i++) {
			for (int j = 1; j < d[i].length; j++) {
				if (i == j)
					d[i][j] = 0;
				else
					d[i][j] = Integer.MAX_VALUE;
			}
		}
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			d[u][v] = Math.min(d[u][v], w);
		}
		
		floyd();
		
		for (int i = 1; i < d.length; i++) {
			for (int j = 1; j < d[i].length; j++)
				sb.append(d[i][j] == Integer.MAX_VALUE ? "0 " : (d[i][j] + " "));
			sb.append("\n");
		} // 아 ㅋㅋ 못갈때 INF 말고 0 쓰라고 ㅋㅋㅋ
		System.out.println(sb);
	}
	public static void floyd() {
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE)
						d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
				// ** 최댓값이라 둘이 걍 더해도 될거같긴 한데
				// Integer.MAX_VALUE라 둘이 더하면 오버플로우 나서 큰일남. 조심.
			}
		}
	}
}

// 2020-10-14 23:11 해결
// 벨만포드보다는 플로이드가 직관적으로 볼때 더 쉬운듯 하다.
