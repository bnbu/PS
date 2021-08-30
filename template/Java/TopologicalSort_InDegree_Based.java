import java.util.*;
import java.io.*;
public class TopologicalSort_InDegree_Based {
	public static class Point implements Comparable<Point>{
		int number;
		int topol;
		public Point(int n) {
			number = n;
			topol = -1;
		}
		public int compareTo(Point p) {
			return this.topol - p.topol;
		}
	}
	public static Point[] topol;
	public static int[] indegree;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		topol = new Point[n + 1];
		indegree = new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<>();
			topol[i] = new Point(i);
		}
		while(m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			indegree[v]++;
			// �������� 1 ����.
		}
		topologicalSort(n);
		Arrays.sort(topol, 1, topol.length);
		for (int i = 1; i <= n; i++)
			System.out.print(topol[i].number + " ");
	}
	public static void topologicalSort(int n) {
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= n; i++)
			if (indegree[i] == 0)
				q.add(i);
		int cnt = 1;
		while(!q.isEmpty()) {
			int i = q.poll();
			topol[i].topol = cnt++;
			for (int j = 0; j < adj[i].size(); j++)
				if (--indegree[adj[i].get(j)] == 0)
					q.add(adj[i].get(j));
		}
	}
}

// ���������� 0�� ������ �ѹ���,
// �ѹ����� ���� ������ �ö󰡸�, �ѹ��� �� ���� �׷������� ���� �Ѱŷ� ����Ѵ�.
// ���� �ٽ� ���������� 0�� ���� �ݺ�, ��� ��尡 �ѹ��� �ɶ����� �ݺ��Ѵ�.

// => 1766�� ���� ť�� ���ÿ� ���� ���Ұ� ������, �� ���ҵ鳢����
// ��������� ������ ���� �ʤ����� �̿��Ѵ�.