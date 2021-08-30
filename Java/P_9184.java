import java.util.*;
import java.io.*;
public class Main {
	public static int[][][] memo = new int[21][21][21];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		while (true) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				c = Integer.parseInt(st.nextToken());
			
			if (a == -1 && b == -1 && c == -1)
				break;
			
			sb.append("w(" + a + ", " + b + ", " + c + ") = " + w(a, b, c) + "\n");
		}
		System.out.print(sb);
	}
	public static int w(int a, int b, int c) {
		if (a <= 0 || b <= 0 || c <= 0)
			return 1;
		
		if (a > 20 || b > 20 || c > 20)
			return w(20, 20, 20);
		
		if (a < b && b < c) {
			if (memo[a][b][c] != 0)
				return memo[a][b][c];
			else
				return memo[a][b][c] = w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c);
		}
		
		if (memo[a][b][c] != 0)
			return memo[a][b][c];
		else 
			return memo[a][b][c] = w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1);
	}
}
// 2021-01-17 00:58
// 간단한 DP
