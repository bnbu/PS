import java.util.*;
import java.io.*;
public class Main {
	public static int[] sccNum, ans;
	public static boolean[] visit;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj, adj_r;
	public static ArrayList<ArrayList<Integer>> scc;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		sccNum = new int[2*n + 1];
		ans = new int[n + 1];
		visit = new boolean[2*n + 1];
		adj = new ArrayList[2*n + 1];
		adj_r = new ArrayList[2*n + 1];
		
		Arrays.fill(ans, -1);
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
		if (chk) {
			for (int i = 0; i < scc.size(); i++) {
				ArrayList<Integer> curr = scc.get(i);
				for (int v : curr) {
					int num = v <= n ? v : v - n;
					if (ans[num] == -1) {
						if (v <= n) 
							ans[num] = 0;
						else
							ans[num] = 1;
					}
				}
			}
			sb.append("1\n");
			for (int i = 1; i <= n; i++)
				sb.append(ans[i] + " ");
		}
		else 
			sb.append("0");
		
		System.out.println(sb);
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
// 2021-01-17 23:16
// 2-SAT의 true 가능 여부와, 가능하다면 각 변수가 어떤값이 되어야 하는지까지 계산
// 계산 방식은 2-SAT를 SCC로 바꾸어 보았을때
// SCC단위로 위상정렬 시킨 순서로 방문시 먼저 방문되는 변수를 false로 설정한다
// x가 먼저 방문되면 x는 false로 (0)
// ㄱx가 먼저 방문되면 x는 true로 (1) 

// 이때 타잔 알고리즘은, dfs를 사용하여 SCC를 형성하므로,
// 형성된 SCC의 역순이 위상정렬된 순서이다.
// **DFS기반 위상정렬은 제일 깊은 곳(더 이상 갈 곳이 없는 정점)부터 번호를 내림차순으로 매긴다.

// 코사라주 알고리즘은 모르겠다
// dfs가 먼저 끝나는 정점순으로 스택에 삽입
// 이후 역방향 그래프에서 스택에서 pop 시키는 순서대로 탐색되는 한 그룹이 SCC를 형성
// dfs한 후 다시 반대로  dfs이므로 어쩌면 자체적으로 위상정렬 된 순서가 아닐까 싶다.

// 위상정렬 범용성면에서 타잔알고리즘을 자주 사용해봐야할 것 같다.
