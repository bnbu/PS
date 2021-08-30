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
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		while (true) {
			st = new StringTokenizer(br.readLine());
			int m = Integer.parseInt(st.nextToken()), n = Integer.parseInt(st.nextToken());

			if (m == 0 && n == 0)
				break;
			
			list = new ArrayList<>();
			parent = new int[m + 1];
			for (int i = 1; i <= m; i++)
				parent[i] = i;
			int total = 0;
			while (n-- > 0) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken()), d = Integer.parseInt(st.nextToken()),
						w = Integer.parseInt(st.nextToken());

				total += w;
				list.add(new Edge(s, d, w));
			}

			Collections.sort(list);
			int cost = 0;
			for (int i = 0; i < list.size(); i++) {
				Edge curr = list.get(i);
				if (find(curr.s) != find(curr.d)) {
					cost += curr.w;
					union(curr.s, curr.d);
				}
			}

			sb.append(total - cost + "\n");
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
// 2021-01-10 02:00 해결
// 간단한 MST문제 2
