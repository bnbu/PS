package D0410;

import java.util.*;
import java.io.*;
public class ArticulationBridge {
	public static class Pair implements Comparable<Pair>{
		int s, d;

		public Pair(int s, int d) {
			this.s = s;
			this.d = d;
		}

		@Override
		public int compareTo(Pair p) {
			if (this.s == p.s)
				return Integer.compare(this.d, p.d);
			return Integer.compare(this.s, p.s);
		}
		
	}
	public static int v, e, cnt = 0;
	public static int[] discover;
	public static ArrayList<Pair> isCut;
	public static ArrayList<Integer>[] adj;
	public static int dfs(int curr, int parent) {
		int ret = discover[curr] = ++cnt;
		
		for (int next : adj[curr]) {
			if (next == parent) continue;
			
			if (discover[next] != -1) {
				ret = Math.min(ret, discover[next]);
				continue;
			}
			
			int prev = dfs(next, curr);
			if (prev > discover[curr])
				isCut.add(new Pair(Math.min(curr, next), Math.max(curr, next)));
			
			ret = Math.min(ret, prev);
		}
		
		return ret;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		
		discover = new int[v + 1];
		isCut = new ArrayList<>();
		Arrays.fill(discover, -1);
		
		adj = new ArrayList[v + 1];
		for (int i = 1; i <= v; i++) adj[i] = new ArrayList<>();
		
		for (int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()),
				b = Integer.parseInt(st.nextToken());
			
			adj[a].add(b);
			adj[b].add(a);
		}
		
		for (int i = 1; i <= v; i++)
			if (discover[i] == -1) dfs(i, 0);
		
		
		isCut.sort(null);
		System.out.println(isCut.size());
		for (Pair p : isCut) {
			System.out.println(p.s + " " + p.d);
		}
	}
}

// 단절선
// 단절점과 같이 DFS를 이용하는 방식으로 A번째 정점에서 
// 부모로 가는 간선이 아닌 간선 중에서 아직 방문안한 노드의 discover 번호가 현재 discover 번호보다 클 경우 단절선이 된다.