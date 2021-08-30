import java.util.*;
import java.io.*;
public class Main {
	public static int[][] matrix;
	public static int n;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		n = Integer.parseInt(br.readLine());
		matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		floyd();
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
	}	
	public static void floyd() {
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (matrix[i][k] == 1 && matrix[k][j] == 1)
						matrix[i][j] = 1;
				}
			}
		}
	}
}
// 2021-01-04 20:37 해결
// 플로이드 1회로 간단하게 해결
// 단, 거리를 구하지 말고 그냥 경유지 기준 출발-경유지 / 경유지-도착지 에 도달할 수 있다면
// 그곳 역시 도달 가능하다고만 표시
