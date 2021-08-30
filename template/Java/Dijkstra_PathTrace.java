import java.util.*;
import java.io.*;
public class Dijkstra_PathTrace {
	public static ArrayList<Edge>[] arr; // 간선 정보
	public static int[] d; // 최소비용
	public static int[] trace; // 가장 마지막으로 거친 정점 번호 저장
	// => 추적 방법, i번째 정점까지 도달하는데 마지막으로 지나친 노드가 j
	// => 다시, j번째 정점까지 마지막으로 지나친 노드를 추적
	// 이를 반복하다 보면, 출발지까지 역추적 되는 방식.
	static class Edge implements Comparable<Edge>{
		int v;
		int w;
		public Edge(int v, int w)  {
			this.v = v;
			this.w = w;
		}
		public int compareTo(Edge o) {
			if (o.w < this.w) 
				return 1;
			else if (o.w > this.w)
				return -1;
			else 
				return 0;
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int V = scan.nextInt(); // 정점 갯수 (voltex)
		int E = scan.nextInt(); // 간선 갯수 (edge)
		int K = scan.nextInt(); // 시작점
		
		arr = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++)
			arr[i] = new ArrayList<>();
		
		for (int i = 1; i <= E; i++) {
			int u = scan.nextInt(); 
			int v = scan.nextInt(); // u, v는 이어진 정점 2개
			int w = scan.nextInt(); // 해당 간선의 가중치
			arr[u].add(new Edge(v, w));
			arr[v].add(new Edge(u, w));
		} // 간선 정보 인접리스트 입력
	
		trace = new int[V + 1]; // 각 노드별로 가장 마지막으로 지난 정점 번호 저장
		d = new int[V + 1]; // 정점간 이동 최소비용
		for (int i = 1; i <= V; i++)
			d[i] = Integer.MAX_VALUE;
		// 최솟값을 구할 수 있게, 최단거리 배열에는 일단 모두 int의 최댓값을 넣음
		
		dijkstra(K);
		
		System.out.println("출발점 " + K + "부터 각 정점간 최단거리");
		for (int i = 1; i <= V; i++) {
			if (d[i] == Integer.MAX_VALUE)
				System.out.print("INF ");
			else
				System.out.print(d[i] + " ");
		}
		System.out.println("\n");
		
		System.out.println("각 정점간 가장 마지막으로 지나친 정점");
		for (int i = 1; i < trace.length; i++) {
			System.out.println(i + ": " + trace[i]);
		}
	}
	public static void dijkstra(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		// 해당 정점에서 다음 정점으로의 최단거리를 바로 가져와줄 우선순위 큐
		pq.add(new Edge(start, 0));
		// => 이때, 시작점에서 시작점으로의 이동거리는 0이므로 0으로 시작한다.
		d[start] = 0;
		// 시작점에서의 최단거리는 0일 수 밖에 없다.
		while (!pq.isEmpty()) {
			Edge curr = pq.poll();
			for (int i = 0; i < arr[curr.v].size(); i++) {
				Edge next = arr[curr.v].get(i);
				if (d[next.v] > d[curr.v] + next.w) {
					d[next.v] = d[curr.v] + next.w;
					trace[next.v] = curr.v; 
					pq.add(new Edge(next.v, next.w + d[curr.v]));
				}
				// 만약 현재 위치까지의 이동거리가 이전보다 더 작다면, 현재 점까지의 이동거리 갱신
			}
		}
	} // => 권장방법, while문 이전에 출발점 거리 갱신 후 반복문에서 갱신이 가능한 부분만 갱신하고 큐에 추가
	
	public static void dijkstra2(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge curr = pq.poll();
			
			if (d[curr.v] > curr.w) {
				d[curr.v] = curr.w;
				for (int i = 0; i < arr[curr.v].size(); i++) {
					pq.add(new Edge(arr[curr.v].get(i).v, arr[curr.v].get(i).w + d[curr.v]));
				}
			}
		}
	} // 기존에 하던 방식 (while문 들어와서, 갱신 시작) => 다음에 더 갱신 못하는 구간도 큐에 넣고 비효율.
}