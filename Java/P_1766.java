import java.util.*;
import java.io.*;
public class Main {
	public static class Problem implements Comparable<Problem>{
		int number;
		int topol;
		public Problem(int n) {
			number = n;
			topol = -1;
		}
		public int compareTo(Problem p) {
			return this.topol - p.topol;
		}
	}
	public static Problem[] p;
	public static int[] indegree;
	public static ArrayList<Integer>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		p = new Problem[n + 1];
		indegree =  new int[n + 1];
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<>();
			p[i] = new Problem(i);
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
			indegree[b]++;
		}
		topologicalSort(n);
		Arrays.sort(p, 1, p.length);
		
		for (int i = 1; i <= n; i++)
			sb.append(p[i].number + " ");
		System.out.println(sb);
	}
	
	public static void topologicalSort(int n) {
		int cnt = 1;
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int i = 1; i <= n; i++)
			if (indegree[i] == 0)
				pq.add(i);
		
		while(!pq.isEmpty()) {
			int curr = pq.poll();
			p[curr].topol = cnt++;
			for (int i = 0; i < adj[curr].size(); i++) {
				int next = adj[curr].get(i);
				if (--indegree[next] == 0)
					pq.add(next);
			}
		}		
	}
}
// 2021-01-10 23:11 해결
// ** DFS 기반의 위상정렬로는 풀 수 없는 문제였다.
// 이는 진입차원 기반으로 하는 위상정렬로 해야하며
// 추가로, 풀 수 있는 문제가 동시에 여러게 존재시 난이도가 낮은걸 먼저 푸는건
// 우선순위큐로 해결
