import java.util.*;
public class Main {
	public static final int div = 1000000000;
	public static long[] memoization = new long[20001];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		System.out.println(factorial(n) % 10);
	}
	public static long factorial(int n) {
		if (n == 0 || n == 1)
			return 1;
		else {
			if (memoization[n] != 0)
				return memoization[n];
			
			memoization[n] = (n * factorial(n - 1) % div) % div;
			while (memoization[n] % 10 == 0)
				memoization[n] /= 10;
			return memoization[n];
		}
	}
 }
// 2020-09-12 해결
// DP로 일단 표현 가능한 부분까지 나눈 팩토리얼 값을 구함,
// 이후 10으로 나눈 나머지
