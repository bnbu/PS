import java.util.*;
import java.io.*;
public class Main {
	public static ArrayList<ArrayList<Integer>> scc;
	public static ArrayList<Integer>[] adj, adj_r;
	public static Stack<Integer> stack;
	public static boolean[] visit;
	public static int[] indegree, sccIndegree;
	public static int v, e;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			st = new StringTokenizer(br.readLine());
			v = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			
			indegree = new int[v + 1];
			visit = new boolean[v + 1];
			adj = new ArrayList[v + 1];
			adj_r = new ArrayList[v + 1];
			for (int i = 1; i <= v; i++) {
				adj[i] = new ArrayList<>();
				adj_r[i] = new ArrayList<>();
			}
			
			while (e-- > 0) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken());
				
				adj[a].add(b);
				adj_r[b].add(a);
				
				indegree[b]++;
			}
			
			kosaraju();
			sccIndegree = new int[scc.size()];
			
			int ans = 0;
			for (int i = 0; i < scc.size(); i++) {
				ArrayList<Integer> curr = scc.get(i);
				if (curr.size() > 1)
					for (int j = 0; j < curr.size(); j++)
						sccIndegree[i] += indegree[curr.get(j)] - 1;
				else 
					sccIndegree[i] = indegree[curr.get(0)];
				
				if (sccIndegree[i] == 0)
					ans++;
			}

			sb.append(ans + "\n");
		}
		System.out.println(sb);
	}
	public static void kosaraju() {
		scc = new ArrayList<>();
		stack = new Stack<>();
		
		Arrays.fill(visit, false);
		
		for (int i = 1; i <= v; i++)
			if (!visit[i])
				dfs(i);
		
		Arrays.fill(visit, false);
		
		int idx = 0;
		while (!stack.isEmpty()) {
			int e = stack.pop();
			if (!visit[e]) {
				scc.add(new ArrayList<>());
				dfs_r(e, idx++);
			}
		}
	}
	public static void dfs(int n) {
		visit[n] = true;
		for (int i = 0; i < adj[n].size(); i++) {
			int next = adj[n].get(i);
			if (!visit[next])
				dfs(next);
		}
		stack.add(n);
	}
	public static void dfs_r(int n, int idx) {
		visit[n] = true;
		scc.get(idx).add(n);
		for (int i = 0; i < adj_r[n].size(); i++) {
			int next = adj_r[n].get(i);
			if (!visit[next])
				dfs_r(next, idx);
		}
	}
}
// 2021-01-12 19:43 해결
// 첫 플래4 문제
// 도미노를 정점으로 치환해서 본다.
// 이후 A -> B인 순서를 간선으로 치환시켰을때 그려치는 그래프로부터
// SCC를 구한 다음 (코사라주를 이용)
// 이 SCC들의 진입차원수를 계산한다.
// 계산 결과 진입차원이 0인 SCC들의 갯수가 쓰러뜨려야 하는 도미노의 개수.
