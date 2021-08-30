import java.util.*;
import java.io.*;
public class Main {
	public static boolean[] visited;
	public static ArrayList<Integer>[] arr;
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]), v = Integer.parseInt(s[2]);
		arr = new ArrayList[n + 1];
		visited = new boolean[n + 1];
		for (int i = 1; i < arr.length; i++)
			arr[i] = new ArrayList<Integer>();
		
		while (m-- > 0) {
			s = br.readLine().split(" ");
			int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]);
			arr[x].add(y);
			arr[y].add(x);
		}
		
		for (int i = 1; i < arr.length; i++)
			Collections.sort(arr[i]);
		
		dfs(v);
		sb.append("\n");
		bfs(v);
		
		System.out.println(sb);
	}
	public static void dfs(int v) {
		if (visited[v])
			return;
		
		sb.append(v + " ");
		visited[v] = true;
		
		for (int i : arr[v])
			dfs(i);
	}
	public static void dfs_stack(int v) {
		Stack<Integer> stack = new Stack<>();
		boolean[] visit = new boolean[arr.length];
		
		stack.push(v);
		
		while (!stack.isEmpty()) {
			int curr = stack.pop();
			if (!visit[curr]) {
				sb.append(curr + " ");
				visit[curr] = true;
			}
			
			for (int i = 0; i < arr[curr].size(); i++) {
				int next = arr[curr].get(i);
				if (!visit[next])
					stack.push(next);
			}
		}
	}
	public static void bfs(int v) {
		Queue<Integer> q = new LinkedList<>();
		boolean[] visit = new boolean[arr.length];
		
		q.add(v);
		
		while (!q.isEmpty()) {
			int curr = q.poll();
			if (!visit[curr]) {
				sb.append(curr + " ");
				visit[curr] = true;
			}
			
			for (int i = 0; i < arr[curr].size(); i++) {
				int next = arr[curr].get(i);
				if (!visit[next])
					q.add(next);
			}
		}
	}
}	
// 2020-10-07 22:47 해결
// => dfs와 bfs. dfs는 간단하기 재귀함수로도 가능하지만 stack을 이용한 것도 알아두자.
// 왜냐하면, stack을 사용한 dfs에서 stack을 queue로 바꾸면 bfs임.
