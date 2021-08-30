import java.util.*;
import java.io.*;
public class Main {
	public static int[] sccIndegree, sccNum;
	public static boolean[] visit;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj, adj_r;
	public static ArrayList<ArrayList<Integer>> scc;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		sccNum = new int[n];
		visit = new boolean[n];
		adj = new ArrayList[n];
		adj_r = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adj[i] = new ArrayList<>();
			adj_r[i] = new ArrayList<>();
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
			adj_r[b].add(a);
		}
		
		kosaraju(n);
		
		sccIndegree = new int[scc.size()];
		for (int i = 0; i < scc.size(); i++) {
			ArrayList<Integer> list = scc.get(i);
			for (int curr_v : list) {
				for (int next_v : adj[curr_v])
					if (sccNum[curr_v] != sccNum[next_v])
						sccIndegree[sccNum[next_v]]++;
			}
		}
		
		int cnt = 0;
		for (int i : sccIndegree)
			if (i == 0)
				cnt++;
		
		System.out.println(cnt);
	}
	public static void kosaraju(int n) {
		scc = new ArrayList<>();
		stack = new Stack<>();
		
		for (int i = 0; i < n; i++)
			if (!visit[i])
				dfs(i);
		
		Arrays.fill(visit, false);
		
		int idx = 0;
		while (!stack.isEmpty()) {
			int v = stack.pop();
			if (!visit[v]) {
				scc.add(new ArrayList<>());
				dfs_r(v, idx++);
			}
		}
	}
	public static void dfs(int v) {
		visit[v] = true;
		for (int i = 0; i < adj[v].size(); i++) {
			int next = adj[v].get(i);
			if (!visit[next])
				dfs(next);
		}
		stack.push(v);
	}
	public static void dfs_r(int v, int idx) {
		visit[v] = true;
		scc.get(idx).add(v);
		sccNum[v] = idx;
		for (int i = 0; i < adj_r[v].size(); i++) {
			int next = adj_r[v].get(i);
			if (!visit[next])
				dfs_r(next, idx);
		}
	}
}
// 2021-01-14 18:50
// SCC를 생성 -> 진입차원이 0인 SCC의 수를 카운트
