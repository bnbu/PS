import java.util.*;
import java.io.*;
public class Main {
	public static int n, m;
	public static long d[];
	public static ArrayList<Pair>[] arr;
	public static boolean negativeCycle = false;
	static class Pair {
		int x;
		int w;
		public Pair(int x, int w) {
			this.x = x;
			this.w = w;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		d = new long[n + 1];
		Arrays.fill(d, Integer.MAX_VALUE);
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
		
		bellmanFord(1);
		if (!negativeCycle) {
			for (int i = 2; i < d.length; i++)
				sb.append(d[i] == Integer.MAX_VALUE ? "-1\n" : (d[i] + "\n"));
		}
		else
			sb.append(-1);
		
		System.out.println(sb);
	}
	public static void bellmanFord(int start) {
		d[start] = 0;
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				for (int k = 0; k < arr[j].size(); k++)
					if (d[j] != Integer.MAX_VALUE && d[arr[j].get(k).x] > d[j] + arr[j].get(k).w) {
						d[arr[j].get(k).x] = d[j] + arr[j].get(k).w;
						// => 정점 j에서 연결된 k번째 간선의 도착지인 arr[j].get(k).x 의 기존 최단거리보다
						// 정점 j를 거쳐 이동한 거리가 더 최단거리라면, 그 거리를 최단거리로 갱신.
						if (i == n)
							negativeCycle = true;
						// n-1번 이후에도 갱신이 발생한다면, 음수 사이클이 존재.
					}
	}
}
// 2020-10-14 22:57 해결
// 벨만포드 첫 문제.
// 출력초과가 한번 떴는데, 이는 d를 long으로 선언해서 해결.
// 
// 정점의 개수가 500개, 간선의 개수가 6000개, 
// 최소 가중치가 -10000이라면 충분히 underflow가 발생할 수 있는 수치
// (-500 * 6000 * 10000 = -3 * 10^10, Integer.MIN_VALUE = 약 -2*10^9)
