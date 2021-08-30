import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int v;
		int t;
		int c;
		public Point(int v, int c) {
			this.v = v;
			this.c = c;
			t = -1;
		}
	}
	public static Point[] p;
	public static int[] indegree;
	public static int[] ans;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		
		p = new Point[n + 1];
		ans = new int[n + 1];
		indegree = new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			p[i] = new Point(i, Integer.parseInt(st.nextToken()));
			while (true) {
				int u = Integer.parseInt(st.nextToken());
				if (u == -1)
					break;
				
				adj[u].add(i);
				indegree[i]++;
			}
		}
		
		topologicalSort(n);
		
		for (int i = 1; i <= n; i++)
			sb.append(ans[i] + "\n");
		System.out.println(sb);
	}
	public static void topologicalSort(int v) {
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= v; i++)
			if (indegree[i] == 0) {
				q.add(i);
				ans[i] = p[i].c;
			}
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (int next : adj[curr]) {
				ans[next] = Math.max(ans[next], ans[curr] + p[next].c);
				if (--indegree[next] == 0)
					q.add(next);
			}
		}
	}
}
// 2021-02-17 00:17
// 1005번과 거의 비슷한 문제
// 입력만 조금 다르고 구하는건 같은 수준이다.
// 마찬가지로 위상정렬 된 순서를 이용하는게 아닌,
// 위상정렬의 과정 중 소요 시간으로 DP를 하는 것.
