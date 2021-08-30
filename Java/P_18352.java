import java.util.*;
import java.io.*;

public class Main {
	static ArrayList<Integer>[] adj;
	static boolean[] visit;
	static int[] dist;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken()),
			x = Integer.parseInt(st.nextToken());
			
		adj = new ArrayList[n + 1];
		visit = new boolean[n + 1];
		dist = new int[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();

		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
		}
		bfs(x);
		
		boolean chk = true;
		for (int i = 1; i <= n; i++)
			if (dist[i] == k) {
				sb.append(i + "\n");
				if (chk)
					chk = false;
			}
		
		if (chk)
			sb.append(-1);
		
		System.out.println(sb);
	}
	
	public static void bfs(int x) {
		Queue<Integer> q = new LinkedList<>();
		q.add(x);
		dist[x] = 0;
		while (!q.isEmpty()) {
			int curr = q.poll();
			visit[curr] = true; 
			for (int next : adj[curr]) {
				if (!visit[next]) {
					q.add(next);
					visit[next] = true;
					dist[next] = dist[curr] + 1;
				}
			}
		}
	}
}
// 2021-07-04 해결
