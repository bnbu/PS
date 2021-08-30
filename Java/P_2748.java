import java.util.*;
public class Main {
	public static long[] memoizationed = new long[91];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		System.out.println(fibonacci(n));
	}
	public static long fibonacci(int n) {
		if (n == 0) 
			return 0;
		else if (n == 1 || n == 2)
			return 1;
		else {
			if (memoizationed[n] != 0)
				return memoizationed[n];
			else {
				long result = fibonacci(n - 1) + fibonacci(n - 2);
				memoizationed[n] = result;
				return result;
			}
		}
	}
}
// 2020-09-01 17:50분쯤 해결
// 메모이제이션을 이용하는 첫 문제.
