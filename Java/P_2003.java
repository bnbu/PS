import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = scan.nextInt();
		
		int start = 0, end = 0, sum = 0, cnt = 0;;
		while (start != n) {
			if (sum < m) {
				if (end == n) {
					sum -= arr[start];
					start++;
				}
				else {
					sum += arr[end];
					end++;
				}
			}
			else if (sum > m) {
				sum -= arr[start];
				start++;
			}
			else {
				cnt++;
				sum -= arr[start];
				start++;
			}
		}
		System.out.println(cnt);
	}
}
// 2020-09-12 해결
// 부분합 보다 쉬운버젼
