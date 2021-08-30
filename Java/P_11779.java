import java.util.*;
import java.io.*;
public class Main {
	public static int n, m;
	public static int[] d;
	public static int[] lastV;
	public static ArrayList<Pair>[] arr;
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
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());
		
		d = new int[n + 1];
		Arrays.fill(d, Integer.MAX_VALUE);
		lastV = new int[n + 1];
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
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken()),
			end = Integer.parseInt(st.nextToken());
		
		dijkstra(start);
		
		Stack<Integer> trace = new Stack<>();
		trace.push(end);
		int temp = end;
		while (temp != start) {
			trace.push(lastV[temp]);
			temp = lastV[temp];
		}
		
		System.out.println(d[end]);
		System.out.println(trace.size());
		while (!trace.isEmpty())
			System.out.print(trace.pop() + " ");
		System.out.println();
	}
	public static void dijkstra(int start) {
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(start, 0));
		d[start] = 0;
		
		while (!pq.isEmpty()) {
			Pair curr = pq.poll();
			
			if (curr.w > d[curr.v])
				continue;
			
			for (int i = 0; i < arr[curr.v].size(); i++) {
				Pair next = arr[curr.v].get(i);
				if (d[next.v] > next.w + curr.w) {
					d[next.v] = next.w + curr.w;
					lastV[next.v] = curr.v;
					pq.add(new Pair(next.v, next.w + curr.w));
				}
			}
		}
	}
}
// 2020-10-16 02:18 해결
// 다익스트라 경로 역추적
