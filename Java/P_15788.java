import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		long [][] map = new long[n][n];
		int x = 0, y = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				map[i][j] = scan.nextInt();
				
				if (map[i][j] == 0) {
					x = j;
					y = i;
				}
			}
		long sum = 0;
		for (int i = 0; i < n; i++) {
			sum += map[y == 0 ? 1 : 0][i];
		}
		
		long result = 0;
		boolean check = true;
		if (n % 2 == 1 && (x == y && x == n - 1 - y)) {
			long r = 0, c = 0, d1 = 0, d2 = 0;
			for (int i = 0; i < n; i++) {
				r += map[y][i];
				c += map[i][x];
				d1 += map[i][i];
				d2 += map[i][n - 1 - i];
			}
			if (r != c || c != d1 || d1 != d2 || r != d2)
				check = false;
			else
				result = sum - r;
		}
		else if (x == y) {
			long r = 0, c = 0, d = 0;
			for (int i = 0; i < n; i++) {
				r += map[y][i];
				c += map[i][x];
				d += map[i][i];
			}
			if (r != c || c != d || r != d)
				check = false;
			else
				result = sum - r;
		}
		else if (x == n - 1 - y) {
			long r = 0, c = 0, d = 0;
			for (int i = 0; i < n; i++) {
				r += map[y][i];
				c += map[i][x];
				d += map[i][n - 1 - i];
			}
			if (r != c || c != d || r != d)
				check = false;
			else
				result = sum - r;
		}
		else {
			long r = 0, c = 0;
			for (int i = 0; i < n; i++) {
				r += map[y][i];
				c += map[i][x];
			}
			if (r != c)
				check = false;
			else
				result = sum - r;
		}
		
		if (check) {
			map[y][x] = result;
			
			boolean lastCheck = true;
			long r = 0, c = 0, d1 = 0, d2 = 0;
			for (int i = 0; i < n; i++) {
				r = 0;
				c = 0;
				d1 += map[i][i];
				d2 += map[i][n - 1 - i];
				for (int j = 0; j < n; j++) {
					r += map[i][j];
					c += map[j][i];
				}
				if (r != c || r != sum || c != sum) {
					lastCheck = false;
					break;
				}
			}
			if (d1 != d2 || d1 != sum || d2 != sum) 
				lastCheck = false;
			
			if (lastCheck) 
				System.out.println(result);
			else
				System.out.println(-1);
		}
		else
			System.out.println(-1);
	}
}
// 2020-09-09 00:10 해결
// 아 ㅋㅋ 범위가 10억까지라 합치면서 int값 넘어감, long으로 바꿔야함.
