package D0425;

import java.util.*;
import java.io.*;
class Edge {
	int v, w, c, f;
	Edge rev;
	public Edge(int v, int w, int c) {
		super();
		this.v = v;
		this.w = w;
		this.c = c;
		f = 0;
		rev = null;
	}
	public int spare() {
		return c - f;
	}
	public void addFlow(int f) {
		this.f += f;
		rev.f -= f;
	}
}
public class MCMF_Adj {
	static int n, m, result, total, s, e;
	static ArrayList<Edge>[] adj;
	static void mcmf() {
		result = 0;
		total = 0;
		while (true) {
			int[] prev = new int[e + 1];
			int[] dist = new int[e + 1];
			Edge[] path = new Edge[e + 1];
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
				for (Edge next : adj[curr]) {
					if (next.spare() <= 0) continue;
					if (dist[next.v] <= dist[curr] + next.w) continue;
					dist[next.v] = dist[curr] + next.w;
					prev[next.v] = curr;
					path[next.v] = next;
					if (!isInQue[next.v]) {
						q.add(next.v);
						isInQue[next.v] = true;
					}
				}
			}
			if (prev[e] == -1) break;
			
			int flow = Integer.MAX_VALUE;
			for (int i = e; i != s; i = prev[i]) flow = Math.min(flow, path[i].spare());
			for (int i = e; i != s; i = prev[i]) {
				result += flow * path[i].w;	// 각 간선의 가중치 * 유량 만큼 비용증가
				path[i].addFlow(flow);
			}
			total += flow;
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
		for (int i = 0; i < adj.length; i++) adj[i] = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			Edge e1 = new Edge(i, 0, Integer.parseInt(st.nextToken())),
				e2 = new Edge(0, 0, 0);
			e1.rev = e2;
			e2.rev = e1;
			adj[0].add(e1);
			adj[i].add(e2);
		}	// source - 사람 연결
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= m; i++) {
			Edge e1 = new Edge(e, 0, Integer.parseInt(st.nextToken())),
				e2 = new Edge(n + i, 0, 0);
			e1.rev = e2;
			e2.rev = e1;
			adj[n + i].add(e1);
			adj[e].add(e2);
		}	// 서점 - sink 연결
		
		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				int w = Integer.parseInt(st.nextToken());
				// 순방향 간선만 용량을 양수로 취급해줘야 한다 (어차피 차단간선들에 의해 용량 제한될 예정)
				// 이 문제(11405)에서만 그런건지는 모르겠음
				Edge e1 = new Edge(n + i, w, Integer.MAX_VALUE), 
					e2 = new Edge(j, -w, 0);
				e1.rev = e2;
				e2.rev = e1;
				adj[j].add(e1);
				adj[n + i].add(e2);
			}	// 사람 - 서점 간 연결
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