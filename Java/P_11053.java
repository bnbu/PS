import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] memoization = new int[n];
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = scan.nextInt();
		
		int max = 1;
		memoization[0] = 1;
		for (int i = 1; i < n; i++) {
			memoization[i] = 1;
			for (int j = 0; j <= i; j++) {
				if (arr[i] > arr[j])
					memoization[i] = memoization[i] < 1 + memoization[j] ? 1 + memoization[j] : memoization[i];
			}
			max = max < memoization[i] ? memoization[i] : max;
		}
		System.out.println(max);
	}
}
// 2020-09-10 01:05 해결
// memoization[i] 는 다음의 의미를 가진다
// => i번째 수열을 마지막 부분수열로 넣었을 때, 가장 긴 수열의 길이.
// 다시 말해서, memoization[0] 은 가장 처음이므로 1일수밖에 없다.
// 다시, memoization[1]은 가장 긴 길이가 만약 2가 되려면, arr[1]이 arr[0]보다 커야한다.
// 다시, memoization[i]의 가장 긴 길이는 arr[i]보다 작은 값을 갖는 arr[j] 중
// memoization[j]가 가장 큰 값에 1을 더한것과 같다.
// 이를 반복해 가장 마지막까지 계산을 진행하면, 가장 긴 부분수열의 길이를 얻을 수 있다.
