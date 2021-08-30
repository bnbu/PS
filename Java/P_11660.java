import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		int[][] prefix = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			prefix[i][1] = Integer.parseInt(st.nextToken());
			for (int j = 2; j <= n; j++) {
				prefix[i][j] = prefix[i][j - 1] + Integer.parseInt(st.nextToken());
			}
		}
		
		for (int j = 1; j <= n; j++) {
			for (int i = 2; i <= n; i++) {
				prefix[i][j] += prefix[i - 1][j];
			}
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken()),
				y1 = Integer.parseInt(st.nextToken()),
				x2 = Integer.parseInt(st.nextToken()),
				y2 = Integer.parseInt(st.nextToken());
			
			int sum = prefix[x2][y2];
			sum -= prefix[x2][y1 - 1];
			sum -= prefix[x1 - 1][y2];
			sum += prefix[x1 - 1][y1 - 1];
			
			sb.append(sum + "\n");
		}
		System.out.print(sb);
	}
}
// 2021-01-18 23:16
// 2차원 누적합
