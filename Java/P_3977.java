import java.util.*;
import java.io.*;
public class Main {
	public static int v, e;
	public static int[] indegree, sccIndegree;
	public static boolean[] visit;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj, adj_r;
	public static ArrayList<HashSet<Integer>> scc;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int t = Integer.parseInt(br.readLine());
		while(t-- > 0) {
			st = new StringTokenizer(br.readLine());
			v = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			
			adj = new ArrayList[v];
			adj_r = new ArrayList[v];
			visit = new boolean[v];
			indegree = new int[v];
			for (int i = 0; i < v; i++) {
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
			if (t > 0)
				br.readLine();
			
			kosaraju();
			
			sccIndegree = new int[scc.size()];
			
			int ansIdx = -1;
			boolean chk = true;
			for (int i = 0; i < scc.size(); i++) {
				HashSet<Integer> curr = scc.get(i);
				if (curr.size() > 1) {
					for (int curr_v : curr) {
						for (int j = 0; j < adj[curr_v].size(); j++) {
							int next = adj[curr_v].get(j);
							if (curr.contains(next))
								indegree[next]--;
						}
					}
					
					for (int curr_v : curr)
						sccIndegree[i] += indegree[curr_v];
				}
				else 
					for (int j : curr)
						sccIndegree[i] = indegree[j];
				
				if (ansIdx == -1 && sccIndegree[i] == 0)
					ansIdx = i;
				else if (ansIdx != -1 && sccIndegree[i] == 0)
					chk = false;
			}
			
			if (chk) {
				for (int i : scc.get(ansIdx))
					sb.append(i + "\n");
			}
			else
				sb.append("Confused\n");
			if (t > 0)
				sb.append("\n");
		}
		System.out.println(sb);
	}
	public static void kosaraju() {
		scc = new ArrayList<>();
		stack = new Stack<>();
		
		for (int i = 0; i < v; i++)
			if (!visit[i])
				dfs(i);
		
		Arrays.fill(visit, false);
		
		int idx = 0;
		while (!stack.isEmpty()) {
			int curr = stack.pop();
			if (!visit[curr]) {
				scc.add(new HashSet<>());
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

// 2021-01-12 22:52 해결
// 코사라주로 SCC를 구한다음, 진입차원이 0인 SCC를 찾는다
// 이때, 0인 SCC가 2개 이상이면 모든점에 도달할 수 없다
// SCC의 진입차원 계산 방법에서 시간복잡도가 많이 갈려버린듯 한데
// 이를 더 좋게 계산하는 방법이 없을련지..

// 사용한 방법
// 1.정방향 그래프에서의 각 정점당 진입차원을 계산
// 2.SCC(HashSet으로 구성)의 각 정점에서의 다음 정점이
//	 SCC집합에 속하는 경우 해당 정점의 진입차원을 1 낮춤
// 3.이를 한 SCC에 속하는 정점에 대해 모두 반복한 다음
//	 SCC에 속하는 정점들의 진입차원을 모두 더한 값이 해당 SCC의 진입차원
// 4. 2~3을 모든 SCC에 대해 반복
// 5. 구하게 된 SCC의 진입차원이 0인것의 인덱스를 가져옴. 만약 2개 이상이면 Confused 출력
