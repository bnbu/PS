import java.util.*;
import java.io.*;
public class Main {
	public static int[] prefix;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		prefix = new int[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) 
			prefix[i] = prefix[i - 1] + Integer.parseInt(st.nextToken());
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken()),
				j = Integer.parseInt(st.nextToken());
			
			sb.append((prefix[j] - prefix[i - 1]) + "\n");
		}
		System.out.print(sb);
	}
}
