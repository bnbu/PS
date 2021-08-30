import java.io.*;
public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int[] prefix;
	public static void main(String[] args) throws NumberFormatException, IOException {
		int n = Integer.parseInt(br.readLine());
		prefix = new int[n + 1];
		String[] s = br.readLine().split(" ");
		for (int i = 1; i <= n; i++) { 
			int curr = Integer.parseInt(s[i - 1]);
			prefix[i] = Math.max(prefix[i - 1] + curr, curr);
		}
		int max = Integer.MIN_VALUE;
		for (int i = 1; i < prefix.length; i++)
			max = Math.max(max, prefix[i]);
		
		System.out.println(max);
	}
}
// 2020-10-08 19:05 해결
// 연속 부분합을 메모이제이션,
// 연속합을 더한 값과 현재값(즉, 현재값부터 다시 연속합을 시작)중 더 큰 값을 저장한다.
