import java.util.*;
import java.io.*;
public class Main {
	public static class Point implements Comparable<Point>{
		double x;
		double y;
		double w;
		int p_num;
		public Point(double x, double y, double w, int p_num) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.p_num = p_num;
		}
		public int compareTo(Point p) {
			if (this.w > p.w)
				return 1;
			else if (this.w < p.w)
				return -1;
			else
				return 0;
		}
	}
	public static Point[] p;
	public static boolean[] selected;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		selected = new boolean[n + 1];
		p = new Point[n + 1];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			double x = Double.parseDouble(st.nextToken()),
					y = Double.parseDouble(st.nextToken());
			
			p[i] = new Point(x, y, 0, i);
		}
		System.out.println(prim(n));
	}
	public static double prim(int v) {
		double cost = 0;
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(p[1]);
		for (int i = 1; i <= v; i++) {
			Point curr = null;
			double min = 1000000;
			while (!pq.isEmpty()) {
				curr = pq.poll();
				if (!selected[curr.p_num]) {
					min = curr.w;
					break;
				}
			}
			selected[curr.p_num] = true;
			cost += min;
			for (int j = 1; j <= v; j++) {
				if (curr.p_num == j)
					continue;
				
				Point next = p[j];
				double next_w = Math.sqrt(Math.pow(curr.x - next.x, 2) + Math.pow(curr.y - next.y, 2));
				pq.add(new Point(next.x, next.y, next_w, next.p_num));
			}
		}
		return cost;
	}
}
// 2021-01-07 02:31 해결
// 좌표평면이라 간선 위주로는 힘들거같고
// 정점위주로 하는 프림 알고리즘을 사용함.
