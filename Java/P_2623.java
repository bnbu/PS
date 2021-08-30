import java.util.*;
import java.io.*;
public class Main {
	public static int[] indegree;
	public static boolean chk = true;
	public static ArrayList<Integer>[] adj;
	public static ArrayList<Integer> ans;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());

		ans = new ArrayList<>();
		indegree = new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList<>();
		
		while(m-- > 0) {
			String[] s = br.readLine().split(" ");
			int a = Integer.parseInt(s[0]);
			
			for (int i = 2; i < s.length; i++) {
				int u = Integer.parseInt(s[i - 1]),
					v = Integer.parseInt(s[i]);
				
				if (!adj[u].contains(v)) {
					adj[u].add(v);
					indegree[v]++;
				}
			}
		}
		
		topologicalSort(n);
		if (ans.size() == n) {
			for (int i : ans)
				sb.append(i + "\n");
			System.out.println(sb);
		}
		else
			System.out.println(0);
	}
	public static void topologicalSort(int n) {
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= n; i++)
			if (indegree[i] == 0)
				q.add(i);
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			ans.add(curr);
			for (int next : adj[curr]) {
				if (--indegree[next] == 0)
					q.add(next);
			}
		}
	}
}
// 2021-01-11 22:09 해결
// 위상정렬에 대해서 한 부분을 더 배워간다.
// => 사이클이 형성 (즉, 서로 반대되는 순서 형성)시 위상정렬한 데이터들의 수가
// 정점의 갯수보다 많게 됨
// 이를 통해 위상정렬이 가능한지 아닌지를 구분할 수 있다.
