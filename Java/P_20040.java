import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static boolean flag;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		parent = new int[n];
		for (int i = 0; i < n; i++)
			parent[i] = i;
		
		int ans = 0;
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			flag = true;
			union(u, v);
			if (!flag && ans == 0) 
				ans = i + 1;
		}
		System.out.println(ans);
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y)
			parent[y] = x;
		else
			flag = false;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return parent[a] = find(parent[a]);
	}
}
// 2020-12-30 06:11 해결
// 유니온 파인드를 사용
// 두 정점을 유니온시, 이미 두 정점의 최상위 개체가 같다면, 이는 사이클이 형성된다고 볼 수 있다.
