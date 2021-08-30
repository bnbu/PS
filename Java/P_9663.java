import java.util.*;
public class Main {
	public static int cnt = 0;
	public static int n;
	public static int[] queen;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		n = scan.nextInt();
		queen = new int[n];
		dfs(0);
		System.out.println(cnt);
	}
	public static void dfs(int r) {
		if (r == n)
			cnt++;
		for (int i = 0; i < n; i++) {
			if (check(i, r)) {
				queen[r] = i;
				dfs(r + 1);
				queen[r] = 0;
			}
		}
	}
	public static boolean check(int x, int y) {
		for (int i = 0; i < y; i++) {
			if (queen[i] == x)
				return false;
			
			if (Math.abs(x - queen[i]) == Math.abs(y - i))
				return false;
		}
		return true;
	}
}

// 체스판을 2차원 배열로 생각하지말고,
// 어쨌든 퀸은 각 행/열에 1개밖에 못오니까
// 각 행에 놓인 퀸의 위치로 생각해보자.
// 그러면 각 행에는 1개만 놓이고, 또한 그 값이 같다면 같은 열에 있다는 뜻이 되니까 제외
// 그리고 대각선은 기울기의 절댓값이 1, 즉 두 퀸간 좌표의 기울기를 생각해보면 되려나?
// 참고로 스택 같은거 쓰니까 시간초과 남. 그 14일때 케이스 돌려봐도
// 그냥 눈으로 보기에도 더 오래 걸리긴 함. 왜 그럴까?
// 2020-09-25 23:41 일단은 해결
