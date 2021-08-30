import java.util.Scanner;
public class Main {
	public static int[] memoization; // 1 => SK 승, 2 => CY 승
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		memoization = new int[n + 1];
		System.out.println(stoneGame(n) == 1 ? "SK" : "CY");
	}
	public static int stoneGame(int n) {
		if (n == 1 || n == 3 || n == 4) 
			return 1;
		else if (n == 2)
			return 2;
		else {
			if (memoization[n] != 0)
				return memoization[n];
			else {
				for (int i = 1; i <= 4; i++) {
					if (i == 2)
						continue;
					
					if (stoneGame(n - i) == 2) {
						memoization[n] = 1;
						return memoization[n];
					}
				}
				memoization[n] = 2;
				return memoization[n];
			}
		}
			
	}
}
// 2020-09-02 13:37 해결
// 둘다 최선의 수로 간다는 뜻은, 하나라도 이기는 수가 있다면 승패가 결정된다는 뜻.
