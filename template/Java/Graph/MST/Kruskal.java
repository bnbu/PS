import java.util.*;
import java.io.*;
public class Kruskal {
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
	public static int cost = 0;
	public static ArrayList<Edge> edgeList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int v = Integer.parseInt(st.nextToken()),
			e = Integer.parseInt(st.nextToken());
		
		parent = new int[v + 1];
		for (int i = 1; i <= v; i++)
			parent[i] = i;
		edgeList = new ArrayList<>();
		while (e-- > 0) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				d = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			edgeList.add(new Edge(s, d, w));
		}	
		Collections.sort(edgeList);
		
		for (int i = 0; i < edgeList.size(); i++) {
			Edge curr = edgeList.get(i);
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
// 유니온-파인드를 기초로 하는 크루스칼 알고리즘, 간선을 중점으로 구하는 방법
// 간선(Edge)들을 가중치순으로 정렬
// 정렬시킨 간선들을 작은 값부터 연결시키기 시작.
// 이떄, 해당 간선의 두 정점이 이미 연결되어 있는 경우(즉, 사이클이 형성될 시)는 제외
// 이를 모두 반복하면 최소 스패닝 트리의 비용을 알 수 있다.

// 시간복잡도
// 간선 정렬 -> O(ElogE)
// 간선 순회 및 union-find -> O(1) * E -> O(E);

// => 결과적으로 O(ElogE + E)
// = O(ElogE + E) = O(ElogV^2) = O(2ElogV) = O(ElogV)