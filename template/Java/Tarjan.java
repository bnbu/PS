import java.util.*;
import java.io.*;
public class Tarjan {
	public static int v, e, cnt = 0;
	public static int[] discover;
	public static boolean[] scc_chk;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj;
	public static ArrayList<ArrayList<Integer>> scc;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		
		stack = new Stack<>();
		scc = new ArrayList<>();
		adj = new ArrayList[v + 1];
		discover = new int[v + 1];
		scc_chk = new boolean[v + 1];
		for (int i = 0; i <= v; i++) {
			adj[i] = new ArrayList<>();
			discover[i] = -1;
		}
		
		while (e-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
		}
		
		for (int i = 1; i <= v; i++) 
			if (discover[i] == -1)
				dfs(i);
		
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
		
		for (ArrayList<Integer> al : adj)
			System.out.println(al);
		System.out.println(scc);
		
		sb.append(scc.size() + "\n");
		for (ArrayList<Integer> al : scc) {
			for (int i : al)
				sb.append(i + " ");
			sb.append("-1\n");
		}
		System.out.println(sb);
	}
	public static int dfs(int n) {
		int ret = discover[n] = cnt++;
		stack.push(n);
		for (int next : adj[n]) {
			if (discover[next] == -1)
				ret = Math.min(ret, dfs(next));
			else if (!scc_chk[next])
				ret = Math.min(ret, discover[next]);
		}
		
		System.out.println(ret + " " + discover[n]);
		if (ret == discover[n]) {
			ArrayList<Integer> temp = new ArrayList<>();
			while (true) {
				int t = stack.pop();
				scc_chk[t] = true;
				temp.add(t);
				if (t == n)
					break;
			}
			Collections.sort(temp);
			scc.add(temp);
		}
		return ret;
	}
}
// 타잔 알고리즘
// DFS 수행시 생성되는 DFS 트리의 간선 정보를 이용하며 모든 정점 DFS 한번으로 SCC를 구하는 방법
// 모든 정점 DFS를 수행하며 spanning tree를 만들 때 마다
// DFS의 호출 순서에 따라 정점을 stack에 push 시킨다.

// 이후 간선 분류를 통하여 먼저 호출되는 정점이 더 높은 위치(부모)를 가진다고 생각할 때
// 가장 높이 올라갈 수 있는 정점을 찾는다. (dfs의 리턴값으로 활용 가능)
// 이때 n->next 교차간선이지만, next가 아직 SCC가 형성되지 않았다면
// discover[next] 역시 고려해줘야 한다.

// DFS가 끝나기 전 ret와 discover[n]이 같다면
// 이는 사이클의 첫 시작 지점이라는 뜻이므로,
// stack에서 pop하면서 n이 나올때까지 모두 같은 SCC로 분류한다.

// 위상정렬을 이용한 방법으로 생성되는 SCC는 위상정렬의 역순으로 생성된다.

// 시간은 O(V+E)
// 하지만 역방향 그래프를 구현하지 않아도 되며
// 무엇보다 DFS 1번으로 가능하다.