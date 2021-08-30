import java.util.*;
import java.io.*;
public class Main {
	public static ArrayList<Computer>[] arr;
	public static int[] d;
	public static int[] trace;
	public static int n, m;
	static class Computer implements Comparable<Computer> {
		int v;
		int w;
		public Computer(int v, int w) {
			this.v = v;
			this.w = w;
		}
		public int compareTo(Computer c) {
			return this.w - c.w;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		d = new int[n + 1];
		Arrays.fill(d, Integer.MAX_VALUE);
		arr = new ArrayList[n + 1];
		trace = new int[n + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<>();
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			arr[u].add(new Computer(v, w));
			arr[v].add(new Computer(u, w));
		}
		
		dijkstra(1);
		
		int cnt = 0;
		for (int i = 1; i < trace.length; i++)
			if (trace[i] != 0) {
				cnt++;
				sb.append(trace[i] + " " + i + "\n");
			}
		System.out.println(cnt);
		System.out.println(sb);
	}
	public static void dijkstra(int start) {
		PriorityQueue<Computer> pq = new PriorityQueue<>();
		pq.add(new Computer(start, 0));
		d[start] = 0;
		
		while (!pq.isEmpty()) {
			Computer curr = pq.poll();
			
			for (int i = 0; i < arr[curr.v].size(); i++) {
				Computer next = arr[curr.v].get(i);
				if (d[next.v] > curr.w + next.w) {
					d[next.v] = curr.w + next.w;
					trace[next.v] = curr.v;
					pq.add(new Computer(next.v, next.w + curr.w));
				}
			}
		}
	}
}
// 2020-10-15 01:02 해결
// 생각한대로, 다익스트라를 통한 각 노드별 최단 거리를 이어주는 경로이다.
// 경로를 어떻게 추적할지에 대해 고민을 계속 했는데
// 어떻게 당장 떠오르는건 없어서 다익스트라를 통한 경로추적을 따로 찾아봤더니
// 다음과 같은 방법이 있다
// 우선, 각 노드별 최단 경로를 추적하는 도중에 가장 마지막으로 거쳐온 노드를 저장한다.
// 이걸 다 이으면, 각 노드별로 가는 최단경로가 된다.
// N번 정점이 마지막으로 거친 정점의 번호가 i번이라면,
// 다시 i번 정점이 마지막으로 거친 정점을 추적,
// 이를 반복하다 보면 출발점이 나올것이다.
// 이로써 역추적을 하게 된 것.
