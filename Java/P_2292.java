import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		int count = 1;
		int limit = 1;
		int multi = 0;
		
		while (limit < num) {
			count++;
			multi++;
			limit += multi * 6;
		}
		
		System.out.println(count);
	}
}
// 2020-08-29 해결
