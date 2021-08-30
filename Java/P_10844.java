import java.util.*;

public class Main {
	public static final int div = 1000000000;
	public static long[] memoization;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		memoization = new long[n + 1];
		System.out.println(stairNum(n));
	}

	public static long stairNum(int n) {
		if (n == 1)
			return 9;
		else if (n == 2)
			return 17;
		else if (n == 3)
			return 32;
		else if (n == 4)
			return 61;
		else if (n == 5)
			return 116;
		else {
			if (memoization[n] == 0)
				memoization[n] = (stairNum(n - 1) % div + (4 * (stairNum(n - 2) % div)) % div
						- (3 * (stairNum(n - 3) % div)) % div - (3 * (stairNum(n - 4) % div)) % div
						+ stairNum(n - 5) % div) % div;
			return memoization[n];
		}
	}
}
