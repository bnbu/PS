import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = scan.nextInt();

		Arrays.sort(arr);
		int sum = 0;
		int result = 0;
		for (int i : arr) {
			sum += i;
			result += sum;
		}
		
		System.out.println(result);
	}
}
// 2020-09-24 00:59 해결
// 이게 ㄹㅇ 그리디지.
