import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int n = scan.nextInt(), m = scan.nextInt();
		int[][] map = new int[n][m];
		int[][] memoization = new int[n][m];
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				map[i][j] = scan.nextInt();
				memoization[i][j] = map[i][j];
			}
		
		for (int i = 0; i < n; i++) 
			for (int j = 1; j < m; j++)
				memoization[i][j] += memoization[i][j - 1];
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int x1 = scan.nextInt(), y1 = scan.nextInt(), x2 = scan.nextInt(), y2 = scan.nextInt();
			int result = 0;
			
			for (int j = x1 - 1; j <= x2 - 1; j++) {
				if (y1 == 1)
					result += memoization[j][y2 - 1];
				else 
					result += memoization[j][y2 - 1] - memoization[j][y1 - 2];
			}
				
			sb.append(result + "\n");
		}
		System.out.println(sb);
	}
}
