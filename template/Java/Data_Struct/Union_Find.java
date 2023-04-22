import java.util.*;
import java.io.*;
public class Union_Find {
	public static int parent[]; // 각 값들의 상위 집합의 값 (부모의 값)
	// 초기에는 자기 자신을 부모로 갖는다, 즉 자기 자신이 최상위 개체로써 각각 따로 존재한다
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()), // 1 ~ n까지의 수 
			m = Integer.parseInt(st.nextToken()); // union 혹은 find 연산 수
		
		parent = new int[n + 1];
		for (int i = 1; i <= n; i++)
			parent[i] = i;
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken());
			if (k == 0) {
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken());
				union(a, b);
			} // union 연산 -> b를 a의 하위 개체로 union
			else {
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken());
				if (find(a) == find(b))
					System.out.println("YES");
				else
					System.out.println("NO");
			} // find 연산 b가 a의 개체와 같은 집합에 존재하는지 find
			for (int i = 1; i <= n; i++)
				System.out.print(find(i) + " ");
			System.out.println();
		}
	} 
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y) // 두 집합의 최상위개체가 서로 다르다면, union 시킨다.
			parent[y] = x; // b의 최상위 개체를 a의 최상위 개체의 하위 개체로 변경
	}
	public static int find(int a) {
		if (a == parent[a])
			return a; // a와 a의 부모(상위개체)가 서로 같다면, a 자체가 최상위 개체.
		else {
			return parent[a] = find(parent[a]);
			// a와 a의 상위개체가 다르다면, b(a의 상위개체의 상위개체)를 다시 a의 상위개체로
			// 이러한 작업을 수행하면, a가 속하는 최상위개체의 바로 하위개체로 존재할 수 있다.
			// 이를 계속 반복시키면 초기 O(n)작업 한번으로
			// 이후의 find를 모두 O(1)로 가능.
		}
	}
	/*
	7 10
	0 7 6
	1 2 3 4 5 7 7 
	0 5 7
	1 2 3 4 5 7 5 
	0 4 5
	1 2 3 4 4 7 5 
	0 3 4
	1 2 3 3 4 7 5 
	0 2 3
	1 2 2 3 4 7 5 
	0 1 2
	1 1 2 3 4 7 5 
	1 1 6
	YES
	1 1 1 1 1 1 1 => 최하위에 존재하던 6을 find하는 순간 
	parent[a] = find(parent[a])에 의해 모두 1의 직계 하위 개체로 되는 모습이다.
	*/
}
