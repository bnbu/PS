import java.util.*;
public class Main {
	public static long[][] memoization;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), k = scan.nextInt();
		memoization = new long[n + 1][k + 1];
		System.out.println(binomal(n, k));
	}
	public static long binomal(int n, int k) {
		if (k == 0 || k == n)
			return 1;
		
		if (memoization[n][k] != 0)
			return memoization[n][k];
		
		memoization[n][k] = binomal(n - 1, k - 1)+ binomal(n - 1, k); 
		return memoization[n][k];
	}
}
// 2020-09-22 23:29 해결
