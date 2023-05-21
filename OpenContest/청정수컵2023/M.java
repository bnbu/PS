package test;
import java.util.*;
import java.io.*;
public class M {
	static int n, m;
	static char s, e;
	static int[] trace, dist;
	static char[] c;
	static boolean[] visit;
	static Queue<Integer> q;
	static ArrayList<Integer>[] adj;
	static void bfs() {
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (int next : adj[curr]) {
				if (visit[next]) continue;
				visit[next] = true;
				q.add(next);
				trace[next] = curr;
				dist[next] = dist[curr] + 1;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		dist = new int[n + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		trace = new int[n + 1];
		c = new char[n + 1];
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		q = new LinkedList<>();
		Arrays.fill(trace, -1);
		for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		s = st.nextToken().charAt(0);
		e = st.nextToken().charAt(0);
		
		ArrayList<Integer> endList = new ArrayList<>();
		String str = br.readLine();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == s) {
				q.add(i + 1);
				visit[i + 1] = true;
				dist[i + 1] = 0;
			}
			else if (str.charAt(i) == e) {
				endList.add(i + 1);
			}
			c[i + 1] = str.charAt(i);
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		
		for (int i = 1; i <= n; i++)
			Collections.sort(adj[i], (i1, i2) -> {
				return Character.compare(c[i1], c[i2]);
			});
		
		bfs();
		Collections.sort(endList, (i1, i2) -> {
			return Integer.compare(dist[i1], dist[i2]);
		});
		
		int idx = endList.get(0);
		if (trace[idx] == -1) sb.append("Aaak!");
		else {
			Stack<Character> stack = new Stack<>();
			while (idx != -1) {
				stack.push(c[idx]);
				idx = trace[idx];
			}
			while (!stack.isEmpty()) sb.append(stack.pop());
		}
		System.out.println(sb);
	}
}

// 이건 시도 한번만 해보고 I번하다 시간 다날림
// 아마 출발점 - 도착점의 문자가 같은 경우에서 틀린것으로 생각중