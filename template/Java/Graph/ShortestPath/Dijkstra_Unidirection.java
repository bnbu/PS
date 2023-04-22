import java.util.*;
import java.io.*;
public class Dijkstra_Unidirection {
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
			arr[u].add(new Edge(v, w)); // 단방향이므로, 출발점-도착점 으로 저장.
		} // 간선 정보 인접리스트 입력
		// 이때의 간선 정보는 단방향 간선임을 주의해라.
	
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

//5 6
//1
//5 1 1
//1 2 2
//1 3 3
//2 3 4
//2 4 5
//3 4 6

// 입력을 기준으로,
// 먼저 시작점 1번부터 시작,
// 1번 정점에서의 dijkstra[1] (이하 d[curr.v]로 표기)는
// int의 최댓값이므로, 현재의 이동거리 0으로 갱신
// 및 인접리스트에서 연결된 간선 정보에 따른 bfs 시작.
// 우선순위큐(compareTo로 비교를 한다)에 삽입하여  최소거리로 자동 정렬
// 이떄 정렬 시간복잡도는 O(logN)이다
// 1번의 갱신 이후 인접리스트에 있는 정보로 2번, 거리2 / 3번 거리 3이 큐에 삽입
//현재 큐 : 2/2 3/3

// 2번 시작.
// 2번 역시 거리가 갱신된 적이 없으므로, 현재까지의 거리 2로 갱신
// 이후 인접리스트 3번, 거리6 / 4번, 거리7 이 큐에 삽입
// 현재 큐 : 3/3 3/6 4/7

// 3번 시작.
// 3번 역시 거리가 갱신된 적이 없으므로, 현재까지의 거리 3으로 갱신
// 이후 인접리스트 4번, 거리 9가 큐에 삽입.
// 현재 큐 : 3/6 4/7 4/9

// 다시 3번
// 1->2->3 을 통해 온 거리이기에 6.
// 현재 d[3]에는 거리가 3이므로, 갱신하지 않고 통과한다.
// 현재 큐 : 4/7 4/9

// 4번 시작.
// 4번은 거리가 갱신된 적이 없으므로, 현재까지의 거리 7로 갱신
// 4번은 인접리스트가 없으니 큐에 추가는 생략한다.
// 현재 큐: 4/9

// 다시 4번.
// d[4]에는 7이므로, 갱신하지 않고 통과
// 현재 큐 : x

// 큐가 모두 비었으니 종료한다.
// 이 과정에 따라, 5번은 갱신된 적이 없으므로 INF로 표기.
// 즉 도달할 수 없다.
// 참고로 이건 단방향, 즉 이동방향이 정해진 그래프에서의 최소 이동 가치이다.
// 예시 입력의 경우 5->1 / 1->2 / 1->3 / 2->3 / 2->4 / 3->4 로만 이동이 가능.
// 따라서, 시작점을 4번으로 할 경우, 다른 점으로의 이동이 불가능
// INF만 4개가 나오는 것을 알 수 있다.