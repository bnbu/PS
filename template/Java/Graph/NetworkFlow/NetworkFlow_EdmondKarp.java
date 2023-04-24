import java.util.*;
import java.io.*;
public class NetworFlow_EdmondKarp {
	static final int v = 52;
	static int total, s, e;
	static int[][] c, f;	// capacity, flow
	static ArrayList<Integer>[] adj;
	static int ChartoIdx(char c) {
		if (c <= 'Z') return c - 'A';
		return c - 'a' + 26;
	}
	static void EdmondKarp() {
		// 다음 증가경로를 찾지 못할때까지 반복
		while (true) {
			int[] prev = new int[v];
			Arrays.fill(prev, -1);
			
			Queue<Integer> q = new LinkedList<>();
			q.add(s);	// source 부터 sink까지 증가경로를 BFS로 탐색
			while (!q.isEmpty() && prev[e] == -1) {
				int curr = q.poll();
				for (int next : adj[curr]) {
					// c[i][j]-f[i][j] > 0: i에서 j로 유량을 흘릴 여유가 남았는가? 여유가 없으면 통과
	                		// prev[next] == -1: next 정점을 아직 방문하지 않았는가? 방문했었으면 통과
					if ((c[curr][next] - f[curr][next]) <= 0) continue;
					if (prev[next] != -1) continue;
					q.add(next);
					prev[next] = curr; // 이전에 넘어온 경로
					if (next == e) break; // sink에 도달시 종료
				}
			}
			if (prev[e] == -1) break; // 싱크로 가는 경로가 없을 시 루프 종료
			
			int flow = Integer.MAX_VALUE;
			for (int i = e; i != s; i = prev[i]) 
				flow = Math.min(flow, c[prev[i]][i] - f[prev[i]][i]);
			// 찾은 증가 경로 중 가장 유량 허용치가 낮은 곳만큼의 유량을 체크
			
			for (int i = e; i != s; i = prev[i]) {
				f[prev[i]][i] += flow;
				f[i][prev[i]] -= flow;
			}
			// 찾은 유량만큼 그 경로에 모두 흘려준다, 이때 역방향으로도 흘려주는 것에 주의
			
			total += flow;
			// 총 유량 증가
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		c = new int[v][v];
		f = new int[v][v];
		adj = new ArrayList[v];
		for (int i = 0; i < v; i++) adj[i] = new ArrayList<>();
		
		int n = Integer.parseInt(br.readLine()); // 간선 개수
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int u = ChartoIdx(st.nextToken().charAt(0)),
				v = ChartoIdx(st.nextToken().charAt(0)),
				w = Integer.parseInt(st.nextToken());
			
			c[u][v] = c[v][u] += w; // 각 간선의 용량을 설정 **(이 문제에서는 같은 간선이 여러번 입력 가능하므로 추가)
			adj[u].add(v);
			adj[v].add(u);
			// 역방향 간선까지 반드시 추가
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
// O(VE^2)
