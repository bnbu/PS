import java.util.*;
public class Main {
	public static long[] memoization;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		memoization = new long[n + 1];
		System.out.println(pinary(n));
	}
	public static long pinary(int n) {

		if (n == 0 || n == 1)
			return 1;
		else if (memoization[n] != 0) {
			return memoization[n];
		}
		else {
			for (int i = 0; i < n - 1; i++)
				memoization[n] += pinary(i);
//			memoization[n] = pinary(n - 1) + pinary(n - 2); (단 이때 n == 0일때 0임)
			return memoization[n];
		}
	}
}
// 2020-09-12 해결
