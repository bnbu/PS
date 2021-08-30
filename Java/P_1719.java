import java.util.*;
import java.io.*;
public class Main {
	public static int[][] d;
	public static int[][] trace;
	public static int n, m;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		trace = new int[n + 1][n + 1];
		d = new int[n + 1][n + 1];
		for (int i = 0; i < n + 1; i++) {
			Arrays.fill(d[i], Integer.MAX_VALUE);
			Arrays.fill(trace[i], 0);
		}
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			d[u][v] = w;
			d[v][u] = w;
		}
		
		floyd();
		
		for (int i = 1; i < trace.length; i++) {
			for (int j = 1; j < trace[i].length; j++) {
				if (i == j)
					sb.append("- ");
				else
					sb.append(trace[i][j] == 0 ? j + " " : trace[i][j] + " ");
			}
			sb.append("\n");
		}
		System.out.print(sb);
	}
	public static void floyd() {
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				if (i == k) continue;
				for (int j = 1; j <= n; j++) {
					if (j == k) continue;
					if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE)
						if (d[i][j] > d[i][k] + d[k][j]) {
							d[i][j] = d[i][k] + d[k][j];
							if (trace[i][k] == 0)
								trace[i][j] = k;
							else 
								trace[i][j] = trace[i][k];
						}
				}
			}
		}
	}
}

// 2020-11-07 00:28 해결
// => 플로이드-와샬을 수행하면서, 최단거리가 경신이 될 때,
// 이때, 해당 경로에서의 가장 첫 방문지를 추적해야한다.
// 따라서, 이 경로의 경유지로 갈 떄의 첫 방문지를 해당 경로의 첫 방문지로 한다.
// 그리고 경유지로의 첫 방문지가 없다는 뜻은 다시 말해 경유지가 첫 방문이란 뜻.
