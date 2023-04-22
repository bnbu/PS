import java.util.*;
import java.io.*;
public class Tarjan_2SAT {
	public static int cnt = 0;
	public static int[] discover;
	public static boolean[] scc_chk;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj;
	public static ArrayList<ArrayList<Integer>> scc;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList[2*n + 1];
		discover = new int[2*n + 1];
		scc_chk = new boolean[2*n + 1];
		for (int i = 1; i <= 2*n; i++) {
			adj[i] = new ArrayList<>();
			discover[i] = -1;
		}
		
		while(m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken()),
				j = Integer.parseInt(st.nextToken());
			
			int u = i < 0 ? getNum(Math.abs(i), n) : i,
				v = j < 0 ? getNum(Math.abs(j), n) : j;

			adj[not(u, n)].add(v);
			adj[not(v, n)].add(u);
		}
		
		tarjan(n);
		System.out.println(scc);
	}
	public static int getNum(int i, int n) {
		return i < 0 ? Math.abs(i) : n + Math.abs(i);
	}
	public static int not(int i, int n) {
		return i <= n ? i + n : i - n;
	}
	public static void tarjan(int n) {
		stack = new Stack<>();
		scc = new ArrayList<>();
		for (int i = 1; i <= 2*n; i++) 
			if (discover[i] == -1)
				dfs(i);
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