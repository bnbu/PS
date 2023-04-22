import java.util.*;
import java.io.*;
public class TopologicalSort_DFS_Based {
	public static class Node implements Comparable<Node>{
		int num;
		int topolNum;
		Node(int n, int t) {
			num = n;
			topolNum = t;
		}
		public int compareTo(Node n) {
			
			return this.topolNum - n.topolNum;
		}
	}
	public static int n, m;
	public static boolean[] visit;
	public static ArrayList<Node> topolList;
	public static int currNum;
	public static ArrayList<Integer>[] adjList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		adjList = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adjList[i] = new ArrayList<>();
		visit = new boolean[n + 1];
		topolList = new ArrayList<>();
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			adjList[u].add(v);
		}
		
		topologicalSort();
		
		Collections.sort(topolList);
		
		for (int i = 0; i < n; i++)
			System.out.print(topolList.get(i).num + " ");		
	}
	public static void topologicalSort() {
		currNum = n;
		for (int i = 1; i <= n; i++)
			if (!visit[i])
				topologicalDFS(i);
	}
	public static void topologicalDFS(int v) {
		visit[v] = true;
		for (int i = 0; i < adjList[v].size(); i++)
			if (!visit[adjList[v].get(i)])
				topologicalDFS(adjList[v].get(i));
		
		topolList.add(new Node(v, currNum));
		currNum--;
	}
}
// DFS로 들어가서 더 이상 다음으로 이어지지 않는 외부노드에 도달시
// 그 노드부터 넘버링, 넘버링은 큰 수부터 내려온다.