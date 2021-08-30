import java.io.*;
import java.util.*;
public class Main {
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
		}
		
		kosaraju();
		for (int i = 0; i < scc.size(); i++)
			Collections.sort(scc.get(i));
		Collections.sort(scc, new Comparator<ArrayList<Integer>> () {
			public int compare(ArrayList<Integer> al1, ArrayList<Integer> al2) {
				if (al1.get(0) < al2.get(0))
					return -1;
				else
					return 1;
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
// 2021-01-12 18:40 
// SCC (kosaraju)
