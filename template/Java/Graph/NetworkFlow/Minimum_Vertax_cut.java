import java.util.*;
import java.io.*;
class Edge {
	int x, y, c, f;
	Edge rev;
	public Edge(int x, int y, int c) {
		this.x = x;
		this.y = y;
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
class Pair {
	int x, y;
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
public class Minimum_Vertax_cut {
	static int n, m, total;
	static boolean chk;
	static Pair s, e;
	static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
	static ArrayList<Edge>[][] adj;
	static void edmondKarp() {
		total = 0;
		while (true) {
			Pair[][] prev = new Pair[n + 1][2*m + 1];
			Edge[][] path = new Edge[n + 1][2*m + 1];
			Queue<Pair> q = new LinkedList<>();
			q.add(s);
			while (!q.isEmpty()) {
				Pair curr = q.poll();
				for (Edge next : adj[curr.y][curr.x]) {
					if (next.spare() <= 0) continue;
					if (prev[next.y][next.x] != null) continue;
					q.add(new Pair(next.x, next.y));
					prev[next.y][next.x] = curr;
					path[next.y][next.x] = next;
					if (next.x == e.x && next.y == e.y) break;
				}
			}
			if (prev[e.y][e.x] == null) break;
			int flow = Integer.MAX_VALUE;
			for (Pair p = e; (p.x != s.x || p.y != s.y); p = prev[p.y][p.x])
				flow = Math.min(flow, path[p.y][p.x].spare());
			for (Pair p = e; (p.x != s.x || p.y != s.y); p = prev[p.y][p.x])
				path[p.y][p.x].addFlow(flow);
			
			if (flow != Integer.MAX_VALUE) total += flow;
			else {
				chk = true;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		adj = new ArrayList[n + 1][2*m + 1];
		for (int i = 1; i <= n; i++) 
			for (int j = 1; j <= 2*m; j++) adj[i][j] = new ArrayList<>();
		
		for (int i = 1; i <= n; i++) {
			String str = br.readLine();
			for (int j = 1; j <= m; j++) {
				char c = str.charAt(j - 1);
				if (c == '#') continue;
				
				if (c == 'K') s = new Pair(j + m, i);	// 출발점 s'
				else if (c == 'H') e = new Pair(j, i); // 도착점 e
				
				Edge e1, e2, e3, e4;
				// v -> v'	(y, x)순 (i, j) <-> (i, j + m) 
				e1 = new Edge(j + m, i, 1);	
				e2 = new Edge(j, i, 0);		// 역간선 용량 0
				e1.rev = e2;
				e2.rev = e1;
				adj[i][j].add(e1);
				adj[i][j + m].add(e2);
				
				for (int k = 0; k < 4; k++) {
					int nextX = j + dx[k],
						nextY = i + dy[k];
					if (nextX <= 0 || nextX > m || nextY <= 0 || nextY > n) continue;
					
					// 상하좌우' -> v, v' -> 상하좌우
					// (nextY, nextX + m) -> (i, j)
					// (i, j + m) -> (nextY, nextX)
					e1 = new Edge(j, i, Integer.MAX_VALUE);
					e2 = new Edge(nextX + m, nextY, 0);  // e1의 역간선
					e3 = new Edge(nextX, nextY, Integer.MAX_VALUE);
					e4 = new Edge(j + m, i, 0);		 	// e3의 역간선
					e1.rev = e2;
					e2.rev = e1;
					e3.rev = e4;
					e4.rev = e3;
					adj[nextY][nextX + m].add(e1);
					adj[i][j].add(e2);
					adj[i][j + m].add(e3);
					adj[nextY][nextX].add(e4);
				}
			}
		}
		edmondKarp();
		System.out.println(chk ? -1 : total);
	}
}
// minimum vertax cut 문제
// 간선을 막는게 아니라
// 정점을 막는 문제
// 정점을 막게 하려면 정점을 2개로 다시 분할시켜서
// v -> v' 으로 가게 그래프를 변형시킨다
// v (진입점), v' (진출점), 이떄 분할시킨 정점간 간선의 용량은 1로 설정한다
// 그 이외의 간선의 용량은 무제한
// 이때, 그래프는 4방향으로 기본적으로 이어지는 꼴이므로

// (상하좌우) -> v 과  v' -> (상하좌우)'
// 이렇게 이어지는 관계를 생각해볼 수 있으므로
// 위와 같이 변형그래프를 모두 모델링 한 다음
// 최대유량 최소컷을 이용하여 최소 정점 컷을 구하면 된다.