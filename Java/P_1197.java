import java.util.*;
import java.io.*;
public class Main {
	public static class Edge implements Comparable<Edge> {
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
	public static ArrayList<Edge> edges;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int v = Integer.parseInt(st.nextToken()),
			e = Integer.parseInt(st.nextToken());
		
		parent = new int[v + 1];
		for (int i = 1; i <= v; i++)
			parent[i] = i;
		
		edges = new ArrayList<>();
		while (e-- > 0) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				d = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			edges.add(new Edge(s, d, w));
		}
		Collections.sort(edges);
		int cost = 0;
		for (int i = 0; i < edges.size(); i++) {
			Edge curr = edges.get(i);
			if (find(curr.s) != find(curr.d)) {
				union(curr.s, curr.d);
				cost += curr.w;
			}
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
// 2021-01-06 20:21 
// MST - 크루스칼 알고리즘 사용
// => 메모리 조금 덜쓰고 시간 조금 더씀
