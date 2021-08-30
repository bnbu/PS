import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int a = scan.nextInt(), b = scan.nextInt(), c = scan.nextInt();
		System.out.println(func(a % c, b, c));
	}
	public static long func(int a, int b, int c) {
		if (b == 1)
			return a % c;
		
		if (b % 2 == 0) {
			long temp = func(a, b / 2, c);
			return (temp % c * temp % c) % c;
		}
		else
			return (a % c) * (func(a, b - 1, c) % c) % c;
	}
}
