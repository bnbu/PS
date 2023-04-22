import java.util.*;
import java.io.*;
public class FloydWarshall_CycleLength {
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
				d[i][j] = Integer.MAX_VALUE;
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
		
		System.out.println("각 정점으로 보는 사이클 존재 유무");
		for (int i = 1; i < d.length; i++) {
			System.out.print(d[i][i] == Integer.MAX_VALUE ? "INF " : (d[i][i] +  " "));
		}
		System.out.println();
		
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

// 기존 플로이드-와샬에서, i == j인 경우도 초기값을 INF로 두고
// 플로이드-와샬을 실행한다.
// 이때, 사이클이 존재한다면, i == j인 경우의 최단거리가 사이클의 길이로 갱신될 것.
// 따라서 그 거리로 사이클의 유무를 알 수 있다.

// 또는, 일반 플로이드를 해서, i->j 와 j->i 가 모두 존재한다면,
// 이 둘의 거리의 합이 바로 사이클의 길이이다.

// 따라서 총 2개의 방법이 있다
// 1. 거리를 모두 INF로 둔 다음, i == j인 경우가 갱신이 되는 거로 판단
// 2. 일반 플로이드로, i->j 와 j->i가 모두 존재하는 거로 판단

// 예시
//6
//7
//1 2 1
//2 3 2
//3 1 3
//3 4 2
//4 5 3
//5 3 4
//5 6 5
//간선 정보만 입력//
//INF 1 INF INF INF INF 
//INF INF 2 INF INF INF 
//3 INF INF 2 INF INF 
//INF INF INF INF 3 INF 
//INF INF 4 INF INF 5 
//INF INF INF INF INF INF 
//
//Floyd-Warshall 진행 후
//6 1 3 5 8 13 
//5 6 2 4 7 12 
//3 4 6 2 5 10 
//10 11 7 9 3 8 
//7 8 4 6 9 5 
//INF INF INF INF INF INF 
//각 정점으로 보는 사이클 존재 유무
//6 6 6 9 9 INF 