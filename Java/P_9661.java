import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		long n = scan.nextLong();
		System.out.println(n % 5 == 2 || n % 5 == 0 ? "CY" : "SK");
	}
}
// 2020-09-02 21:40 해결
// 이번엔 5번마다 반복임 ㅋㅋ 
