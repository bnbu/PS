import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] arr = new int[n];
		int[] A = new int[n];
		long result = 0;
		for (int i = 0; i < n; i++) {
			arr[i] = scan.nextInt();
		}
		Arrays.parallelSort(arr);
		if (n % 2 == 0) {
			for (int i = 0; i < n / 2; i++) {
				if (i % 2 == 0) {
					A[n / 2 - 1 - i] = arr[n - 1 - i];
					A[n / 2 + i] = arr[i];
				}
				else {
					A[n / 2 - 1 - i] = arr[i];
					A[n / 2 + i] = arr[n - 1 - i];
				}
			}
			
			for (int i = 0; i < n - 1; i++) {
				result += A[i] < A[i + 1] ? A[i + 1] - A[i] : A[i] - A[i + 1];
			}
			
			for (int i : A)
				System.out.print(i + " ");
			System.out.println();
		}
		else {
			int[] temp = new int[n];
			for (int i = 0; i < n / 2; i++) {
				if (i % 2 == 0) {
					A[n / 2 - 1 - i] = arr[n - 1 - i];
					A[n / 2 + i] = arr[i];
					temp[n / 2 - i] = arr[n - 1 - i];
					temp[n / 2 + i + 1] = arr[i];
				}
				else {
					A[n / 2 - 1 - i] = arr[i];
					A[n / 2 + i] = arr[n - 1 - i];
					temp[n / 2 - i] = arr[i];
					temp[n / 2 + i + 1] = arr[n - 1 - i];
				}
			}
			temp[0] = arr[n / 2];
			A[n - 1] = arr[n / 2];
			
			long tempResult = 0;
			for (int i = 0; i < n - 1; i++) {
				result += A[i] < A[i + 1] ? A[i + 1] - A[i] : A[i] - A[i + 1];
				tempResult += temp[i] < temp[i + 1] ? temp[i + 1] - temp[i] : temp[i] - temp[i + 1];
			}
			result = result < tempResult ? tempResult : result;
			
			for (int i : A)
				System.out.print(i + " ");
			System.out.println();
			for (int i : temp)
				System.out.print(i + " ");
			System.out.println();
		}
		System.out.println(result);
	}
}
// 2020-09-05 00:10 해결.
// 자. 왜 틀렸을까 생각을 해보니, 최대 입력 100만개, 각 값은 -10만~10만까지
// 즉 배열을 구하는 과정에서 분명 int값을 넘어가는게 있을것이다.
// 따라서, result는 long으로 바꿔 선언.
