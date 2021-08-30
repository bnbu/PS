import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent, money;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		
		money = new int[n + 1];
		parent = new int[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			parent[i] = i;
			money[i] = Integer.parseInt(st.nextToken());
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			union(u, v);
		}
		
		HashSet<Integer> set = new HashSet<>();
		for (int i = 1; i <= n; i++) {
			int p = find(i);
			if (!set.contains(p))
				set.add(p);
		}
		int sum = 0;
		for (int i : set)
			sum += money[i];
		
		if (sum <= k)
			System.out.println(sum);
		else
			System.out.println("Oh no");
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y) {
			if (money[x] > money[y])
				parent[x] = y;
			else
				parent[y] = x;
		}
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return parent[a] = find(parent[a]);
	}
}
// 2021-01-04 20:50 해결
// 최소비용이므로, 친구비가 가장 적은 개체를 최상위 개체가 되게끔 부모 관계를 설정한다.
// 이후 parent가 아닌, find(i)로 해당 개체와 친구가 되기 위해 친구가 되어야 할 최상위 개체를 알아온다.
// 그 개체들을 따로 추려내어 (set을 사용하여 중복되는 경우 배제) 그 친구들과 친구가 되기 위한 비용을 계산
// 계산한 비용이 예산을 초과하는지를 체크.
