import java.io.*;
public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int[][] memo;
	public static void main(String[] args) throws IOException {
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]), k = Integer.parseInt(s[1]);
		memo = new int[n + 1][k + 1];
		int[] w = new int[n + 1], v = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			s = br.readLine().split(" ");
			w[i] = Integer.parseInt(s[0]);
			v[i] = Integer.parseInt(s[1]);
		}
		
		for (int i = 1; i < memo.length; i++) {
			for (int j = 1; j < memo[i].length; j++) {
				if (w[i] <= j)
					memo[i][j] = Math.max(v[i] + memo[i - 1][j - w[i]], memo[i - 1][j]);
				else 
					memo[i][j] = memo[i - 1][j];
			}
		}
		
		System.out.println(memo[n][k]);
	}
}
// 2020-10-08 18:43 해결
// 냅색의 기초문제. 
