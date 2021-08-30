import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int x;
		int y;
		int z;
		int n;
		public Point(int x, int y, int z, int n) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.n = n;
		}
	}
	public static class Edge implements Comparable<Edge> {
		Point s;
		Point d;
		int w;
		public Edge(Point s, Point d, int w) {
			this.s = s;
			this.d = d;
			this.w = w;
		}
		public int compareTo(Edge e) {
			return this.w - e.w;
		}
	}
	public static ArrayList<Point> list;
	public static ArrayList<Edge> edges;
	public static int[] parent;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		
		edges = new ArrayList<>();
		parent = new int[n + 1];
		list = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()),
				y = Integer.parseInt(st.nextToken()),
				z = Integer.parseInt(st.nextToken());
			
			parent[i] = i;
				list.add(new Point(x, y, z, i));
		}
		Collections.sort(list, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				return p1.x - p2.x;
			}
		});
		for (int i = 1; i < n; i++) {
			edges.add(new Edge(list.get(i), list.get(i - 1),
					Math.abs(list.get(i).x - list.get(i - 1).x)));
		}
		Collections.sort(list, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				return p1.y - p2.y;
			}
		});
		for (int i = 1; i < n; i++) {
			edges.add(new Edge(list.get(i), list.get(i - 1),
					Math.abs(list.get(i).y - list.get(i - 1).y)));
		}
		Collections.sort(list, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				return p1.z - p2.z;
			}
		});
		for (int i = 1; i < n; i++) {
			edges.add(new Edge(list.get(i), list.get(i - 1),
					Math.abs(list.get(i).z - list.get(i - 1).z)));
		}
		
		Collections.sort(edges);
		
		int cost = 0;
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);
			if (find(e.s.n) != find(e.d.n)) {
				cost += e.w;
				union(e.s.n, e.d.n);
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
// 2021-01-07 04:09 해결
// 터널 연결 비용이 각 좌표의 차 중 최솟값임을 감안하여
// 정점들을 x, y, z축으로 각각 정렬했을때 인접한 좌표들이 유망한 간선이 된다
// 따라서 이 유망한 간선들을 리스트로 만든 후
// 이 간선들로 크루스칼을 사용
// => 멍청하게 불필요한 간선들을 추가하고 있었음;;
