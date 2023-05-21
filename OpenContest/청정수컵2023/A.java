import java.util.*;
import java.io.*;
public class A {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int ans = 0;
		for (int i = 1; i <= n; i++) 
			ans = Math.max(ans, arr[i] - (n+1 - i));
		
		System.out.println(ans);
	}
}
// 특정 한 칸에서 모두 수확한 후
// 이후 집까지 갈때마다 1칸당 1개씩 잃어버림

// 매 칸마다 (현재의 칸의 값 - 집까지의 거리) 를 한 값이 가장 큰 값을 구하면 된다.
