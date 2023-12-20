import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Pair {
	int first, second;
	public Pair(int first, int second) {
		this.first = first;
		this.second = second;
	}

	// adj
	// first -> destination
	// second -> distance(weight)
	
	// costs
	// first -> distance
	// second -> depth
}
public class P_5820 {
	static final int INF = Integer.MAX_VALUE;
	static int ans = INF;
	static int n, k;
	static int[] size, pathCnt; // pathCnt[i] = 길이가 i인 경로를 이루는 도로의 개수 
	static boolean[] visit; // centroid decomposition시 분할정복 적용때 중복되지 않게 하기 위함
	static ArrayList<Pair> calcDepth;
	static ArrayList<Integer> toReset;
	static ArrayList<Pair>[] adj;	// 
	static int getSize(int curr, int parent) {
		size[curr] = 1;
		for (Pair p : adj[curr])
			if (parent != p.first && !visit[p.first]) size[curr] += getSize(p.first, curr);
		return size[curr];
	}
	static int getCentroid(int curr, int parent, int capacity) {
		for (Pair p : adj[curr]) 
			if (parent != p.first && !visit[p.first] && size[p.first] * 2 > capacity) 
				return getCentroid(p.first, curr, capacity);
		return curr;
	}
	static void dfs(int curr, int parent, int depth, int sum) {
		if (sum > k) return;
		calcDepth.add(new Pair(depth, sum));
		if (pathCnt[k - sum] != Integer.MAX_VALUE) {
			ans = Math.min(ans, pathCnt[k - sum] + depth);
		}
		
		for (Pair p : adj[curr])
			if (p.first != parent && !visit[p.first])
				dfs(p.first, curr, depth + 1, sum + p.second);
	}
	static void dnc(int curr, int parent) {
		int centroid = getCentroid(curr, -1, getSize(curr, -1));
		for (Pair p : adj[centroid]) {
			if (p.first != parent && !visit[p.first]) {
				dfs(p.first, centroid, 1, p.second);
				for (Pair c : calcDepth) {
					toReset.add(c.second);
					pathCnt[c.second] = Math.min(pathCnt[c.second], c.first);
				}
				calcDepth.clear();
			}
		}
		ans = Math.min(ans, pathCnt[k]);
		for (int i : toReset) pathCnt[i] = INF;
		toReset.clear();
		visit[centroid] = true; 
		
		for (Pair p : adj[centroid]) 
			if (!visit[p.first]) dnc(p.first, centroid);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		size = new int[n];
		pathCnt = new int[k + 1]; Arrays.fill(pathCnt, INF);
		visit = new boolean[n];
		calcDepth = new ArrayList<>();
		toReset = new ArrayList<>();
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
		// 정점은 0부터 n-1까지
		
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[u].add(new Pair(v, w));
			adj[v].add(new Pair(u, w));
		}
		dnc(0, -1);
		sb.append(ans == INF ? -1 : ans);
		System.out.println(sb);
	}
}
