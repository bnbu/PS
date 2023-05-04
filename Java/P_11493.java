import java.util.*;
import java.io.*;
class Edge {
	int v, c, f, w;
	Edge rev;
	public Edge(int v, int c, int w) {
		this.v = v;
		this.c = c;
		this.w = w;
	}
	public int spare() {
		return c - f;
	}
	public void addFlow(int f) {
		this.f += f;
		rev.f -= f;
	}
}
public class Main {
	static int n, m, s, e, total, result;
	static boolean[] isWhite, visit; 
	static ArrayList<Edge>[] adj;
	static int[][] w;
	static void floyd() {
		for (int k = 1; k <= n; k++) 
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= n; j++)
					if (w[i][k] != Integer.MAX_VALUE && w[k][j] != Integer.MAX_VALUE)
						w[i][j] = Math.min(w[i][j], w[i][k] + w[k][j]);
	}
	static void mcmf() {
		total = 0;
		result = 0;
		while (true) {
			int[] prev = new int[e + 1];
			int[] dist = new int[e + 1];
			Edge[] path = new Edge[e + 1];
			boolean[] isInQue = new boolean[e + 1];
			Arrays.fill(prev, -1);
			Arrays.fill(dist, Integer.MAX_VALUE);
			dist[s] = 0;
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			while (!q.isEmpty()) {
				int curr = q.poll();
				isInQue[curr] = false;
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
				result += flow * path[i].w;
				path[i].addFlow(flow);
			}
			total += flow;
		}
	}
	public static void main(String[] args) throws IOException {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 StringBuilder sb = new StringBuilder();
		 StringTokenizer st;
		 int t = Integer.parseInt(br.readLine());
		 while (t-- > 0) {
			 st = new StringTokenizer(br.readLine());
			 n = Integer.parseInt(st.nextToken());
			 m = Integer.parseInt(st.nextToken());
			 
			 s = 0; e = 4*n + 1;
			 // 1 ~ n : start
			 // n+1 ~ 2n : start'
			 // 2n+1 ~ 3n : destination
			 // 3n+1 ~ 4n : destination'
			 adj = new ArrayList[e + 1];
			 isWhite = new boolean[e + 1];
			 for (int i = 0; i <= e; i++) adj[i] = new ArrayList<>();
			 
			 visit = new boolean[n + 1];
			 w = new int[n + 1][n + 1];
			 for (int i = 1; i <= n; i++) Arrays.fill(w[i], Integer.MAX_VALUE);
			 
			 
			 while (m-- > 0) {
				 st = new StringTokenizer(br.readLine());
				 int u = Integer.parseInt(st.nextToken()),
					 v = Integer.parseInt(st.nextToken());
				 w[u][v] = w[v][u] = 1;
			 }
			 st = new StringTokenizer(br.readLine());
			 for (int i = 1; i <= n; i++) 
				 isWhite[i] = Integer.parseInt(st.nextToken()) == 1 ? true : false;
			 st = new StringTokenizer(br.readLine());
			 for (int i = 1; i <= n; i++)
				 isWhite[n + i] = Integer.parseInt(st.nextToken()) == 1 ? true : false;
			 
			 floyd();
			 
			 // source - start
			 for (int i = 1; i <= n; i++) {
				 Edge e1 = new Edge(i, Integer.MAX_VALUE, 0),
						 e2 = new Edge(s, 0, 0);
				 e1.rev = e2;
				 e2.rev = e1;
				 adj[s].add(e1);
				 adj[i].add(e2);
			 }
			 
			 // destination - sink
			 for (int i = 1; i <= n; i++) {
				 Edge e1 = new Edge(e, Integer.MAX_VALUE, 0),
						 e2 = new Edge(3*n + i, 0, 0);
				 e1.rev = e2;
				 e2.rev = e1;
				 adj[3*n + i].add(e1);
				 adj[e].add(e2);
			 }
			 
			 // 정점 분할
			 for (int i = 1; i <= n; i++) {
				 if (isWhite[i] == isWhite[n + i]) continue;
				 
				 int u = 0, v = 0;
				 if (isWhite[i] && !isWhite[n + i]) {
					 // start 분할
					 u = i;
					 v = n + i;
				 }
				 else if (!isWhite[i] && isWhite[n + i]) {
					 // destination 분할
					 u = 2*n + i;
					 v = 3*n + i;
				 }
				 Edge e1 = new Edge(v, 1, 0),
						 e2 = new Edge(u, 0, 0);
				 e1.rev = e2;
				 e2.rev = e1;
				 adj[u].add(e1);
				 adj[v].add(e2);
			 }
			 
			 // start - destination
			 for (int i = 1; i <= n; i++) {
				 for (int j = 1; j <= n; j++) {
					 Edge e1 = new Edge(2*n + j, 1, w[i][j]),
							 e2 = new Edge(n + i, 0, -w[i][j]);
					 e1.rev = e2;
					 e2.rev = e1;
					 adj[n + i].add(e1);
					 adj[2*n + j].add(e2);
				 }
			 }
			 mcmf();
			 sb.append(result);
			 sb.append("\n");
		 }
		 System.out.print(sb);
	}
}
// 사실 왜 그런진 모르겠는데
// 색이 서로 다른 정점까지의 거리의 최솟값의 합이  최소이동 횟수가 된다
// 아무튼 한 그래프 4~5개 아무렇게 그려보고 위에 것이 타당하다고 생각이 들어서
// 위의 방법대로 하기로 했다

// 일단 이렇게 하려면 먼저 입력받은 그래프의 모든 정점쌍 최단거리를 알아야 한다
// N이 최대 500이지만 시간제한도 매우 크니 플로이드로 구했다 O(N^3)이지만 8초면 어떻게든 될거라 생각함

// 플로이드로 모든 정점쌍 최단거리를 구했다면
// 이제 기존 정점 n개짜리 그래프를 출발-도착 및 각 정점을 정점분할을 해서
// 총 4n+2개의  네트워크 플로우 그래프로 변형시킨다

// 0은 source, 4n+1은 sink 역할을 하게 되며
// 1 ~ n : start 
// n+1 ~ 2n : start'			(start는 동전 역할)
// 2n+1 ~ 3n : destination
// 3n+1 ~ 4n : destination'		(destination은 정점 역할)

// source - start, destination' - sink를 기본적으로 연결해주며
// 이때의 연결은 용량은 무제한, 가중치는 0으로 설정한다
// 기존에 하던것처럼 마찬가지로 역간선은 반드시 존재해야한다

// 그 다음 색이 서로 다른 정점에서 다른 정점으로의 이동인데
// 이때, 색이 서로 다른 정점은 반드시 쌍으로 존재하므로
// 정점, 동전순으로 흰색-검은색 쪽을 출발로 놓고 시작해도 되고, 반대로 해도 된다.
// 그리고 동전은 반드시 하나뿐이므로 정점분할을 해서 사용한다
// 정점이 흰색 - 동전이 검은색 : start 정점분할 용량 1로 설정 (이외에는 그냥 만들지도 않음)
// 정점이 검은색 - 동전이 흰색 : destination 정점분할 용량 1로 설정 (이외에는 그냥 만들지도 않음)

// 마지막으로 아까 플로이드로 구한 각 정점쌍 가중치를 기반으로
// start' - destination 간 연결을 해주며
// 마찬가지로 동전은 하나만 이동해야 하므로 용량은 1, 가중치는 각각 구해준 것으로 사용한다

// 이렇게 그래프를 모두 만들었으면
// mcmf를 통해 답을 구해낼 수 있다
