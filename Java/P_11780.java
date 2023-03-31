package D0331;

import java.util.*;
import java.io.*;
public class P_11780 {
	public static int[][] dist, trace;
	public static int n;
	public static void floyd() {
		for (int k = 1; k <= n; k++) 
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= n; j++) {
					if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
						if (dist[i][j] > dist[i][k] + dist[k][j]) {
							dist[i][j] = dist[i][k] + dist[k][j];
							trace[i][j] = trace[k][j];
						}
					}
				}
	}
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		
		trace = new int[n + 1][n + 1];	// trace[a][b] a에서 b로 갈 때, 가장 마지막으로 들르는 곳
		dist = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
		for (int i = 1; i <= n; i++) dist[i][i] = 0;
		while (m-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				c = Integer.parseInt(st.nextToken());
			
			if (dist[a][b] > c) {
				dist[a][b] = c;
				trace[a][b] = a;
			}
		}

		floyd();

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) 
				sb.append(dist[i][j] == Integer.MAX_VALUE ? "0 " : dist[i][j] + " ");
			sb.append("\n");
		}
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (trace[i][j] == 0) { 
					sb.append("0\n");
					continue;
				}
				
				Stack<Integer> st = new Stack<>();
				int curr = j;
				while (curr != 0) {
					st.add(curr);
					curr = trace[i][curr];
				}
				
				sb.append(st.size() + " ");
				while (!st.empty()) sb.append(st.pop() + " ");
				sb.append("\n");
			}
		}
		System.out.print(sb.toString());
	}
}

// 플로이드를 사용하되
// 각 정점들의 최단거리까지의 경로를 모두 역추적하는 문제
// 다익스트라의 경로 역추적 하는것처럼 해서 하는데
// 맨 처음에는
//	if (dist[i][j] > dist[i][k] + dist[k][j]) {
//		dist[i][j] = dist[i][k] + dist[k][j];
//		trace[i][j] = trace[k][j];
//	}
// 여기서 최단거리가 갱신될 경우, trace[i][j] = k로 했었다.
// 맨 처음의 생각은 거리가 갱신이 될 경우에는 경유지가 바로 이전 위치일 것이라 생각해서 했으나
// 다시 생각해보면 출발지-경유지-도착지 에서 경유지가 항상 도착지의 바로 전이라 보장은 되지 않으므로
// trace[i][j] = trace[k][j] 
// 즉 경유지 k에서 j로 이동할때, j가 바로 이전에 이동했던 지점으로 바꿔서 기록하게 했다.
