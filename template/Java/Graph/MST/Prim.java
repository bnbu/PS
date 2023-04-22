import java.util.*;
import java.io.*;
public class Prim {
	public static class Point implements Comparable<Point>{
		int d;
		int w;
		public Point(int d, int w) {
			this.d = d;
			this.w = w;
		}
		public int compareTo(Point p) {
			return this.w - p.w;
		}
	}
	public static boolean[] selected;
	public static ArrayList<Point>[] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int v = Integer.parseInt(st.nextToken()),
			e = Integer.parseInt(st.nextToken());
		
		selected = new boolean[v + 1];
		adj = new ArrayList[v + 1];
		for (int i = 1; i <= v; i++)
			adj[i] = new ArrayList<>();
		while (e-- > 0) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()),
				d = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			adj[s].add(new Point(d, w));
			adj[d].add(new Point(s, w));
		}	
		System.out.println(prim(v));
	}
	public static int prim(int v) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(1, 0)); // 시작점
		int cost = 0;
		for (int i = 1; i <= v; i++) {
			Point curr = null;
			int min = Integer.MAX_VALUE;
			
			while (!pq.isEmpty()) {
				curr = pq.poll();
				if (!selected[curr.d]) {
					System.out.println(curr.d + " " + curr.w);
					min = curr.w;
					break;
				}
			}
			// 제일 처음 시작에는 출발점 1개 (1,0)만 존재
			// while문 종료 후 밑에서 curr이 출발점인 1을 가리키므로
			// 1에 연결된 정점들을 모두 pq에 추가
			// 이후 다시 pq에서 1에 연결된 정점들로 반복한다
			
			if (min == Integer.MAX_VALUE)
				return Integer.MAX_VALUE;
			// => 연결그래프가 아니라는 뜻.
			
			cost += min;
			selected[curr.d] = true; // 현재 정점은 트리에 포함되었다고 표시
			
			for (int j = 0; j < adj[curr.d].size(); j++) 
				pq.add(adj[curr.d].get(j));
			// => 연결시킨 정점의 인접 정점들을 추가
			// 우선순위큐에는 이전 정점들의 인접 정점과 새롭게 추가된 정점들이 같이 있다.
			
			System.out.println();
		}		
		return cost;
	}
}
// 우선순위큐를 사용하는 프림 알고리즘, 정점을 중점으로 구하는 방법
// 임의의 정점부터 시작하여 이 정점을 비어있는 트리 T에 포함시킨다
// T에 있는 노드와 T에 없는 노드 사이 간선 중 가중치가 최소인 간선을 찾는다.
// 찾은 간선이 연결하는 두 노드 중 T에 없는 노드를 T에 포함
// 이를 모든 노드가 연결, 즉 T에 모든 노드가 포함될 때까지 반복한다.

// ex)
// 6개 정점(1,2,3,4,5,6) 9개의 간선이 있다 하자
// 1 2 5
// 1 3 4
// 2 3 2
// 2 4 7
// 3 4 6
// 3 5 11
// 4 5 3
// 4 6 8
// 5 6 8

// => 1번 정점에서 시작한다고 하자
// 1번을 T에 포함 (T에는 1이 있다.)
// T에 있는 1과 연결된 간선 중 가중치가 최소인 1-3(4)를 선택
// 3번을 T에 포함  (T에는 1,3이 있다.)
// T에 있는 1,3과 연결된 간선중 가중치가 최소인 2-3(2)를 선택
// 2번을 T에 포함 (T에는 1,2,3이 있다.)
// T에 있는 1,2,3과 연결된 간선 중 가중치가 최소인 3-4(6)을 선택
// 4번을 T에 포함 (T에는 1,2,3,4가 있다.)
// T에 있는 1,2,3,4와 연결된 간선 중 가중치가 최소인 4-5(3)을 선택
// 5번을 T에 포함 (T에는 1,2,3,4,5가 있다.)
// T에 있는 1,2,3,4,5와 연결된 간선 중 가중치가 최소인 5-6(8) (사실 남은게 이거뿐)을 선택
// 6번을 T에 포함 (T에는 1,2,3,4,5,6이 있다.)
// 이제 T에 모든 노드가 다 포함되었다. => 종료
// 최소비용은 선택한 간선의 가중치의 합 (4+2+6+3+8)이 된다.

// 이를 우선순위큐를 사용하여 다익스트라와 비슷한 방법으로 트리에 포함된
// 정점들이 연결하는 최소비용간선만 찾을 수 있다.
// 우선 출발점부터 시작- 출발점에 연결된 정점-가중치 를 모두 우선순위큐에 삽입
// 최소비용을 연결시켰다면, 반복문을 탈출.
// => 이제 우선순위큐에는 이전에 추가시킨 정점-가중치들과
// 최소비용으로 연결시킨 정점에서의 연결된 정점-가중치를 모두 가지고 있다.
// 이를 이용하여 모든 간선들을 탐색이 가능하고 이 중에서 최소비용 간선들만 추려 탐색이 가능하다.

// 시간복잡도는 크루스칼과 같은 O(ElogV)