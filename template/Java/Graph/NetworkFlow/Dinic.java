package D0426;

import java.util.*;
import java.io.*;
class Edge {
	int v, c, f;
	Edge rev;
	public Edge(int v, int c) {
		this.v = v;
		this.c = c;
	}
	public int spare() {
		return c - f;
	}
	public void addFlow(int f) {
		this.f += f;
		rev.f -= f;
	}
}
public class Dinic {
	static int n, total, s, e;
	static int[] level;
	static int[] work;
	static ArrayList<Edge>[] adj;
	static int dfs(int curr, int dest, int flow) {
		// 흘려보내는 유량이 sink에 도달한 경우 정지
		if (curr == dest) return flow;
		
		// 현재 정점에서부터 인접한 정점들을 탐색
		// 단, 이번 단계에서 이미 유망하지 않다고 판단된 간선은 다시 볼 필요가 없으므로
		// work 배열에 몇번째 간선을 확인 할 차례인지를 기록 
		for (; work[curr] < adj[curr].size(); work[curr]++) {
			Edge next = adj[curr].get(work[curr]);
			
			// 다음 정점의 레벨이 1이고 동시에 간선에 여유 용량이 있어야만 유량을 흘릴 수 있다.
			if (level[next.v] != level[curr] + 1) continue;
			if (next.spare() <= 0) continue;
			
			// 이번 정점에서 다음 정점으로 흘릴 유량을 결정해야한다
			// 유량은 sink까지 이어지는 간선들 중 차단간선을 기준으로 흘리게 된다.
			int currFlow = dfs(next.v, dest, Math.min(flow, next.spare()));
			if (currFlow > 0) {
				next.addFlow(currFlow);
				return currFlow;
			} // 흘리게 된 유량이 있으면 이를 반영 후 흘린 유량 값을 반환
		}
		// 유량이 없으면 흘리지 않았다고 0을 반환
		return 0;
	}
	static boolean bfs() {
		Arrays.fill(level, -1);
		level[s] = 0;	// source의 레벨은 0이다.
		Queue<Integer> q = new LinkedList<>();
		q.add(s);
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (Edge next : adj[curr]) {
				// 레벨값이 설정 전이고, 반드시 간선의 용량이 남아있어야 한다.
				if (level[next.v] != -1) continue;
				if (next.spare() <= 0) continue;
				
				// 이렇게 찾은 정점은 다음 레벨 정점으로 표시
				level[next.v] = level[curr] + 1;
				q.add(next.v);
			}
		}
		// sink에 도달이 가능하다면 유량을 흘려 보낼 수 있도록 true 반환 아니면 false
		return level[e] != -1;
	}
	static void dinic() {
		total = 0;
		
		// 레벨 그래프가 만들어질 수 있을 동안만 유량을 흘림
		while (bfs()) {
			// 레벨 그래프가 새로 만들어지면, work 배열도 같이 초기화
			Arrays.fill(work, 0);
			while (true) {
				// 만들어진 레벨그래프 기준으로 source - sink까지 유량을 흘려보낸다
				int flow = dfs(s, e, Integer.MAX_VALUE);
				
				// 유량을 더 이상 흘려보내지 못하면 이번 레벨 그래프에서는 흘릴 수 있을만큼 다 보낸 것
				if (flow == 0) break;
				total += flow;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		s = 0;
		e = n + 1;
		level = new int[e + 1];
		work = new int[e + 1];
		adj = new ArrayList[e + 1];
		for (int i = s; i <= e; i++) adj[i] = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			int v = Integer.parseInt(st.nextToken());
			Edge e1, e2;
			if (v == 1) {
				e1 = new Edge(i, Integer.MAX_VALUE);
				e2 = new Edge(s, 0);
				e1.rev = e2;
				e2.rev = e1;
				adj[s].add(e1);
				adj[i].add(e2);
			} // A (Source - i)
			else if (v == 2) {
				e1 = new Edge(e, Integer.MAX_VALUE);
				e2 = new Edge(i, 0);
				e1.rev = e2;
				e2.rev = e1;
				adj[i].add(e1);
				adj[e].add(e2);
			} // B (i - Sink)
		}
		
//		for (int i = 1; i <= n; i++) {
//			st = new StringTokenizer(br.readLine());
//			for (int j = 1; j <= n; j++) {
//				int c = Integer.parseInt(st.nextToken());
//				if (i == j) continue;
//				Edge e1 = new Edge(j, c),
//					e2 = new Edge(i, 0);
//				e1.rev = e2;
//				e2.rev = e1;
//				adj[i].add(e1);
//				adj[j].add(e2);
//			}
//		} 
		// 양방향이 아니라 단방향이면 이렇게 모두 역간선을 만들어야 함
		
		int[][] w = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				w[i][j] = Integer.parseInt(st.nextToken());
				if (i == j) continue;
			}
		}
		
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				if (i == j) continue;
				Edge e1 = new Edge(j, w[i][j]),
						e2 = new Edge(i, w[j][i]);
				e1.rev = e2;
				e2.rev = e1;
				adj[i].add(e1);
				adj[j].add(e2);
			}
		}
		// 양방향이고 각 방향마다 용량이 따로 정해져 있어서 간선을 각각 설정해줘야 했음
		
		dinic();
		sb.append(total + "\n");
		
		// 이 이후는 P_13161에서 따로 요구하는 각 그룹에 속하는 정점 번호 부분
		
		boolean[] visit = new boolean[e + 1];
		visit[s] = true;
		Queue<Integer> q = new LinkedList<>();
		q.add(s);
		while (!q.isEmpty()) {
			int curr = q.poll();
			for (Edge next : adj[curr]) {
				if (visit[next.v]) continue;
				if (next.spare() <= 0) continue;
				// 간선의 용량이 남아있는 부분만 (꽉 찬 부분은 차단간선으로써, 최대유량 최소컷 정리에 의해 두 부분으로 나누기 위한 컷으로 취급)
				visit[next.v] = true;
				q.add(next.v);
			}
		}
		
		for (int i = 1; i <= n; i++)
			if (visit[i]) sb.append(i + " ");
		sb.append("\n");
		for (int i = 1; i <= n; i++)
			if (!visit[i]) sb.append(i + " ");
		System.out.println(sb);
	}
}
// 디닉 알고리즘
// 각 단계마다 레벨 그래프를 생성하고
// 이 레벨 그래프에서 보낼 수 있는 최대 유량을 매번 보낸다
// 레벨 그래프는 소스를 0레벨로 생각하고
// 0레벨부터 간선 하나식 연결을 하는데
// 반드시 한단계 낮은 레벨에서 한단계 높은 레벨로 이동해야하는 제약이 생기며
// 이떄 간선의 여유용량이 있어야만 이동할 수 있다.

// 레벨 그래프는 bfs로 작성하고
// 유량은 dfs로 흘려보낸다

// O(V^2 * E)
