import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		parent = new int[n + 1];
		for (int i = 1; i <= n; i++)
			parent[i] = i;
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken()),
				a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			if (k == 0) 
				union(a, b);
			else {
				if (find(a) == find(b))
					sb.append("YES\n");
				else
					sb.append("NO\n");
			}
		}
		System.out.println(sb);
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y)
			parent[y] = x;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else 
			return parent[a] = find(parent[a]);
	}
}
// 유니온-파인드의 기초
