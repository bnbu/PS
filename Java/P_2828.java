import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt(), j = scan.nextInt();
		int[] apple = new int[j];
		for (int i = 0; i < j; i++) {
			apple[i] = scan.nextInt();
		}
		int position = 1;
		int moveLength = 0;
		for (int i = 0; i < apple.length; i++) {
			moveLength += (apple[i] <= position + (m - 1)) && (position <= apple[i]) ?
					0 : ((apple[i] < position) ? position - apple[i] : apple[i] - position - (m - 1));
			position = (apple[i] <= position + (m - 1)) && (position <= apple[i]) ?
					position : ((apple[i] < position) ? apple[i] : apple[i] - (m - 1));
		}
		System.out.println(moveLength);
	}
}
// 2020-09-03 23:58 해결
