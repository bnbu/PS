import java.util.*;
import java.io.*;
public class FloydWarshall {
	public static int[][] d;
	public static int n, m;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());
		
		d = new int[n + 1][n + 1];
		for (int i = 1; i < d.length; i++)
			for (int j = 1; j < d[i].length; j++) {
				d[i][j] = i == j ? 0 : Integer.MAX_VALUE;
			}
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			d[u][v] = Math.min(d[u][v], w); // 중복 입력이 될 수도 있으므로
			// 기존 입력값과 새로운 값중 더 작은 값을 저장해놓는다.
		}
		System.out.println("간선 정보만 입력//");
		print();
		System.out.println();
		
		System.out.println("Floyd-Warshall 진행 후");
		floyd();
		print();
		
	}
	public static void floyd() {
		// k 경유지 / 거리를 측정할 두 정점.
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE)
						d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
			}
		}
	}
	public static void print() {
		for (int i = 1; i < d.length; i++) {
			for (int j = 1; j < d[i].length; j++)
				System.out.print(d[i][j] == Integer.MAX_VALUE ? "INF " : (d[i][j] + " "));
			System.out.println();
		}
	}
}

// 모든 정점 쌍 간 최단거리를 구하는 플로이드-와샬
// 정점 개수 V에 대해 O(V^3)의 복잡도를 가짐
// 정점 i와 j사이의 모든 경유지를 탐색하여, 최단 경로를 찾는 방법으로,
// 그래프의 정점이 1~V까지라고 한다면, 경유지는 k이다.
// 즉, 경유지 k를 거치는 정점 i와 j사이의 거리 중 최단거리를 갱신하는것.
// 다시 말해, d[i][j] 는 지금까지의 최단거리 d[i][j]와 k를 경유한 최단거리
// d[i][k] + d[k][j] 와 비교하게 되는 것이다.

// 별도의 pair 클래스 필요없이
// 2차원 배열로 인접행렬을 구성하여 한다.

// 1->2 / 1->3 / 1->4 / 2->3 / 2->4 / 3->4 의 간선을 가지는 그래프가 있다고 하자
// 우선 현재 연결된 간선의 정보로, 인접행렬을 먼저 구성한다
// 이후, K=1,2,3,4 일때, 즉 경유 정점 1,2,3,4일때의 거리를 모두 계산하여
// 기존 거리와 비교하여 최단거리를 갱신해준다.

// 예시
//4
//8
//1 2 7
//1 3 5
//2 4 3
//3 1 2
//3 2 1
//3 4 8
//4 2 4
//4 3 2

//간선 정보만 입력//
//0 7 5 INF 
//INF 0 INF 3 
//2 1 0 8 
//INF 4 2 0 
//
//Floyd-Warshall 진행 후
//0 6 5 9 
//7 0 5 3 
//2 1 0 4 
//4 3 2 0 