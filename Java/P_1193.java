import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int x = scan.nextInt();
		int n = 0;
		int dif = 0;
		while (true) {
			int temp = n * (n + 1) / 2;
			if (x <= temp) {
				dif = temp - x;
				break;
			}
			n++;
		}
		if (n % 2 == 0) 
			System.out.println((n - dif) + "/" + (1 + dif));
		else 
			System.out.println((1 + dif + "/" + (n - dif)));
	}
}
// 2020-12-23 03:18 해결
// 옛날의 무식한 방법을 다시 바꿔서 해결
