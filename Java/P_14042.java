import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int q = scan.nextInt(), n = scan.nextInt();
		int[] d = new int[n], p = new int[n];
		
		for (int i = 0; i < 2 * n; i++) {
			if (i < n) d[i] = scan.nextInt();
			else p[i % n] = scan.nextInt(); 
		}
		Arrays.sort(d);
		Arrays.sort(p);
		
		int result = 0;
		if (q == 1)
			for (int i = 0; i < n; i++)
				result += d[i] < p[i] ? p[i] : d[i];
		else if (q == 2)
			for (int i = 0; i < n; i++)
				result += d[i] < p[n - 1 - i] ? p[n - 1 - i] : d[i];
		
		System.out.println(result);
	}
}
// 2020-09-08 22:43 해결
