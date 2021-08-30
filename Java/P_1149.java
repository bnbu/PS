import java.util.*;
public class Main {
	public static int[][] fee;
	public static int min = Integer.MAX_VALUE;
	public static int[][] memo;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		fee = new int[n][3];
		memo = new int[n][3];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 3; j++)
				fee[i][j] = scan.nextInt();
		}
		
		getMin(n);
		for (int i : memo[n - 1])
			min = min > i ? i : min;
		System.out.println(min);
	}
	public static void getMin(int n) {
		for (int i = 0; i < 3; i++)
			memo[0][i] = fee[0][i];
		
		for (int i = 1; i < n; i++) {
			memo[i][0] = fee[i][0] + memo[i - 1][1] < fee[i][0] + memo[i - 1][2] ?
					fee[i][0] + memo[i - 1][1] : fee[i][0] + memo[i - 1][2];
			memo[i][1] = fee[i][1] + memo[i - 1][0] < fee[i][1] + memo[i - 1][2] ?
					fee[i][1] + memo[i - 1][0] : fee[i][1] + memo[i - 1][2];
			memo[i][2] = fee[i][2] + memo[i - 1][0] < fee[i][2] + memo[i - 1][1] ?
					fee[i][2] + memo[i - 1][0] : fee[i][2] + memo[i - 1][1];
		}
	}	
}
// DP를 이용 2020-09-01 23:07 해결
// 메모이제이션으로 어떤걸 남길지 고민할게 아니라, 3개 동시에 계산하면서 메모이제이션을 하면 된다.
