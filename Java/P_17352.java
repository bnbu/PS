import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		parent = new int[n + 1];
		for (int i = 1; i <= n; i++)
			parent[i] = i;
		for (int i = 0; i < n - 2; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			union(a, b);
		}
		
		for (int i = 1; i < n; i++)
			if (find(i) != find(i + 1)) {
				System.out.println(i + " " + (i + 1));
				break;
			}
		
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
// 2021-01-02 06:00
// 어차피 끊기는 부분이 반드시 존재하므로
// 유니온 작업 이후, 최상위 개체가 일치하지 않는 첫 부분을 출력시킨다.
