import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int num = scan.nextInt();

		for (int i = 0; i < num; i++) {
			System.out.println(plus(scan.nextInt()));
		}

	}

	public static int plus(int n) {
		if (n == 1) {
			return 1;
		} else if (n == 2) {
			return 2;
		} else if (n == 3) {
			return 4;
		} else {
			return plus(n - 1) + plus(n - 2) + plus(n - 3);
		}
	}
}
// 2020 08 10 01:31 해결
// n의 1,2,3 합의 가지수 F(n)에 대해 F(n) = F(n-1) + F(n-2) + F(n-3)의 관계를 지님을 이용함.
