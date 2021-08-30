import java.util.*;
import java.io.*;
public class Main {
	public static int[] parent;
	public static int[] truth;
	public static ArrayList<Integer>[] party;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		parent = new int[n + 1];
		for (int i = 1; i <= n; i++)
			parent[i] = i;
		party = new ArrayList[m + 1];
		for (int i = 1; i <= m; i++)
			party[i] = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		int t = Integer.parseInt(st.nextToken());
		truth = new int[t + 1];
		for (int i = 1; i <= t; i++)
			truth[i] = Integer.parseInt(st.nextToken());
		
		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken());
			if (k > 0) {
				int f = Integer.parseInt(st.nextToken());
				party[i].add(f);
				k--;
				while (k-- > 0) {
					int e = Integer.parseInt(st.nextToken());
					party[i].add(e);
					union(f, e);
				}
			}
		}
		
		int cnt = 0;
		
		for (int i = 1; i < party.length; i++) {
			boolean flag = true;
			for (int j : party[i]) {
				for (int k = 1; k < truth.length; k++) {
					if (find(truth[k]) == find(j)) {
						flag = false;
						break;
					}
				}
				if (!flag)
					break;
			}
			if (flag)
				cnt++;
		}
		
		System.out.println(cnt);
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
// 2020-12-30 05:50 해결
// 매 파티 집합 인원들을 유니온시켜놓는다.
// 이후 매 파티 집합인원들과 진실을 알고 있는 인원들의 find값 (최상위개체)가 일치한다면
// 이 파티에서는 진실만을 이야기 해야한다.
// 파티 집합 인원들과 진실을 알고 있는 인원들의 find가 일치하지 않는다면,
// 이 파티에서는 과장하여 이야기 해도 된다.
