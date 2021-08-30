import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int num = scan.nextInt();
		int[] divisors = new int[num];
		for (int i = 0; i < num; i++) {
			divisors[i] = scan.nextInt();
		}
		
		Arrays.sort(divisors);
		
		System.out.print(divisors[0] * divisors[num - 1] + "\n");
	}
}
// 2020 08 10 01:47 해결
