import java.util.*;
import java.io.*;
public class Main {
	public static int v, e;
	public static int d[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		
		d = new int[v + 1][v + 1];
		for (int i = 1; i < d.length; i++) 
			Arrays.fill(d[i], Integer.MAX_VALUE);
		
//		for (int i = 1; i < d.length; i++)
//			d[i][i] = 0;
		
		for (int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken()),
				w = Integer.parseInt(st.nextToken());
			
			d[u][v] = w;
		}
		
		floyd();
		
		for (int i = 1; i < d.length; i++) {
			for (int j = 1; j < d[i].length; j++)
				System.out.print(d[i][j] == Integer.MAX_VALUE ? "x " : (d[i][j] + " "));
			System.out.println();
		}
		
		int result = Integer.MAX_VALUE;
		
//		for (int i = 1; i < d.length; i++) {
//			for (int j = i + 1; j < d[i].length; j++) {
//				if (d[i][j] != Integer.MAX_VALUE && d[j][i] != Integer.MAX_VALUE)
//					result = Math.min(result, d[i][j] + d[j][i]);
//			}
//		} //=> 일반 플로이드에서, i->j 와 j->i의 최단거리가 모두 있을 경우
		// 이 두 거리의 합이 최단거리가 된다.
		
		for (int i = 1; i < d.length; i++)
			result = Math.min(result, d[i][i]); //=> 모두 INF로 하고 플로이드 실행시 판단
		// 속도는 이게 더 빠르지만, 거리가 이상하게 나오기도 함.
		// 따라서 다른 곳의 최단거리가 필요할 때는, 이 방법 말고 위의 방법으로
			
		
		if (result == Integer.MAX_VALUE)
			System.out.println(-1);
		else
			System.out.println(result);
	}
	public static void floyd() {
		for (int k = 1; k <= v; k++) {
			for (int i = 1; i <= v; i++) {
				for (int j = 1; j <= v; j++) {
					if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE)
						d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
			}
		}
	}
}
