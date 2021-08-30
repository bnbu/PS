import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		int[] A = new int[n], B = new int[n]; // A 앞 B 뒤
		
		for (int i = 0; i < n; i++) {
			A[i] = scan.nextInt();
			B[i] = scan.nextInt();
		}
		
		for (int i = 0; i < m; i++) {
			int k = scan.nextInt();
			
			for (int j = 0; j < n; j++) {
				if (A[j] <= k) {
					int temp = A[j];
					A[j] = B[j];
					B[j] = temp;
				}
			}
		}
		
		int sum = 0;
		for (int i : A)
			sum += i;
		
		System.out.println(sum);
	}
}
// 2020-09-07 00:27 해결
