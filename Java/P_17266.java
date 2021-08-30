import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		int[] light = new int[m];
		for (int i = 0; i < m; i++)
			light[i] = scan.nextInt();
		
		int max = light[0];
		for (int i = 0; i < m - 1; i++) {
			int distance = (light[i + 1] - light[i]) % 2 == 0 ?
					(light[i + 1] - light[i]) / 2 : (light[i + 1] - light[i]) / 2 + 1;
			max = max < distance ? distance : max;
		}
		max = max < n - light[m - 1] ? n - light[m - 1] : max;
		System.out.println(max);
	}
}
//2020-09-07 20:23 해결
// 메모리가 좀 많이 나왔는데 줄일 방법 생각해보자
// 이분 탐색??
