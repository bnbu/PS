import java.util.*;
import java.io.*;
public class BellmanFord {
	public static ArrayList<Pair>[] arr;
	public static int[] d;
	public static int n, m;
	public static boolean negativeCycle = false;
	static class Pair implements Comparable<Pair> {
		int x;
		int w;
		public Pair(int x, int w) {
			this.x = x;
			this.w = w;
		}
		public int compareTo(Pair p) {
			return this.w - p.w;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken()); // 정점 개수
		m = Integer.parseInt(st.nextToken()); // 간선 개수
		
		d = new int[n + 1];
		Arrays.fill(d, Integer.MAX_VALUE);
		arr = new ArrayList[n + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<>();
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			arr[u].add(new Pair(v, w));
		}
		
		System.out.println("Negative-Cycle이 없을 경우, 최악의 케이스 일 때를 가정한 BellmanFord 갱신 과정");
		bellmanford(1);
		System.out.println();
		
		System.out.println("Negative-Cycle 여부를 알아보는 BellmanFord를 통한 결과");
		bellmanford_negativeCycle(1);
		System.out.println("negativeCycle : " + negativeCycle);
	}
	
	public static void bellmanford(int start) {
//		Arrays.fill(d, Integer.MAX_VALUE);
		d[start] = 0; // 시작점만 0으로 해서 시작.
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= n; j++)
				for (int k = 0; k < arr[j].size(); k++)
					if (d[j] != Integer.MAX_VALUE && d[arr[j].get(k).x] > d[j] + arr[j].get(k).w)
						d[arr[j].get(k).x] = d[j] + arr[j].get(k).w;
			print();
		}
		// 정점 N개에 대해  이어진 간선들을 모두 다 탐색.
		// 이때 모두 탐색할 때의 최악의 케이스는 일렬로 되있는 경우
		// 즉 정점 N개일 때, N-1번의 갱신작업을 거치면 된다.
		// 이떄 출발점은 거리가 0으로 시작.
	}
	public static void bellmanford_negativeCycle(int start) {
		Arrays.fill(d, Integer.MAX_VALUE);
		
		d[start] = 0; // 시작점만 0으로 해서 시작.
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++)
				for (int k = 0; k < arr[j].size(); k++)
					if (d[j] != Integer.MAX_VALUE && d[arr[j].get(k).x] > d[j] + arr[j].get(k).w) {
						d[arr[j].get(k).x] = d[j] + arr[j].get(k).w;
						if (i == n) 
							negativeCycle = true;
					}
			print();
		}
		// 음의 사이클이 없는 경우,
		// 최악의 케이스일 때, N-1번 갱신작업 이후
		// 거리의 갱신이 이루어지면 안되는데,
		// 음의 사이클이 존재할 경우에는 이후에도 거리 갱신이 이루어진다
		// 이를 이용해 N-1번 이후, N번째 루프에서 갱신이 있을 시
		// 음의 사이클이 있다고 판단한다.
	}
	public static void print() {
		for (int i = 1; i < d.length; i++) {
			if (d[i] == Integer.MAX_VALUE)
				System.out.print("INF ");
			else
				System.out.print(d[i] + " ");
		}
		System.out.println();
	}
}
// 시작점에서 먼저 이어진 간선들로 가는 정점들의 거리를 갱신
// 이후 각 정점들의 이어진 간선들로 다시 가는 정점들의 거리를 계산하며
// 최소 거리가 되는 경우 다시 갱신을 반복한다.
// (이때, 시작점-다음정점 으로 이어지는 경로로 보면 된다)
// 즉 모든 정점의 모든 간선을 다 확인하는 방법으로
// 모든 정점의 간선을 다 살피게 되므로, 다익스트라보다는 느린 O(VE)지만
// 모든 정점의 간선을 다 살피게 되므로, 음의 가중치를 포함한 거리의 최단거리도 계산이 가능.
// 최악의 경우에는 정점의 개수 - 1번까지 (모두 일렬로 연결된 경우)
// 갱신작업을 반복해야 모두 갱신이 된다.

// 그리고 만약, 음의 사이클 (가중치가 모두 음수로 이루어진 사이클)이 존재한다면,
// 이러한 벨만-포드 알고리즘을 1회 더 돌렸을 시,
// 갱신 될 리 없는 최단거리가
// 음의 사이클에 의해서 더 줄어드는, 즉 갱신되는 경우가 발생한다
// 이로 인해 음의 사이클의 여부를 판단할 수 있다!
// 이를 축약시키면, V개의 정점일때, 

// 음의 사이클이 어느 부분에 의해 연결되어 있는지는.. 그건 모르겠고

// 예시
// 음의 사이클 (X)
//3 4
//1 2 4
//1 3 3
//2 3 -1
//3 1 -2

// 음의 사이클 (O)
//3 4
//1 2 4
//1 3 3
//2 3 -4
//3 1 -2
