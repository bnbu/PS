import java.io.*;
import java.util.StringTokenizer;

public class Main {
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int n, m;
	static int[] arr;
	
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		arr = new int[m];
		
		dfs(0);
		bw.flush();
	}
	
	public static void dfs(int num) throws IOException {
		if (num == m) {
			boolean check = false;
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i] >= arr[i + 1]) {
					check = true;
					break;
				}
			}
			if (!check) {
				for (int i : arr) {
					bw.write(i + " ");
				}
				bw.write("\n");
				return;
			}
			else {
				return;
			}
		}
		
		for (int i = 1; i <= n; i++) {
			arr[num] = i;
			dfs(num + 1);
		}
	}
}

// 2020 08 24 22:57
// 마찬가지로 조건을 정해놓음
