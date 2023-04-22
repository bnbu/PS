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
			// 진입차원 1 증가.
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

// 진입차원이 0인 노드부터 넘버링,
// 넘버링은 작은 수부터 올라가며, 넘버링 한 노드는 그래프에서 제거 한거로 취급한다.
// 이후 다시 진입차원이 0인 노드로 반복, 모든 노드가 넘버링 될때까지 반복한다.

// => 1766번 같은 큐에 동시에 여러 원소가 있을때, 그 원소들끼리는
// 위상순서에 영향을 주지 않ㅇ므을 이용한다.