import java.util.*;
public class Main {
	public static int[][] map;
	public static int blue = 0;
	public static int white = 0;
	public static int[] dx = {0, 1, 0, 1};
	public static int[] dy = {0, 0, 1, 1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		scan.nextLine();
		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			String[] s = scan.nextLine().split(" ");
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(s[j]);
		}
		
		quadTree(n, 0, 0);
		System.out.println(white);
		System.out.println(blue);
	}
	public static void quadTree(int n, int x, int y) {
		if (n == 2) {
			if (check(2, x, y)) {
				if (map[y][x] == 1)
					blue++;
				else
					white++;
				return; 
			}
			else
				for (int i = 0; i < 4; i++)
					if (map[y + dy[i]][x + dx[i]] == 1)
						blue++;
					else
						white++;
			return;
		}
		
		if (check(n, x, y)) {
			if (map[y][x] == 1)
				blue++;
			else
				white++;
		}
		else {
			int half = n / 2;
			for (int i = 0; i < 4; i++) {
				if (check(half, x + dx[i] * half, y + dy[i] * half)) {
					if (map[y + dy[i] * half][x + dx[i] * half] == 1)
						blue++;
					else
						white++;
				}
				else
					quadTree(half, x + dx[i] * half, y + dy[i] * half);
			}
		}
	}
	public static boolean check(int n, int x, int y) {
		int temp = map[y][x];
		for (int i = y; i < y + n; i++)
			for (int j = x; j < x + n; j++)
				if (temp != map[i][j])
					return false;
		return true;
	}
}
// 2020-09-30 17:15 해결
// 마찬가지의 분할정복
