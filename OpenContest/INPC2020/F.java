import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class F {
	public static int[] arr;
	public static int[] answer;
	public static int cnt = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		arr = new int[10];
		answer = new int[10];
		String[] s = br.readLine().split(" ");
		for (int i = 0; i < 10; i++)
			answer[i] = Integer.parseInt(s[i]);
		
		dfs(0);
		System.out.println(cnt);
	}
	public static void dfs(int depth) {
		//basic;
		if (depth == 10) {
			for (int i = 0; i < 8; i++) {
				if (arr[i] == arr[i + 1] && arr[i + 1] == arr[i + 2])
					return;
			}
			
			int correct = 0;
			for (int i = 0; i < depth; i++) {
				if (arr[i] == answer[i])
					correct++;
			}
			
			if (correct >= 5)
				cnt++;
			
			return;
		}
		//recursive;
		for (int j = 1; j <= 5; j++) {
			arr[depth] = j;
			dfs(depth + 1);
			arr[depth] = 0;
		}
	}
}