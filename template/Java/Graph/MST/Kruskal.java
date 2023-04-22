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
// ���Ͽ�-���ε带 ���ʷ� �ϴ� ũ�罺Į �˰���, ������ �������� ���ϴ� ���
// ����(Edge)���� ����ġ������ ����
// ���Ľ�Ų �������� ���� ������ �����Ű�� ����.
// �̋�, �ش� ������ �� ������ �̹� ����Ǿ� �ִ� ���(��, ����Ŭ�� ������ ��)�� ����
// �̸� ��� �ݺ��ϸ� �ּ� ���д� Ʈ���� ����� �� �� �ִ�.

// �ð����⵵
// ���� ���� -> O(ElogE)
// ���� ��ȸ �� union-find -> O(1) * E -> O(E);

// => ��������� O(ElogE + E)
// = O(ElogE + E) = O(ElogV^2) = O(2ElogV) = O(ElogV)