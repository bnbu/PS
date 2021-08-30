import java.util.*;
public class Main {
	public static int sum = 0;
	public static int result = Integer.MIN_VALUE;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[][] memoization = new int[n][];
		int[][] triangle = new int[n][];
		for (int i = 0; i < n; i++) {
			triangle[i] = new int[i + 1];
			memoization[i] = new int[i + 1];
			
			for (int j = 0; j <= i; j++)
				triangle[i][j] = scan.nextInt();
		}
		
		memoization[0][0] = triangle[0][0];
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < triangle[i].length; j++) {
				memoization[i + 1][j] = memoization[i + 1][j] < memoization[i][j] + triangle[i + 1][j] ?
						memoization[i][j] + triangle[i + 1][j] : memoization[i + 1][j];
				memoization[i + 1][j + 1] = memoization[i + 1][j + 1] < memoization[i][j] + triangle[i + 1][j + 1] ?
						memoization[i][j] + triangle[i + 1][j + 1] : memoization[i + 1][j + 1];
			}
		}
		
		int max = Integer.MIN_VALUE;
		for (int i : memoization[n - 1])
			max = max < i ? i : max;
		
		System.out.println(max);
	}
}
// 2020-09-07 해결
// 원래는 아래 => 위 로 그리디 하면서 올라오게 했는데, 이게 같은 숫자가 있으면 한쪽방향 갔다가 돌아오질 못해
// 그래서 택한 방법이 삼각형 크기만큼 memoization용 배열을 선언, 여기에 차례로 더해 내려가면서
// 더 큰값은 교체해가며 최종적으로 마지막줄을 비교해서 최댓값을 구함
