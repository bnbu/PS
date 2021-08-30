import java.util.*;
public class Main {
	public static final int div = 987654321;
	public static long[] memoization;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		memoization = new long[n / 2 + 1];
		System.out.println(getNum(n / 2));
	}
	public static long getNum(int n) {
		if (n == 0 || n == 1)
			return 1;
		else {
			if (memoization[n] != 0)
				return memoization[n];
			
			for (int i = 0; i <= n - 1; i++) 
				memoization[n] += ((getNum(i) % div) * (getNum(n - 1 - i) % div)) % div;
			memoization[n] %= div;
			return memoization[n];
		}
	}
}
// 2020-09-06 21:52 해결
// ab % c = ((a % c) * (b % c)) % c 인거 아는놈이 왜 자꾸 마지막 % c 연산 까먹는데 ㅋㅋㅋㅋㅋ
// 1670번이랑 같은 문제임
