import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		long n = scan.nextLong();
		System.out.println(n % 7 == 1 || n % 7 == 3 ? "CY" : "SK");
	}
}
// 2020-09-02 13:49 해결
// 패턴이 7번 단위로 반복됨. 이를 이용해 값을 7로 나눠 나머지로 분석함.
