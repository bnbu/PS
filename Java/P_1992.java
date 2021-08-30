import java.util.*;
public class Main {
	public static int[][] map;
	public static int[] dx = {0, 1, 0, 1};
	public static int[] dy = {0, 0, 1, 1};
	public static String compressed = "";
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		scan.nextLine();
		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			String s = scan.nextLine();
			for (int j = 0; j < n; j++)
				map[i][j] = s.charAt(j) - '0';
		}
		
		quadTree(n, 0, 0);
		System.out.println(compressed);
	}
	public static void quadTree(int n, int x, int y) {
		if (n == 1) {
			compressed += map[y][x];
			return;
		}
		if (n == 2) {
			if (check(2, x, y)) {
				compressed += map[y][x];
				return;
			}
			else {
				compressed += "(";
				for (int i = 0; i < 4; i++)
					compressed += map[y + dy[i]][x + dx[i]];
				compressed += ")";
			}
			return;
		}
		if (check(n, x, y)) {
			compressed += map[y][x];
		}
		else {
		compressed += "(";
			int half = n / 2;
			for (int i = 0; i < 4; i++) {
				if (check(half, x + dx[i] * half, y + dy[i] * half))
					compressed += map[y + dy[i] * half][x + dx[i] * half];
				else
					quadTree(half, x + dx[i] * half, y + dy[i] * half);
			}
			compressed += ")";
		}
	}
	public static boolean check(int n, int x, int y) {
		int temp = map[y][x];
		for (int i = y; i < y + n; i++) {
			for (int j = x; j < x + n; j++) {
				if (temp != map[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}
//2020-09-29 22:00 해결
// 큰 문제를 같은 방법의 작은 문제로 쪼개서 해결
