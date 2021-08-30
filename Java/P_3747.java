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
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		String input = "";
		while ((input = br.readLine()) != null) {
			st = new StringTokenizer(input);
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
				int i = Integer.parseInt(st.nextToken()),
					j = Integer.parseInt(st.nextToken());
				
				int u = i < 0 ? n + Math.abs(i) : i,
					v = j < 0 ? n + Math.abs(j) : j;
				
				System.out.println(not(u, n) + " " + not(v, n));
					
				adj[not(u, n)].add(v);
				adj_r[v].add(not(u, n));
				
				adj[not(v, n)].add(u);
				adj_r[u].add(not(v, n));
			}
			
			kosaraju(2*n);
			System.out.println(scc);
			boolean chk = true;
			for (int i = 1; i <= n; i++)
				if (sccNum[i] == sccNum[i + n]) {
					chk = false;
					break;
				}
			
			if (chk)
				sb.append(1 + "\n");
			else
				sb.append(0 + "\n");
		}
		System.out.print(sb);
	}
	public static int not(int i, int n) {
		return i <= n ? i + n : i - n;
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
		for (int next : adj[v])
			if (!visit[next])
				dfs(next);
		stack.push(v);
	}
	public static void dfs_r(int v, int idx) {
		visit[v] = true;
		sccNum[v] = idx;
		scc.get(idx).add(v);
		for (int next : adj_r[v])
			if (!visit[next])
				dfs_r(next, idx);
	}
}
// 2021-01-18 22:32
// 평범한 2-SAT 문제2
// 아니 근데 입력의 끝에 EOF가 아닌거같은데?
// 런타임에러 계속 나길래 그냥 C++로 풀었음
// EOF가 아닌 다른 뭔가가 있는 기분인데 일단 자바로는 런타임에러를 해결을 못함
