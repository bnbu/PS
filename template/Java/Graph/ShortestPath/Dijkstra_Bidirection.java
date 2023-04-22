import java.util.*;
import java.io.*;
public class Dijkstra_Bidirection {
	public static ArrayList<Edge>[] arr; // 간선 정보
	public static int[] d; // 최소비용
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
	
		d = new int[V + 1]; // 정점간 이동 최소비용
		for (int i = 1; i <= V; i++)
			d[i] = Integer.MAX_VALUE;
		// 최솟값을 구할 수 있게, 최단거리 배열에는 일단 모두 int의 최댓값을 넣음
		
		dijkstra(K);
		
		for (int i = 1; i <= V; i++) {
			if (d[i] == Integer.MAX_VALUE)
				System.out.print("INF ");
			else
				System.out.print(d[i] + " ");
		}
		System.out.println();
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
			if (d[curr.v] != curr.w)
				continue;
			// => 지금 정점의 거리가 최단거리가 이미 최단거리가 아니라면,
			// 어디로 가든 최단거리가 될 수 없으므로 검사하지 않는다.
			for (int i = 0; i < arr[curr.v].size(); i++) {
				Edge next = arr[curr.v].get(i);
				if (d[next.v] > d[curr.v] + next.w) {
					d[next.v] = d[curr.v] + next.w;
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
// 단방향 그래프에서의 다익스트라
// 출발점->도착점 및 가중치로 이동경로의 최솟값을 구하는 방법.
// 안멈추고 끝없이 탐색할 것 같았지만
// 다시 잘 생각해보니? 어차피 최솟값 갱신만 하면 조건을 충족하지 못하는
// 나머지 탐색에 대해서는 추가 탐색이 이루어지지 않기 때문에 단순하게
// 양방향 간선 연결을 해서 탐색을 진행해도 큰 상관이 없다.
// O(ElogV) 복잡도를 가짐