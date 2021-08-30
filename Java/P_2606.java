import java.util.*;
import java.io.*;
public class Main {
	public static ArrayList<Integer>[] arr;
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws NumberFormatException, IOException {
		int n = Integer.parseInt(br.readLine()), m = Integer.parseInt(br.readLine());
		arr = new ArrayList[n + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<>();
		
		String[] s;
		while (m-- > 0) {
			s = br.readLine().split(" ");
			int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]);
			arr[x].add(y);
			arr[y].add(x);
		}
		
		bfs(1);
		System.out.println(sb);
	}
	public static void bfs(int v) {
		Queue<Integer> q = new LinkedList<>();
		boolean[] visit = new boolean[arr.length];
		
		q.add(v);
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			if (!visit[curr]) 
				visit[curr] = true;
			
			for (int i = 0; i < arr[curr].size(); i++) {
				int next = arr[curr].get(i);
				if (!visit[next])
					q.add(next);
			}
		}
		int cnt = 0;
		for (boolean b : visit)
			if (b)
				cnt++;
		sb.append(cnt - 1 + "\n");
	}
}
// 2020-10-07 23:00 해결
// bfs 돌릴까 dfs 돌릴까 고민함 ㅋㅋ
// bfs나 dfs 돌려서 1번을 제외한 나머지 노드에 몇개나 방문하는지 카운트
