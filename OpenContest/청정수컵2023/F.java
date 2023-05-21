import java.util.*;
import java.io.*;
public class F {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			k = Integer.parseInt(st.nextToken());
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= n; i++) q.add(i);
		
		while (q.size() > 1) {
			int survive = q.poll();
			for (int i = 0; i < k - 1; i++) {
				if (q.isEmpty()) break;
				q.poll();
			}
			q.add(survive);
		}
		
		System.out.println(q.poll());
	}
}
// 기존 요세푸스와 약간 다르게
// 첫번째 순서를 뺴고
// 그 뒤로부터 K번째는 모두 큐에서 뺴버려도 된다
// 이때분에 최대 10^6이어도, O(N)으로 해결이 되서 그냥 나이브하게 진행함