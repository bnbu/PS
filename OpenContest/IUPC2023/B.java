import java.util.*;
import java.io.*;
public class B {
	static int n, m, ans;
	static int[][] arr;
	static void dfs(int d, int s, int prev) {
		if (d == n) {
			if (s >= m) ans++;
			return;
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				int currScore = s;
				currScore += j == prev ? arr[i][j] / 2 : arr[i][j];
				dfs(d + 1, currScore, j);
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		arr = new int[2][3];
		for (int i = 0; i < 2; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 3; j++) arr[i][j] = Integer.parseInt(st.nextToken());
		}
		
		dfs(0, 0, -1);
		System.out.println(ans);
	}
}
// 입력 제한이 작아서 완전탐색으로 가능
// 이전날과 같은 날일 경우, 획득하는 점수가 절반이 되는것만 주의
