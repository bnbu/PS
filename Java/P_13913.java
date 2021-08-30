import java.util.*;
import java.io.*;
public class Main {
	public static class Point {
		int v;
		int t;
		public Point(int v, int t) {
			this.v = v;
			this.t = t;
		}
	}
	public static int[] trace;
	public static int n, k, t = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		trace = new int[200001];
		Arrays.fill(trace, -1);
		
		bfs(n);
		sb.append(t + "\n");
		int temp = k;
		Stack<Integer> stack = new Stack<>();
		while (temp != n) {
			stack.push(temp);
			temp = trace[temp];
		}
		
		sb.append(n + " ");
		while (!stack.isEmpty())
			sb.append(stack.pop() + " ");
		
		System.out.println(sb);
	}
	public static void bfs(int v) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(v, 0));
		trace[v] = v;
		while (!q.isEmpty()) {
			Point curr = q.poll();
			
			if (curr.v == k) {
				t = curr.t;
			}
			
			int walk1 = curr.v + 1,
				walk2 = curr.v - 1,
				teleport = curr.v * 2;
			
			if (walk1 < 200001 && trace[walk1] == -1) {
				q.add(new Point(walk1, curr.t + 1));
				trace[walk1] = curr.v;
			}
			
			if (walk2 >= 0 && trace[walk2] == -1) {
				q.add(new Point(walk2, curr.t + 1));
				trace[walk2] = curr.v;
			}
			
			if (teleport < 200001 && trace[teleport] == -1) {
				q.add(new Point(teleport, curr.t + 1));
				trace[teleport] = curr.v;
			}
		}
	}
}
// 2021-01-04 21:15 해결
// bfs를 이용하여 해결
// 이때 주의점은 현재위치에서 2배하여 2배값에서부터 내려오는게 더 짧은 경우도 있다.
// => 즉, max size를 10만으로 잡으면 위의 경우를 생각할 수 없기 떄문에 20만으로 잡아야한다.
