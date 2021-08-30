import java.util.*;
public class Main {
	public static final int div = 1000000000;
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
		else if (m == 2)
			return n + 1;
		else {
			if (memoization[n][m] != 0)
				return memoization[n][m];
			
			for (int i = 0; i <= n; i++) {
				memoization[n][m] += getNum(i, m - 1);
				memoization[n][m] %= div;
			}
			return memoization[n][m];
		}
	}
}
