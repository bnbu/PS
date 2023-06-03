import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[n + 2], imos = new int[n + 2];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				e = Integer.parseInt(st.nextToken()),
				k = Integer.parseInt(st.nextToken());
			imos[s] += k;
			imos[e + 1] -= k;
		}

		int curr = 0;
		for (int i = 1; i <= n; i++) {
			curr += imos[i];
			arr[i] += curr;
		}
		for (int i = 1; i <= n; i++) sb.append(arr[i]).append(" ");
		System.out.print(sb);
	}
}
