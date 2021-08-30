import java.util.*;
public class Main {
	public static StringBuilder sb = new StringBuilder();
	public static boolean[] visit;
	public static int[] map;
	public static int[] trace;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		map = new int [n + 1];
		trace = new int[n + 1];
		visit = new boolean[n + 1];
		
		bfs(n);
		
		Stack<Integer> st = new Stack<>();
		st.push(1);
		int temp = 1;
		while (temp != n) {
			st.push(trace[temp]);
			temp = trace[temp];
		}
		
		sb.append(st.size() - 1 + "\n");
		while (!st.isEmpty()) 
			sb.append(st.pop() + " ");
		
		System.out.println(sb);
	}
	public static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		visit[start] = true;
		while (!q.isEmpty()) {
			int curr = q.poll();
			
			if (curr - 1 > 1 && !visit[curr - 1]) {
				visit[curr - 1] = true;
				trace[curr - 1] = curr;
				q.add(curr - 1);
			}
			
			if (curr % 3 == 0 && !visit[curr / 3]) {
				visit[curr / 3] = true;
				trace[curr / 3] = curr;
				q.add(curr / 3);
			}
			
			if (curr % 2 == 0 && !visit[curr / 2]) {
				visit[curr / 2] = true;
				trace[curr / 2] = curr;
				q.add(curr / 2);
			}
		}
	}
}
// 2020-10-16 04:02 해결
// bfs로 가는 경로를 추적함
// 경로는 stack에 담아서 다시 역순으로 출력
