import java.util.*;
import java.io.*;
public class Main {
	public static ArrayList<Pair>[] arr;
	public static int[] dist;
	static class Pair implements Comparable<Pair>{
		int v;
		int w;
		public Pair(int v, int w) {
			this.v = v;
			this.w = w;
		}

		public int compareTo(Pair p) {
			if (this.w < p.w)
				return -1;
			else if (this.w > p.w)
				return 1;
			else
				return 0;
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			e = Integer.parseInt(st.nextToken());
		
		dist = new int[n + 1];
		arr = new ArrayList[n + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<>();
		
		for (int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			arr[u].add(new Pair(v, w));
			arr[v].add(new Pair(u, w));
		}
		
		st = new StringTokenizer(br.readLine());
		int v1 = Integer.parseInt(st.nextToken()),
			v2 = Integer.parseInt(st.nextToken());
		
		int[] result = new int[3];
		boolean chk = true;
		dijkstra(1);
		if (dist[v1] != Integer.MAX_VALUE && dist[v2] != Integer.MAX_VALUE) {
			result[1] += dist[v1];
			result[2] += dist[v2];
			dijkstra(v1);
			if (dist[v2] != Integer.MAX_VALUE && dist[n] != Integer.MAX_VALUE) {
				result[1] += dist[v2];
				result[2] += dist[n];
				dijkstra(v2);
				if (dist[n] != Integer.MAX_VALUE && dist[n] != Integer.MAX_VALUE) {
					result[1] += dist[n];
					result[2] += dist[v1];
				}
				else
					chk = false;
			}
			else
				chk = false;
		}
		else
			chk = false;
		
		if (chk)
			System.out.println(result[1] < result[2] ? result[1] : result[2]);
		else
			System.out.println(-1);
	}
	public static void dijkstra(int start) {
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		Arrays.fill(dist, Integer.MAX_VALUE);
		pq.add(new Pair(start, 0));
		
		while (!pq.isEmpty()) {
			Pair curr = pq.poll();
			
			if (dist[curr.v] > curr.w) {
				dist[curr.v] = curr.w;
				for (int i = 0; i < arr[curr.v].size(); i++)
					pq.add(new Pair(arr[curr.v].get(i).v, dist[curr.v] + arr[curr.v].get(i).w));
			}
		}
	}
}
// 2020-10-11 00:53 해결
// 시작점(1) -> 필수점(v1, v2) -> 도착점(N) 으로 가야할 때
// 1->v1->v2->N 과 1->v2->v1->N 중 어느게 더 가까운지를 모른다.
// 따라서 다익스트라를 3번 돌려서 각각의 거리를 구한 후 최솟값을 출력
// INF가 도중에 하나라도 있다면 -1.
// (맨처음 할땐 다익스트라 6번 돌림;; 근데 잘 생각해보니 3번으로도 가능함)
