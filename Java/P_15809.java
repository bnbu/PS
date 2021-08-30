import java.io.*;
import java.util.*;
public class Main {
	public static int cnt;
	public static int[] parent;
	public static int[] power;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		cnt = n;
		parent = new int[n + 1];
		power = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			power[i] = Integer.parseInt(br.readLine());
			parent[i] = i;
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int o = Integer.parseInt(st.nextToken()),
				p = Integer.parseInt(st.nextToken()),
				q = Integer.parseInt(st.nextToken());
			
			if (o == 1) 
				union(p, q);
			else 
				war(p, q);
		}
		Arrays.sort(power);
		System.out.println(cnt);
		for (int i = 1; i <= n; i++)
			if (power[i] != 0)
				System.out.print(power[i] + " ");
	}
	public static void war(int a, int b) {
		int x = find(a);
		int y = find(b);
		
		if (power[x] > power[y]) {
			power[x] -= power[y];
			power[y] = 0;
			union(x, y);
		}
		else if (power[x] < power[y]) {
			power[y] -= power[x];
			power[x] = 0;
			union(y, x);
		}
		else {
			power[x] = 0;
			power[y] = 0;
			cnt -= 2;
		}
		
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		parent[y] = x;
		power[x] += power[y];
		power[y] = 0;
		cnt--;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return parent[a] = find(parent[a]);
	}
}
// 2021-01-02 04:26 해결
// 간단한 유니온파인드
