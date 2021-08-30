import java.util.*;
public class Main {
	public static int[] memoization; // 1 => SK 승, 2 => CY 승
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		long n = scan.nextLong();
		memoization = new int[] {2, 1, 2, 1, 1, 1, 1};
		System.out.println(stoneGame((int)(n % 7)) == 1 ? "SK" : "CY");
	}
	public static int stoneGame(int n) {
		return memoization[n];
	}
}
// 2020-09-02 13:49 해결
// 패턴이 7번 단위로 반복됨. 이를 이용해 값을 7로 나눠 나머지로 분석함.
