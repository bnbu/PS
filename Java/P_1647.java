import java.util.*;
import java.io.*;
public class Main {
	public static class Edge implements Comparable<Edge>{
		int s;
		int d;
		int w;
		public Edge(int s, int d, int w) {
			this.s = s;
			this.d = d;
			this.w = w;
		}
		public int compareTo(Edge e) {
			return this.w - e.w;
		}
	}
	public static int[] parent;
	public static ArrayList<Edge> list;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		list = new ArrayList<>();
		parent = new int[n + 1];
		for (int i = 1; i <= n; i++)
			parent[i] = i;
		
		long total = 0;
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				d = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			total += w;
			list.add(new Edge(s, d, w));
		}
		Collections.sort(list);
		
		long cost = 0;
		int cnt = 0;
		for (int i = 0; i < list.size(); i++) {
			Edge e = list.get(i);
			if (find(e.s) != find(e.d)) {
				union(e.s, e.d);
				cost += e.w;
				cnt++;
			}
			if (cnt == n - 2)
				break;
		}
		System.out.println(cost);
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
// 2021-01-10 02:30 해결
// 간단한 MST문제였지만,
// 두 마을로 분할, 이떄 마을에는 하나 이상의 집이 존재
// 즉 MST를 만들되, 여기서 연결된 간선을 하나 제거하면
// 최소비용으로 이루어졌음에도 두 마을로 분할할 수 있다.
// 이때 제거하는 간선은 MST를 이루는 간선 중에서도 최대 비용의 간선
// 따라서 MST형성시 n-2개의 간선으로만 형성시키면 된다.
