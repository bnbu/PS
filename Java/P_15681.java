import java.util.*;
import java.io.*;
public class Main {
	public static int[] memo;
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			r = Integer.parseInt(st.nextToken()),
			q = Integer.parseInt(st.nextToken());
		
		
		memo = new int[n + 1];
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		dfs(r);
		
		while (q-- > 0) 
			sb.append(memo[Integer.parseInt(br.readLine())] + "\n");
		System.out.print(sb);
	}
	public static void dfs(int v) {
		visit[v] = true;
		memo[v] = 1;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (!visit[next]) {
				dfs(next);
				memo[v] += memo[next];
			}
		}
	}
}
// 2021-01-15 23:02
// 트리 DP의 기본

