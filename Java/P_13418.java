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
	public static ArrayList<Edge> list;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		list = new ArrayList<>();
		while (m-- > -1) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				d = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			list.add(new Edge(s, d, w == 0 ? 1 : 0));
		}
		
		init(n);
		Collections.sort(list);
		
		int min, max;
		int cost = 0;
		for (int i = 0; i < list.size(); i++) {
			Edge e = list.get(i);
			if (find(e.s) != find(e.d)) {
				union(e.s, e.d);
				cost += e.w;
			}
		}
		min = cost;
		
		init(n);
		cost = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			Edge e = list.get(i);
			if (find(e.s) != find(e.d)) {
				union(e.s, e.d);
				cost += e.w;
			}
		}
		max = cost;
		
		System.out.println(max*max - min*min);
	}
	public static void init(int n) {
		parent = new int[n + 1];
		for (int i = 0; i <= n; i++)
			parent[i] = i;
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
// 2021-01-10 18:19 해결
// MST를 2번 만들면 된다
// 이때, 오르막길(0으로 입력)은 가중치를 1로 저장 / 반대로 내리막길 (1로 입력)은 가중치를 0으로 저장
// 1. Minimum-Spanning-Tree를 형성시킨다 => 내리막길 위주의 경로로 형성 (최소 피로도)
// 2. Maximum-Spanning-Tree를 형성시킨다 => 오르막길 위주의 경로로 형성 (최대 피로도)
// 형성시킨 두 MST의 비용의 제곱의 차를 출력
