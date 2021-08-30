import java.util.*;
public class Main {
	public static long[] memo = new long[101];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			sb.append(padoban(n) + "\n");
		}
		System.out.println(sb.toString());
	}
	public static long padoban(int n) {
		if (n == 1 || n == 2 || n == 3) {
			return 1;
		}
		else {
			if (memo[n] != 0)
				return memo[n];
			else {
				memo[n] = padoban(n - 2) + padoban(n - 3);
				return memo[n];
			}
		}
	}
}
// 2020-09-01 18:50 해결
// 한번 살짝 꼬아서 이건 p(n) = p(n - 2) + p(n - 3)임
