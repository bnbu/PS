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
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		parent = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			parent[i] = i;
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				int cost = Integer.parseInt(st.nextToken());
				
				if (j > i) {
					list.add(new Edge(i, j, cost));
				}
			}
		}
		Collections.sort(list);
		long min = 0;
		for (int i = 0; i < list.size(); i++) {
			Edge e = list.get(i);
			if (find(e.s) != find(e.d)) {
				union(e.s, e.d);
				min += e.w;
			}
		}
		System.out.println(min);
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
// 2021-01-10 02:16
// 간단한 MST문제 3 
