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
		while (true) {
			String input;
			if ((input = br.readLine()) == null)
				break;
			
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
				st = new StringTokenizer(br.readLine());
				int i = Integer.parseInt(st.nextToken()),
					j = Integer.parseInt(st.nextToken());
				
				int u = i < 0 ? Math.abs(i) + n : i,
					v = j < 0 ? Math.abs(j) + n : j;
				
				adj[not(u, n)].add(v);
				adj_r[v].add(not(u, n));
				
				adj[not(v, n)].add(u);
				adj_r[u].add(not(v, n));
			}
			adj[not(1, n)].add(1);
			adj_r[1].add(not(1, n));
			
			kosaraju(2*n);
			
			boolean chk = true;
			for (int i = 1; i <= n; i++)
				if (sccNum[i] == sccNum[i + n]) {
					chk = false;
					break;
				}
			
			if (chk)
				sb.append("yes\n");
			else
				sb.append("no\n");
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
		scc.get(idx).add(v);
		sccNum[v] = idx;
		for (int next : adj_r[v])
			if (!visit[next])
				dfs_r(next, idx);
	}
}
// 2021-01-18 21:35 
// 특정 값을 true로 하면서 전체를 true로 할 수 있는지를 알아보는 2-SAT 문제

// 1번은 무조건 합격시켜야 한다 (합격은 true)
// 따라서, 1번이 무조건 합격상태가 되는 절 (x1 V x1)을 추가한다
// 입력으로 치면 1 1 을 추가하고 시작하는 것
// 따라서 1 1은 2-SAT식 표현을 그래프 바꾸면,
// -1 -> 1이 되므로, 이러한 연결관계를 추가시킨 상태에서 SCC형성시
// x와 ㄱx가 서로 같은 SCC에 포함되는지를 체크하면 된다.
