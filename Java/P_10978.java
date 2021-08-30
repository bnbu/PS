import java.util.*;
public class Main {
	public static long[] memoization = new long[21];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			sb.append(getNum(n) + "\n");
		}
		System.out.println(sb);
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
				memoization[n] = (n - 1) * ((getNum(n - 1) + getNum(n - 2))); 
				return memoization[n];
			}
		}
	}
}
