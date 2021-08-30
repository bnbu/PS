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
		
		sccNum = new int[2*n + 1];
		visit = new boolean[2*n + 1];
		adj = new ArrayList[2*n + 1];
		adj_r = new ArrayList[2*n + 1];
		
		for (int i = 1; i <= 2*n; i++) {
			adj[i] = new ArrayList<>();
			adj_r[i] = new ArrayList<>();
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken()),
				j = Integer.parseInt(st.nextToken());

			int u = i < 0 ? getNum(Math.abs(i), n) : i,
				v = j < 0 ? getNum(Math.abs(j), n) : j;

			adj[not(u, n)].add(v);
			adj_r[v].add(not(u, n));
			
			adj[not(v, n)].add(u);
			adj_r[u].add(not(v, n));
		}
		
		kosaraju(n);
		
		boolean chk = true;
		for (int i = 1; i <= n; i++) {
			if (sccNum[i] == sccNum[n + i]) {
				chk = false;
				break;
			}
		}
		System.out.println(chk ? 1 : 0);
	}
	public static int getNum(int i, int n) {
		return i < 0 ? Math.abs(i) : n + Math.abs(i);
	}
	public static int not(int i, int n) {
		return i <= n ? i + n : i - n;
	}
	public static void kosaraju(int n) {
		stack = new Stack<>();
		scc = new ArrayList<>();
		for (int i = 1; i <= 2*n; i++)
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
		sccNum[v] = idx;
		scc.get(idx).add(v);
		for (int i = 0; i < adj_r[v].size(); i++) {
			int next = adj_r[v].get(i);
			if (!visit[next])
				dfs_r(next, idx);
		}
	}
}
// 2021-01-17 00:16
// 2-SAT를 SCC로 확장시켜서 보는 문제
//
