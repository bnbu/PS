import java.util.*;
import java.io.*;
class Edge {
	int v, c, f;
	Edge reverse;
	public Edge(int v, int c) {
		this.v = v;
		this.c = c;
		f = 0;
		reverse = null;
	}
	int spare() {
		return c - f;
	}
	void addFlow(int f) {
		this.f += f;
		reverse.f -= f;
	}
}
public class NetworFlow_EdmondKarp_exist_adj_only {
	static final int v = 52;
	static int total, s, e, c, f;
	static ArrayList<Edge>[] adj;
	static int ChartoIdx(char c) {
		if (c <= 'Z') return c - 'A';
		return c - 'a' + 26;
	}
	static void EdmondKarp() {
		while (true) {
			int[] prev = new int[v];
			Edge[] path = new Edge[v];
			Arrays.fill(prev, -1);
			
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			while (!q.isEmpty() && prev[e] == -1) {
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
			for (int i = e; i != s; i = prev[i]) 
				flow = Math.min(flow, path[i].spare());
			
			for (int i = e; i != s; i = prev[i]) 
				path[i].addFlow(flow);
			
			total += flow;
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		adj = new ArrayList[v];
		for (int i = 0; i < v; i++) adj[i] = new ArrayList<>();
		
		int n = Integer.parseInt(br.readLine());
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int u = ChartoIdx(st.nextToken().charAt(0)),
				v = ChartoIdx(st.nextToken().charAt(0)),
				w = Integer.parseInt(st.nextToken());
			
			Edge e1 = new Edge(v, w), e2 = new Edge(u, 0);
			e1.reverse = e2;
			e2.reverse = e1;
			adj[u].add(e1);
			adj[v].add(e2);
		}
		
		total = 0;
		s = ChartoIdx('A');
		e = ChartoIdx('Z');
		EdmondKarp();
		System.out.println(total);
	}
}
// source - sink 까지 보낼 수 있는 유량의 최대치를 구하는 방법
// source 로부터 증가 경로 (아직 보낼 수 있는 여유가 있는 간선들로 이루어진 경로)를 찾는다
// 찾은 증가경로 중 가장 낮은 최대치 유량을 찾아서 그것 만큼 흘려보낸다
// 이 과정을 sink까지 닿는 증가경로를 더 이상 찾지 못할때까지 반복

// 이때, Edmond-Karp는 BFS를 기반으로 증가경로를 찾아낸다
// 존재만 하는 간선만을 리스트로 만들어서 사용하는 방법
