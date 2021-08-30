import java.util.*;
import java.io.*;
public class Main {
	public static boolean[] visit;
	public static ArrayList<Integer>[] adj;
	public static long d = 0, g = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		visit = new boolean[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			adj[u].add(v);
			adj[v].add(u);
		}
		
		bfs();
		
		if (d == g * 3) 
			System.out.println("DUDUDUNGA");
		else if (d > g * 3)
			System.out.println("D");
		else
			System.out.println("G");
	}
	public static void bfs() {
		Queue<Integer> q = new LinkedList<>();
		q.add(1);
		visit[1] = true;
		while (!q.isEmpty()) {
			int curr = q.poll();
			
			for (int i = 0; i < adj[curr].size(); i++) {
				int linked = adj[curr].get(i);
				if (visit[linked])
					continue;
				
				d += (adj[curr].size() - 1) * (adj[linked].size() - 1);
			}

			if (adj[curr].size() >= 3)
				g += combi(adj[curr].size());
			
			for (int i = 0; i < adj[curr].size(); i++) {
				int next = adj[curr].get(i);
				if (!visit[next]) {
					q.add(next);
					visit[next] = true;
				}
			}
		}
	}
	public static long combi(int n) {
		long a = 1, b = 1;
		for (int i = 3; i >= 1; i--) {
			a = a * n--;
			b = b * i;
		}
		return a / b;
	}
}
// 2021-01-10 20:25 해결
// ㄷ-트리 -> 연결시킨 두 정점의 자식수를 각각 n, m이라 하면, (n-1)*(m-1) 이다.
// ㅈ-트리 -> 한 정점의 자식 수 k >= 3 일때, kC3 이다.
// 이를 매 bfs마다 계산하여 각각 d, g로 카운트

// d == 3*g 이면 ㄷㄷㄷㅈ
