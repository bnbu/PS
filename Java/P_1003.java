import java.util.*;
public class Main {
	public static int[] count;
	public static int[][] memoizationedCount;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			count = new int[2];
			memoizationedCount = new int[41][2];
			int n = scan.nextInt();
			
			setCount(n);
			sb.append(count[0] + " " + count[1] + "\n");
		}
		System.out.println(sb.toString());
	}
	public static void setCount(int n) {
		if (n == 0) {
			count[0]++;
		}
		else if (n == 1) {
			count[1]++;
		}
		else {
			if (memoizationedCount[n][0] != 0 || memoizationedCount[n][1] != 0) {
				count[0] += memoizationedCount[n][0];
				count[1] += memoizationedCount[n][1];
			}
			else {
				setCount(n - 1);
				setCount(n - 2);
				memoizationedCount[n][0] = count[0];
				memoizationedCount[n][1] = count[1];
			}
		}
	}
}
// 2020-09-01 17:58 해결
// 메모이제이션을 활용하는 두번째문제.
