import java.util.*;
import java.io.*;
public class Main {
	public static class Point{
		long x;
		long y;
		int p;
		public Point(int x, int y, int p) {
			this.x = x;
			this.y = y;
			this.p = p;
		}
	}
	public static class Edge implements Comparable<Edge>{
		Point s;
		Point d;
		double w;
		public Edge(Point s, Point d) {
			this.s = s;
			this.d = d;
			w = Math.sqrt(Math.pow(s.x - d.x, 2) + Math.pow(s.y - d.y, 2));
		}
		public int compareTo(Edge e) {
			if (this.w > e.w)
				return 1;
			else if (this.w < e.w)
				return -1;
			else
				return 0;
		}
	}
	public static Point[] p;
	public static int[] parent;
	public static ArrayList<Edge> edges;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		edges = new ArrayList<>();
		parent = new int[n + 1];
		p = new Point[n + 1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()),
				y = Integer.parseInt(st.nextToken());
			
			p[i] = new Point(x, y, i);
			parent[i] = i;
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());

			union(a, b);
		}
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j)
					continue;
				
				edges.add(new Edge(p[i], p[j]));
			}
		}
		Collections.sort(edges);
		
		double cost = 0;
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);
			if (find(e.s.p) != find(e.d.p)) {
				cost += e.w;
				union(e.s.p, e.d.p);
			}
		}
		System.out.printf("%.2f", cost);
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
// 2021-01-07 03:11 해결
// 일단은 크루스칼을 사용하여 해결
// 하지만 프림으로도 해볼만 할 듯 한데 아이디어가 모자람.
