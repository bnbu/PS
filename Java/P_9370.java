import java.util.*;
import java.io.*;
public class Main {
	public static ArrayList<Pair>[] arr;
	public static ArrayList<Integer> desti;
	public static int[] d;
	static class Pair implements Comparable<Pair> {
		int v;
		int w;
		public Pair(int v, int w) {
			this.v = v;
			this.w = w;
		}
		public int compareTo(Pair p) {
			return this.w - p.w;
		}
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		while (T-- > 0) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken()),
				m = Integer.parseInt(st.nextToken()),
				t = Integer.parseInt(st.nextToken());
			
			d = new int[n + 1];
			Arrays.fill(d, Integer.MAX_VALUE);
			arr = new ArrayList[n + 1];
			for (int i = 1; i < arr.length; i++)
				arr[i] = new ArrayList<>();
			desti = new ArrayList<>();
		
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				g = Integer.parseInt(st.nextToken()),
				h = Integer.parseInt(st.nextToken());
			
			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken()),
					b = Integer.parseInt(st.nextToken()),
					d = Integer.parseInt(st.nextToken());
				
				if ((a == g && b == h) || (a == h && b == g)) {
					arr[a].add(new Pair(b, d * 2 - 1));
					arr[b].add(new Pair(a, d * 2 - 1));
				} // 특정 길을 지난 경우 가중치를 홀수로 저장
				else {
					arr[a].add(new Pair(b, d * 2));
					arr[b].add(new Pair(a, d * 2));
				} // 나머지는 짝수로 저장
			}
			
			for (int i = 0; i < t; i++) 
				desti.add(Integer.parseInt(br.readLine()));
			
			dijkstra(s);
			
			Collections.sort(desti);
			for (int i : desti) {
				if (d[i] != Integer.MAX_VALUE && d[i] % 2 == 1)
					sb.append(i + " ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	public static void dijkstra(int start) {
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(start, 0));
		while (!pq.isEmpty()) {
			Pair curr = pq.poll();
			
			if (d[curr.v] > curr.w) {
				d[curr.v] = curr.w;
				for (int i = 0; i < arr[curr.v].size(); i++)
					pq.add(new Pair(arr[curr.v].get(i).v, d[curr.v] + arr[curr.v].get(i).w));
			}
		}
 	}
}
// 2020-10-12 22:41 해결
// 이동 경로중 특정 경로 (g-h부분) 만 간선의 길이를 홀수
// 나머지를 짝수로 하게끔 변형시키면,
// 출발지에서 최소 경로들 중 이동 길이가 홀수인 부분은 g-h를 지났다는 뜻이므로,
// 다익스트라를 출발지로부터 한번 돌린 후
// 목적 후보지들의 최소 거리중 INF가 아니면서 홀수인 경로를 가지는 곳이 바로 목적지로 가능한 곳이다.
