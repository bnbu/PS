import java.util.*;
public class Main {
	public static long[] memoizationed = new long[1000001];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		System.out.println(tileNum(n));
	}
	public static long tileNum(int n) {
		if (n == 1)
			return 1;
		else if (n == 2)
			return 2;
		else {
			if (memoizationed[n] != 0)
				return memoizationed[n];
			else {
				memoizationed[n] = (tileNum(n - 1) + tileNum(n - 2)) % 15746;
				return memoizationed[n];
			}
		}
	}
}
// 2020-09-01 18:34 해결
// a % b + c % b = (a + c) % b ㅋㅋ
