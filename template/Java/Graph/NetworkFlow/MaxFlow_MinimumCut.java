package D0424;

import java.util.*;
import java.io.*;
class Edge {
	int v, c, f;
	Edge rev;
	public Edge(int v, int c) {
		this.v = v;
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
public class MaxFlow_MinimumCut {
	static int n, m, total;
	static ArrayList<Edge>[] adj;
	static void edmondKarp(int s, int e) {
		total = 0;
		while (true) {
			int[] prev = new int[n + 1];
			Edge[] path = new Edge[n + 1];
			Arrays.fill(prev, -1);
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			while (!q.isEmpty()) {
				int curr = q.poll();
				for (Edge next : adj[curr]) {
					if (next.spare() <= 0) continue;
					if (prev[next.v] != -1) continue;
					q.add(next.v);
					prev[next.v] = curr;
					path[next.v] = next;
					if (next.v == e) break;
				}
			}
			if (prev[e] == -1) break;
			int flow = Integer.MAX_VALUE;
			for (int i = e; i != s; i = prev[i]) flow = Math.min(flow, path[i].spare());
			for (int i = e; i != s; i = prev[i]) path[i].addFlow(flow);
			total += flow;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken()),
				c = Integer.parseInt(st.nextToken());
			Edge e1 = new Edge(b, c), e2 = new Edge(a, c);
			e1.rev = e2;
			e2.rev = e1;
			adj[a].add(e1);
			adj[b].add(e2);
		}
		
		st = new StringTokenizer(br.readLine());
		edmondKarp(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		System.out.println(total);
	}
}
// max flow - minimum cut
// 가중치 그래프에서 두 컴포넌트로 나누기 위해 없애는 간선 가중치의 최소는
// 최대 유량과 같다