import java.util.*;
import java.io.*;
public class Main {
	public static int[] trace;
	public static boolean[] visit;
	public static ArrayList<Integer>[] arr;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		
		trace = new int[t + 1];
		visit = new boolean[t + 1];
		arr = new ArrayList[t + 1];
		for (int i = 1; i <= t; i++)
			arr[i] = new ArrayList<>();
		for (int i = 0; i < t - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()),
				v = Integer.parseInt(st.nextToken());
			
			arr[u].add(v);
			arr[v].add(u);
		}
		bfs(1);
		
		for (int i = 2; i <= t; i++) {
			System.out.println(trace[i]);
		}
	}
	public static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(1);
		visit[1] = true;
		while(!q.isEmpty()) {
			int curr = q.poll();
			for (int i = 0; i < arr[curr].size(); i++) {
				if (!visit[arr[curr].get(i)]) {
					q.add(arr[curr].get(i));
					trace[arr[curr].get(i)] = curr;
					visit[arr[curr].get(i)] = true;
				}
			}
		}
	}
}
// 2020-11-07 21:04 해결
// 트리 구조를 직접 만들어보려 하긴 했는데, 간선 정보가 연결된 순서로 주는게 아니라 포기
