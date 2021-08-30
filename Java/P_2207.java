import java.util.*;
import java.io.*;
public class Main {
	public static int[] sccNum;
	public static boolean[] visit;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj, adj_r;
	public static ArrayList<ArrayList<Integer>> scc;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		sccNum = new int[2*m + 1];
		visit = new boolean[2*m + 1];
		adj = new ArrayList[2*m + 1];
		adj_r = new ArrayList[2*m + 1];
		for (int i = 1; i <= 2*m; i++) {
			adj[i] = new ArrayList<>();
			adj_r[i] = new ArrayList<>();
		}
		
		while (n-- > 0) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken()),
				j = Integer.parseInt(st.nextToken());
			
			int u = i < 0 ? m + Math.abs(i) : i,
				v = j < 0 ? m + Math.abs(j) : j;
				
			adj[not(u, m)].add(v);
			adj_r[v].add(not(u, m));
			
			adj[not(v, m)].add(u);
			adj_r[u].add(not(v, m));
		}
		kosaraju(2*m);
		
		boolean chk = true;
		for (int i = 1; i <= m; i++) {
			if (sccNum[i] == sccNum[m + i]) {
				chk = false;
				break;
			}
		}
		
		if (chk)
			System.out.println("^_^");
		else
			System.out.println("OTL");
	}
	public static int not(int a, int m) {
		return a <= m ? a + m : a - m;
	}
	public static void kosaraju(int n) {
		stack = new Stack<>();
		scc = new ArrayList<>();
		
		for (int i = 1; i <= n; i++)
			if (!visit[i])
				dfs(i);
		
		Arrays.fill(visit, false);
		
		int idx = 0;
		while (!stack.isEmpty()) {
			int curr = stack.pop();
			if (!visit[curr]) {
				scc.add(new ArrayList<>());
				dfs_r(curr, idx++);
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
// 2021-01-18 18:57
// 2-SAT문제
