import java.util.*;
import java.io.*;
public class Main {
	public static int[] indegree, ans, w;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		
		w = new int[n + 1];
		indegree = new int[n + 1];
		ans = new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			w[i] = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			while (t-- > 0) {
				int j = Integer.parseInt(st.nextToken());
				adj[j].add(i);
				indegree[i]++;
			}
		}
		
		topologicalSort(n);
		
		int rs = 0;
		for (int i : ans)
			rs = Math.max(rs, i);
		
		System.out.println(rs);
	}
	public static void topologicalSort(int v) {
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= v; i++) 
			if (indegree[i] == 0) {
				q.add(i);
				ans[i] = w[i];
			}
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (int next : adj[curr]) {
				ans[next] = Math.max(ans[next], ans[curr] + w[next]);
				if (--indegree[next] == 0)
					q.add(next);
			}
		}
	}
}
// 2021-02-17 00:50
// 1005번과 마찬가지의 문제
// 단, 모든 작업이 완료되는 시점
// 즉 계산한 작업시간들 중 최대값을 출력해야 한다.
// 이거를 잠깐 놓쳤었다.
