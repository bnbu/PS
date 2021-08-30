import java.util.*;
import java.io.*;
public class Main {
	public static boolean[] visit;
	public static int ans = Integer.MAX_VALUE;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		visit = new boolean[n + 1];
		bfs(n);
		System.out.println(ans);
	}
	public static void bfs(int n) {
		Queue<Integer> q = new LinkedList<>();
		q.add(n);
		q.add(0);
		visit[n] = true;
		while (!q.isEmpty()) {
			int curr = q.poll();
			int num = q.poll();
			
			if (curr == 1) 
				ans = Math.min(ans, num);
			
			int next = curr - 1;
			if (next > 0 && !visit[next]) {
				q.add(next);
				q.add(num + 1);
				visit[next] = true;
			}
			if (curr % 3 == 0) {
				next = curr / 3;
				if (next > 0 && !visit[next]) {
					q.add(next);
					q.add(num + 1);
					visit[next] = true;
				}
			}
			if (curr % 2 == 0) {
				next = curr / 2;
				if (next > 0 && !visit[next]) {
					q.add(next);
					q.add(num + 1);
					visit[next] = true;
				}
			}
		}
	}
}
// 2020-09-13 일단은 해결
// 일단 홀수 (3으로 나눠지던 말던)면 바로 이전의 수에서 1을 더한것이 기본이다.
// 만약 3의 배수라면? 이전값과 3의 배수를 비교해본다. 더 작은게 값으로 인정됨
// 마찬가지로 짝수면? 대부분의 경우엔 2로 나눈 값이 더 작겠지만 같은 것으로 비교한다.
// 하지만 3의 배수이면서 2의 배수라면? 둘다 검사해보면 된다.
