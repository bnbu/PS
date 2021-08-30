import java.util.*;
import java.io.*;
public class Main {
	public static int[] indegree;
	public static int[] ans;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		indegree = new int[n + 1];
		ans = new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			indegree[v]++;
		}
		
		topologicalSort(n);
		
		for (int i = 1; i <= n; i++)
			sb.append(ans[i] + " ");
		sb.append("\n");
		System.out.println(sb);
	}
	public static void topologicalSort(int v) {
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= v; i++) 
			if (indegree[i] == 0) {
				q.add(i);
				ans[i] = 1;
			}
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (int next : adj[curr]) {
				if (--indegree[next] == 0) {
					q.add(next);
					ans[next] = ans[curr] + 1;
				}
			}
		}
	}
}
// 2021-02-17 00:31
// 1005번과 같은 위상정렬 순서를 이용하는게 아닌
// 위상정렬 과정 중 계산하는 문제
