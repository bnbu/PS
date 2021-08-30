import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int h = scan.nextInt();
			int w = scan.nextInt();
			int n = scan.nextInt();
			int floor = 1;
			int number = 1;
			
			while (n > 1) {
				floor++;
				
				if (floor > h) {
					number++;
					floor = 1;
				}
				n--;
			}
			
			sb.append(Integer.toString(floor) + (number < 10 ? "0" + number : number) + "\n");
		}
		System.out.print(sb.toString());
	}
}
// 2020-08-29 해결
