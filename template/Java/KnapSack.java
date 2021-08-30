import java.util.*;
import java.io.*;
public class KnapSack {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static StringBuilder sb = new StringBuilder();
	public static int max = 0;
	public static int[][] memo;
	public static void main(String[] args) throws IOException {
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]), k = Integer.parseInt(s[1]);
		int[] w = new int[n + 1], v = new int[n + 1];
		memo = new int[n + 1][k + 1];
		// n번째 물건을 넣을 수 있고, 무게 제한이 k 일때 최대 가치를 저장하는 배열.
		// 편의상 0번 인덱스는 사용X
		for (int i = 1; i < n + 1; i++) {
			s = br.readLine().split(" ");
			w[i] = Integer.parseInt(s[0]);
			v[i] = Integer.parseInt(s[1]);
		} // 무게와 가치 저장
		
		for (int i = 1; i < memo.length; i++) {
			for (int j = 1; j < memo[i].length; j++) {
				if (w[i] <= j)
					memo[i][j] = Math.max(v[i] + memo[i - 1][j - w[i]], memo[i - 1][j]);
				else
					memo[i][j] = memo[i - 1][j];
				
				max = Math.max(max, memo[i][j]);
			}
		}
		// i번째 물건만 넣을 수 있는 상황이고, 무게 제한은 j라고 하자
		// i번째 물건의 무게 w[i]에 대해 w[i] <= j 면 넣을 수 있는 상황
		// 넣을 수 있으니, 물건을 넣고, 남은 무게 제한 j - w[i]에서
		// 이전 물건 무게 중 j - w[i] 내에 충족하는 최대 가치가 있다면
		// 그것과 더해서 같은 무게제한, 이전번째 물건일때의 최대치와 비교해서
		// 최댓값을 저장한다 (memo[i][j]에는 항상 최댓값만 들어감)
		
		// 무게 제한을 충족시키지 못한다면, 이전번째 같은 무게제한의 최대가치를 가져온다
		// **첫번째 (i == 1)일때는 0번째를 참조하고, 0번째는 알다시피 모두 0임.
		
		// 일련의 과정을 거쳐 n번째 물건, 무게제한이 k일때의 값까지 구한다.
		
		for (int[] arr : memo) {
			for (int i : arr)
				System.out.print(i + " ");
			System.out.println();
		}
		
		System.out.println(max);
	}
}
