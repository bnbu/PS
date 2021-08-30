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
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine()),
			m = Integer.parseInt(br.readLine());

		arr = new ArrayList[n + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<>();
		
		int u = 0, v = 0, w = 0;
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			arr[u].add(new Pair(v, w));
		}
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken()),
			end = Integer.parseInt(st.nextToken());
		
		dist = new int[n + 1];
		for (int i = 1; i < dist.length; i++)
			dist[i] = Integer.MAX_VALUE;
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(start, 0));
		while (!pq.isEmpty()) {
			Pair curr = pq.poll();
			
			if (dist[curr.v] > curr.w) {
				dist[curr.v] = curr.w;
				for (int i = 0; i < arr[curr.v].size(); i++)
					pq.add(new Pair(arr[curr.v].get(i).v, dist[curr.v] + arr[curr.v].get(i).w));
			}
		}	
		
		System.out.println(dist[end]);
	}
}
// 2020-10-11 01:20 해결
// 다익스트라 부분을 함수로 따로 분리했을떄는 메모리 초과가 떴었다
