import java.util.*;
import java.io.*;
public class Main {
	public static int cnt, idx;
	public static int[] sccNum, discover;
	public static boolean[] sccChk;
	public static Stack<Integer> stack;
	public static ArrayList<Integer>[] adj;
	public static ArrayList<ArrayList<Integer>> scc;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		while (true) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken()),
				m = Integer.parseInt(st.nextToken());
			
			if (n == 0 && m == 0)
				break;
			
			sccNum = new int[2*m + 1];
			discover = new int[2*m + 1];
			sccChk = new boolean[2*m + 1];
			adj = new ArrayList[2*m + 1];
			for (int i = 1; i <= 2*m; i++) {
				adj[i] = new ArrayList<>();
				discover[i] = -1;
			}
			
			while (n-- > 0) {
				st = new StringTokenizer(br.readLine());
				int i = Integer.parseInt(st.nextToken()),
					j = Integer.parseInt(st.nextToken());
				
				int u = i < 0 ? m + Math.abs(i) : i,
					v = j < 0 ? m + Math.abs(j) : j;
				
				adj[not(u, m)].add(v);
				adj[not(v, m)].add(u);
			}
			
			tarjan(2*m);
			
			boolean chk = true;
			for (int i = 1; i <= m; i++)
				if (sccNum[i] == sccNum[i + m]) {
					chk = false;
					break;
				}
			
			if (chk)
				sb.append(1 + "\n");
			else
				sb.append(0 + "\n");
		}
		System.out.print(sb);
	}
	public static int not(int a, int m) {
		return a <= m ? a + m : a - m;
	}
	public static void tarjan(int n) {
		cnt = 0;
		idx = 0;
		stack = new Stack<>();
		scc = new ArrayList<>();
		for (int i = 1; i <= n; i++) 
			if (discover[i] == -1)
				dfs(i);
	}
	public static int dfs(int v) {
		int ret = discover[v] = cnt++;
		stack.push(v);
		for (int next : adj[v]) {
			if (discover[next] == -1)
				ret = Math.min(ret, dfs(next));
			else if (!sccChk[next])
				ret = Math.min(ret, discover[next]);
		}
		
		if (ret == discover[v]) {
			ArrayList<Integer> temp = new ArrayList<>();
			while (true) {
				int curr = stack.pop();
				sccChk[curr] = true;
				sccNum[curr] = idx;
				temp.add(curr);
				if (curr == v)
					break;
			}
			scc.add(temp);
			idx++;
		}
		return ret;
	}
}
// 2021-01-18 20:39
// 코사라주, 타잔 모두 메모리초과 당해서
// C++로 코사라주 쓴거 냈는데통과된거 보면(624ms), 자바당한게 맞다.
// 참고로 타잔은 C++로 냈을때 436ms

// 문제 자체는 2-SAT로

// 규칙의 두 증세가 모두 나타난다면, 사망
// 반대로 생존의 조건으로 보면, 두 증세중 하나만 나타나거나 모두 나타나지 않아야 한다

// 증세가 나타나지 않는 경우를 true로 치환시켜 볼때
// 이는 2-SAT로 나타내볼 수 있다.

// => SCC를 추출하지 않고 하는 방법으로 해볼 것 (2416번)
