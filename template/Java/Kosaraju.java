import java.io.*;
import java.util.*;
public class Kosaraju {
	public static boolean[] visit;
	public static ArrayList<ArrayList<Integer>> scc = new ArrayList<>();
	public static ArrayList<Integer>[] adj, reverseAdj;
	public static Stack<Integer> stack;
	public static int v, e;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		
		visit = new boolean[v + 1];
		adj = new ArrayList[v + 1];
		reverseAdj = new ArrayList[v + 1];
		stack = new Stack<>();
		for (int i = 1; i <= v; i++) {
			adj[i] = new ArrayList<>();
			reverseAdj[i] = new ArrayList<>();
		}
		
		while (e-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
			reverseAdj[b].add(a);
			// 정방향 및 역방향 인접리스트 추가
		}
		
		kosaraju();
		for (int i = 0; i < scc.size(); i++)
			Collections.sort(scc.get(i));
		Collections.sort(scc, new Comparator<ArrayList<Integer>> () {
			public int compare(ArrayList<Integer> al1, ArrayList<Integer> al2) {
				if (al1.size() == al2.size()) {
					if (al1.get(0) < al2.get(0))
						return -1;
					else 
						return 1;
				}
				return al2.size() - al1.size();
			}
		});
		
		
		sb.append(scc.size() + "\n");
		for (ArrayList<Integer> al : scc) {
			for (int i : al)
				sb.append(i + " ");
			sb.append("-1\n");
		}
		System.out.println(sb);
	}
	public static void kosaraju() {
		scc = new ArrayList<>();
		
		for (int i = 1; i <= v; i++)
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
	public static void dfs(int n) {
		visit[n] = true;
		for (int i = 0; i < adj[n].size(); i++) {
			int next = adj[n].get(i);
			if (!visit[next])
				dfs(next);
		}
		stack.push(n);
	}
	public static void dfs_r(int n, int idx) {
		visit[n] = true;
		scc.get(idx).add(n);
		for (int i = 0; i < reverseAdj[n].size(); i++) {
			int next = reverseAdj[n].get(i);
			if (!visit[next])
				dfs_r(next, idx);
		}
	}
}

// 코사라주 알고리즘
// 주어지는 방향 그래프와 주어지는 방향 그래프의 방향을 모두 뒤집은 역방향 그래프를 준비한다.
// 방향그래프, 역방향그래프, 스택 이렇게 준비

// 정방향 그래프를 DFS 탐색하여 **수행이 끝나는 순서로 스택에 삽입**
// ** 이때 DFS는 모든 정점에 대해서 수행해야한다.
// 이후, 스택에서 pop하는 순서대로 역방향 그래프에서의 DFS 탐색을 진행한다.
// 이 한 정점에서 수행시 탐색되는 모든 정점들을 같은 SCC로 묶는다.

// DFS로 구현되어서 O(V+E)의 시간 복잡도

// 단, 타잔 알고리즘에 비해 
// 정방향 / 역방향 그래프 2개를 구현해야 하며
// DFS역시 2번을 해야한다.