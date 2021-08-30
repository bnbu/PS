import java.util.*;

public class Main {
	public static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		sb.append((int)Math.pow(2, n) - 1 + "\n");
		hanoi(n, 1, 3, 2);
		System.out.println(sb.toString());
	}
	public static void hanoi(int n, int start, int desti, int via) {
		if (n == 1) {
			printMove(start, desti);
			return;
		}
		else {
			hanoi(n - 1, start, via, desti);
			printMove(start, desti);
			hanoi(n - 1, via, desti, start);
		}
	}
	public static void printMove(int start, int desti) {
		sb.append(start + " " + desti + "\n");
	}
}
// 2020-08-31 19:28 해결
// 포인트는 N개의 하노이 이동은 2번의 N-1 하노이 이동이 동반됨이다.
// 첫번째 N-1개의 하노이 이동은 목적지가 아닌 중간에 다른 곳(경유지)로의 이동
// 첫번째 N-1개의 하노이 이동 이후 N이 이동한다. 이때, 이동한 N은 남은 N-1개의 수보다 큰 수이므로 없는 수로 취급이 가능하다.
// 따라서 다시 경유지를 출발지로하여 다시 N-1개의 하노이 이동을 실시한다.
