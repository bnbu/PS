import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		System.out.println(n % 2 == 1 ? "CY" : "SK");
	}
}
// 2020-09-02 20:06 해결
// 홀수 짝수 싸움 에반데2
