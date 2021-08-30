import java.util.*;
public class Main {
	public static int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
	public static int[] number;
	public static int[] oper;
	public static int current;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		number = new int[n];
		for (int i = 0; i < number.length; i++)
			number[i] = scan.nextInt();
		oper = new int[4]; // 인덱스 순서대로 + - x /
		for (int i = 0; i < oper.length; i++)
			oper[i] = scan.nextInt();
		
		current = number[0];
		dfs(1);
		System.out.println(max);
		System.out.println(min);
	}
	public static void dfs(int depth) {
		// basic
		if (depth == number.length) {
			max = Math.max(max, current);
			min = Math.min(min, current);
			return;
		}
		// recursive
		for (int j = 0; j < oper.length; j++) {
			if (oper[j] != 0) {
				if (j == 0) {
					current += number[depth];
					oper[j]--;
					dfs(depth + 1);
					oper[j]++;
					current -= number[depth];
				} else if (j == 1) {
					current -= number[depth];
					oper[j]--;
					dfs(depth + 1);
					oper[j]++;
					current += number[depth];
				} else if (j == 2) {
					current *= number[depth];
					oper[j]--;
					dfs(depth + 1);
					oper[j]++;
					current /= number[depth];
				} else if (j == 3) {
					int temp = current;
					current /= number[depth];
					oper[j]--;
					dfs(depth + 1);
					oper[j]++;
					current = temp;
				} // 나눗셈은 다시 곱해도 원래로 돌아오는 보장이 없음;
			}
		}
	}
}
// 2020-09-26 21:16 해결
// 가지치기는 없지만, 백트랙 기법으로 모든 연산자를 끼워 넣어본 다음
// 모든 결과값을 구해서 비교함.
