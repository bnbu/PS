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
			boolean overlapped = false;
			for (int i = 0; i < arr.length; i++) {
				int temp = arr[i];
				for (int j = 1; j < arr.length; j++) {
					if (j == i) 
						continue;
					else {
						if (temp == arr[j]) {
							overlapped = true;
							break;
						}
					}
				}
			}
			if (!overlapped) {
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
		
		for (int i = 0; i < n; i++) {
			arr[num] = i;
			dfs(num + 1);
		}
	}
}
// 2020 08 24 22:51 해결
// 출력 조건을 겹치는게 없는것이 있는 경우에만 출력하게 끔 함
