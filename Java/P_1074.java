import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), r = scan.nextInt(), c = scan.nextInt();
		int x = (int)Math.pow(2, n) / 2, y = x; // 사분면을 구분할 기준점.
		// x < c && y < r => 4사분면
		// x == c && y < r => 3사분면
		// x < c && y == r => 2사분면
		// x == c && y == r => 1사분면
		
		int result = 0;
		while (n-- > 0) {
			if (x <= c && y <= r) {
				result += Math.pow(4, n) * 3;
				x += (int)Math.pow(2, n) / 2;
				y += (int)Math.pow(2, n) / 2;
			}//4
			else if (x > c && y <= r) {
				result += Math.pow(4, n) * 2;
				x -= (int)Math.pow(2, n) / 2;
				y += (int)Math.pow(2, n) / 2;
			}//3
			else if (x <= c && y > r) {
				result += Math.pow(4, n) * 1;
				x += (int)Math.pow(2, n) / 2;
				y -= (int)Math.pow(2, n) / 2;
			}//2
			else if (x > c && y > r) {
				x -= (int)Math.pow(2, n) / 2;
				y -= (int)Math.pow(2, n) / 2;
			}//1
		}
		System.out.println(result);
	}
}
// 2020-09-17 해결
// 큰 문제 -> 작은 문제로 쪼개면서 시작
// 큰문제 (즉 전체를 4조각) => r,c가 속한 부분 1부분을다시 4조각
// 반복....... 
