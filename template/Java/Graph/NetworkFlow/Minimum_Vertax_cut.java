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
				
				if (c == 'K') s = new Pair(j + m, i);	// ����� s'
				else if (c == 'H') e = new Pair(j, i); // ������ e
				
				Edge e1, e2, e3, e4;
				// v -> v'	(y, x)�� (i, j) <-> (i, j + m) 
				e1 = new Edge(j + m, i, 1);	
				e2 = new Edge(j, i, 0);		// ������ �뷮 0
				e1.rev = e2;
				e2.rev = e1;
				adj[i][j].add(e1);
				adj[i][j + m].add(e2);
				
				for (int k = 0; k < 4; k++) {
					int nextX = j + dx[k],
						nextY = i + dy[k];
					if (nextX <= 0 || nextX > m || nextY <= 0 || nextY > n) continue;
					
					// �����¿�' -> v, v' -> �����¿�
					// (nextY, nextX + m) -> (i, j)
					// (i, j + m) -> (nextY, nextX)
					e1 = new Edge(j, i, Integer.MAX_VALUE);
					e2 = new Edge(nextX + m, nextY, 0);  // e1�� ������
					e3 = new Edge(nextX, nextY, Integer.MAX_VALUE);
					e4 = new Edge(j + m, i, 0);		 	// e3�� ������
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
// minimum vertax cut ����
// ������ ���°� �ƴ϶�
// ������ ���� ����
// ������ ���� �Ϸ��� ������ 2���� �ٽ� ���ҽ��Ѽ�
// v -> v' ���� ���� �׷����� ������Ų��
// v (������), v' (������), �̋� ���ҽ�Ų ������ ������ �뷮�� 1�� �����Ѵ�
// �� �̿��� ������ �뷮�� ������
// �̶�, �׷����� 4�������� �⺻������ �̾����� ���̹Ƿ�

// (�����¿�) -> v ��  v' -> (�����¿�)'
// �̷��� �̾����� ���踦 �����غ� �� �����Ƿ�
// ���� ���� �����׷����� ��� �𵨸� �� ����
// �ִ����� �ּ����� �̿��Ͽ� �ּ� ���� ���� ���ϸ� �ȴ�.