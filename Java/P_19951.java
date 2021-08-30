import java.util.*;
import java.io.*;

public class Main {
	public static int[] prefix;
	public static int[] arr;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		arr = new int[n];
		prefix = new int[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			arr[i] = Integer.parseInt(st.nextToken());
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				k = Integer.parseInt(st.nextToken());
			
			prefix[a - 1] += k;
			prefix[b] += -k;
		}
		
		int k = 0;
		for (int i = 0; i < n; i++) {
			k += prefix[i];
			sb.append(arr[i] + k + " ");
		}
		System.out.println(sb);
	}
}
// 2020-12-21 해결
