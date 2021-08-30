import java.util.*;
import java.io.*;
public class Main {
	public static ArrayList<Pair>[] go;
	public static ArrayList<Pair>[] back;
	public static int[] distGo;
	public static int[] distBack;
	public static PriorityQueue<Pair> pq;
	static class Pair implements Comparable<Pair>{
		int v;
		int w;
		public Pair(int v, int w) {
			this.v = v;
			this.w = w;
		}
		public int compareTo(Pair p) {
			if (p.w > this.w)
				return -1;
			else if (p.w < this.w)
				return 1;
			else
				return 0;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken()),
			x = Integer.parseInt(st.nextToken());
		
		go = new ArrayList[n + 1];
		back = new ArrayList[n + 1];
		for (int i = 1; i < n + 1; i++) {
			go[i] = new ArrayList<>();
			back[i] = new ArrayList<>();
		}
		
		int u = 0, v = 0, w = 0;
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			go[u].add(new Pair(v, w));
			back[v].add(new Pair(u, w));
		}
		
		distGo = new int[n + 1];
		distBack = new int[n + 1];
		Arrays.fill(distGo, Integer.MAX_VALUE);
		Arrays.fill(distBack, Integer.MAX_VALUE);
		
		dijkstra(x, go, distGo);
		dijkstra(x, back, distBack);
		
		int max = 0;
		for (int i = 1; i < n + 1; i++) 
			max = Math.max(max, distGo[i] + distBack[i]);
		System.out.println(max);
	}
	public static void dijkstra(int start, ArrayList<Pair>[] list, int[] d) {
		pq = new PriorityQueue<Pair>();
		pq.add(new Pair(start, 0));
		while (!pq.isEmpty()) {
			Pair curr = pq.poll();
			
			if (d[curr.v] > curr.w) {
				d[curr.v] = curr.w;
				for (int i = 0; i < list[curr.v].size(); i++)
					pq.add(new Pair(list[curr.v].get(i).v, d[curr.v] + list[curr.v].get(i).w));
			}
		}
	}
}
// 2020-10-11 02:12 해결
// 이어진 도로들을 정방향으로 이어놓은 경우, 
// 이 때는 돌아가는 길의 최단시간을 다익스트라로 구할 수 있다.
// 반대로 이어진 도로들을 입력한 방향의 반대로 이어놓은 경우,
// 이떄는 도착지점인 x에서 다익스트라를 실행시 구해지는 최솟값들이 의미하는 것은 다음과 같다.
// 모든 점에서 x로 도착하는 최단시간.
// 따라서,
// 정방향으로 이어놓은 도로의 다익스트라 => 집으로 돌아가는 길의 최단시간들
// 역방향으로 이어놓은 도로의 다익스트라 => x로 오는 길의 최단시간들.
// 따라서 이 두 최단시간을 학생들 끼리 합했을때, 가장 큰 값을 출력하면 된다.
