import java.util.*;
import java.io.*;
public class I {
	static int n, k;
	static int[] dist;
	static void bfs() {
		Queue<Integer> q = new LinkedList<>();
		q.add(0);
		dist[0] = 0;
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			
			int next = curr + 1;
			if (next <= n && dist[next] == -1) {
				q.add(next);
				dist[next] = dist[curr] + 1;
			}
			next = curr + (curr / 2);
			if (next <= n && dist[next] == -1) {
				q.add(next);
				dist[next] = dist[curr] + 1;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		dist = new int[1000001];
		Arrays.fill(dist, -1);
		bfs();
		System.out.println(dist[n] <= k ? "minigimbob" : "water");
	}
}
// 아
// 어차피 1 동작에 의해 1씩 증가가 가능해가지고
// N까지의 최단거리 구하고, k값이 그 최단거리보다 크기만 하면 어떻게든 k번만에 들어갈 수는 있음
// 이거를 놓쳐가지고
