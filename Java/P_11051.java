import java.util.*;
public class Main {
	public static int div = 10007;
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
		
		memoization[n][k] = (binomal(n - 1, k - 1) % div + binomal(n - 1, k) % div) % div; 
		return memoization[n][k];
	}
}
// 2020-09-22 23:29해결
// 여기서 mod 연산만 빼면 그게 11050번 임 ㅋㅋ
