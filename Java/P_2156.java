import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int max = 0;
		int n = scan.nextInt();
		int[] memoization = new int[n];
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = scan.nextInt();
		
		switch(n) {
		default:
			memoization[2] = Math.max(arr[0] + arr[2], arr[1] + arr[2]);
		case 2:
			memoization[1] = arr[0] + arr[1];
		case 1:
			memoization[0] = arr[0];
		}
		
		for (int i = 3; i < n; i++) {
			memoization[i] = arr[i];
			for (int j = 0; j < i - 1; j++) {
				memoization[i] = Math.max(memoization[i], memoization[j] + arr[i]);
				if (j < i - 2)
					memoization[i] = Math.max(memoization[i], arr[i] + arr[i - 1] + memoization[j]);
			}
			max = max < memoization[i] ? memoization[i] : max;
		}
		
		if (max == 0)
			for (int i : memoization)
				max = max < i ? i : max;
		
		System.out.println(max);
	}
}
// 2020-09-10 13:09 해결
// 계단 오르기 문제의 변형버전, 계단 오르기는 점프의 범위가 정해져 있었지만, 여긴 어디서든 어디로 점프가 가능
// 단, 연속된 3개는 불가능
// 바꿔 말하면, 해당칸으로 점프해 올 수 있는 모든 범위를 검사해야한다.
