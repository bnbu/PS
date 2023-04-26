package D0425;

import java.util.*;
import java.io.*;
public class MCMF_matrix {
	static int n, m, result, s, e;
	static int[][] c, f, w;
	static ArrayList<Integer>[] adj;
	static void mcmf() {
		result = 0;
		while (true) {
			int[] prev = new int[e + 1];
			int[] dist = new int[e + 1];
			boolean[] isInQue = new boolean[e + 1]; // 큐 안에 있는지 여부
			Arrays.fill(prev, -1);
			Arrays.fill(dist, Integer.MAX_VALUE);
			Arrays.fill(isInQue, false);
			dist[s] = 0;
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			while (!q.isEmpty()) {
				int curr = q.poll();
				isInQue[curr] = false; // 큐에서 꺼냄
				for (int next : adj[curr]) {
					if (c[curr][next] - f[curr][next] <= 0) continue;
					if (dist[next] <= dist[curr] + w[curr][next]) continue;
					dist[next] = dist[curr] + w[curr][next];
					prev[next] = curr;
					if (!isInQue[next]) {
						q.add(next);
						isInQue[next] = true;
					}
				}
			}
			if (prev[e] == -1) break;
			
			int flow = Integer.MAX_VALUE;
			for (int i = e; i != s; i = prev[i]) flow = Math.min(flow, c[prev[i]][i] - f[prev[i]][i]);
			for (int i = e; i != s; i = prev[i]) {
				result += flow * w[prev[i]][i];	// 각 간선의 가중치 * 유량 만큼 비용증가
				f[prev[i]][i] += flow;
				f[i][prev[i]] -= flow;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		s = 0;
		e = n + m + 1;
		adj = new ArrayList[e + 1];
		c = new int[e + 1][e + 1];
		f = new int[e + 1][e + 1];
		w = new int[e + 1][e + 1];
		for (int i = 0; i < adj.length; i++) adj[i] = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			c[s][i] = Integer.parseInt(st.nextToken());
			adj[s].add(i);
			adj[i].add(s);
		}
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= m; i++) {
			c[n + i][e] = Integer.parseInt(st.nextToken());
			adj[n + i].add(e);
			adj[e].add(n + i);
		}
		
		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				w[j][n + i] = Integer.parseInt(st.nextToken());
				w[n + i][j] = -w[j][n + i];
				c[j][n + i] = Integer.MAX_VALUE;
				adj[j].add(n + i);
				adj[n + i].add(j);
			}
		}
		mcmf();
		System.out.println(result);
	}
}
 	
// 최대유량을 찾되,
// 매번 찾는 경로가 최단경로(가중치의 합이 최소)

// 최단경로를 찾을 때,
// 유량을 더 흘릴 수 있게 하기 위해
// 역방향으로 흘리는 역방향 간선의 존재때문에 
// 이 경우는 가중치가 음수로 작용해야 하므로
// 음수 가중치까지 고려해야 하기 때문에 밸만포드를 베이스로 하는
// SPFA 알고리즘을 사용