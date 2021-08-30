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
			if (p.w > this.w)
				return -1;
			else if (p.w < this.w)
				return 1;
			else
				return 0;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken()),
			E = Integer.parseInt(st.nextToken());
		
		int K = Integer.parseInt(br.readLine());
		arr = new ArrayList[V + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<>();
		
		int u = 0, v = 0, w = 0;
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			arr[u].add(new Pair(v, w));
		}
		
		dist = new int[V + 1];
		for (int i = 1; i < dist.length; i++)
			dist[i] = Integer.MAX_VALUE;
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(K, 0));
		while (!pq.isEmpty()) {
			Pair curr = pq.poll();
			
			if (dist[curr.v] > curr.w) {
				dist[curr.v] = curr.w;
				for (int i = 0; i < arr[curr.v].size(); i++)
					pq.add(new Pair(arr[curr.v].get(i).v, dist[curr.v] + arr[curr.v].get(i).w));
			}
		}
		
		for (int i = 1; i < dist.length; i++)
			System.out.println((dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
	}
}
// 2020-10-11 00:20 해결
// 첫 다익스트라 사용.
// 흠.. 근데
//
//if (dist[curr.v] > curr.w) {
//	dist[curr.v] = curr.w;
//	for (int i = 0; i < arr[curr.v].size(); i++)
//		pq.add(new Pair(arr[curr.v].get(i).v, dist[curr.v] + arr[curr.v].get(i).w));
//}
// 저 부분 조건을 반대로 해서 continue 하게 돌렸더니 가치가 같은 경우에서까지 다시 큐에 추가하는 바람에
// 메모리가 초과됬었음.
// 다시 하려면 continue 조건을 같은 경우까지 고려해서 추가해야함. 주의할 것.
