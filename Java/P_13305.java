import java.util.*;
import java.io.*;
public class Main {
	public static long[] length;
	public static long[] cost;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		long[] length = new long[n - 1];
		long[] cost = new long[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n - 1; i++)
			length[i] = Long.parseLong(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			cost[i] = Long.parseLong(st.nextToken());
		
		long curr = Long.MAX_VALUE;
		long sum = 0;
		for (int i = 0; i < n - 1; i++) {
			if (curr > cost[i]) {
				curr = cost[i];
			}
			sum += curr * length[i];
		}
		System.out.println(sum);
	}
}
