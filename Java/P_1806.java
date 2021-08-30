import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), s = scan.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = scan.nextInt();
		
		int start = 0, end = 0, sum = 0, length = Integer.MAX_VALUE;
		while (start != n) {
			if (sum < s) {
				if (end == n) {
					sum -= arr[start++];
				}
				else 
					sum += arr[end++];
			}
			else if (sum >= s) {
				sum -= arr[start++];
				length = length < (end - start + 1) ? length : end - start + 1;
			}
		}
		System.out.println(length == Integer.MAX_VALUE ? 0 : length);
	}
}
