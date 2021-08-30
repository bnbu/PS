import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static int[] plan;
	public static void main(String[] args) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine()),
			m = Integer.parseInt(br.readLine());
		
		parent = new int[n + 1];
		plan = new int[m];
		for (int i = 1; i <= n; i++)
			parent[i] = i;
		
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				int curr = Integer.parseInt(st.nextToken());
				if (curr == 1) 
					union(i, j);
			}
		}
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < m; i++)
			plan[i] = Integer.parseInt(st.nextToken());
		
		boolean flag = true;
		for (int i = 0; i < m - 1; i++)
			if (find(plan[i]) != find(plan[i + 1])) {
				flag = false;
				break;
			}
		
		if (flag)
			System.out.println("YES");
		else
			System.out.println("NO");
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
		else {
			return parent[a] = find(parent[a]);
		}
	}
}
// 2020-12-30 04:37 해결
// 유니온-파인드를 사용
// 행렬로 유니온 작업을 미리 진행
// 유니온 시킨 것을 토대로 경로 (출발-도착지)를 find하여 두 위치의 find값, 즉 집합의 상위개체가 일치
// 이 경우엔, 같은 집합에 속하므로 어떤식으로든 둘이 닿을 수 있다.
// 따라서 경로 전체의 find값이 일치한다면 가능한 여행경로이다.
