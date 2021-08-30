import java.util.*;
public class Main {
	public static int[][] map;
	public static int[] cnt = new int[3];
	public static int[] dx = {0, 1, 2, 0, 1, 2, 0 ,1, 2};
	public static int[] dy = {0, 0, 0, 1, 1, 1, 2, 2, 2};
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
		
		func(n, 0, 0);
		for (int i : cnt)
			System.out.println(i);
	}
	public static void func(int n, int x, int y) {
		if (n == 1) {
			cnt[map[y][x] + 1]++;
			return;
		}
		if (n == 3) {
			if (check(3, x, y)) 
				cnt[map[y][x] + 1]++;
			else {
				for (int i = 0; i < 9; i++)
					cnt[map[y + dy[i]][x + dx[i]] + 1]++;
			}
			return;
		}
		
		if (check(n, x, y))
			cnt[map[y][x] + 1]++;
		else {
			int next = n / 3;
			for (int i = 0; i < 9; i++) {
				if (check(next, x + dx[i] * next, y + dy[i] * next))
					cnt[map[y + dy[i] * next][x + dx[i] * next] + 1]++;
				else
					func(next, x + dx[i] * next, y + dy[i] * next);
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
// 2020-09-30 17:44 해결
// 쿼드트리를 9개로 쪼개는 방식. 오타땜에 런타임에러 봄
