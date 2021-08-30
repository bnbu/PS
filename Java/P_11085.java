import java.util.*;	
import java.io.*;
public class Main {
	public static class Road implements Comparable<Road>{
		int s;
		int e;
		int w;
		public Road(int s, int e, int w) {
			this.s = s;
			this.e = e;
			this.w = w;
		}
		public int compareTo(Road r) {
			return r.w - this.w;
		}
	}
	public static int[] parent;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int p = Integer.parseInt(st.nextToken()),
			w = Integer.parseInt(st.nextToken());
		
		parent = new int[p];
		for (int i = 0; i < p; i++) 
			parent[i] = i;
		st = new StringTokenizer(br.readLine());
		int c = Integer.parseInt(st.nextToken()),
			v = Integer.parseInt(st.nextToken());
		
		int ans = 0;
		PriorityQueue<Road> pq = new PriorityQueue<>();
		while (w-- > 0) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken()),
				end = Integer.parseInt(st.nextToken()),
				width = Integer.parseInt(st.nextToken());
			
			pq.add(new Road(start, end, width));
		}
		
		while (!pq.isEmpty()) {
			Road curr = pq.poll();
			union(curr.s, curr.e);
			ans = curr.w;
			
			if (find(c) == find(v))
				break;
		}
		System.out.println(ans);
	}
	public static void union(int a, int b) {
		int x = find(a);
		int y = find(b);
		if (x != y)
			parent[y] = x;
	}
	public static int find(int a) {
		if (a == parent[a])
			return a;
		else
			return parent[a] = find(parent[a]);
	}
}
// 2021-01-02 02:17 해결
// 길의 너비를 내림차순으로 union 진행
// 이렇게 될 경우, 두 수도가 같은 find값을 갖는 경우, 즉 길이 연결되는 순간의 너비가 최소너비다.
