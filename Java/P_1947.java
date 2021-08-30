import java.util.*;
public class Main {
	public static final int div = 1000000000;
	public static long[] memoization;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		memoization = new long[n + 1];
		System.out.println(getNum(n));
	}
	public static long getNum(int n) {
		if (n == 1)
			return 0;
		else if (n == 2)
			return 1;
		else {
			if (memoization[n] != 0)
				return memoization[n];
			else {
				memoization[n] = ((n - 1) % div * ((getNum(n - 1) + getNum(n - 2)) % div)) % div;
				return memoization[n];
			}
		}
	}
}
// 2020-09-09 01:32 해결
// 일단 백트래킹으로 8까지의 결과값을 알아냄 (Main2 가 백트래킹으로 한거)
// 이후 분석을 통한 점화식을 유도 및 DP방식으로 해결
// 사실 백트래킹으로 8까지 결과 알아낸다음 수열 검색 사이트에 쳐보는거도 있었지만, 
// 이건 내 힘으로 하는게 아니니까 이번은 참았다.
