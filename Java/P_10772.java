import java.util.*;
public class Main {
	public static int[][] memoization;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		memoization = new int[n + 1][m + 1];
		System.out.println(getNum(n, m));
	}
	public static int getNum(int n, int m) {
		if (m == 1)
			return 1;
		else if (n == m)
			return 1;
		else if (n < m)
			return 0;
		else {
			if (memoization[n][m] != 0)
				return memoization[n][m];
			else {
				for (int i = 1; i <= m; i++) 
					memoization[n][m] += getNum(n - m, i);
				return memoization[n][m];
			}
		}
	}
}
// 2020-09-10 12:20 해결
// 점화식을 발견하는게 관건
