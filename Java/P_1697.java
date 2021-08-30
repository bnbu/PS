import java.util.*;
public class Main {
	public static int[] map = new int[100001];
	public static boolean[] visit = new boolean[100001];
	public static int[] dx = {-1, 1};
	static class Position {
		int x;
		int t;
		public Position(int x, int t) {
			this.x = x;
			this.t = t;
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), k = scan.nextInt();
		Queue<Position> q = new LinkedList<>();
		
		q.add(new Position(n, 0));
		visit[n] = true;
		int time = 0;
		while (!q.isEmpty()) {
			Position curr = q.poll();
			
			if (curr.x == k) {
				time = curr.t;
				break;
			}
			
			for (int i = 0; i < 2; i++) {
				int nextX = curr.x + dx[i];
				
				if (nextX < 0 || nextX > 100000)
					continue;
				if (visit[nextX])
					continue;
				
				visit[nextX] = true;
				q.add(new Position(nextX, curr.t + 1));
			}
			
			int next2 = curr.x * 2;
			if (next2 < 0 || next2 > 100000)
				continue;
			if (visit[next2])
				continue;
			visit[next2] = true;
			q.add(new Position(next2, curr.t + 1));
		}
		
		System.out.println(time);
	}
}
// 2020-10-10 00:05 해결
// 1차원 직선 위 bfs
// 참고로 범위 0~100000 까지인데 1~100000까지 해가지고 한번 틀린거
// 범위 잘 보고 하도록;
