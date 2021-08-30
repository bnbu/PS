import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int v;
		int w;
		public Point(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
	public static int f, s, g, u, d, cnt = Integer.MAX_VALUE;
	public static boolean chk = false;
	public static boolean[] visit;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		f = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());
		g = Integer.parseInt(st.nextToken());
		u = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		visit = new boolean[f + 1];
		
		bfs(s);
		if (chk)
			System.out.println(cnt);
		else
			System.out.println("use the stairs");

	}
	public static void bfs(int v) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(v, 0));
		visit[v] = true;
		while (!q.isEmpty()) {
			Point curr = q.poll();
			
			if (curr.v == g) {
				chk = true;
				cnt = Math.min(cnt, curr.w);
			}
			
			int next_u = curr.v + u,
				next_d = curr.v - d;
			
			if (next_u <= f && !visit[next_u]) {
				q.add(new Point(next_u, curr.w + 1));
				visit[next_u] = true;
			}
			if (next_d > 0 && !visit[next_d]) {
				q.add(new Point(next_d, curr.w + 1));
				visit[next_d] = true;
			}
		}
	}
}
// 2021-01-04 20:20 해결
// 간단한 그래프탐색문제
