import java.util.*;
public class Main {
	public static long[] memoization = new long[36];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		System.out.println(squence(n));
	}
	public static long squence(int n) {
		if (n == 0)
			return 1;
		else {
			if (memoization[n] != 0)
				return memoization[n];
			else {
				for (int i = 0; i < n; i++) {
					memoization[n] += squence(n - i - 1) * squence(i);
				}
				return memoization[n];
			}
		}
	}
}
// 2020-09-02 12:57 해결
// 그냥 흔한 dp문제.
