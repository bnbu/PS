import java.util.*;
public class Main {
	public static int[] arroundY = {0, 1, -1, 0, 0};
	public static int[] arroundX = {0, 0, 0, 1, -1};
	public static int[][] costMap;
	public static boolean[][] chk;
	public static int seed = 3;
	public static int minCost = Integer.MAX_VALUE;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		costMap = new int[n][n];
		chk= new boolean[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				costMap[i][j] = scan.nextInt();
		
		dfs(n, 3);
		System.out.println(minCost);
	}
	public static void dfs(int n, int seed) {
		//base case
		if (seed == 0) {
			int sum = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (chk[i][j]) {
						sum += costMap[i][j];
					}
				}
			}		
//			for (boolean[] arr : chk) {
//				for (boolean b : arr)
//					System.out.print(b ? 1 + " ": 0 + " ");
//				System.out.println();
//			}
//			System.out.println();
			minCost = sum < minCost ? sum : minCost;
			return;
		}
		
		//backtrack
		for (int i = 1; i < n - 1; i++) {
			for (int j = 1; j < n - 1; j++) {
				boolean check = true;
				for (int k = 0; k < 5; k++)  {
					if (chk[i + arroundY[k]][j + arroundX[k]]) {
						check = false;
						break;
					}
				}
				
				if (check) {
					for (int k = 0; k < 5; k++)
						chk[i + arroundY[k]][j + arroundX[k]] = true;
					dfs(n, seed - 1);
					for (int k = 0; k < 5; k++)
						chk[i + arroundY[k]][j + arroundX[k]] = false;
				}
			}
		}
	}
}
// 2020-09-06 해결
// 백트래킹을 좀 잘못 적용 하고 있었나보다.
// 백트래킹을 다시 잘 생각해보자
