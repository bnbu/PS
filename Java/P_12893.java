import java.util.*;
import java.io.*;
public class Main {
	public static boolean chk = true;
	public static int[] parent;
	public static int[] enemy;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		parent = new int[n + 1];
		enemy = new int[n + 1];
		for (int i = 1; i <= n; i++)
			parent[i] = i;

		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			if (find(a) == find(b))
				chk = false;
			
			union(enemy[a], b);
			union(enemy[b], a);
			enemy[a] = b;
			enemy[b] = a;
			
		}
		for (int i = 1; i <= n; i++)
			System.out.print(parent[i] + " ");
		System.out.println();
		
		System.out.println(chk ? 1 : 0);
	}
	public static void union(int a, int b) {
		if (a == 0)
			return;
		
		int x = find(a);
		int y = find(b);
		if (x != y)
			parent[y] = x;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return find(parent[a]);
	}
}
// 2021-01-02 05:23
// parent를 우호관계로 치환시켜 봐야한다.
// a와 b가 적이다 -> a의 기존 적들을 b의 우호관계로 / b의 기존 적들을 a의 우호관계로
// a의 가장 마지막으로 입력받은 적을 기준으로 b와 union.
// (단, 처음 적대관계가 주어지는 경우는 0이므로, 0일때, union은 진행하지 않는다)
// 이 집합은 모두 a를 적으로 두는 집합이 된다. (a의 적만 집합에 포함시키므로)
// b역시 마찬가지이게 된다. 따라서 parent는 결과적으로 같은 적을 두는 우호관계의 집합이 된다.
// 이때, a와 b가 이미 우호관계, 즉 같은 집합에 속한다면 이론은 성립할 수 없다.
