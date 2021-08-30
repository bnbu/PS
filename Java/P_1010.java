import java.util.*;
public class Main {
	public static long[][] memoization;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		while (t-- > 0) {
			int n = scan.nextInt(), m = scan.nextInt();
			memoization = new long[n + 1][m + 1];
			sb.append(func(n, m) + "\n");
		}
		System.out.println(sb);
	}
	public static long func(int n, int m) {
		if (n == 1)
			return m;
		if (n == m)
			return 1;
		if (memoization[n][m] != 0)
			return memoization[n][m];
		
		for (int i = 1; i <= m - n + 1; i++)
			memoization[n][m] += func(n - 1, m - i);
		return memoization[n][m];
	}
}
// 2020-09-15 해결
// 간단한 DP 문제, 점화식을 구상하는데 10초도 안걸림.
